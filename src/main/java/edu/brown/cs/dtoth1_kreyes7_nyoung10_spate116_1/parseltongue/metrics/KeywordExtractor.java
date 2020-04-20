package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class KeywordExtractor {
  private KeywordExtractor() { }

  /**
   * Function which extracts keywords from a set of input.
   * @param keywords input keywords from query
   * @param documents set of snippets with words mapped to occurance
   * @return a set of useful keywords linked with their weight
   */
  public static Map<String, Double> extractKeywords(
          List<String> keywords, List<Map<String, Double>> documents) {
    Map<String, Double> allWords = new HashMap<>();
    Map<String, Double> keywordHeuristics = new HashMap<>();
    double avgCounter = 0;
    for (String keyword : keywords) {
      double keywordTfidf = tfidf(keyword, documents);
      avgCounter += keywordTfidf;
      keywordHeuristics.put(keyword, keywordTfidf);
    }
    final double avgfKeywordTfidf = avgCounter / keywords.size();
    keywordHeuristics.values().removeIf(tfidf -> tfidf < avgfKeywordTfidf);
    for (Map<String, Double> doc : documents) {
      for (String word : doc.keySet()) {
        if (!allWords.keySet().contains(word)) {
          allWords.put(word, (double) 0);
        }
      }
    }
    avgCounter = 0;
    for (String word : allWords.keySet()) {
      double tandumFreq = ttfidf(keywordHeuristics, word, documents);
      avgCounter += tandumFreq;
      allWords.put(word, tandumFreq);
    }
    double avgTandumFreq = avgCounter / allWords.size();
    avgCounter = 0;
    for (Double ttfidf : allWords.values()) {
      avgCounter += (ttfidf - avgTandumFreq) * (ttfidf - avgTandumFreq);
    }
    final double ttidfStd = Math.sqrt(avgCounter / allWords.size());
    allWords.values().removeIf(val -> val < avgTandumFreq + 2 * ttidfStd);
    keywordHeuristics.putAll(allWords);
    return keywordHeuristics;
  }
  private static double tfidf(String word, List<Map<String, Double>> documents) {
    int docFreq = 0;
    double tf = 0;
    for (Map<String, Double> doc : documents) {
      if (doc.containsKey(word)) {
        docFreq++;
        tf += doc.get(word);
      }
    }
    double idf =  Math.max(Math.log(documents.size() / (0.5 + docFreq)), 0);
    return tf * idf;
  }
  //tandem term frequency inverse document frequency
  private static double ttfidf(
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
            / (Math.abs(independentFreq - dependantFreq) + 0.5);
    double idf = Math.max(Math.log(documents.size() / (0.5 + docFreq)), 0);
    return ttf * idf;
  }
}
