package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.VertexMetadata;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;

public class RankMetadata implements VertexMetadata {
  private String id;
  private Snippet snippet;

  public RankMetadata(Snippet snippet) {
    this.snippet = snippet;
    this.id = String.valueOf(snippet.hashCode());
  }

  @Override
  public String getID() {
    return id;
  }

  public Snippet getSnippet() {
    return snippet;
  }
}
