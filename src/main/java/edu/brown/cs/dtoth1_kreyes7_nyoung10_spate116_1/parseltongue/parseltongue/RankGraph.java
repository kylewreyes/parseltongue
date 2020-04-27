package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.KeywordExtractor;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.RelevanceMetric;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Rank Graph Class! TODO: Complete Docs.
 */
public class RankGraph implements
    Graph<RankVertex, RankEdge, RankMetadata> {
  private List<RankVertex> nodes = new ArrayList<>();
  private PageRank<RankGraph, RankVertex, RankEdge, RankMetadata> pRank = new PageRank<>(this);
  private Map<RankVertex, Set<RankEdge>> inboundMap = new HashMap<>();

  /**
   * Constructor TODO: Complete Docs.
   *
   * @param rawCoreText rawCoreText.
   * @param keywords  keywords.
   * @param metric  metric.
   */
  public RankGraph(List<Snippet> rawCoreText, List<String> keywords, RelevanceMetric metric) {
    List<Map<String, Double>> dist = new ArrayList<>();
    for (Snippet s : rawCoreText) {
      nodes.add(new RankVertex(new RankMetadata(s)));
      dist.add(s.distribution());
    }

    Map<String, Double> keywordDistribution = KeywordExtractor.extractKeywords(keywords, dist);

    List<List<Double>> keywordScoring = new ArrayList<>(nodes.size());
    for (int i = 0; i < nodes.size(); i++) {
      keywordScoring.add(rawCoreText.get(i).keywordScores(keywordDistribution));
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

    for (RankVertex v : nodes) {
      inboundMap.put(v, new HashSet<>());
    }

    for (RankEdge e : edges) {
      inboundMap.get(e.getDest()).add(e);
    }
  }

  /**
   * rank TODO: Complete Docs.
   *
   * @return  List of Snippets.
   */
  public List<Snippet> rank() {
    List<RankVertex> metaDataRanked = pRank.pageRank();
    List<Snippet> returnList = new ArrayList<>();
    for (RankVertex v : metaDataRanked) {
      returnList.add(v.getValue().getSnippet());
    }
    return returnList;
  }

  /**
   * Get Vertex. TODO: Complete Docs.
   *
   * @param id the unique ID
   * @return  Vertex.
   */
  @Override
  public RankVertex getVertex(String id) {
    for (RankVertex v : nodes) {
      if (v.equals(id)) {
        return v;
      }
    }
    return null;
  }

  /**
   * Contains Vertex. TODO: Complete Docs.
   *
   * @param id the unique ID
   * @return  True if contains.
   */
  @Override
  public boolean containsVertex(String id) {
    for (RankVertex v : nodes) {
      if (v.equals(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Vertice. TODO: Complete Docs.
   *
   * @return  List of vertices.
   */
  @Override
  public List<RankVertex> getVertices() {
    return nodes;
  }

  /**
   * Get Incoming. TODO: Complete Docs.
   *
   * @param target the target node
   * @return  Set of rank edges.
   */
  @Override
  public Set<RankEdge> getIncoming(RankVertex target) {
    return inboundMap.get(target);
  }

  /**
   * Run Imputation. TODO: Complete Docs.
   *
   * @param edges edges.
   * @param zscore  zscore.
   * @return  Imputed.
   */
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

  public List<RankVertex> getTop(int n) {
    nodes.sort(Comparator.comparing(RankVertex::getScore));
    return nodes.subList(nodes.size() - n, nodes.size());
  }
}
