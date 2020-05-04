package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RankEdgeTest {

  @Test
  public void testGetters() {
    RankVertex node1 = new RankVertex(new RankMetadata(new Snippet("1")));
    RankVertex node2 = new RankVertex(new RankMetadata(new Snippet("2")));
    RankEdge edge = new RankEdge(node1, node2, 1.0);
    assertEquals(edge.getWeight(), Double.valueOf(1.0));
    assertEquals(edge.getSource(), node1);
    assertEquals(edge.getDest(), node2);
  }
}
