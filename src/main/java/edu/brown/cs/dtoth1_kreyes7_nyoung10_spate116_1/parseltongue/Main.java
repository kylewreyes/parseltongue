package edu.brown.cs.ilee26_nyoung10.maps;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.brown.cs.ilee26_nyoung10.maps.Routes.ActorHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.ConnectHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.FrontHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.MovieHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.NeighborsCoordsHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.NeighborsNameHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.RadiusCoordsHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.RadiusNameHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.StarsHandler;
import edu.brown.cs.ilee26_nyoung10.maps.Routes.TimdbHandler;
import edu.brown.cs.ilee26_nyoung10.maps.maps.Maps;
import edu.brown.cs.ilee26_nyoung10.maps.stars.Stars;
import edu.brown.cs.ilee26_nyoung10.maps.timdb.Timdb;
import edu.brown.cs.ilee26_nyoung10.maps.utils.Command;
import edu.brown.cs.ilee26_nyoung10.maps.utils.REPL;
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
  private static Stars starsProgram = new Stars();
  private static Timdb timdbProgram = new Timdb();
  private static Maps mapsProgram = new Maps();

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

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
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // REPL Handling.
    REPL repl = new REPL(new InputStreamReader(System.in));
    repl.addCommand("stars", new Command(starsProgram::starsCommand));
    repl.addCommand("neighbors", new Command(starsProgram::neighborsCommand));
    repl.addCommand("radius", new Command(starsProgram::radiusCommand));
    repl.addCommand("mdb", new Command(timdbProgram::mdbCommand));
    repl.addCommand("connect", new Command(timdbProgram::connectCommand));
    repl.addCommand("map", new Command(mapsProgram::mapCommand));
    repl.addCommand("ways", new Command(mapsProgram::waysCommand));
    repl.addCommand("nearest", new Command(mapsProgram::nearestCommand));
    repl.addCommand("route", new Command(mapsProgram::routeCommand));
    repl.begin();
  }

  /**
   * Accessor method for the Stars program.
   * @return  The Stars program.
   */
  public static Stars getStarsProgram() {
    return starsProgram;
  }

  /**
   * Accessor method for the Timdb program.
   * @return  The Timdb program.
   */
  public static Timdb getTimdbProgram() {
    return timdbProgram;
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
   * Runs the Spark Server.
   */
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes for Stars
    Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.post("/stars", new StarsHandler(), freeMarker);
    Spark.post("/neighborsName", new NeighborsNameHandler(), freeMarker);
    Spark.post("/neighborsCoords", new NeighborsCoordsHandler(), freeMarker);
    Spark.post("/radiusName", new RadiusNameHandler(), freeMarker);
    Spark.post("/radiusCoords", new RadiusCoordsHandler(), freeMarker);
    // Setup Spark Routes for tIMDb
    Spark.get("/timdb", new TimdbHandler(), freeMarker);
    Spark.get("/timdb/results", new ConnectHandler(), freeMarker);
    Spark.get("/timdb/actor/:id", new ActorHandler(), freeMarker);
    Spark.get("/timdb/movie/:id", new MovieHandler(), freeMarker);
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

