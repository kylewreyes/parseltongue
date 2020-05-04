package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RankMetadataTest {
  @Test
  public void testIdGeneration() {
    Snippet mySnip = new Snippet("test input");
    RankMetadata meta = new RankMetadata(mySnip);
    String id = String.valueOf(mySnip.hashCode());
    assertEquals(meta.getID(), id);
    assertEquals(meta.getSnippet(), mySnip);
  }
}
