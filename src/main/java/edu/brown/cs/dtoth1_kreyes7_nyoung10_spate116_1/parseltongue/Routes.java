package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import com.google.common.collect.ImmutableMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselCommands;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselDB;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankVertex;
import org.apache.pdfbox.multipdf.PDFCloneUtility;
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
import java.util.Comparator;
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
      if (isLogged(req)) {
        res.redirect("/dashboard");
      }
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
    ParselDB.UserSchema ret = ParselDB.getUserByID(username);
    if (ret != null && ret.getPassword().equals("" + password.hashCode())) {
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
      String username = req.queryParams("username");
      String password = req.queryParams("password");
      // Check username hasn't been used yet
      ParselDB.UserSchema ret = ParselDB.getUserByID(username);
      if (ret != null) {
        req.session().attribute("error", "Email is invalid or already in use.");
        res.redirect("/error");
      } else {
        // TODO: Better password encryption
        ParselDB.UserSchema newUser = new ParselDB.UserSchema(username, "" + password.hashCode());
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
   */
  public static class GETDashHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String logged = currentUser(req);
      List<ParselDB.QuerySchema> queryObjects = ParselDB.getQueriesByUser(logged);
      String queries = formatQueries(queryObjects);
      List<ParselDB.PDFSchema> pdfObjects = ParselDB.getPDFsByUser(logged);
      String pdfs = formatPDFs(pdfObjects);
      Map<String, Object> variables =
          ImmutableMap.of("loggedIn", logged, "pdfs", pdfs, "queries", queries);
      return new ModelAndView(variables, "dashboard.ftl");
    }
  }

  /**
   * Handles POST requests to the /download/:pdf_id route.
   */
  public static class GETDownloadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String pdf_id = req.params("pdf_id");
      String logged = currentUser(req);
      ParselDB.PDFSchema pdf = ParselDB.getPDFByID(pdf_id);
      if (pdf != null && pdf.getUser().equals(logged)) {
        try {
          res.header("Content-disposition",
              String.format("attachment; filename=%s;", pdf.getFilename()));
          OutputStream outputStream = res.raw().getOutputStream();
          outputStream.write(pdf.getData());
          outputStream.flush();
        } catch (Exception e) {
          System.err.println("ERROR: " + e.getMessage());
        }
        res.redirect("/dashboard");
      } else {
        req.session().attribute("error", "PDF doesn't exist or malformed PDF.");
        res.redirect("/error");
      }
      return null;
    }
  }

  /**
   * Handles POST requests to the /delete/:pdf_id route.
   */
  public static class GETDeleteHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String pdf_id = req.params("pdf_id");
      String logged = currentUser(req);
      ParselDB.PDFSchema pdf = ParselDB.getPDFByID(pdf_id);
      if (pdf != null && pdf.getUser().equals(logged)) {
        ParselDB.removePDFByID(pdf_id);
        res.redirect("/dashboard");
      } else {
        req.session().attribute("error", "PDF doesn't exist or malformed PDF.");
        res.redirect("/error");
      }
      return null;
    }
  }

  /**
   * Handles GET requests to the /upload route.
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
   * TODO: Multiple files
   */
  public static class POSTUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Set form datatype
      req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      // Save PDF to database
      try (InputStream is = req.raw().getPart("file").getInputStream()) {
        byte[] fileContent = is.readAllBytes();
        // TODO: Better IDs.
        String pdf_id = req.session().attribute("logged") + ("_f_" + (int) (Math.random() * 999999999));
        String filename = req.raw().getPart("file").getSubmittedFileName();
        ParselDB.PDFSchema newPDF =
            new ParselDB.PDFSchema(pdf_id, req.session().attribute("logged"), filename, fileContent);
        ParselDB.updatePDF(newPDF);
        res.redirect("/dashboard");
      } catch (Exception e) {
        System.err.println("ERROR: Bad File Upload at /upload");
        req.session().attribute("error", "Bad File Upload at /upload");
        res.redirect("/error");
      }
      return null;
    }
  }

  /**
   * Handles GET requests to the /query route.
   */
  public static class GETQueryHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String logged = currentUser(req);
      List<ParselDB.PDFSchema> pdfObjects = ParselDB.getPDFsByUser(logged);
      StringBuilder pdfs = new StringBuilder();
      for (ParselDB.PDFSchema pdf : pdfObjects) {
        pdfs.append(String.format(
            "<input type=\"checkbox\" id=\"%s\" name=\"pdf\" value=\"%s\"><label for=\"%s\">%s</label><br/>",
            pdf.getId(), pdf.getId(), pdf.getId(), pdf.getFilename()));
      }
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged, "pdfs", pdfs.toString());
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   * Handles POST requests to the /query route.
   * // TODO: Do this
   */
  public static class POSTQueryHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String queryString = req.queryParams("keywords");
      String[] fileStrings = req.queryMap().toMap().get("pdf");
      if (fileStrings == null) {
        req.session().attribute("error", "Please select at least one PDF.");
        res.redirect("/error");
        return null;
      }
      List<String> files = new ArrayList<>();
      for (String fileString : fileStrings) {
        try {
          byte[] pdfData = ParselDB.getPDFByID(fileString).getData();
          String filePath = "temp/" + fileString + ".pdf";
          FileOutputStream fileOutputStream = new FileOutputStream(filePath);
          fileOutputStream.write(pdfData);
          files.add(filePath);
        } catch (Exception e) {
          System.err.println("ERROR: " + e.getMessage());
        }
      }
      RankGraph graph = ParselCommands.parsel(files, queryString);
      // TODO: Better IDs.
      String logged = req.session().attribute("logged");
      String query_id = logged + ("_q_" + (int) (Math.random() * 999999999));
      ParselDB.QuerySchema queryObject = new ParselDB.QuerySchema(query_id, logged, queryString, files);
      ParselDB.updateQuery(queryObject);
      processSnippets(graph.getVertices(), query_id);
      res.redirect("/query/" + query_id);
      return null;
    }
  }

  /**
   * Handles GET requests to the /query/:query_id route.
   * TODO: Check that pdf belongs to the current user.
   * TODO: limit # per page
   * TODO: get similar
   * TODO: find other things that pagerank gives for free
   */
  public static class GETQueryViewHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String logged = currentUser(req);
      String query_id = req.params(":query_id");
      ParselDB.QuerySchema query = ParselDB.getQueryByID(query_id);
      List<ParselDB.SnippetSchema> snippets = ParselDB.getSnippetsByQuery(query_id);
      if (snippets.size() == 0) {
        req.session().attribute("error", "PDF doesn't exist or malformed PDF.");
        res.redirect("/error");
      }
      String formattedSnippets = formatSnippets(snippets);
      Map<String, Object> variables =
          ImmutableMap.of("loggedIn", logged,
              "snippets", formattedSnippets,
              "query", query.getQueryString());
      return new ModelAndView(variables, "view.ftl");
    }
  }

  /**
   * Handles POST requests to the /query/delete/:query_id route.
   */
  public static class GETQueryDeleteHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (!isLogged(req)) {
        res.redirect("/");
      }
      String query_id = req.params("query_id");
      String logged = currentUser(req);
      ParselDB.QuerySchema query = ParselDB.getQueryByID(query_id);
      if (query != null && query.getUser().equals(logged)) {
        ParselDB.removeQueryByID(query_id);
        ParselDB.removeSnippetsByQuery(query_id);
        res.redirect("/dashboard");
      } else {
        req.session().attribute("error", "Query doesn't exist or malformed Query.");
        res.redirect("/error");
      }
      return null;
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
   * Format queries.
   * @param queries  List of pdfs.
   */
  private static String formatQueries(List<ParselDB.QuerySchema> queries) {
    StringBuilder ret = new StringBuilder();
    for (ParselDB.QuerySchema query : queries) {
      ret.append("<div class=\"query\">");
      ret.append(String.format("<a href=\"/query/%s\">", query.get_id()));
      ret.append("\"" + query.getQueryString() + "\"");
      ret.append(String.format("</a><a class=\"delete\" href=\"/query/delete/%s\">", query.get_id()));
      ret.append("delete");
      ret.append("</a></div>");
    }
    return ret.toString();
  }

  /**
   * Format pdfs.
   * @param pdfs  List of pdfs.
   */
  private static String formatPDFs(List<ParselDB.PDFSchema> pdfs) {
    StringBuilder ret = new StringBuilder();
    for (ParselDB.PDFSchema pdf : pdfs) {
      ret.append("<div class=\"pdf\">");
      ret.append(String.format("<a href=\"/download/%s\">", pdf.getId()));
      ret.append("<img src=\"https://img.icons8.com/dotty/80/000000/pdf-2.png\"/>");
      ret.append("<br/>");
      ret.append(pdf.getFilename());
      ret.append("</a>");
      ret.append("<br/><br/>");
      ret.append(String.format("<a class=\"delete\" href=\"/delete/%s\">", pdf.getId()));
      ret.append("delete");
      ret.append("</a></div>");
    }
    return ret.toString();
  }


  /**
   * Format snippets.
   * @param snippets  List of snippets.
   */
  private static String formatSnippets(List<ParselDB.SnippetSchema> snippets) {
    List<ParselDB.SnippetSchema> list = new ArrayList<>();
    double maxScore = 0;
    for (ParselDB.SnippetSchema snippet : snippets){
      list.add(new ParselDB.SnippetSchema("", snippet.getScore(), snippet.getContent()));
      maxScore = Math.max(maxScore, snippet.getScore());
    }

    snippets.sort(Comparator.comparingDouble(ParselDB.SnippetSchema::getScore).reversed());

    StringBuilder ret = new StringBuilder();
    // Capped at 100
    // TODO: Make this a variable, make dynamic pageviews
    // TODO: Remove/fix normalize
    for (int i = 0; i < Math.min(100, snippets.size()); i++) {
      ret.append("<div class=\"snippet\"><div class=\"snippet-score\">Score: ");
      ret.append(snippets.get(i).getScore() / (maxScore / 100));
      ret.append("</div>");
      ret.append(snippets.get(i).getContent());
      ret.append("</div>");
    }
    return ret.toString();
  }

  /**
   * Process snippets - upload them to DB and return a list of Strings
   * @param vertices  snippets.
   */
  private static void processSnippets(List<RankVertex> vertices, String query_id) {
    for (RankVertex v : vertices) {
      ParselDB.SnippetSchema newSnippet = new ParselDB.SnippetSchema(
          query_id, v.getScore(), v.getValue().getSnippet().getOriginalText());
      ParselDB.updateSnippet(newSnippet);
    }
  }
}
