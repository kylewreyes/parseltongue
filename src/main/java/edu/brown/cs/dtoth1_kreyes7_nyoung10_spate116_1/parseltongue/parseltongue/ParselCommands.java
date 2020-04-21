package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.CosineSimilarity;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.PDFParser;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.REPL;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;

import java.util.ArrayList;
import java.util.List;

public class ParselCommands {
  public static PDFParser parser = new PDFParser();
  public static final String PARSE_ARGUMENT_PATTERN =
          "([\\S]*\\.pdf)( [\\S]*\\.pdf)* \\\"[^\\\"]*\\\"";
  private static REPL.Command parse = new REPL.Command() {
    @Override
    public String execute(String[] args) {
      List<String> coreTexts = new ArrayList<>();
      for (int i = 0; i < args.length - 1; i++) {
        String temp = args[i];
        coreTexts.add(temp);
      }
      String queryString = args[args.length - 1];
      List<Snippet> results = parsel(coreTexts, queryString);

      return "TODO TODO TODO,TODO,TODOTODOOO DOO DODODO";
    }
  };

  public static List<Snippet> parsel(List<String> pdfPaths, String queryString) {
    List<Snippet> coreTexts = new ArrayList<>();
    for (String path : pdfPaths) {
      List<Snippet> temp = extractCorePDFText(path);
      assert temp != null;
      coreTexts.addAll(temp);
    }
    Snippet query = new Snippet(queryString);
    RankGraph g = new RankGraph(coreTexts, new ArrayList<>(query.distribution().keySet()), new CosineSimilarity());
    return g.rank();
  }

  public static REPL.Command getParseCommand() {
    return parse;
  }

  /**
   * Extracts the necessary text from a PDF to be analyzed.
   * @param filePath the location of the PDF
   * @return a {@link List<Snippet>} of text from the PDF
   */
  public static List<Snippet> extractCorePDFText(String filePath) {
    return Snippet.parseText(parser.getText(filePath));
  }
}
