package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.CosineSimilarity;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.PDFParser;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.REPL;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsel Commands Class!
 */
public final class ParselCommands {
  // TODO: Make these private.
  public static PDFParser parser = new PDFParser();
  public static final String PARSE_ARGUMENT_PATTERN =
      "([\\S]*\\.pdf)( [\\S]*\\.pdf)* \\\"[^\\\"]*\\\"";

  /**
   * Private Constructor.
   */
  private ParselCommands() {

  }

  private static REPL.Command parse = new REPL.Command() {
    /**
     * REPL command wrapper for parse command. TODO: Complete Docs.
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
      List<Snippet> results = parsel(coreTexts, queryString);
      StringBuilder sb = new StringBuilder();
      for (Snippet s : results) {
        sb.append(s.getOriginalText());
        sb.append("\n");
      }
      return sb.toString();
    }
  };

  /**
   * Parse! TODO: Complete Docs.
   *
   * @param pdfPaths    PDF Paths.
   * @param queryString Query string.
   * @return List of snippets.
   */
  public static List<Snippet> parsel(List<String> pdfPaths, String queryString) {
    List<Snippet> coreTexts = new ArrayList<>();
    for (String path : pdfPaths) {
      List<Snippet> temp = extractCorePDFText(path);
      assert temp != null;
      coreTexts.addAll(temp);
    }
    Snippet query = new Snippet(queryString);
    try {
      RankGraph g = new RankGraph(coreTexts, new ArrayList<>(query.distribution().keySet()), new CosineSimilarity());
      return g.rank();
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
    return Snippet.parseText(parser.getText(filePath));
  }
}
