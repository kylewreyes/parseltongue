package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.util.List;

import static edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.VectorOperations.dot;
import static edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.VectorOperations.norm2;

/**
 * Relevance metric based on cosine similarity for calculating the similarity of two
 * documents based on their keyword vectors.
 */
public class CosineSimilarity implements RelevanceMetric {
  /**
   * Calculates the relevance between two keywords. TODO: Complete Docs
   * @param doc1 vector representing keyword presence in first document
   * @param doc2 vector representing keyword presence in second document
   * @return  relevance constant.
   */
  @Override
  public double calculateRelevance(List<Double> doc1, List<Double> doc2) {
    return dot(doc1, doc2) / Math.max(norm2(doc1), norm2(doc2));
  }
}
