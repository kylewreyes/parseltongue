package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Rankable;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.Jaccardish;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.PDFParser;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.REPL;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class that contains all the necessary commands for ParselTongue to run.
 */
public final class ParselCommands {
  private static final String PARSE_ARGUMENT_PATTERN =
      "([\\S]*\\.pdf)( [\\S]*\\.pdf)* \\\"[^\\\"]*\\\"";

  /**
   * Private Constructor.
   */
  private ParselCommands() {
  }

  /**
   * @return the parsing argument pattern to be used for the REPL
   */
  public static String getParseArgumentPattern() {
    return PARSE_ARGUMENT_PATTERN;
  }

  private static REPL.Command parse = new REPL.Command() {
    /**
     * REPL command wrapper for parse command.
     * @param args parameters to execute the command
     * @return String.
     */
    @Override
    public String execute(String[] args) {
      List<String> coreTexts = new ArrayList<>();
      for (int i = 0; i < args.length - 1; i++) {
        String temp = args[i];
        coreTexts.add(temp);
      }
      String queryString = args[args.length - 1];
      List<Snippet> results = parsel(coreTexts, queryString).rank();
      StringBuilder sb = new StringBuilder();
      for (Snippet s : results) {
        sb.append(s.getOriginalText());
        sb.append("\n");
      }
      return sb.toString();
    }
  };

  /**
   * Creates a {@link RankGraph} that ranks different {@link Snippet}s from the given files,
   * based on the keywords given.
   *
   * @param pdfPaths    PDF Paths.
   * @param queryString Query string.
   * @return List of snippets.
   */
  public static RankGraph parsel(List<String> pdfPaths, String queryString) {
    List<Snippet> coreTexts = new ArrayList<>();
    for (String path : pdfPaths) {
      List<Snippet> temp = extractCorePDFText(path);
      assert temp != null;
      coreTexts.addAll(temp);
    }
    Snippet query = new Snippet(queryString);
    try {
      RankGraph g = new RankGraph(coreTexts, new Jaccardish());
      g.populateEdges(new ArrayList<>(query.distribution().keySet()));
      Rankable<RankGraph, RankVertex, RankEdge, RankMetadata> ranker = new PageRank<>(g);
      ranker.rank();
      return g;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Returns the parse command to the REPL.
   *
   * @return parse command.
   */
  public static REPL.Command getParseCommand() {
    return parse;
  }

  /**
   * Extracts the necessary text from a PDF to be analyzed.
   *
   * @param filePath the location of the PDF
   * @return a List of Snippets of text from the PDF
   */
  public static List<Snippet> extractCorePDFText(String filePath) {
    File file = new File(filePath);
    if (filePath.length() > 4) {
      String fileEnding = filePath.substring(filePath.length() - 4);
      if (fileEnding.equals(".pdf")) {
        try (PDFParser parser = new PDFParser(file)) {
          List<String> pages = new ArrayList<>();
          for (int i = 1; i <= parser.getPageCount(); i++) {
            pages.add(parser.getTextFromPage(i));
          }
          return Snippet.parseText(pages, file.getName());
        } catch (IOException e) {
          throw new IllegalArgumentException("ERROR: Invalid PDF file");
        }
      } else {
        throw new IllegalArgumentException("ERROR: Invalid file path");
      }
    } else {
      throw new IllegalArgumentException("ERROR: Invalid file path");
    }
  }
}
