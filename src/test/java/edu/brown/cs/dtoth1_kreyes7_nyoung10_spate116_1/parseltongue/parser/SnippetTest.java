package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;

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

  @Test
  public void parseTextTest() {
    /* TODO:
  Test for separation by paragraphs
  Test for margins (headers and footers)
  Test for multiple pages
  Test for removing bibliographies
  - Lines that contain (Capital letter): references, works cited, bibliography, references cited, acknowledgements
  - Word by itself OR First word in the line but followed by period or colon
   */
  }

}