package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselCommands;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselDB;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankVertex;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Routes class! Holds and handles all web server routing.
 */
public final class Routes {

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
      // Redirect to dashboard if logged in.
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
   *
   * @param req Req.
   * @param res Res.
   * @return null.
   */
  public static Object postLoginHandler(Request req, Response res) {
    // Get user input
    String username = req.queryParams("username");
    String password = req.queryParams("password");
    // Check if username-password combination is valid.
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
   *
   * @param req Req.
   * @param res Res.
   * @return null.
   */
  public static Object getLogoutHandler(Request req, Response res) {
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
      // Redirect to dashboard if logged in.
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
      // Get user input.
      String username = req.queryParams("username");
      String password = req.queryParams("password");
      // Check username hasn't been used yet
      ParselDB.UserSchema ret = ParselDB.getUserByID(username);
      if (ret != null) {
        req.session().attribute("error", "Email is invalid or already in use.");
        res.redirect("/error");
      } else {
        // Create user schema, update database.
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
      // Redirect to homepage if not logged in.
      if (!isLogged(req)) {
        res.redirect("/");
      }
      // Get and format user data.
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
      // Redirect to homepage if not logged in.
      if (!isLogged(req)) {
        res.redirect("/");
      }
      // Get requested PDF
      String pdfId = req.params("pdf_id");
      String logged = currentUser(req);
      ParselDB.PDFSchema pdf = ParselDB.getPDFByID(pdfId);
      // If PDF belongs to the current user, download it.
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
      // Redirect to homepage if not logged in.
      if (!isLogged(req)) {
        res.redirect("/");
      }
      // Get requested PDF
      String pdfId = req.params("pdf_id");
      String logged = currentUser(req);
      ParselDB.PDFSchema pdf = ParselDB.getPDFByID(pdfId);
      // If PDF belongs to the current user, delete it.
      if (pdf != null && pdf.getUser().equals(logged)) {
        ParselDB.removePDFByID(pdfId);
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
      // Redirect to homepage if not logged in.
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
   * TODO: Better IDs.
   */
  public static class POSTUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Set form datatype
      req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      // Save PDFs object and data to database
      try {Collection<Part> files = req.raw().getParts();
      for (Part file : files) {
          InputStream is = file.getInputStream();
          byte[] fileContent = is.readAllBytes();
          String pdfId =
              req.session().attribute("logged") + ("_f_" + (int) (Math.random() * 999999999));
          String filename = file.getSubmittedFileName();
          ParselDB.PDFSchema newPDF = new ParselDB.PDFSchema(
              pdfId, req.session().attribute("logged"), filename, fileContent);
          ParselDB.updatePDF(newPDF);
          is.close();
        }
        res.redirect("/dashboard");
      } catch (Exception e) {
        System.err.println("ERROR: Bad File Upload at /upload: " + e.getMessage());
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
      // Redirect to homepage if not logged in.
      if (!isLogged(req)) {
        res.redirect("/");
      }
      // Get and format snippets.
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
   * TODO: Better IDs.
   */
  public static class POSTQueryHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Get and verify user input.
      String labelString = req.queryParams("label");
      String queryString = req.queryParams("keywords");
      String[] fileStrings = req.queryMap().toMap().get("pdf");
      if (fileStrings == null) {
        req.session().attribute("error", "Please select at least one PDF.");
        res.redirect("/error");
        return null;
      }
      // Download all files to temp storage.
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
      // Create graph, run PageRank
      RankGraph graph = ParselCommands.parsel(files, queryString);
      // Create and upload query object.
      String logged = req.session().attribute("logged");
      String queryId = logged + ("_q_" + (int) (Math.random() * 999999999));
      ParselDB.QuerySchema queryObject =
          new ParselDB.QuerySchema(queryId, logged, labelString, queryString, files);
      ParselDB.updateQuery(queryObject);
      // Process snippets.
      processSnippets(graph.getVertices(), queryId);
      // Delete files.
      for (String file : files) {
        File f = new File(file);
        f.delete();
      }
      res.redirect("/query/" + queryId);
      return null;
    }
  }

  /**
   * Handles GET requests to the /query/:query_id route.
   */
  public static class GETQueryViewHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Redirect to home page if not logged in.
      if (!isLogged(req)) {
        res.redirect("/");
      }
      // Get query and snippets, check for validity.
      String logged = currentUser(req);
      String queryId = req.params(":query_id");
      ParselDB.QuerySchema query = ParselDB.getQueryByID(queryId);
      List<ParselDB.SnippetSchema> snippets = ParselDB.getSnippetsByQuery(queryId);
      if (snippets.size() == 0 || query == null || !query.getUser().equals(logged)) {
        req.session().attribute("error", "PDF doesn't exist or malformed PDF.");
        res.redirect("/error");
      }
      // Format snippets and send to user.
      String formattedSnippets = formatSnippets(snippets);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged, "snippets",
          formattedSnippets, "label", query.getLabel(), "query", query.getQueryString());
      return new ModelAndView(variables, "view.ftl");
    }
  }

