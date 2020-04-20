package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

import java.util.*;

/**
 * An object containing chunks of text to be used for
 * {@link edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank}.
 */
public class Snippet {
  private String plainText;
  private final String originalText;

  /**
   * @param text the original text from the document
   */
  public Snippet(String text) {
    originalText = text;
  }

  /**
   * Filters through the original text for non-alphanumeric characters.
   *
   * @return the filtered text
   */
  public String getPlainText() {
    if (plainText == null) {
      String[] splitText = originalText.split("[^\\p{Alnum}]]*");
      StringBuilder builder = new StringBuilder();
      for (String s : splitText) {
        if (!s.equals("")) {
          builder.append(s).append(" ");
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
   * @return the distribution as a hashmap
   */
  public Map<String, Double> distribution() {
    Map<String, Double> dist = new HashMap<>();
    Set<String> uniqueWords = new HashSet<>(Arrays.asList(plainText.split(" ")));
    for (String key : uniqueWords) {
      dist.put(key, 0.0);
    }

    for (String word : plainText.split(" ")) {
      dist.put(word, dist.get(word) + 1);
    }

    int n = plainText.split(" ").length;
    for (String key : uniqueWords) {
      dist.put(key, dist.get(key) / n);
    }

    return dist;
  }

  /**
   * Gets the original text from the document. To be used to display the original text for
   * the user to read.
   *
   * @return the original text
   */
  public String getOriginalText() {
    return originalText;
  }
}
