package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

import java.io.Serializable;
import java.util.List;

/**
 *  An interface that defines how a ranker should work on a graph.
 *  @param <G> The graph type to be used.
 *  @param <V> The vertex type to be used.
 *  @param <E> The edge type to be used.
 *  @param <T> The metadata type to be used.
 */
public interface Rankable<G extends Graph<V, E, T>, V extends Vertex<E, T>, E extends Edge<V>,
    T extends VertexMetadata> extends Serializable {

  /**
   * Rank.
   * @return Ranked vertices.
   */
  List<V> rank();
}
