package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keyword Extractor Class for generating a set of useful keywords from a set of documents and
 * some predetermined primary keywords. Uses Statistical Methods.
 */
public final class StatisticalKeywordExtractor implements KeywordExtractor {
  private static final double OFFSET = 0.5;
  private static final double PRIMARY_KEYWORD_WEIGHT = 1.25;
  /**
   * Function which extracts keywords from a set of input.
   *
   * @param keywords  input keywords from query
   * @param documents set of snippets with words mapped to occurance
   * @return a set of useful keywords linked with their weight
   */
  @Override
  public Map<String, Double> extractKeywords(
      List<String> keywords, List<Map<String, Double>> documents) {
    Map<String, Double> allWords = new HashMap<>();
    Map<String, Double> keywordHeuristics = new HashMap<>();
    //Calculate statistical values for primary keywords
    double avgCounter = 0;
    for (String keyword : keywords) {
      double keywordIdf = primaryKeywordMetric(keyword, documents);
      avgCounter += keywordIdf;
      keywordHeuristics.put(keyword, keywordIdf);
    }
    //Filter primary keywords
    final double avgfKeywordIdf = avgCounter / keywords.size();
    keywordHeuristics.values().removeIf(idf -> idf < avgfKeywordIdf);
    //Generate a set of all words in the documents
    for (Map<String, Double> doc : documents) {
      for (String word : doc.keySet()) {
        if (!allWords.keySet().contains(word)) {
          allWords.put(word, (double) 0);
        }
      }
    }
    //Calculate tandem frequency for all words
    avgCounter = 0;
    for (String word : allWords.keySet()) {
      if (!word.matches(".*\\d.*")) {
        double tandumFreq = secondaryKeywordWeight(keywordHeuristics, word, documents);
        avgCounter += tandumFreq;
        allWords.put(word, tandumFreq);
      }
    }
    //calculate average and standard deviation of tandem frequency
    double avgTandumFreq = avgCounter / allWords.size();
    avgCounter = 0;
    for (Double ttfidf : allWords.values()) {
      avgCounter += (ttfidf - avgTandumFreq) * (ttfidf - avgTandumFreq);
    }
    final double ttidfStd = Math.sqrt(avgCounter / allWords.size());
    //Filter all words by tandem frequency to generate secondary keywords
    allWords.values().removeIf(val -> val < avgTandumFreq + 5 * ttidfStd);
    allWords.keySet().removeIf(word -> word.length() < 4);
    //Create final set of keywords and return
    keywordHeuristics.replaceAll((s, w) -> PRIMARY_KEYWORD_WEIGHT * w);
    keywordHeuristics.putAll(allWords);
    return keywordHeuristics;
  }

  /**
   * Primary keyword metric. Scores a keyword based on its term frequency inverse document
   * frequency value. Words are more important are given a higher score.
   *
   * @param word      word to score
   * @param documents documents to base score from
   * @return a double score of the keyword in the list of documents
   */
  private static double primaryKeywordMetric(String word, List<Map<String, Double>> documents) {
    int docFreq = 0;
    double tf = 0;
    for (Map<String, Double> doc : documents) {
      if (doc.containsKey(word)) {
        docFreq++;
        tf += doc.get(word);
      }
    }
    double idf = Math.max(Math.log(documents.size() / (OFFSET + docFreq)), 0);
    return tf * idf * idf;
  }

  /**
   * Calculates the tandem term frequency inverse document frequency of a words based on
   * a list of primary keywords and a set of document data. Words that are semi-covariant
   * with the keywords are given a higher score.
   *
   * @param keywords  list of keywords to use
   * @param word      word to score
   * @param documents documents to score from
   * @return ^.
   */
  private static double secondaryKeywordWeight(
      Map<String, Double> keywords, String word, List<Map<String, Double>> documents) {
    double dependantFreq = 0;
    double independentFreq = 0;
    int docFreq = 0;
    for (Map<String, Double> doc : documents) {
      if (doc.containsKey(word)) {
        docFreq++;
        for (String keyword : keywords.keySet()) {
          if (doc.containsKey(keyword)) {
            dependantFreq += doc.get(word) * keywords.get(keyword);
          } else {
            independentFreq += doc.get(word) * keywords.get(keyword);
          }
        }
      }
    }
    double ttf = Math.sqrt(independentFreq * dependantFreq)
        / (Math.abs(independentFreq - dependantFreq) + OFFSET);
    double idf = Math.max(Math.log(documents.size() / (OFFSET + docFreq)), 0);
    return ttf * idf;
  }
}
