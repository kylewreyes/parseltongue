package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.KeywordExtractor;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.RelevanceMetric;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.Snippet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RankGraph<M extends RelevanceMetric> implements
    Graph<RankVertex, RankEdge, RankMetadata> {
  private List<RankVertex> nodes = new ArrayList<>();

  public RankGraph(List<Snippet> rawCoreText, List<String> keywords, RelevanceMetric metric) {
    List<Map<String, Double>> dist = new ArrayList<>();
    for (Snippet s : rawCoreText) {
      nodes.add(new RankVertex(new RankMetadata(s)));
      dist.add(s.distribution());
    }

    Map<String, Double> keywordDistribution = KeywordExtractor.extractKeywords(keywords, dist);

    List<List<Double>> keywordScoring = new ArrayList<>(nodes.size());
    for (int i = 0; i < nodes.size(); i++) {
      keywordScoring.set(i, rawCoreText.get(i).keywordScores(keywordDistribution));
    }

    List<RankEdge> edges = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      for (int j = i; j < nodes.size(); j++) {
        double weight = metric.calculateRelevance(keywordScoring.get(i), keywordScoring.get(j));
        edges.add(new RankEdge(nodes.get(i), nodes.get(j), weight));
        edges.add(new RankEdge(nodes.get(j), nodes.get(i), weight));
      }
    }

    edges = runImputation(edges, 1);
    for (RankEdge e : edges) {
      e.getSource().addEdge(e);
    }
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

  private List<RankEdge> runImputation(List<RankEdge> edges, double zscore) {
    List<RankEdge> finalEdges = new ArrayList<>();

    double mean = 0.0;
    for (RankEdge e : edges) {
      mean += e.getWeight();
    }
    mean /= edges.size();

    double stdev = 0.0;
    for (RankEdge e : edges) {
      stdev += (mean - e.getWeight()) * (mean - e.getWeight());
    }
    stdev /= edges.size();
    stdev = Math.sqrt(stdev);

    for (RankEdge e : edges) {
      if (e.getWeight() >= mean + stdev * zscore) {
        finalEdges.add(e);
      }
    }

    return finalEdges;
  }
}
