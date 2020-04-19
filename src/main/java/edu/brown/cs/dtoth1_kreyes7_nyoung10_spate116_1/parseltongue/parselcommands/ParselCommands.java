package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parselcommands;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parselgraph.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.repl.REPL;

import java.util.ArrayList;
import java.util.List;

public class ParselCommands {
  public static final String PARSE_ARGUMENT_PATTERN =
          "([\\S]*\\.pdf)( [\\S]*\\.pdf)* \\\"[^\\\"]*\\\"";
  private static REPL.Command parse = new REPL.Command() {
    @Override
    public String execute(String[] args) {
      List<List<String>> coreTexts = new ArrayList<>();
      for (int i = 0; i < args.length - 1; i++) {
          coreTexts.add(extractCorePDFText(args[i]));
      }
      String query = args[args.length - 1];
      RankGraph g = new RankGraph(coreTexts, query);
      return "TODO TODO TODO,TODO,TODOTODOOO DOO DODODO";
    }
  };
  public static REPL.Command getParseCommand() {
    return parse;
  }
  public static List<String> extractCorePDFText(String filePath) {
    return null;
  }
}
