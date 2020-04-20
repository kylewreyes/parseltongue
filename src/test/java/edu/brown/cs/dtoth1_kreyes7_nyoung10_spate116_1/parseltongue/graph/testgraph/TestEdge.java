package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Edge;

public class TestEdge implements Edge<TestVertex> {
  private TestVertex to, from;
  private double weight;

  public TestEdge(TestVertex from, TestVertex to, double weight) {
    this.to = to;
    this.from = from;
    this.weight = weight;
  }

  @Override
  public TestVertex getSource() {
    return from;
  }

  @Override
  public TestVertex getDest() {
    return to;
  }

  @Override
  public double getWeight() {
    return weight;
  }
}
