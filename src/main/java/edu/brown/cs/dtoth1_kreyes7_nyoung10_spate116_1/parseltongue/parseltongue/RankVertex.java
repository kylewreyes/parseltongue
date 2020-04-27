package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Vertex;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * PageRank Vertex Class!
 */
public class RankVertex implements Vertex<RankEdge, RankMetadata> {
  private RankMetadata meta;
  private Set<RankEdge> adj = new HashSet<>();
  private double score = 0;

  /**
   * Construcor. TODO: Complete Docs.
   * @param meta metadata.
   */
  public RankVertex(RankMetadata meta) {
    this.meta = meta;
  }

  /**
   * Add Edge. TODO: Complete Docs.
   * @param e Edge to add.
   */
  public void addEdge(RankEdge e) {
    adj.add(e);
  }

  /**
   * Clears the adjacency list of the RankVertex.
   */
  public void clearEdges() {
    adj.clear();
  }

  /**
   * Get Value. TODO: Complete Docs.
   * @return  Metadata.
   */
  @Override
  public RankMetadata getValue() {
    return meta;
  }

  /**
   * Get Edges. TODO: Complete Docs.
   * @return  Edges.
   */
  @Override
  public Set<RankEdge> getEdges() {
    return adj;
  }

  /**
   * Total Weight. TODO: Complete Docs.
   * @return  Weight.
   */
  @Override
  public Double totalWeight() {
    double sum = 0.0;
    for (RankEdge e : adj) {
      sum += e.getWeight();
    }
    return sum;
  }

  @Override
  public void setScore(double score) {
    this.score = score;
  }

  @Override
  public Double getScore() {
    return score;
  }

  /**
   * Equals.
   * @param o Other object.
   * @return  True if equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RankVertex that = (RankVertex) o;
    return meta.equals(that.meta);
  }

  /**
   * Hashcode.
   * @return  Hashed Vertex.
   */
  @Override
  public int hashCode() {
    return Objects.hash(meta);
  }
}
