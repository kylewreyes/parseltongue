package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

/**
 * An object containing chunks of text to be used for
 * {@link edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank}
 */
public class Snippet {
  private String plainText, originalText;

  /**
   * @param text the original text from the document
   */
  public Snippet(String text) {
    originalText = text;
  }

  /**
   * Filters through the original text for non-alphanumeric characters
   *
   * @return the filtered text
   */
  public String getPlainText() {
    if (plainText == null) {
      String[] splitText = originalText.split("[^\\p{Alnum}]]*");
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
   * Gets the original text from the document. To be used to display the original text for
   * the user to read
   *
   * @return the original text
   */
  public String getoriginalText() {
    return originalText;
  }
}
