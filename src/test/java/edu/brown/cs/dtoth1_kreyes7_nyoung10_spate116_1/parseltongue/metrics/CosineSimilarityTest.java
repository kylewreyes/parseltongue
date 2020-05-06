package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

public class CosineSimilarityTest {
  CosineSimilarity cs = new CosineSimilarity();
  @Test
  public void CosineTest() {
    List<Double> v1 = List.of(1.0, 0.0, 0.0, 1.0);
    List<Double> v2 = List.of(0.0, 1.0, 1.0, 0.0);
    List<Double> v3 = List.of(0.0, 0.0, 0.0, 0.0);
    List<Double> v4 = List.of(1.0, 1.0, 1.0, 1.0);
    assertEquals(cs.calculateRelevance(v3,v4),0,0.00001);
    assertEquals(cs.calculateRelevance(v1,v2),0,0.00001);
    assertEquals(cs.calculateRelevance(v1,v1), Math.sqrt(2),0.00001);
    assertEquals(cs.calculateRelevance(v1,v4), Math.sqrt(2),0.00001);
    assertEquals(cs.calculateRelevance(v3,v3),0,0.00001);
  }
}
