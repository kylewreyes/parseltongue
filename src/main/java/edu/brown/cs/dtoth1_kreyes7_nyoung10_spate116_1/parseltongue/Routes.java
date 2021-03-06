package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import com.google.common.collect.ImmutableMap;

import com.google.gson.Gson;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb.PDFSchema;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb.QuerySchema;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb.SnippetSchema;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb.UserSchema;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselCommands;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb.ParselDB;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankVertex;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
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
  private static final Gson GSON = new Gson();
  private static final int NUM_ADJ = 5;
  private static final int ID_CONSTANT = 999999999;
  private static final int MAX_SNIPPETS = 50;
  private static final int NORMALIZE = 100;

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
    UserSchema ret = ParselDB.getUserByID(username);
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
      UserSchema ret = ParselDB.getUserByID(username);
      if (ret != null) {
        req.session().attribute("error", "Email is invalid or already in use.");
        res.redirect("/error");
      } else {
        // Create user schema, update database.
        // to do: Better password encryption
        UserSchema newUser = new UserSchema(username, "" + password.hashCode());
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
      List<QuerySchema> queryObjects = ParselDB.getQueriesByUser(logged);
      String queries = formatQueries(queryObjects);
      List<PDFSchema> pdfObjects = ParselDB.getPDFsByUser(logged);
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
      PDFSchema pdf = ParselDB.getPDFByID(pdfId);
      // If PDF belongs to the current user, download it.
      if (pdf != null && pdf.getUser().equals(logged)) {
        try {
          res.header("Content-disposition",
              String.format("attachment; filename=\"%s\";", pdf.getFilename()));
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
      PDFSchema pdf = ParselDB.getPDFByID(pdfId);
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
   */
  public static class POSTUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // Set form datatype
      req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      // Save PDFs object and data to database
      try {
        Collection<Part> files = req.raw().getParts();
        for (Part file : files) {
          InputStream is = file.getInputStream();
          byte[] fileContent = is.readAllBytes();
          String pdfId =
              req.session().attribute("logged") + ("_f_" + (int) (Math.random() * ID_CONSTANT));
          String filename = file.getSubmittedFileName();
          PDFSchema newPDF = new PDFSchema(
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
      List<PDFSchema> pdfObjects = ParselDB.getPDFsByUser(logged);
      StringBuilder pdfs = new StringBuilder();
      for (PDFSchema pdf : pdfObjects) {
        pdfs.append(String.format(
            "<input type=\"checkbox\" id=\"%s\" name=\"pdf\" value=\"%s\">"
                + "<label for=\"%s\">%s</label><br/>",
            pdf.getId(), pdf.getId(), pdf.getId(), pdf.getFilename()));
      }
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged, "pdfs", pdfs.toString());
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   * Handles POST requests to the /query route.
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
          PDFSchema pdf = ParselDB.getPDFByID(fileString);
          if (pdf == null) {
            req.session().attribute("error", "Malformed or missing PDF.");
            res.redirect("/error");
            return null;
          }
          byte[] pdfData = pdf.getData();
          String filePath = "temp/" + fileString + ".pdf";
          FileOutputStream fileOutputStream = new FileOutputStream(filePath);
          fileOutputStream.write(pdfData);
          fileOutputStream.close();
          files.add(filePath);
        } catch (Exception e) {
          for (String file : files) {
            File f = new File(file);
            if (f.delete()) {
              System.out.println(file + " deleted successfully.");
            } else {
              System.out.println(file + " deleted unsuccessfully.");
            }
          }
          System.err.println("ERROR: " + e.getMessage());
        }
      }
      // Create graph, run PageRank, convert to byte[]
      RankGraph graph = ParselCommands.parsel(files, queryString);
      if (graph == null) {
        req.session().attribute("error", "Malformed graph.");
        res.redirect("/error");
        return null;
      }
      // Delete files.
      for (String file : files) {
        File f = new File(file);
        if (f.delete()) {
          System.out.println(file + " deleted successfully.");
        } else {
          System.out.println(file + " deleted unsuccessfully.");
        }
      }
      byte[] graphData = RankGraph.objToBytes(graph);
      // Create and upload query object.
      String logged = req.session().attribute("logged");
      String queryId = logged + ("_q_" + (int) (Math.random() * ID_CONSTANT));
      QuerySchema queryObject =
          new QuerySchema(queryId, logged, labelString, queryString, graphData, files);
      ParselDB.updateQuery(queryObject);
      // Process snippets.
      processSnippets(graph.getVertices(), queryId);
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
      QuerySchema query = ParselDB.getQueryByID(queryId);
      if (query == null) {
        req.session().attribute("error", "Malformed query.");
        res.redirect("/error");
        return null;
      }
      List<SnippetSchema> snippets = ParselDB.getSnippetsByQuery(queryId);
      if (snippets.size() == 0 || !query.getUser().equals(logged)) {
        req.session().attribute("error", "PDF doesn't exist or malformed PDF.");
        res.redirect("/error");
      }
      // Get keywords
      StringBuilder keywords = new StringBuilder();
      RankGraph graph = RankGraph.byteToObj(query.getData());
      if (graph != null) {
        List<String> keys = new ArrayList<>(graph.getCurrentKeywords().keySet());
        for (String key : keys) {
          keywords.append(String.format(
              "<input type='checkbox' class='keyword-toggle' value='%s' onclick='highlight(this)'>",
              key));
          keywords.append(String.format("<label for='%s'>%s</label", key, key));
          keywords.append("</input><br/>");
        }
      }
      // Format snippets and send to user.
      String formattedSnippets = formatSnippets(snippets);
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged, "snippets",
          formattedSnippets, "label", query.getLabel(), "query", query.getQueryString(),
          "keywords", keywords.toString());
      return new ModelAndView(variables, "view.ftl");
    }
  }

  /**
   * Handles post requests for getting adjacent snippets.
   */
  public static class POSTAdjacentSnippetsHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      // Get snippet data
      QueryParamsMap qm = req.queryMap();
      String snippetId = qm.value("snippetId");
      String queryId = qm.value("queryId");
      // Get query and graph from db, get closest vertices to given snippet
      QuerySchema query = ParselDB.getQueryByID(queryId);
      if (query == null) {
        req.session().attribute("error", "Malformed query.");
        res.redirect("/error");
        return null;
      }
      RankGraph graph = RankGraph.byteToObj(query.getData());
      if (graph == null) {
        req.session().attribute("error", "Malformed graph.");
        res.redirect("/error");
        return null;
      }
      RankVertex vertex = graph.getVertex(snippetId);
      List<RankVertex> vertices = vertex.getTopAdj(NUM_ADJ);
      // Set to contain filenames to minimize DB calls.
      Map<String, String> filenames = new HashMap<>();
      // Construct JSON return.
      List<Map<String, Object>> ret = new ArrayList<>();
      for (RankVertex v : vertices) {
        String content = v.getValue().getSnippet().getOriginalText();
        String page = "" + v.getValue().getSnippet().getPageNum();
        String fileId = v.getValue().getSnippet().getFileName()
            .substring(0, v.getValue().getSnippet().getFileName().length() - 4);
        String filename;
        if (filenames.get(fileId) != null) {
          filename = filenames.get(fileId);
        } else {
          PDFSchema pdf = ParselDB.getPDFByID(fileId);
          if (pdf == null) {
            filename = "[MISSING PDF]";
          } else {
            filename = pdf.getFilename();
          }
          filenames.put(fileId, filename);
        }
        // Make map
        Map<String, Object> vertexMap =
            ImmutableMap.of("content", content, "filename", filename, "page", page);
        ret.add(vertexMap);
      }
      // GSON convert, return
      Map<String, List<Map<String, Object>>> variables;
      variables = ImmutableMap.of("result", ret);
      return GSON.toJson(variables);
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
      QuerySchema query = ParselDB.getQueryByID(queryId);
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
   * // to do: Better unlogged management.
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
  private static String formatQueries(List<QuerySchema> queries) {
    StringBuilder ret = new StringBuilder();
    for (QuerySchema query : queries) {
      ret.append("<div class=\"query\">");
      ret.append(String.format("<a href=\"/query/%s\">", query.getId()));
      ret.append(query.getLabel());
      ret.append(String.format(
          "</a><a class=\"delete\" href=\"/query/delete/%s\" onclick=\"loading()\">", query.getId()));
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
  private static String formatPDFs(List<PDFSchema> pdfs) {
    StringBuilder ret = new StringBuilder();
    for (PDFSchema pdf : pdfs) {
      ret.append("<div class=\"pdf\">");
      ret.append(String.format("<a href=\"/download/%s\">", pdf.getId()));
      ret.append("<img src=\"https://img.icons8.com/dotty/80/000000/pdf-2.png\"/>");
      ret.append("<br/>");
      ret.append(pdf.getFilename());
      ret.append("</a>");
      ret.append("<br/><br/>");
      ret.append(String.format(
          "<a class=\"delete\" href=\"/delete/%s\" onclick=\"loading()\">", pdf.getId()));
      ret.append("delete");
      ret.append("</a></div>");
    }
    return ret.toString();
  }


  /**
   * Format snippets.
   *
   * @param snippets List of snippets.
   */
  private static String formatSnippets(List<SnippetSchema> snippets) {
    // Get max score for normalizing.
    double maxScore = 0;
    for (SnippetSchema snippet : snippets) {
      maxScore = Math.max(maxScore, snippet.getScore());
    }
    // Sort snippets by score
    snippets.sort(Comparator.comparingDouble(SnippetSchema::getScore).reversed());
    // Set to contain filenames to minimize DB calls.
    Map<String, String> filenames = new HashMap<>();
    // Capped at 100 snippets returned.
    StringBuilder ret = new StringBuilder();
    for (int i = 0; i < Math.min(MAX_SNIPPETS, snippets.size()); i++) {
      // Get filename
      String fileId =
          snippets.get(i).getFile().substring(0, snippets.get(i).getFile().length() - 4);
      String filename;
      if (filenames.get(fileId) != null) {
        filename = filenames.get(fileId);
      } else {
        PDFSchema pdf = ParselDB.getPDFByID(fileId);
        if (pdf == null) {
          filename = "[MISSING PDF]";
        } else {
          filename = pdf.getFilename();
        }
        filenames.put(fileId, filename);
      }
      // Construct snippet.
      ret.append(String.format(
          "<div id=\"%s\" class=\"snippet\"><div class=\"snippet-score\">Score: ",
          snippets.get(i).getSnippetId()));
      ret.append(snippets.get(i).getScore() / (maxScore / NORMALIZE));
      ret.append("</div><div class=\"snippet-score\">Source: ");
      ret.append(filename);
      ret.append(", pg. ");
      ret.append(snippets.get(i).getPage());
      ret.append("</div>");
      ret.append(snippets.get(i).getContent());
      ret.append("<br/>");
      ret.append(String.format(
          "<button class='similar' onclick=\"getSimilar('%s', '%s')\">View Similar</button>",
          snippets.get(i).getSnippetId(), snippets.get(i).getQueryId()));
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
      SnippetSchema newSnippet = new SnippetSchema(queryId, v.getValue().getID(),
          curr.getOriginalText(), curr.getFileName(), v.getScore(), curr.getPageNum());
      ParselDB.updateSnippet(newSnippet);
    }
  }
}
