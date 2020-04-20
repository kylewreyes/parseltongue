package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

import org.junit.After;
import org.junit.Test;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph.TestEdge;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph.TestGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph.TestVertex;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph.TestVertexMetadata;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PageRankTest {
  TestGraph graph;

  @After
  public void tearDown() {
    graph = null;
  }

  @Test
  public void testSimpleUnweighted() {
    TestVertex v0, v1;
    TestEdge e0, e1;

    v0 = new TestVertex(new TestVertexMetadata("0"));
    v1 = new TestVertex(new TestVertexMetadata("1"));

    e0 = new TestEdge(v0, v1, 1);
    e1 = new TestEdge(v1, v0, 1);

    v0.setAdj(Set.of(e0));
    v1.setAdj(Set.of(e1));

    graph = new TestGraph(List.of(v0, v1));
    PageRank<TestGraph, TestVertex, TestEdge, TestVertexMetadata> pr = new PageRank<>(graph);
    assertEquals(pr.pageRank(0.85, 0.01), List.of(v1, v0));
  }

  @Test
  public void testSimpleWeighted() {
    TestVertex v0, v1;
    TestEdge e0, e1;

    v0 = new TestVertex(new TestVertexMetadata("0"));
    v1 = new TestVertex(new TestVertexMetadata("1"));

    e0 = new TestEdge(v0, v1, 1.5);
    e1 = new TestEdge(v1, v0, 0.1);

    v0.setAdj(Set.of(e0));
    v1.setAdj(Set.of(e1));

    graph = new TestGraph(List.of(v0, v1));
    PageRank<TestGraph, TestVertex, TestEdge, TestVertexMetadata> pr = new PageRank<>(graph);
    assertEquals(pr.pageRank(0.85, 0.01), List.of(v1, v0));
  }

  @Test
  public void testComplexUnweighted() {
    TestVertex v0, v1, v2, v3;
    TestEdge e0, e1, e2, e3, e4, e5, e6, e7, e8;

    v0 = new TestVertex(new TestVertexMetadata("0"));
    v1 = new TestVertex(new TestVertexMetadata("1"));
    v2 = new TestVertex(new TestVertexMetadata("2"));
    v3 = new TestVertex(new TestVertexMetadata("3"));

    e0 = new TestEdge(v0, v1, 1);
    e1 = new TestEdge(v1, v2, 1);
    e2 = new TestEdge(v2, v3, 1);
    e3 = new TestEdge(v3, v0, 1);
    e4 = new TestEdge(v0, v3, 1);
    e5 = new TestEdge(v3, v2, 1);
    e6 = new TestEdge(v2, v1, 1);
    e7 = new TestEdge(v1, v0, 1);
    e8 = new TestEdge(v2, v0, 1);

    v0.setAdj(Set.of(e0, e4));
    v1.setAdj(Set.of(e1, e7));
    v2.setAdj(Set.of(e2, e6, e8));
    v3.setAdj(Set.of(e3, e5));

    graph = new TestGraph(List.of(v0, v1, v2, v3));
    PageRank<TestGraph, TestVertex, TestEdge, TestVertexMetadata> pr = new PageRank<>(graph);
    assertEquals(pr.pageRank(0.85, 0.01), List.of(v0, v3, v1, v2));
  }

  @Test
  public void testComplexWeighted() {
    TestVertex v0, v1, v2;
    TestEdge e0, e1, e2, e3, e4, e5;

    v0 = new TestVertex(new TestVertexMetadata("0"));
    v1 = new TestVertex(new TestVertexMetadata("1"));
    v2 = new TestVertex(new TestVertexMetadata("2"));

    e0 = new TestEdge(v0, v1, 0.7);
    e1 = new TestEdge(v0, v2, 0.3);
    e2 = new TestEdge(v1, v0, 0.6);
    e3 = new TestEdge(v1, v2, 0.4);
    e4 = new TestEdge(v2, v0, 0.3);
    e5 = new TestEdge(v2, v1, 0.7);

    v0.setAdj(Set.of(e0, e1));
    v1.setAdj(Set.of(e2, e3));
    v2.setAdj(Set.of(e4, e5));

    graph = new TestGraph(List.of(v0, v1, v2));
    PageRank<TestGraph, TestVertex, TestEdge, TestVertexMetadata> pr = new PageRank<>(graph);
    assertEquals(pr.pageRank(0.85, 0.01), List.of(v1, v0, v2));
  }
}
