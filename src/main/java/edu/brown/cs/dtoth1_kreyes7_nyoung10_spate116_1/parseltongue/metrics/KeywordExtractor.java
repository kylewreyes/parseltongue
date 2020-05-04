package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface for Keyword Extraction Strategies.
 */
public interface KeywordExtractor extends Serializable {
  /**
   * Method which takes in list of primary keywords and the document corpus
   * and returns a list of keywords with an associated weight.
   * @param keywords  Initial Keywords
   * @param documents Document Corpus
   * @return          Final keywords associated with a weight
   */
  Map<String, Double> extractKeywords(
          List<String> keywords, List<Map<String, Double>> documents);
}
