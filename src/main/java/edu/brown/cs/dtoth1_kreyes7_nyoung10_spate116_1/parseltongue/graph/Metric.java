package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

public interface Metric<E extends Edge<? extends Vertex<E, ? extends VertexMetadata>>> {
  double getWeight(E edge);
}
