package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;


import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.Stemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * An object containing chunks of text to be used for.
 */
public class Snippet implements Serializable {
  private String plainText, originalText, file;
  private static final double NORM_WEIGHT = 0.95;
  private int pageNum;

  /**
   * @param text the original text from the document.
   */
  public Snippet(String text) {
    originalText = text;
  }

  /**
   * @param text the original text from the document.
   * @param file the name of the file that this Snippet originates from.
   */
  public Snippet(String text, String file) {
    originalText = text;
    this.file = file;
  }

  /**
   * @param text    the original text from the document.
   * @param file    the name of the file that this Snippet originates from.
   * @param pageNum the page number that this Snippet is from
   */
  public Snippet(String text, String file, int pageNum) {
    originalText = text;
    this.file = file;
    this.pageNum = pageNum;
  }

  /**
   * Gets the page number of the page that this Snippet originates from. Note that indexing
   * starts at 1. If this Snippet is not from a source that contains page numbers, the output is 0.
   *
   * @return the page number
   */
  public int getPageNum() {
    return pageNum;
  }

  /**
   * @return the name of the file that this Snippet is from
   */
  public String getFileName() {
    return file;
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
   * Stems plaintext based on provided strategy.
   *
   * @param stemmer stemming strategy
   */
  public void stemPlainText(Stemmer stemmer) {
    if (plainText == null) {
      getPlainText();
    }
    StringBuilder sb = new StringBuilder();
    for (String s : plainText.split(" ")) {
      sb.append(stemmer.stemWord(s)).append(" ");
    }
    if (sb.length() != 0) {
      // Removes the last space
      sb.deleteCharAt(sb.length() - 1);
    }
    plainText = sb.toString();
  }

  /**
   * Gets the distribution for words.
   *
   * @return the distribution as a {@link HashMap}.
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
      dist.replace(key, dist.get(key) / Math.pow(n, NORM_WEIGHT));
    }

    return dist;
  }

  /**
   * This function returns the scoring vector given a map of keywords to their associated score.
   *
   * @param scoring the map that determines how much each keyword is worth.
   * @return A list in order of the keywords given in scoring with the value being the points
   * accrued for that keyword.
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

  private static boolean matchesHeading(String heading, Set<String> validHeadings) {
    for (String s : validHeadings) {
      // String followed by zero or more whitespaces
      // String followed by whitespace then a capital letter (then any more characters)
      // String followed by a period/colon (then any more characters)
      if (heading.matches(s + "\\s*") || heading.matches(s + " \\p{javaUpperCase}.*")
          || heading.matches(s + "\\..*") || heading.matches(s + ":.*")) {
        return true;
      }
    }
    return false;
  }

  private static boolean matchesAbstract(String line) {
    final int abstractLength = "Abstract".length();
    final Set<String> abstractTags = new HashSet<>();
    abstractTags.add("Abstract");
    abstractTags.add("ABSTRACT");
    if (line.length() >= abstractLength) {
      return matchesHeading(line, abstractTags);
    }
    return false;
  }

  private static boolean matchesCaption(String line) {
    final int captionLength = "Fig. 1".length();
    final Set<String> captionTags = new HashSet<>();
    captionTags.add("Table (\\d)+(\\p{Lower})?");
    captionTags.add("Figure (\\d)+(\\p{Lower})?");
    captionTags.add("Fig\\. (\\d)+(\\p{Lower})?");
    captionTags.add("Map\\. (\\d)+(\\p{Lower})?");
    if (line.length() >= captionLength) {
      return matchesHeading(line, captionTags);
    }
    return false;
  }

  private static boolean matchesDocumentEnd(String line) {
    final int endingTagLength = "References".length();
    final Set<String> documentEndings = new HashSet<>();
    documentEndings.add("References");
    documentEndings.add("Bibliography");
    documentEndings.add("Works Cited");
    documentEndings.add("References Cited");
    documentEndings.add("Acknowledgments");
    documentEndings.add("Acknowledgements");
    documentEndings.add("REFERENCES");
    documentEndings.add("BIBLIOGRAPHY");
    documentEndings.add("WORKS CITED");
    documentEndings.add("REFERENCES CITED");
    documentEndings.add("ACKNOWLEDGMENTS");
    documentEndings.add("ACKNOWLEDGEMENTS");
    if (line.length() >= endingTagLength) {
      return matchesHeading(line, documentEndings);
    }
    return false;
  }

  /**
   * Filters text for relevant content and converts text into Snippets, separated by paragraphs.
   *
   * @param text Text acquired from a {@link SourceParser}.
   * @param file The name of the file that this Snippet is acquired from
   * @return a List of Snippets, each one containing a paragraph
   */
  public static List<Snippet> parseText(String text, String file) {
    return getSnippetObject(text, file, Optional.empty(), Optional.empty(), Optional.empty())
        .getText();
  }

  /**
   * Splits up a {@link String} into Snippets.
   *
   * @param text    the {@link String} to be split up
   * @param file    the name of the file that text originated from
   * @param pageNum an {@link Optional} that specifies if text is from a specific page of the source
   * @return Snippet Object.
   */
  private static SnippetObject getSnippetObject(String text, String file,
                                                Optional<Integer> pageNum,
                                                Optional<StringBuilder> previousSnippet,
                                                Optional<Integer> previousSnippetPage) {
    final int minSnippetLength = 20;
    final Set<String> paragraphEnds = new HashSet<>();
    paragraphEnds.add(".");
    paragraphEnds.add("!");
    paragraphEnds.add("?");
    paragraphEnds.add("\"");
    List<Snippet> snippets = new ArrayList<>();
    try (BufferedReader textReader = new BufferedReader(new StringReader(text))) {
      String nextLine;
      StringBuilder currentSnippet = new StringBuilder();
      if (!previousSnippet.isEmpty()) {
        currentSnippet = previousSnippet.get();
      }
      boolean foundStartOfContent = false, foundEnding = false, foundCaption = false,
          usedPreviousSnippet = false;
      while ((nextLine = textReader.readLine()) != null) {
        // Apache OpenPDF uses non-breaking spaces, so this replaces it w/ normal whitespaces
        // Also gets rid of leading whitespace that might be added to the end of a line.
        nextLine = nextLine.replaceAll("\\u00A0", " ").trim();

        // Not all documents contain an abstract. But if they do, only everything starting from
        // the abstract is wanted. So, if an abstract is found, everything before it is removed.
        if (!foundStartOfContent && matchesAbstract(nextLine)) {
          foundStartOfContent = true;
          snippets = new ArrayList<>();
          currentSnippet.setLength(0);
        }

        // Most but not all the documents contain their references, so if one is found,
        // everything after it will be ignored
        if (matchesDocumentEnd(nextLine) && !pageNum.isEmpty() && pageNum.get() >= 2) {
          // Check to see if currentSnippet contains anything
          if (currentSnippet.length() >= minSnippetLength) {
            Snippet paragraph;
            if (pageNum.isEmpty()) {
              paragraph = new Snippet(currentSnippet.toString(), file);
            } else {
              // Check if there a previous preexisting Snippet from a previous page
              if (!previousSnippetPage.isEmpty() && !usedPreviousSnippet) {
                usedPreviousSnippet = true;
                paragraph =
                    new Snippet(currentSnippet.toString(), file, previousSnippetPage.get());
              } else {
                paragraph = new Snippet(currentSnippet.toString(), file, pageNum.get());
              }
            }
            snippets.add(paragraph);
          } else { // The Snippet is too short to be relevant, so it is not added to the list.
            currentSnippet.setLength(0);
          }
          foundEnding = true;
          break;
        }
        // Checks if this is part of a Figure or Table caption. If it is, ignore everything else
        // until a paragraph ending is found.
        if (!foundCaption) {
          foundCaption = matchesCaption(nextLine);
        }

        // Checks if next line contains actual characters
        if (nextLine != "" && !nextLine.matches("\\p{javaWhitespace}*")) {
          // If a caption is found, everything is ignored until a paragraph ender is found.
          if (!foundCaption) {
            currentSnippet.append(nextLine);
          }
          String lastChar = nextLine.substring(nextLine.length() - 1);
          // Check if we have reached the end of a paragraph.
          if (paragraphEnds.contains(lastChar)) {
            Snippet paragraph;
            if (!foundCaption && currentSnippet.length() >= minSnippetLength) {
              if (pageNum.isEmpty()) {
                paragraph = new Snippet(currentSnippet.toString(), file);
              } else {
                // Check if there a previous preexisting Snippet from a previous page
                if (!previousSnippetPage.isEmpty() && !usedPreviousSnippet) {
                  usedPreviousSnippet = true;
                  paragraph =
                      new Snippet(currentSnippet.toString(), file, previousSnippetPage.get());
                } else {
                  paragraph = new Snippet(currentSnippet.toString(), file, pageNum.get());
                }
              }
              snippets.add(paragraph);
            }
            // After adding a Snippet (or finding a Snippet that is too short), reset the
            // StringBuilder
            currentSnippet.setLength(0);
            // Always set foundCaption to false at the end of a paragraph
            foundCaption = false;
          } else if (!foundCaption) {
            // Appends a line separator to the end of a legitimate line of text.
            currentSnippet.append(System.lineSeparator());
          }
        }
      }
      if (currentSnippet.length() == 0) {
        return new SnippetObject(foundStartOfContent, snippets, foundEnding, Optional.empty(),
            Optional.empty());
      } else {
        if (pageNum.isEmpty()) {
          snippets.add(new Snippet(currentSnippet.toString(), file));
          return new SnippetObject(foundStartOfContent, snippets, foundEnding, Optional.empty(),
              Optional.empty());
        }
        return new SnippetObject(foundStartOfContent, snippets, foundEnding,
            Optional.of(currentSnippet), pageNum);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid text input");
    }
  }

  /**
   * A temporary object to store a List<Snippet> from a page of a source and whether or not an
   * Abstract and References section was found in this page.
   */
  private static class SnippetObject {
    private boolean foundAbstract, foundEnding;
    private List<Snippet> text;
    private Optional<StringBuilder> previousSnippet;
    private Optional<Integer> previousSnippetPage;

    SnippetObject(boolean foundAbstract, List<Snippet> text, boolean foundEnding,
                  Optional<StringBuilder> previousSnippet, Optional<Integer> previousSnippetPage) {
      this.foundAbstract = foundAbstract;
      this.text = text;
      this.foundEnding = foundEnding;
      this.previousSnippet = previousSnippet;
      this.previousSnippetPage = previousSnippetPage;
    }

    public List<Snippet> getText() {
      return text;
    }

    public boolean foundAbstract() {
      return foundAbstract;
    }

    public boolean foundEnding() {
      return foundEnding;
    }

    public Optional<StringBuilder> getPreviousSnippet() {
      return previousSnippet;
    }

    public Optional<Integer> getPreviousSnippetPage() {
      return previousSnippetPage;
    }
  }

  /**
   * Filters text for relevant content and converts text into Snippets, separated by paragraphs.
   *
   * @param pages Text acquired from a {@link SourceParser}, separated by page.
   * @param file  The name of the file that this Snippet is acquired from
   * @return a List of Snippets, each one containing a paragraph
   */
  public static List<Snippet> parseText(List<String> pages, String file) {
    List<Snippet> snippets = new ArrayList<>();
    Optional<StringBuilder> previousSnippet = Optional.empty();
    Optional<Integer> previousSnippetPage = Optional.empty();
    for (int i = 1; i <= pages.size(); i++) {
      SnippetObject page = getSnippetObject(pages.get(i - 1), file, Optional.of(i), previousSnippet,
          previousSnippetPage);
      if (page.foundAbstract()) {
        snippets = new ArrayList<>();
      }
      snippets.addAll(page.getText());
      if (page.foundEnding()) {
        break;
      }
      previousSnippet = page.getPreviousSnippet();
      previousSnippetPage = page.getPreviousSnippetPage();
    }
    // Check to see if the last page has an unadded Snippet
    if (!previousSnippet.isEmpty()) {
      snippets.add(new Snippet(previousSnippet.get().toString(), file, previousSnippetPage.get()));
    }
    return snippets;
  }

  @Override
  public String toString() {
    return "Snippet{\"" + originalText + "\"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Snippet)) {
      return false;
    }
    Snippet snippet = (Snippet) o;
    return getPageNum() == snippet.getPageNum()
        && getOriginalText().equals(snippet.getOriginalText())
        && Objects.equals(file, snippet.file);
  }

  /**
   * Hashcode.
   *
   * @return Hashed snippet.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getOriginalText(), file, getPageNum());
  }
}
