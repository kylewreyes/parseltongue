package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

import java.util.Set;

/**
 * An interface for a vertex to be used in a {@link Graph}.
 *
 * @param <E> the type of {@link Edge} the {@link Graph} uses
 * @param <T> the type of {@link VertexMetadata} this Vertex stores
 */
public interface Vertex<E extends Edge<? extends Vertex>, T extends VertexMetadata> {
  /**
   * Gets the {@link VertexMetadata} stored in this Vertex.
   *
   * @return the {@link VertexMetadata} stored
   */
  T getValue();

  /**
   * Gets all the {@link Edge}s that stem from this Vertex.
   *
   * @return a {@link Set} of {@link Edge}s from this Vertex
   */
  Set<E> getEdges();

  /**
   * Gets the total weight of all the edges from the vertex.
   *
   * @return a double with the total weight.
   */
  Double totalWeight();

  /**
   * Sets the pageRank score.
   *
   * @param score a double with the pageRank score
   */
  void setScore(double score);

  /**
   * Gets the pageRank score.
   *
   * @return score a double with the pageRank score
   */
  Double getScore();
}
