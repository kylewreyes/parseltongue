package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Rankable;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.CosineSimilarity;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.Jaccardish;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.PDFParser;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class RankGraphTest {
  @Test
  public void testMultipleParagraphs() {
    try (PDFParser parser = new PDFParser("data/test.pdf")) {
      RankGraph g = new RankGraph(Snippet.parseText(parser.getText(), "test.pdf",
          Optional.empty()), new CosineSimilarity());
      g.populateEdges(List.of("rocks", "rock", "the"));
      Rankable ranker = new PageRank(g);
      List<RankVertex> snippets = ranker.rank();
       for (int i = 0; i < 5; i++) {
          System.out.println(snippets.get(i).getValue().getSnippet().getOriginalText());
          System.out.println();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: JUnit test failed");
    }
  }

  @Test
  public void testCorrectNumberVertex() {
    List<Snippet> corpus = List.of(new Snippet("1"), new Snippet("2"), new Snippet("3"));
    RankGraph g = new RankGraph(corpus, new Jaccardish());
    g.populateEdges(List.of("1", "2", "3"));
    assertEquals(g.rank().size(), 3);
  }

  @Test
  public void testStorageRetreival() {
    List<Snippet> corpus = List.of(new Snippet("1"), new Snippet("2"), new Snippet("3"));
    RankGraph g = new RankGraph(corpus, new Jaccardish());
    g.populateEdges(List.of("1", "2", "3"));
    assertEquals(g.getVertex(String.valueOf((new Snippet("1")).hashCode())), new RankVertex(new RankMetadata(new Snippet("1"))));
    assertTrue(g.containsVertex(String.valueOf((new Snippet("1")).hashCode())));
    assertFalse(g.containsVertex(String.valueOf((new Snippet("4")).hashCode())));
  }

  @Test
  public void testDataConversion() {
    List<Snippet> corpus = List.of(new Snippet("1"), new Snippet("2"), new Snippet("3"));
    RankGraph g = new RankGraph(corpus, new Jaccardish());
    g.populateEdges(List.of("1", "2", "3"));
    assertEquals(g.getVertices(), RankGraph.byteToObj(RankGraph.objToBytes(g)).getVertices());
  }
}
