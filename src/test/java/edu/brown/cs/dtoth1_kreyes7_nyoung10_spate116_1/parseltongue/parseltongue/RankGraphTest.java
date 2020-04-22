package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.Jaccardish;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.PDFParser;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;
import org.junit.Test;

import java.util.List;

public class RankGraphTest {
  @Test
  public void testMultipleParagraphs() {
    // TODO: Activate.
    RankGraph g = new RankGraph(Snippet.parseText(new PDFParser().getText("data/test.pdf")),
        List.of("rocks", "rock", "the"), new Jaccardish());
    PageRank ranker = new PageRank(g);
    List<RankVertex> snippets = ranker.pageRank();
//    for (int i = 0; i < 5; i++) {
//      System.out.println(snippets.get(i).getValue().getSnippet().getOriginalText());
//      System.out.println();
//    }
  }
}
