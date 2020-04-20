package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parselgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.keyword.KeywordExtractor;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.RelevanceMetric;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RankGraph<M extends RelevanceMetric> implements
    Graph<RankVertex, RankEdge, RankMetadata> {
  private Map<String, Double> keywordDistribution;
  private RelevanceMetric metric;
  private List<RankVertex> nodes;

  public RankGraph(List<Snippet> rawCoreText, List<String> keywords, RelevanceMetric metric) {
    this.metric = metric;
    List<Map<String, Double>> dist = new ArrayList<>();
    for (Snippet s : rawCoreText) {
      nodes.add(new RankVertex());
      dist.add(s.distribution());
    }

    keywordDistribution = KeywordExtractor.extractKeywords(keywords, dist);
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
