package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    File f = new File("data/references_on_first_page.pdf");
    try (PDFParser parser = new PDFParser(f)) {
      List<String> pages = new ArrayList<>();
      for (int i = 1; i <= parser.getPageCount(); i++) {
        pages.add(parser.getTextFromPage(i));
      }
      List<String> expectedStrings = new ArrayList<>();
      expectedStrings.add("URUGUAY: THE RISE AND FALL OF A WELFARE STATE SEEN AGAINST A " +
          "BACKGROUND" + System.lineSeparator() +
          "OF DEPENDENCY THEORY" + System.lineSeparator() +
          "Author(s): J.M.G. Kleinpenning" + System.lineSeparator() +
          "Source: Revista Geográfica, No. 93 (ENERO-JUNIO 1981), pp. 101-117" + System.lineSeparator() +
          "Published by: Pan American Institute of Geography and History" + System.lineSeparator() +
          "Stable URL: https://www.jstor.org/stable/40993122" + System.lineSeparator() +
          "Accessed: 21-04-2020 16:28 UTC" + System.lineSeparator() +
          "REFERENCES" + System.lineSeparator() +
          "Linked references are available on JSTOR for this article:" + System.lineSeparator() +
          "https://www.jstor.org/stable/40993122?seq=1&cid=pdf-reference#references_tab_contents" +
          "" + System.lineSeparator() +
          "You may need to log in to JSTOR to access the linked references.");
      expectedStrings.add("JSTOR is a not-for-profit service that helps scholars, researchers, " +
          "and students discover, use, and build upon a wide" + System.lineSeparator() +
          "range of content in a trusted digital archive. We use information technology and tools" +
          " to increase productivity and" + System.lineSeparator() +
          "facilitate new forms of scholarship. For more information about JSTOR, please contact " +
          "support@jstor.org.");
      expectedStrings.add("Your use of the JSTOR archive indicates your acceptance of the Terms &" +
          " Conditions of Use, available at" + System.lineSeparator() +
          "https://about.jstor.org/terms" + System.lineSeparator() +
          "Pan American Institute of Geography and History is collaborating with JSTOR to " +
          "digitize," + System.lineSeparator() +
          "preserve and extend access to Revista Geográfica");

      List<String> snippetStrings = new ArrayList<>();
      for (Snippet s : Snippet.parseText(pages, f.getName())) {
        snippetStrings.add(s.getOriginalText());
      }
      assertEquals(expectedStrings, snippetStrings);
    } catch (IOException e) {
      e.printStackTrace();
    }


    try (PDFParser parser = new PDFParser("data/UruguayWithFace.pdf")) {

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
