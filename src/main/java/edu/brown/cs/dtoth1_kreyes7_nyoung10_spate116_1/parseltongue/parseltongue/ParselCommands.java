package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.CosineSimilarity;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.REPL;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.Snippet;

import java.util.ArrayList;
import java.util.List;

public class ParselCommands {
  public static final String PARSE_ARGUMENT_PATTERN =
          "([\\S]*\\.pdf)( [\\S]*\\.pdf)* \\\"[^\\\"]*\\\"";
  private static REPL.Command parse = new REPL.Command() {
    @Override
    public String execute(String[] args) {
      List<Snippet> coreTexts = new ArrayList<>();
      for (int i = 0; i < args.length - 1; i++) {
        List<Snippet> temp = extractCorePDFText(args[i]);
        assert temp != null;
        coreTexts.addAll(temp);
      }
      Snippet query = new Snippet(args[args.length - 1]);
      RankGraph g = new RankGraph(coreTexts, new ArrayList<>(query.distribution().keySet()), new CosineSimilarity());
      return "TODO TODO TODO,TODO,TODOTODOOO DOO DODODO";
    }
  };
  public static REPL.Command getParseCommand() {
    return parse;
  }
  public static List<Snippet> extractCorePDFText(String filePath) {
    return null;
  }
}
