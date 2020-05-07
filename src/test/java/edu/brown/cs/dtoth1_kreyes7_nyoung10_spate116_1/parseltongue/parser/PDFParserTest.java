package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class PDFParserTest {
  // Apache OpenPDF uses non-breaking spaces and inserts extra spacing/line separators, so this
  // method is to filter thru those for testing purposes
  private String processRawInput(String raw) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader textReader = new BufferedReader(new StringReader(raw))) {
      String currentLine;
      while ((currentLine = textReader.readLine()) != null) {
        sb.append(currentLine.replaceAll("\\u00A0", " ").trim() + System.lineSeparator());
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: Unexpected issue in JUnit Test");
    }
    return sb.toString();
  }

  @Test
  public void testForMargins() {
    String marginTest = "";
    try (SourceParser parser = new PDFParser("data/test_for_margins.pdf")) {
      assertEquals("", parser.getText().replaceAll("\\u00A0", " ").trim());
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: Unexpected issue in JUnit Test");
    }
  }

  @Test
  public void getTextFromPageTest() {
    File f = new File("data/test_multiple_paragraphs.pdf");
    try (PDFParser parser = new PDFParser(f)) {
      String expected = "Dave watched as the forest burned up on the hill, only a few miles from " +
          "her house. The car had been" + System.lineSeparator() +
          "hastily packed and Marta was inside trying to round up the last of the pets. Dave went" +
          " through his" + System.lineSeparator() +
          "mental list of the most important papers and documents that they couldn't leave behind" +
          ". He scolded" + System.lineSeparator() +
          "himself for not having prepared these better in advance and hoped that he had " +
          "remembered everything" + System.lineSeparator() +
          "that was needed. He continued to wait for Marta to appear with the pets, but she still" +
          " was nowhere to" + System.lineSeparator() +
          "be seen." + System.lineSeparator();
      assertEquals(expected, processRawInput(parser.getTextFromPage(3)));
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: Unexpected issue in JUnit Test");
    }
  }

  @Test
  public void noTextJustImageTest() {
    try (SourceParser parser = new PDFParser("data/The Wizard of Oz Parable on Populism.pdf")) {
      assertEquals("", parser.getText().replaceAll("\\u00A0", " ").trim());
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: Unexpected issue in JUnit Test");
    }
  }

  @Test
  public void badInputTest() {
    assertThrows(IllegalArgumentException.class, () -> new PDFParser("PorterStemmingTestVocabInput.txt"));
    assertThrows(IllegalArgumentException.class, () -> new PDFParser("data/not_a_pdf.pdf"));
  }

  @Test
  public void manyParagraphsTest() {
    String multipleParagraphs = "Multiple paragraph test" + System.lineSeparator() +
        "Haha" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
        "Ok" + System.lineSeparator() + System.lineSeparator() +
        "It was that terrifying feeling you have as you tightly hold the covers over you with the" +
        " knowledge that" + System.lineSeparator() +
        "there is something hiding under your bed. You want to look, but you don't at the same " +
        "time. You're" + System.lineSeparator() +
        "frozen with fear and unable to act. That's where she found herself and she didn't know " +
        "what to do next" + System.lineSeparator() +
        "The lone lamp post of the one‐street town flickered, not quite dead but definitely on " +
        "its way out." + System.lineSeparator() +
        "Suitcase by her side, she paid no heed to the light, the street or the town. A car was " +
        "coming down the" + System.lineSeparator() +
        "street and with her arm outstretched and thumb in the air, she had a plan." +
        System.lineSeparator() +
        "The words hadn't flowed from his fingers for the past few weeks. He never imagined he'd " +
        "find himself" + System.lineSeparator() +
        "with writer's block, but here he sat with a blank screen in front of him. That blank " +
        "screen taunting him" + System.lineSeparator() +
        "day after day had started to play with his mind. He didn't understand why he couldn't " +
        "even type a single" + System.lineSeparator() +
        "word, just one to begin the process and build from there. And yet, he already knew that " +
        "the eight hours" + System.lineSeparator() +
        "he was prepared to sit in front of his computer today would end with the screen " +
        "remaining blank." + System.lineSeparator() +
        "The young man wanted a role model. He looked long and hard in his youth, but that role " +
        "model never" + System.lineSeparator() +
        "materialized. His only choice was to embrace all the people in his life he didn't want " +
        "to be like." + System.lineSeparator() +
        "Do you think you're living an ordinary life? You are so mistaken it's difficult to even " +
        "explain. The mere" + System.lineSeparator() +
        "fact that you exist makes you extraordinary. The odds of you existing are less than " +
        "winning the lottery," + System.lineSeparator() +
        "but here you are. Are you going to let this extraordinary opportunity pass?" +
        System.lineSeparator() +
        "" + System.lineSeparator() +
        "" + System.lineSeparator() +
        "The computer wouldn't start. She banged on the side and tried again. Nothing. She lifted" +
        " it up and" + System.lineSeparator() +
        "dropped it to the table. Still nothing. She banged her closed fist against the top. It " +
        "was at this moment" + System.lineSeparator() +
        "she saw the irony of trying to fix the machine with violence." + System.lineSeparator() +
        "She didn't understand how changed worked. When she looked at today compared to " +
        "yesterday, there" + System.lineSeparator() +
        "was nothing that she could see that was different. Yet, when she looked at today " +
        "compared to last year," + System.lineSeparator() +
        "she couldn't see how anything was ever the same." + System.lineSeparator() +
        "Since they are still preserved in the rocks for us to see, they must have been formed " +
        "quite recently, that" + System.lineSeparator() +
        "is, geologically speaking. What can explain these striations and their common " +
        "orientation? Did you ever" + System.lineSeparator() +
        "hear about the Great Ice Age or the Pleistocene Epoch? Less than one million years ago, " +
        "in fact, some" + System.lineSeparator() +
        "12,000 years ago, an ice sheet many thousands of feet thick rode over Burke Mountain in " +
        "a" + System.lineSeparator() +
        "southeastward direction. The many boulders frozen to the underside of the ice sheet " +
        "tended to scratch" + System.lineSeparator() +
        "the rocks over which they rode. The scratches or striations seen in the park rocks were " +
        "caused by these" + System.lineSeparator() +
        "attached boulders. The ice sheet also plucked and rounded Burke Mountain into the shape " +
        "it possesses" + System.lineSeparator() +
        "today." + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
        "Dave watched as the forest burned up on the hill, only a few miles from her house. The " +
        "car had been" + System.lineSeparator() +
        "hastily packed and Marta was inside trying to round up the last of the pets. Dave went " +
        "through his" + System.lineSeparator() +
        "mental list of the most important papers and documents that they couldn't leave behind. " +
        "He scolded" + System.lineSeparator() +
        "himself for not having prepared these better in advance and hoped that he had remembered" +
        " everything" + System.lineSeparator() +
        "that was needed. He continued to wait for Marta to appear with the pets, but she still " +
        "was nowhere to" + System.lineSeparator() +
        "be seen." + System.lineSeparator() + System.lineSeparator();
    try (SourceParser parser = new PDFParser("data/test_multiple_paragraphs.pdf")) {
      assertEquals(multipleParagraphs, processRawInput(parser.getText()));
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: Unexpected issue in JUnit Test");
    }
  }
}
