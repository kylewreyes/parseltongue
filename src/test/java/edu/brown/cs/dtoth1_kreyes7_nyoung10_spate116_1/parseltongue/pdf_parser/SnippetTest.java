package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.Snippet;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnippetTest {
  private String complexString = "However, does this really work?" + System.lineSeparator() + "Oh well...";
  @Test
  public void getOriginalTextTest() {
    assertEquals("", new Snippet("").getOriginalText());
    assertEquals("test", new Snippet("test").getOriginalText());
    assertEquals(complexString, new Snippet(complexString).getOriginalText());
  }

  @Test
  public void getPlainTextTest() {
    String nonAlphaNums = "   $%.?';" + System.lineSeparator() + System.lineSeparator() + "[]";
    assertEquals("", new Snippet("").getPlainText());
    assertEquals("however does this really work oh well", new Snippet(complexString).getPlainText());
    assertEquals("", new Snippet(nonAlphaNums).getPlainText());
  }

}
