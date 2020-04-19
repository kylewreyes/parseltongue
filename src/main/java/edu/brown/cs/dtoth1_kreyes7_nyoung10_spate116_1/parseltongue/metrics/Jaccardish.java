package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

/**
 * Method for calculating Document similarity loosely based on the Jaccard index method.
 */
public class Jaccardish implements RelevanceMetric {
  @Override
  public double calculateRelevance(double[] doc1, double[] doc2) throws IllegalArgumentException {
    if (doc1.length != doc2.length) {
      throw new IllegalArgumentException("Document keyword vectors not of same size");
    }
    double union = 0;
    double intersect = 0;
    for (int i = 0; i < doc1.length; i++) {
      union += Math.max(doc1[i], doc2[i]);
      intersect += Math.min(doc1[i], doc2[i]);
    }
    if (union == 0) {
      return 0;
    } else {
      return intersect / union;
    }
  }
}
