package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.VertexMetadata;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;

/**
 * PageRank Metadata Class!
 */
public class RankMetadata implements VertexMetadata {
  private String id;
  private Snippet snippet;

  /**
   * Constructor. TODO: Complete Docs.
   * @param snippet snippet.
   */
  public RankMetadata(Snippet snippet) {
    this.snippet = snippet;
    this.id = String.valueOf(snippet.hashCode());
  }

  /**
   * Get ID. TODO: Complete Docs.
   * @return  ID.
   */
  @Override
  public String getID() {
    return id;
  }

  /**
   * Get Snippet. TODO: Complete Docs.
   * @return  Snippet.
   */
  public Snippet getSnippet() {
    return snippet;
  }
}