  /**
   * Handles POST requests to the /query/delete/:query_id route.
   */
  public static class GETQueryDeleteHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Redirect to home page if not logged in.
      if (!isLogged(req)) {
        res.redirect("/");
      }
      // Get query and user
      String queryId = req.params("query_id");
      String logged = currentUser(req);
      ParselDB.QuerySchema query = ParselDB.getQueryByID(queryId);
      // If query exists and belongs to the current user, delete from DB.
      if (query != null && query.getUser().equals(logged)) {
        ParselDB.removeQueryByID(queryId);
        ParselDB.removeSnippetsByQuery(queryId);
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
      // Get current user and current error message.
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
   *
   * @param req Request.
   * @return false if not logged in, true if they are.
   */
  private static boolean isLogged(Request req) {
    String status = req.session().attribute("logged");
    return status != null && !status.equals("0");
  }

  /**
   * Method to check which user is logged in.
   * // TODO: Better unlogged management.
   *
   * @param req Request.
   * @return "0" if not logged in, the username if they are.
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
   *
   * @param queries List of pdfs.
   */
  private static String formatQueries(List<ParselDB.QuerySchema> queries) {
    StringBuilder ret = new StringBuilder();
    for (ParselDB.QuerySchema query : queries) {
      ret.append("<div class=\"query\">");
      ret.append(String.format("<a href=\"/query/%s\">", query.getId()));
      ret.append(query.getLabel());
      ret.append(String.format("</a><a class=\"delete\" href=\"/query/delete/%s\">", query.getId()));
      ret.append("delete");
      ret.append("</a></div>");
    }
    return ret.toString();
  }

  /**
   * Format pdfs.
   *
   * @param pdfs List of pdfs.
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
   * TODO: Normalize?
   *
   * @param snippets List of snippets.
   */
  private static String formatSnippets(List<ParselDB.SnippetSchema> snippets) {
    // Get max score for normalizing.
    double maxScore = 0;
    for (ParselDB.SnippetSchema snippet : snippets) {
      maxScore = Math.max(maxScore, snippet.getScore());
    }
    // Sort snippets by score
    snippets.sort(Comparator.comparingDouble(ParselDB.SnippetSchema::getScore).reversed());
    // Set to contain filenames to minimize DB calls.
    Map<String, String> filenames = new HashMap<>();
    // Capped at 100 snippets returned.
    StringBuilder ret = new StringBuilder();
    for (int i = 0; i < Math.min(50, snippets.size()); i++) {
      // Get filename
      String fileId = snippets.get(i).getFile().substring(0, snippets.get(i).getFile().length()-4);
      String filename;
      if (filenames.get(fileId) != null) {
        filename = filenames.get(fileId);
      } else {
        filename = ParselDB.getPDFByID(fileId).getFilename();
        filenames.put(fileId, filename);
      }
      // Construct snippet.
      ret.append("<div class=\"snippet\"><div class=\"snippet-score\">Score: ");
      ret.append(snippets.get(i).getScore() / (maxScore / 100));
      ret.append("</div><div class=\"snippet-score\">Source: ");
      ret.append(filename);
      ret.append(", pg. ");
      ret.append(snippets.get(i).getPage());
      ret.append("</div>");
      ret.append(snippets.get(i).getContent());
      ret.append("</div>");
    }
    return ret.toString();
  }

  /**
   * Process snippets - upload them to DB and return a list of Strings.
   *
   * @param vertices snippets.
   */
  private static void processSnippets(List<RankVertex> vertices, String queryId) {
    // Add all snippets to DB.
    for (RankVertex v : vertices) {
      Snippet curr = v.getValue().getSnippet();
      ParselDB.SnippetSchema newSnippet = new ParselDB.SnippetSchema(
          queryId, curr.getOriginalText(), curr.getFileName(), v.getScore(), curr.getPageNum());
      ParselDB.updateSnippet(newSnippet);
    }
  }
}
