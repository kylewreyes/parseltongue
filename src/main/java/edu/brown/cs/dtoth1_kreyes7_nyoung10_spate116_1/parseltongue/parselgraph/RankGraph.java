package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parselgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.keyword.KeywordExtractor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RankGraph implements Graph<RankVertex, RankEdge, RankMetadata> {
  private Map<String, Double> keywords;
  public RankGraph(List<List<String>> rawCoreText, String rawQuery) {

    keywords = KeywordExtractor.extractKeywords(null, null);
  }
  @Override
  public RankVertex getVertex(String id) {
    return null;
  }
  @Override
  public boolean containsVertex(String id) {
    return false;
  }
  @Override
  public List<RankVertex> getVertices() {
    return null;
  }
  @Override
  public Set<RankEdge> getIncoming(RankVertex target) {
    return null;
  }
}
