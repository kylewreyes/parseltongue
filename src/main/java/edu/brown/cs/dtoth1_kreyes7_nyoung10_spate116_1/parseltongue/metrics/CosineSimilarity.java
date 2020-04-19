package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;
import static edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.vectorOperators.VectorOperations.dot;
import static edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.vectorOperators.VectorOperations.norm2;

/**
 * Relevance metric based on cosine similarity for calculating the similarity of two
 * documents based on their keyword vectors.
 */
public class CosineSimilarity implements RelevanceMetric {
  @Override
  public double calculateRelevance(double[] doc1, double[] doc2) {
    return dot(doc1, doc2)/Math.max(norm2(doc1),norm2(doc2));
  }
}
