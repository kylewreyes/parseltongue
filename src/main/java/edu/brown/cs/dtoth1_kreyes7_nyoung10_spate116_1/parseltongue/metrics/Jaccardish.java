package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.io.Serializable;
import java.util.List;

/**
 * Method for calculating Document similarity loosely based on the Jaccard index method.
 */
public class Jaccardish implements RelevanceMetric, Serializable {
  /**
   * Calculates relevance between two keywords.
   *
   * @param doc1 vector representing keyword presence in first document
   * @param doc2 vector representing keyword presence in second document
   * @return relevance.
   * @throws IllegalArgumentException Exception.
   */
  @Override
  public double calculateRelevance(List<Double> doc1, List<Double> doc2)
      throws IllegalArgumentException {
    if (doc1.size() != doc2.size()) {
      throw new IllegalArgumentException("Document keyword vectors not of same size");
    }
    double union = 0;
    double intersect = 0;
    for (int i = 0; i < doc1.size(); i++) {
      union += Math.max(doc1.get(i), doc2.get(i));
      intersect += Math.min(doc1.get(i), doc2.get(i));
    }
    if (union == 0) {
      return 0;
    } else {
      return intersect / union;
    }
  }
}
