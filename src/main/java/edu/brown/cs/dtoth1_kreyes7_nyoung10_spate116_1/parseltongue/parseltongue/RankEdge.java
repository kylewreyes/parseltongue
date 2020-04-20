package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Edge;

public class RankEdge implements Edge<RankVertex> {
  private final RankVertex to;
  private final RankVertex from;
  private final double weight;

  public RankEdge(RankVertex from, RankVertex to, double weight) {
    this.to = to;
    this.from = from;
    this.weight = weight;
  }

  @Override
  public RankVertex getSource() {
    return from;
  }

  @Override
  public RankVertex getDest() {
    return to;
  }

  @Override
  public double getWeight() {
    return weight;
  }
}
