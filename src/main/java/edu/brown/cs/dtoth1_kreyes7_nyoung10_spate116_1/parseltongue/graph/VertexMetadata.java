package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

/**
 * An interface for objects to be stored in a {@link Vertex}.
 */
public interface VertexMetadata {
  /**
   * Returns a unique ID for this VertexMetadata. This ID will be used to identify {@link Vertex}
   * s in a {@link Graph} and will be used to check for equality
   *
   * @return unique ID for this VertexMetaData
   */
  String getID();
}
