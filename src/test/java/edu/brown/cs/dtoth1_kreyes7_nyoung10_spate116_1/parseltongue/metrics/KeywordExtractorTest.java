package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Rankable;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankVertex;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class KeywordExtractorTest {
  Map<String, Double> doc1;
  Map<String, Double> doc2;
  Map<String, Double> doc3;
  Map<String, Double> doc4;
  List<Map<String, Double>> testList;

  @Before
  public void setup() {
    doc1 = new HashMap<>();
    doc2 = new HashMap<>();
    doc3 = new HashMap<>();
    doc4 = new HashMap<>();
    testList = new ArrayList<>();

    doc1.put("1", 0.4);
    doc2.put("1", 0.4);
    doc3.put("1", 0.4);
    doc4.put("1", 0.4);

    doc1.put("2", 0.2);
    doc2.put("2", 0.3);
    doc3.put("2", 0.1);

    doc1.put("3", 0.01);
    doc2.put("3", 0.01);

    doc1.put("6", 0.1);
    doc2.put("6", 0.1);

    doc3.put("4", 0.01);
    doc4.put("4", 0.01);

    doc1.put("5", 1.0);
  }

  @After
  public void tearDown() {
    doc1 = null;
    doc2 = null;
    doc3 = null;
    doc4 = null;
  }

  @Test
  public void basicKeywordTest() {
    testList.add(doc1);
    testList.add(doc2);
    testList.add(doc3);
    testList.add(doc4);
    StatisticalKeywordExtractor k = new StatisticalKeywordExtractor();
    Map<String, Double> res = k.extractKeywords(Arrays.asList("1", "3"), testList);
    assertTrue(res.containsKey("3"));
    //assertTrue(res.containsKey("2"));
    assertFalse(res.containsKey("1"));
    assertFalse(res.containsKey("6"));
  }
}
