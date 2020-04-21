package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

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
      Map<String, Object> variables = ImmutableMap.of("loggedIn", "TODO: ");
      return new ModelAndView(variables, "index.ftl");
    }
  }

  /**
   *  Handles GET requests to the /dashboard route.
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
   *  Handles GET requests to the /upload route.
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
   *  Handles GET requests to the /view route.
   * TODO: parse data.
   */
  public static class GETViewHandler implements TemplateViewRoute {
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
}
