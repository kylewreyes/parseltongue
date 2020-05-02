package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.VertexMetadata;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;

import java.io.Serializable;

/**
 * PageRank Metadata Class!
 */
public class RankMetadata implements VertexMetadata, Serializable {
  private String id;
  private Snippet snippet;

  /**
   * Constructor.
   * @param snippet snippet.
   */
  public RankMetadata(Snippet snippet) {
    this.snippet = snippet;
    this.id = String.valueOf(snippet.hashCode());
  }

  /**
   * Get ID.
   * @return  ID.
   */
  @Override
  public String getID() {
    return id;
  }

  /**
   * Get Snippet.
   * @return  Snippet.
   */
  public Snippet getSnippet() {
    return snippet;
  }
}
