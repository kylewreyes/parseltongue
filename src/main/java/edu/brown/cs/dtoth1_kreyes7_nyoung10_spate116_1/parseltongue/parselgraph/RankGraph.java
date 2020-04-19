package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parselgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;

import java.util.List;
import java.util.Set;

public class RankGraph implements Graph<RankVertex, RankEdge, RankMetadata> {
  @Override
  public RankVertex getVertex(String id) {
    return null;
  }

  @Override
  public boolean containsVertex(String id) {
    return false;
  }

  @Override
  public List<RankVertex> getVertices() {
    return null;
  }
  @Override
  public Set<RankEdge> getIncoming(RankVertex target) {
    return null;
  }
}
