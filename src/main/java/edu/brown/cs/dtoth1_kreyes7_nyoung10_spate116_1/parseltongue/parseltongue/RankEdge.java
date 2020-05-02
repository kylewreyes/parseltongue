package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Edge;

/**
 * PageRank Edge Class! TODO: Complete Docs.
 */
public class RankEdge implements Edge<RankVertex> {
  private final RankVertex to;
  private final RankVertex from;
  private final double weight;

  /**
   * Constructor. TODO: Complete Docs.
   *
   * @param from   source.
   * @param to     destination.
   * @param weight weight.
   */
  public RankEdge(RankVertex from, RankVertex to, double weight) {
    this.to = to;
    this.from = from;
    this.weight = weight;
  }

  /**
   * Get source.
   *
   * @return source.
   */
  @Override
  public RankVertex getSource() {
    return from;
  }

  /**
   * Get dest.
   *
   * @return dest.
   */
  @Override
  public RankVertex getDest() {
    return to;
  }

  /**
   * Get weight.
   *
   * @return weight.
   */
  @Override
  public Double getWeight() {
    return weight;
  }
}
