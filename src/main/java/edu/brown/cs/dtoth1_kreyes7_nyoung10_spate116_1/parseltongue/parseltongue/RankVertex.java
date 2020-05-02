package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Vertex;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * PageRank Vertex Class!
 */
public class RankVertex implements Vertex<RankEdge, RankMetadata>, Serializable {
  private RankMetadata meta;
  private Set<RankEdge> adj = new HashSet<>();
  private double score = 0;

  /**
   * Construcor.
   *
   * @param meta metadata.
   */
  public RankVertex(RankMetadata meta) {
    this.meta = meta;
  }

  /**
   * Add Edge.
   *
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
   * Get Value.
   *
   * @return Metadata.
   */
  @Override
  public RankMetadata getValue() {
    return meta;
  }

  /**
   * Get Edges.
   *
   * @return Edges.
   */
  @Override
  public Set<RankEdge> getEdges() {
    return adj;
  }

  /**
   * Get top n vertices adjacent.
   *
   * @param n the number of nodes wanted
   * @return list of n vertices
   */
  public List<RankVertex> getTopAdj(int n) {
    List<RankEdge> edges = new ArrayList<>(adj);
    edges.sort((o1, o2) -> -o1.getWeight().compareTo(o2.getWeight()));
    List<RankVertex> ret = new ArrayList<>();
    for (int i = 1; i <= Math.min(n, edges.size()); ++i) {
      ret.add(edges.get(i).getDest());
    }
    return ret;
  }

  /**
   * Total Weight.
   *
   * @return Weight.
   */
  @Override
  public Double totalWeight() {
    double sum = 0.0;
    for (RankEdge e : adj) {
      sum += e.getWeight();
    }
    return sum;
  }

  /**
   * GSet score.
   *
   * @param score a double with the pageRank score
   */
  @Override
  public void setScore(double score) {
    this.score = score;
  }

  /**
   * Get score.
   *
   * @return Score.
   */
  @Override
  public Double getScore() {
    return score;
  }

  /**
   * Equals.
   *
   * @param o Other object.
   * @return True if equal.
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
   *
   * @return Hashed Vertex.
   */
  @Override
  public int hashCode() {
    return Objects.hash(meta);
  }
}
