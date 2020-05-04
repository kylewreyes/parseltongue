package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.CosineSimilarity;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.PDFParser;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RankGraphTest {
  @Test
  public void testMultipleParagraphs() {
    try (PDFParser parser = new PDFParser("data/test.pdf")) {
      RankGraph g = new RankGraph(Snippet.parseText(parser.getText(), "test.pdf",
          Optional.empty()), new CosineSimilarity());
      g.populateEdges(List.of("rocks", "rock", "the"));
      PageRank ranker = new PageRank(g);
      List<RankVertex> snippets = ranker.rank();
//    for (int i = 0; i < 5; i++) {
//        System.out.println(snippets.get(i).getValue().getSnippet().getOriginalText());
//        System.out.println();
//    }
    } catch (IOException e) {
      new IllegalArgumentException("ERROR: JUnit test failed");
    }
  }
}
