package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An object containing chunks of text to be used for.
 */
public class Snippet {
  private String plainText, originalText;

  /**
   * @param text the original text from the document.
   */
  public Snippet(String text) {
    originalText = text;
  }

  /**
   * Filters through the original text for non-alphanumeric characters.
   *
   * @return the filtered text.
   */
  public String getPlainText() {
    if (plainText == null) {
      String[] splitText = originalText.toLowerCase().split("[^\\p{Alnum}]]*");
      StringBuilder builder = new StringBuilder();
      for (String s : splitText) {
        if (!s.equals("")) {
          builder.append(s + " ");
        }
      }
      if (builder.length() != 0) {
        // Removes the last space
        builder.deleteCharAt(builder.length() - 1);
      }
      plainText = builder.toString();
    }
    return plainText;
  }

  /**
   * Gets the distribution for words.
   *
   * @return the distribution as a hashmap.
   */
  public Map<String, Double> distribution() {
    if (plainText == null) {
      getPlainText();
    }
    Map<String, Double> dist = new HashMap<>();
    Set<String> uniqueWords = new HashSet<>(Arrays.asList(plainText.split(" ")));
    for (String key : uniqueWords) {
      dist.put(key, 0.0);
    }

    for (String word : plainText.split(" ")) {
      dist.replace(word, dist.get(word) + 1);
    }

    int n = plainText.split(" ").length;
    for (String key : uniqueWords) {
      dist.replace(key, dist.get(key) / n);
    }

    return dist;
  }

  /**
   * This function returns the scoring vector given a map of keywords to their associated score.
   *
   * @param scoring the map that determines how much each keyword is worth.
   * @return A list in order of the keywords given in scoring with the value being the points
   *        accrued for that keyword.
   */
  public List<Double> keywordScores(Map<String, Double> scoring) {
    Map<String, Double> pointsVec = new HashMap<>();
    for (String s : scoring.keySet()) {
      pointsVec.put(s, 0.0);
    }

    for (String s : plainText.split(" ")) {
      if (pointsVec.containsKey(s)) {
        pointsVec.replace(s, pointsVec.get(s) + scoring.get(s));
      }
    }

    return new ArrayList<>(pointsVec.values());
  }

  /**
   * Gets the original text from the document. To be used to display the original text for
   * the user to read.
   *
   * @return the original text.
   */
  public String getOriginalText() {
    return originalText;
  }

  /**
   * Filters text for relevant content and converts text into Snippets, separated by paragraphs.
   *
   * @param text Text acquired from a PDF.
   * @return a List of Snippets, each one containing a paragraph.
   */
  public static List<Snippet> parseText(String text) {
    List<Snippet> snippets = new ArrayList<>();
    try (BufferedReader textReader = new BufferedReader(new StringReader(text))) {
      String nextLine;
      StringBuilder currentSnippet = new StringBuilder();
      boolean foundStartOfContent = false;
      final String ABSTRACT = "ABSTRACT", ABSTRACT_2 = "Abstract";
      final Set<String> PARAGRAPH_ENDS = new HashSet<>();
      PARAGRAPH_ENDS.add(".");
      PARAGRAPH_ENDS.add("!");
      PARAGRAPH_ENDS.add("?");
      PARAGRAPH_ENDS.add("\"");
      PARAGRAPH_ENDS.add(":");
      final Set<String> CONTENT_ENDINGS = new HashSet<>();
      CONTENT_ENDINGS.add("References");
      CONTENT_ENDINGS.add("Bibliography");
      CONTENT_ENDINGS.add("Works Cited");
      CONTENT_ENDINGS.add("References Cited");
      CONTENT_ENDINGS.add("Acknowledgments");
      CONTENT_ENDINGS.add("Acknowledgements");
      CONTENT_ENDINGS.add("REFERENCES");
      CONTENT_ENDINGS.add("BIBLIOGRAPHY");
      CONTENT_ENDINGS.add("WORKS CITED");
      CONTENT_ENDINGS.add("REFERENCES CITED");
      CONTENT_ENDINGS.add("ACKNOWLEDGMENTS");
      CONTENT_ENDINGS.add("ACKNOWLEDGEMENTS");

      while ((nextLine = textReader.readLine()) != null) {
        // Apache OpenPDF uses non-breaking spaces, so this replaces it w/ normal whitespaces
        // TODO: This is immutable.
        nextLine.replace(" ", " ");

        // Not all documents contain an abstract. But if they do, only everything starting from
        // the abstract is wanted. So, if an abstract is found, everything before it is removed.
        if (!foundStartOfContent && nextLine.length() >= ABSTRACT.length()) {
          String initialString = nextLine.substring(0, ABSTRACT.length());
          if (initialString.equals(ABSTRACT) || initialString.equals(ABSTRACT_2)) {
            foundStartOfContent = true;
            snippets = new ArrayList<>();
            currentSnippet.setLength(0);
          }
        }

        // Most but not all the documents contain their references, so if one is found,
        // everything after it will be ignored
        boolean foundEnding = false;
        if (CONTENT_ENDINGS.contains(nextLine) || CONTENT_ENDINGS.contains(nextLine + " ")
            || CONTENT_ENDINGS.contains(nextLine + ". ")
            || CONTENT_ENDINGS.contains(nextLine + ": ")) {
          foundEnding = true;
        } else {
          for (String s : CONTENT_ENDINGS) {
            String s1 = s + ". ";
            String s2 = s + ": ";
            if (nextLine.length() >= s1.length()) {
              String nextLine2 = nextLine.substring(0, s1.length());
              if (nextLine2.equals(s1) || nextLine2.equals(s2)) {
                foundEnding = true;
                break;
              }
            }
          }
        }
        if (foundEnding) {
          break;
        }

        // Checks if next line is either empty or just whitespace
        if (nextLine != "" && !nextLine.matches("\\p{javaWhitespace}*")) {
          currentSnippet.append(nextLine);
          String lastChar = nextLine.substring(nextLine.length() - 1);
          // Check if we have reached the end of a paragraph.
          if (PARAGRAPH_ENDS.contains(lastChar)) {
            Snippet paragraph = new Snippet(currentSnippet.toString());
            snippets.add(paragraph);
            // Clear out the StringBuilder for the next paragraph
            currentSnippet.setLength(0);
          } else {
            currentSnippet.append(System.lineSeparator());
          }
        } else if (currentSnippet.length() != 0) { // Line is empty or just contains spaces
          Snippet paragraph = new Snippet(currentSnippet.toString());
          snippets.add(paragraph);
          // Clear out the StringBuilder for the next paragraph
          currentSnippet.setLength(0);
        }
      }
      return snippets;
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid text input");
    }
  }

  /**
   * To String.
   * @return  Original Text.
   */
  @Override
  public String toString() {
    return "Snippet{\"" + originalText + "\"}";
  }

  /**
   * Equals.
   * @param o Other object.
   * @return  True if equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Snippet)) {
      return false;
    }
    Snippet snippet = (Snippet) o;
    return originalText.equals(snippet.originalText);
  }

  /**
   * Hashcode.
   * @return  Hashed Snippet.
   */
  @Override
  public int hashCode() {
    return Objects.hash(originalText);
  }

  public static void main(String[] args) {
    PDFParser parser = new PDFParser();
    for (Snippet s : Snippet.parseText(parser.getText("C:/Users/kwill/Desktop/Temp/BP-Event-in-the-Mediterranean.pdf"))) {
      System.out.println(s.getOriginalText());
      System.out.println();
    }
  }
}
