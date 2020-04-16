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
  // =============================================================================================
  // STARS ROUTES
  // =============================================================================================
  /**
   * Handle GET requests to the landing page.
   */
  public static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "index.ftl");
    }
  }

  /**
   *
   */
  public static class DashHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "dashboard.ftl");
    }
  }
}
