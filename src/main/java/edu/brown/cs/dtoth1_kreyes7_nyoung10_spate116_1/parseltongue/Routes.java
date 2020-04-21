package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Routes class! Holds and handles all web server routing.
 */
public class Routes {
  /**
   * Handle GET requests to the landing page.
   */
  public static class GETMainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged;
      if (req.session().attribute("logged") == null) {
        logged = "0";
      } else {
        logged = req.session().attribute("logged");
      }
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "index.ftl");
    }
  }

  /**
   * Handle GET requests to the registration page.
   */
  public static class GETRegisterHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged;
      if (req.session().attribute("logged") == null) {
        logged = "0";
      } else {
        logged = req.session().attribute("logged");
        res.redirect("/");
      }
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
      //TODO: Register Logic
      System.out.println("New user created!");
      res.redirect("/");
      return null;
    }
  }

  /**
   * Handles GET requests to the /dashboard route.
   */
  public static class GETDashHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged;
      if (req.session().attribute("logged") == null) {
        logged = "0";
      } else {
        logged = req.session().attribute("logged");
      }
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "dashboard.ftl");
    }
  }

  /**
   * Handles GET requests to the /upload route.
   */
  public static class GETUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged;
      if (req.session().attribute("logged") == null) {
        logged = "0";
      } else {
        logged = req.session().attribute("logged");
      }
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "upload.ftl");
    }
  }

  /**
   * Handles GET requests to the /upload route.
   */
  public static class POSTUploadHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      System.out.println("FILE: " + req.queryMap("file"));
      req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      try (InputStream is = req.raw().getPart("file").getInputStream()) {
        String filename = req.raw().getPart("file").getSubmittedFileName();
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        File targetFile = new File(String.format("temp/%s.pdf", filename.hashCode()));
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        res.redirect("/");
        // TODO: Save file to db, process snippets, redirect to view.
      } catch (Exception e) {
        System.err.println("ERROR: Bad File Upload at /upload");
        res.redirect("/error");
      }
      return null;
    }
  }

  /**
   * Handles GET requests to the /snippets route.
   * TODO: parse data.
   */
  public static class GETSnippetsHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String logged;
      if (req.session().attribute("logged") == null) {
        logged = "0";
      } else {
        logged = req.session().attribute("logged");
      }
      Map<String, Object> variables = ImmutableMap.of("loggedIn", logged);
      return new ModelAndView(variables, "view.ftl");
    }
  }

  /**
   * Handles GET requests to the /snippets route.
   * TODO: parse data.
   */
  public static class GETViewHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      res.redirect("/error");
      return null;
    }
  }

  /**
   * Handles GET requests to the /error route.
   */
  public static class GETErrorHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      // TODO: Make a better error page.
      return new ModelAndView(variables, "error.ftl");
    }
  }
}
