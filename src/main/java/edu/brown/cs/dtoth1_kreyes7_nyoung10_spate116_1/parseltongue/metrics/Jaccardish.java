package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.util.List;

/**
 * Method for calculating Document similarity loosely based on the Jaccard index method.
 */
public class Jaccardish implements RelevanceMetric {
  @Override
  public double calculateRelevance(List<Double> doc1, List<Double> doc2) throws IllegalArgumentException {
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
