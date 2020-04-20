package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.util.List;

/**
 * RelevanceMetric interface for calculating the relevance between two documents based
 * on the passed keyword vectors.
 */
public interface RelevanceMetric {
  /**
   * function which given two vectors which represent the occurrence of keywords in document,
   * return a double which represents the relevance of the two documents to each other.
   * Assumes that the keyword vectors have the same mapping order. i.e. the keyword
   * for the first entry of the two vectors are the same.
   * @param doc1 vector representing keyword presence in first document
   * @param doc2 vector representing keyword presence in second document
   * @return the relevance of the two documents to each other.
   */
  double calculateRelevance(List<Double> doc1, List<Double> doc2);
}
