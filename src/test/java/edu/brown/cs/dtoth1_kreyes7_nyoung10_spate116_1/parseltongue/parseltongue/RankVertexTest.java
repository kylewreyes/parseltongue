package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RankVertexTest {
  private RankVertex node;
  private RankEdge edge1, edge2;

  @Before
  public void setUp() {
    node = new RankVertex(new RankMetadata(new Snippet("1")));
    RankVertex node2 = new RankVertex(new RankMetadata(new Snippet("2")));
    RankVertex node3 = new RankVertex(new RankMetadata(new Snippet("3")));
    edge1 = new RankEdge(node, node2, 1);
    edge2 = new RankEdge(node, node3, 2);
    node.addEdge(edge1);
    node.addEdge(edge2);
  }

  @After
  public void tearDown() {
    node = null;
    edge1 = null;
    edge2 = null;
  }

  @Test
  public void testEquality() {
    setUp();
    assertNotEquals(node, 2.0);
    assertNotEquals(node, null);
    assertEquals(node, new RankVertex(new RankMetadata(new Snippet("1"))));
    tearDown();
  }

  @Test
  public void testAdj(){
    setUp();
    assertEquals(node.getEdges(), Set.of(edge1, edge2));
    assertEquals(node.totalWeight(), Double.valueOf(3));
    tearDown();
  }

  @Test
  public void getTop() {
    setUp();
    assertEquals(node.getTopAdj(4), List.of(edge2.getDest(), edge1.getDest()));
    assertEquals(node.getTopAdj(1), List.of(edge2.getDest()));
    tearDown();
  }

  @Test
  public void testMisc() {
    setUp();
    assertEquals(node.getScore(), Double.valueOf(0));
    tearDown();
  }
}
