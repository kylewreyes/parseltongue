package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parselgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Vertex;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RankVertex implements Vertex<RankEdge, RankMetadata> {
  private RankMetadata meta;
  private Set<RankEdge> adj = new HashSet<>();

  public RankVertex(RankMetadata meta) {
    this.meta = meta;
  }

  public void addEdge(RankEdge e) {
    adj.add(e);
  }

  @Override
  public RankMetadata getValue() {
    return meta;
  }

  @Override
  public Set<RankEdge> getEdges() {
    return adj;
  }

  @Override
  public Double totalWeight() {
    double sum = 0.0;
    for (RankEdge e : adj) {
      sum += e.getWeight();
    }
    return sum;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RankVertex that = (RankVertex) o;
    return meta.equals(that.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta);
  }
}
