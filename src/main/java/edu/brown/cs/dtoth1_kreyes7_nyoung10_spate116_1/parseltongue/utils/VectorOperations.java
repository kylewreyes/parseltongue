package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

import java.util.List;

/**
 * Utility class for linear algebra operations on java double[].
 */
public final class VectorOperations {
  private VectorOperations() { }

  /**
   * Method for calculating the dot product between two vectors.
   * @param v2 first vector
   * @param v1 second vector
   * @return the dot product of the first and second vector
   * @throws IllegalArgumentException thrown when the vectors do not have same size
   */
  public static double dot(List<Double> v2, List<Double> v1) throws IllegalArgumentException {
    if (v2.size() != v1.size()) {
      throw new IllegalArgumentException("Vectors to dot not of same size");
    }
    double ret = 0;
    for (int i = 0; i < v1.size(); i++) {
      ret += v1.get(i) * v2.get(i);
    }
    return ret;
  }

  /**
   * Method for getting the magnitude (norm2) of a vector.
   * @param v vector to get magnitude of
   * @return the norm2 magnitude of the vector passed
   */
  public static double norm2(List<Double> v) {
    double ret = 0;
    for (double d : v) {
      ret += d * d;
    }
    return Math.sqrt(ret);
  }
}
