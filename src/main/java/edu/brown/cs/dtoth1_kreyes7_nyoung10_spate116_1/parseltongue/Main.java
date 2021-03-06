package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogManager;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.ParselCommands;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb.ParselDB;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.REPL;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import freemarker.template.Configuration;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {
  private static final int DEFAULT_PORT = 4567;
  private String[] args;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Runs the program and adds commands to REPL.
   */
  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);
    parser.accepts("test");
    OptionSet options = parser.parse(args);
    if (!options.has("test")) {
      // Connect to database.
      String uri = "mongodb+srv://n-young:IL5hkmuVnDfwsjqk@cluster0-dgi6r.mongodb"
              + ".net/test?retryWrites=true&w=majority";
      ParselDB.connect(uri);

      // Start webserver.
      runSparkServer();
    }
    // Disables logging from Apache PDFBox
    LogManager.getLogManager().reset();
    // REPL Handling.
    REPL repl = new REPL();
    repl.addCommand("parse", ParselCommands.getParseArgumentPattern(),
        ParselCommands.getParseCommand());
    repl.process();
  }

  /**
   * Creates a Spark Engine.
   */
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Gets Heroku Port.
   *
   * @return port.
   */
  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return DEFAULT_PORT; //return default port if heroku-port isn't set (i.e. on localhost)
  }

  /**
   * Runs the Spark Server.
   */
  private void runSparkServer() {
    Spark.port(getHerokuAssignedPort());
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    // GET Landing Page - "/"
    Spark.get("/", new Routes.GETMainHandler(), freeMarker);

    // GET Login Request - "/login"
    Spark.post("/login", Routes::postLoginHandler);

    // GET Logout Request - "/logout"
    Spark.get("/logout", Routes::getLogoutHandler);

    // GET Registration Page - "/register"
    Spark.get("/register", new Routes.GETRegisterHandler(), freeMarker);

    // POST Registration - "/register"
    Spark.post("/register", new Routes.POSTRegisterHandler(), freeMarker);

    // GET Dashboard - "/dashboard"
    Spark.get("/dashboard", new Routes.GETDashHandler(), freeMarker);

    // GET Delete - "/download/:pdf_id"
    Spark.get("/download/:pdf_id", new Routes.GETDownloadHandler(), freeMarker);

    // GET Delete - "/delete/:pdf_id"
    Spark.get("/delete/:pdf_id", new Routes.GETDeleteHandler(), freeMarker);

    // GET Upload Page - "/upload"
    Spark.get("/upload", new Routes.GETUploadHandler(), freeMarker);

    // POST Upload - "/upload"
    Spark.post("/upload", new Routes.POSTUploadHandler(), freeMarker);

    // GET Query Page - "/query"
    Spark.get("/query", new Routes.GETQueryHandler(), freeMarker);

    // POST Query - "/query"
    Spark.post("/query", new Routes.POSTQueryHandler(), freeMarker);

    // GET View query - "/query/:query_id"
    Spark.get("/query/:query_id", new Routes.GETQueryViewHandler(), freeMarker);

    // POST get nearest vertices - "/query/:query_id"
    Spark.post("/nearest", new Routes.POSTAdjacentSnippetsHandler());

    // GET delete query - "/query/delete/:query_id"
    Spark.get("/query/delete/:query_id", new Routes.GETQueryDeleteHandler(), freeMarker);

    // GET Error page
    Spark.get("/error", new Routes.GETErrorHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}

