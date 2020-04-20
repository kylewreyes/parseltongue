package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;

import java.util.*;

public class TestGraph implements Graph<TestVertex, TestEdge, TestVertexMetadata> {
  List<TestVertex> nodes;
  Map<TestVertex, Set<TestEdge>> inboundMap = new HashMap<>();

  public TestGraph(List<TestVertex> nodes) {
    this.nodes = nodes;
    List<TestEdge> edges = new ArrayList<>();
    for (TestVertex v : nodes) {
      edges.addAll(v.getEdges());
      inboundMap.put(v, new HashSet<>());
    }

    for (TestEdge e : edges) {
      inboundMap.get(e.getDest()).add(e);
    }
  }

  @Override
  public TestVertex getVertex(String id) {
    for (TestVertex v : nodes) {
      if (v.getValue().equals(id)) {
        return v;
      }
    }
    return null;
  }

  @Override
  public boolean containsVertex(String id) {
    boolean cont = false;
    for (TestVertex v : nodes) {
      cont |= v.getValue().equals(id);
    }
    return cont;
  }

  @Override
  public List<TestVertex> getVertices() {
    return nodes;
  }

  @Override
  public Set<TestEdge> getIncoming(TestVertex target) {
    return inboundMap.get(target);
  }
}
