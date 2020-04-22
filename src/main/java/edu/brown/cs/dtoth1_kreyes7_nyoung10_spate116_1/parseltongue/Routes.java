package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import com.google.common.collect.ImmutableMap;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselCommands;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselDB;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankVertex;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Routes class! Holds and handles all web server routing.
 */
public class Routes {

  /**
   * Private Constructor.
   */
  private Routes() {
  }

  /**
   * Handle GET requests to the landing page.
   */
  public static class GETMainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged = currentUser(req);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "index.ftl");
    }
  }

  /**
   * Login Callback.
   * @param req Req.
   * @param res Res.
   * @return  null.
   */
  public static Object POSTLoginHandler(Request req, Response res) {
    String username = req.queryParams("username");
    String password = req.queryParams("password");
    // Check if username-password combination is valid
    DBCursor ret = ParselDB.getUserByIDPW(username, password);
    if (ret.count() == 1) {
      req.session().attribute("logged", username);
      res.redirect("/dashboard");
    } else {
      req.session().attribute("error", "Invalid Login.");
      res.redirect("/error");
    }
    return null;
  }

  /**
   * Logout Callback.
   * @param req Req.
   * @param res Res.
   * @return  null.
   */
  public static Object GETLogoutHandler(Request req, Response res) {
    req.session().removeAttribute("logged");
    res.redirect("/");
    return null;
  }

  /**
   * Handle GET requests to the registration page.
   */
  public static class GETRegisterHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (isLogged(req)) {
        res.redirect("/dashboard");
      }
      String logged = currentUser(req);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "register.ftl");
    }
  }

  /**
   * Handle GET requests to the registration page.
   */
  public static class POSTRegisterHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // TODO: Better password encryption
      String username = req.queryParams("username");
      String password = req.queryParams("password");
      // Check username hasn't been used yet
      DBCursor ret = ParselDB.getUserByID(username);
      if (ret.count() >= 1) {
        req.session().attribute("error", "Email is invalid or already in use.");
        res.redirect("/error");
      } else {
        ParselDB.UserSchema newUser = new ParselDB.UserSchema(username, password);
        ParselDB.updateUser(newUser);
        System.out.println("New user created!");
        Map<String, Object> variables = ImmutableMap.of("loggedIn", "0");
        return new ModelAndView(variables, "success.ftl");
      }
      return null;
    }
  }

  /**
   * Handles GET requests to the /dashboard route.
   * TODO: delete PDFs
   */
  public static class GETDashHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String logged = currentUser(req);
      DBCursor ret = ParselDB.getPDFByUser(logged);
      String pdfs = formatPDFs(ret);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged, "pdfs", pdfs);
      return new ModelAndView(variables, "dashboard.ftl");
    }
  }

  /**
   * Handles GET requests to the /upload route.
   * TODO: Multiple files.
   */
  public static class GETUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String logged = currentUser(req);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "upload.ftl");
    }
  }

  /**
   * Handles POST requests to the /upload route.
   * TODO: Make loading screen.
   */
  public static class POSTUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Set form datatype
      req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      // Get file input stream, convert to byte stream and write to temp folder.
      try (InputStream is = req.raw().getPart("file").getInputStream()) {
        String filename = req.raw().getPart("file").getSubmittedFileName();
        String query = req.queryParams("query");
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        String filePath = String.format("temp/%s.pdf", filename.hashCode());
        File targetFile = new File(filePath);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

        // Save PDF to DB
        // TODO: Ensure unique ID.
        // TODO: Save bytestream of PDF
        String pdf_id = req.session().attribute("logged") + ("_" + (int) (Math.random() * 9999999));
        ParselDB.PDFSchema newPDF =
            new ParselDB.PDFSchema(pdf_id, req.session().attribute("logged"), filename, query);
        ParselDB.updatePDF(newPDF);

        // Run parsel command
        List<String> paths = new ArrayList<>();
        paths.add(filePath);
        RankGraph graph = ParselCommands.parsel(paths, req.queryParams("query"));
        List<RankVertex> vertices = graph.getVertices();

        // TODO: Save Graph
        // Process snippets, head to view.
        processSnippets(vertices, pdf_id);
        targetFile.delete();
        res.redirect("/snippets/" + pdf_id);
      } catch (Exception e) {
        System.err.println("ERROR: Bad File Upload at /upload");
        req.session().attribute("error", "Bad File Upload at /upload");
        res.redirect("/error");
      }
      return null;
    }
  }

  /**
   * Handles GET requests to the /snippets/:pdf_id route.
   * TODO: Check that pdf belongs to the current user.
   */
  public static class GETSnippetsHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String logged = currentUser(req);
      String pdf_id = req.params(":pdf_id");
      DBCursor ret = ParselDB.getSnippetsByPDF(pdf_id);
      if (ret.count() == 0) {
        req.session().attribute("error", "PDF doesn't exist.");
        res.redirect("/error");
      }
      String snippets = formatSnippets(ret);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged, "snippets", snippets);
      return new ModelAndView(variables, "view.ftl");
    }
  }

  /**
   * Handles GET requests to the /error route.
   */
  public static class GETErrorHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged = currentUser(req);
      String errorMessage = req.session().attribute("error");
      if (errorMessage == null) {
        errorMessage = "No error.";
      }
      req.session().removeAttribute("error");
      Map<String, Object> variables =
          ImmutableMap.of("errorMessage", errorMessage, "loggedIn", logged);
      return new ModelAndView(variables, "error.ftl");
    }
  }

  /**
   * Method to check if a user is logged in.
   * @param req Request.
   * @return  false if not logged in, true if they are.
   */
  private static boolean isLogged(Request req) {
    String status = req.session().attribute("logged");
    return status != null && !status.equals("0");
  }

  /**
   * Method to check which user is logged in.
   * // TODO: Fix this 0 issue
   * @param req Request.
   * @return  "0" if not logged in, the username if they are.
   */
  private static String currentUser(Request req) {
    if (req.session().attribute("logged") == null) {
      return "0";
    } else {
      return req.session().attribute("logged");
    }
  }

  /**
   * Format snippets.
   * TODO: Cap number, Links, Delete
   * @param pdfs  List of pdfs.
   */
  private static String formatPDFs(DBCursor pdfs) {
    StringBuilder ret = new StringBuilder();
    while (pdfs.hasNext()) {
      ret.append("<div class=\"pdf\">");
      DBObject pdf = pdfs.next();
      ret.append(String.format("<a href=\"/snippets/%s\">", pdf.get("_id")));
      ret.append(pdf.get("filename"));
      ret.append("</a></div>");
    }
    return ret.toString();
  }


  /**
   * Format snippets.
   * TODO: Cap number, Create links, metadata, ranked???.
   * @param snippets  List of snippets.
   */
  private static String formatSnippets(DBCursor snippets) {
    StringBuilder ret = new StringBuilder();
    // TODO: Rank them
    while (snippets.hasNext()) {
      ret.append("<div class=\"snippet\">");
      DBObject snippet = snippets.next();
      ret.append(snippet.get("content"));
      ret.append("</div>");
    }
    return ret.toString();
  }

  /**
   * Process snippets - upload them to DB and return a list of Strings
   * @param vertices  snippets.
   */
  private static void processSnippets(List<RankVertex> vertices, String pdf_id) {
    for (RankVertex v : vertices) {
      ParselDB.SnippetSchema newSnippet =
          new ParselDB.SnippetSchema(pdf_id, v.getScore() * 1000.0, v.getValue().getSnippet().getOriginalText());
      ParselDB.updateSnippet(newSnippet);
    }
  }
}
