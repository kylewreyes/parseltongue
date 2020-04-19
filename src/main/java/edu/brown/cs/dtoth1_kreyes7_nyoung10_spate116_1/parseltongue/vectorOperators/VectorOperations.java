package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.vectorOperators;

/**
 * Utility class for linear algebra operations on java double[]
 */
public class VectorOperations {
  /**
   * Method for calculating the dot product between two vectors.
   * @param v2 first vector
   * @param v1 second vector
   * @return the dot product of the first and second vector
   * @throws IllegalArgumentException thrown when the vectors do not have same size
   */
  public static double dot(double[] v2, double[] v1) throws IllegalArgumentException{
    if (v2.length != v1.length) {
      throw new IllegalArgumentException("Vectors to dot not of same size");
    }
    double ret = 0;
    for(int i = 0; i < v1.length; i++) {
      ret += v1[i]*v2[i];
    }
    return ret;
  }

  /**
   * Method for getting the magnitude (norm2) of a vector.
   * @param v vector to get magnitude of
   * @return the norm2 magnitude of the vector passed
   */
  public static double norm2(double[] v) {
    double ret = 0;
    for(double d : v){
      ret += d*d;
    }
    return Math.sqrt(ret);
  }
}
