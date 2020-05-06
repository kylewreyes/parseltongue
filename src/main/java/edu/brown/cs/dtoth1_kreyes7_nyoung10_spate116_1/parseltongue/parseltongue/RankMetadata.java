package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.VertexMetadata;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;

import java.io.Serializable;
import java.util.Objects;

/**
 * PageRank Metadata Class!
 */
public class RankMetadata implements VertexMetadata, Serializable {
  private final String id;
  private final Snippet snippet;

  /**
   * Constructor.
   *
   * @param snippet snippet.
   */
  public RankMetadata(Snippet snippet) {
    this.snippet = snippet;
    this.id = String.valueOf(snippet.hashCode());
  }

  /**
   * Get ID.
   *
   * @return ID.
   */
  @Override
  public String getID() {
    return id;
  }

  /**
   * Get Snippet.
   *
   * @return Snippet.
   */
  public Snippet getSnippet() {
    return snippet;
  }

  /**
   * Override equals.
   *
   * @param o the other rankmetadata
   * @return true if equal by id.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RankMetadata that = (RankMetadata) o;
    return id.equals(that.id);
  }

  /**
   * Override hashcode.
   *
   * @return hash of id
   */
  @Override
  public int hashCode() {
    return Objects.hash(snippet);
  }
}
