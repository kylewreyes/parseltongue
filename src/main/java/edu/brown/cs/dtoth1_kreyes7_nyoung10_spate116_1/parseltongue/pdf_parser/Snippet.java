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
   *        accrued for that keyword
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
   * @return the original text
   */
  public String getOriginalText() {
    return originalText;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Snippet snippet = (Snippet) o;
    return plainText.equals(snippet.plainText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plainText);
  }
}
