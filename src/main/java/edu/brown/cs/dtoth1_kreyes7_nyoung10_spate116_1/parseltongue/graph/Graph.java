package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

import java.util.List;
import java.util.Set;

/**
 * An interface for a graph.
 *
 * @param <V> the type of {@link Vertex} the {@link Graph} uses
 * @param <E> the type of {@link Edge} the {@link Graph} uses
 * @param <T> the type of {@link VertexMetadata} to be stored in a {@link Vertex}
 */
public interface Graph<V extends Vertex<E, T>, E extends Edge<V>, T extends VertexMetadata> {
  /**
   * Gets a {@link Vertex} from this Graph with the given ID.
   *
   * @param id the unique ID
   * @return a {@link Vertex} with the given ID
   */
  V getVertex(String id);

  /**
   * Checks if the Graph contains a {@link Vertex} with a given ID.
   *
   * @param id the unique ID
   * @return true or false
   */
  boolean containsVertex(String id);

  /**
   * Grabs all the vertices in a graph object.
   *
   * @return a list of vertices that are in the graph;
   */
  List<V> getVertices();

  /**
   * Grabs all the edges in a graph object pointing to target.
   *
   * @param target the target node
   * @return a set of edges that are in the graph;
   */
  Set<E> getIncoming(V target);
}
