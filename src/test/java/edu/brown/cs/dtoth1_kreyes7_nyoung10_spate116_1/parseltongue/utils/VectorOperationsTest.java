package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

public class VectorOperationsTest {
  List<Double> v1 = List.of(1.0, 0.0, 0.0, 1.0);
  List<Double> v2 = List.of(0.0, 1.0, 1.0, 0.0);
  List<Double> v3 = List.of(0.0, 0.0, 0.0, 0.0);
  List<Double> v4 = List.of(1.0, 1.0, 1.0, 1.0);
  List<Double> v5 = List.of(-2.0, 1.0, -3.0, 5.0);

  @Test
  public void dotTest() {
    assertEquals(VectorOperations.dot(v1,v2),0,0.00001);
    assertEquals(VectorOperations.dot(v4,v3),0,0.00001);
    assertEquals(VectorOperations.dot(v4,v5),1,0.00001);
    assertEquals(VectorOperations.dot(v4,v1),2,0.00001);
    assertEquals(VectorOperations.dot(v4,v4),4,0.00001);
    assertEquals(VectorOperations.dot(v1,v5),3,0.00001);
  }
  @Test
  public void norm2Test() {
    assertEquals(VectorOperations.norm2(v1),Math.sqrt(2),0.00001);
    assertEquals(VectorOperations.norm2(v2),Math.sqrt(2),0.00001);
    assertEquals(VectorOperations.norm2(v3),0,0.00001);
    assertEquals(VectorOperations.norm2(v4),2,0.00001);
    assertEquals(VectorOperations.norm2(v5),Math.sqrt(4 + 1 + 9 + 25),0.00001);
  }
}
