package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

/**
 * An interface for an edge of a {@link Graph}.
 *
 * @param <V> the type of {@link Vertex} the {@link Graph} uses
 */
public interface Edge<V extends Vertex<? extends Edge, ? extends VertexMetadata>> {
  /**
   * Gets the {@link Vertex} that this Edge originates from.
   *
   * @return the source {@link Vertex}
   */
  V getSource();

  /**
   * Gets the {@link Vertex} that this Edge points to.
   *
   * @return the destination {@link Vertex}
   */
  V getDest();

  /**
   * Returns the weight of this Edge.
   *
   * @return the weight
   */
  Double getWeight();
}
