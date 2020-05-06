package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Graph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Rankable;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.KeywordExtractor;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.RelevanceMetric;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics.StatisticalKeywordExtractor;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser.Snippet;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.Stemmer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of {@link Graph} that holds PageRank data. Designed to
 * work with a PageRank implementation.
 */
public class RankGraph implements Graph<RankVertex, RankEdge, RankMetadata>, Serializable {
  private List<RankVertex> nodes;
  private final Rankable<RankGraph, RankVertex, RankEdge, RankMetadata> pRank =
      new PageRank<>(this);
  private final Map<RankVertex, Set<RankEdge>> inboundMap = new HashMap<>();
  private final RelevanceMetric metric;
  private final List<Map<String, Double>> dist;
  private final List<Snippet> rawCoreText;
  private Map<String, Double> keywordDistribution = new HashMap<>();
  private final KeywordExtractor keyExtract;
  private final Stemmer stemmer;
  private static final double DISSIMILAR_SOURCE_INCREASE = 1.3;

  /**
   * Constructor for rank graph which initializes internal data of the graph.
   * Uses Statistical Keyword Constructor as a default.
   *
   * @param rawCoreText raw snippets to be used as the node data for this graph
   * @param newMetric  a metric for determining similarity between two snippets
   */
  public RankGraph(List<Snippet> rawCoreText, RelevanceMetric newMetric) {
    dist = new ArrayList<>();
    nodes = new ArrayList<>();
    for (Snippet s : rawCoreText) {
      nodes.add(new RankVertex(new RankMetadata(s)));
      dist.add(s.distribution());
    }
    Set<RankVertex> unique = new HashSet<>(nodes);
    nodes = new ArrayList<>();
    nodes.addAll(unique);
    this.metric = newMetric;
    this.rawCoreText = rawCoreText;
    this.keyExtract = new StatisticalKeywordExtractor();
    this.stemmer = new IdentityStemmer();
  }
  /**
   * Constructor for rank graph which initializes internal data of the graph.
   *
   * @param rawCoreText raw snippets to be used as the node data for this graph
   * @param newMetric  a metric for determining similarity between two snippets
   * @param keyExtract Strategy for extracting and filtering keywords
   * @param stemmer    Strategy for stemming words to roots
   */
  public RankGraph(
          List<Snippet> rawCoreText,
          RelevanceMetric newMetric,
          KeywordExtractor keyExtract,
          Stemmer stemmer) {
    dist = new ArrayList<>();
    nodes = new ArrayList<>();
    for (Snippet s : rawCoreText) {
      nodes.add(new RankVertex(new RankMetadata(s)));
      dist.add(s.distribution());
    }
    Set<RankVertex> unique = new HashSet<>(nodes);
    nodes = new ArrayList<>();
    nodes.addAll(unique);
    this.metric = newMetric;
    for (Snippet s : rawCoreText) {
      s.stemPlainText(stemmer);
    }
    this.rawCoreText = rawCoreText;
    this.keyExtract = keyExtract;
    this.stemmer = stemmer;
  }

  /**
   * Function which takes new keywords and reweighs all the edges between nodes.
   * @param keywords the list of new keywords to be used.
   */
  public void populateEdges(List<String> keywords) {
    List<String> stemmedKeywords = new ArrayList<>();
    for (String keyword : keywords) {
      stemmedKeywords.add(stemmer.stemWord(keyword));
    }
    keywords = stemmedKeywords;
    for (RankVertex v : nodes) {
      v.clearEdges();
    }

    keywordDistribution = keyExtract.extractKeywords(keywords, dist);

    List<List<Double>> keywordScoring = new ArrayList<>(nodes.size());
    for (int i = 0; i < nodes.size(); i++) {
      keywordScoring.add(rawCoreText.get(i).keywordScores(keywordDistribution));
    }

    List<RankEdge> edges = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      for (int j = i + 1; j < nodes.size(); j++) {
        double weight = metric.calculateRelevance(keywordScoring.get(i), keywordScoring.get(j));
        RankVertex n1 = nodes.get(i);
        RankVertex n2 = nodes.get(j);
        String n1FileName = n1.getValue().getSnippet().getFileName();
        String n2FileName = n2.getValue().getSnippet().getFileName();
        if (n1FileName != null && n2FileName != null && !n2FileName.equals(n1FileName)) {
          weight = weight * DISSIMILAR_SOURCE_INCREASE;
        }
        edges.add(new RankEdge(n1, n2, weight));
        edges.add(new RankEdge(n2, n1, weight));
      }
    }

    edges = runImputation(edges, 1);
    for (RankEdge e : edges) {
      e.getSource().addEdge(e);
    }

    for (RankVertex node : nodes) {
      inboundMap.put(node, new HashSet<>());
    }

    for (RankEdge e : edges) {
      inboundMap.get(e.getDest()).add(e);
    }
  }

  /**
   * Getter method for current internal keyword distribution.
   * @return Map of string indicating keyword content to number
   *         representing relative importance of that keyword
   */
  public Map<String, Double> getCurrentKeywords() {
    return keywordDistribution;
  }

  /**
   * Function which runs Pagerank and returns the snippets in order.
   * @return  List of Snippets.
   */
  public List<Snippet> rank() {
    List<RankVertex> metaDataRanked = pRank.rank();
    List<Snippet> returnList = new ArrayList<>();
    for (RankVertex v : metaDataRanked) {
      returnList.add(v.getValue().getSnippet());
    }
    return returnList;
  }

  /**
   * Returns a vertex in the graph based on the vertex ID.
   *
   * @param id the unique ID
   * @return  Vertex.
   */
  @Override
  public RankVertex getVertex(String id) {
    for (RankVertex v : nodes) {
      if (v.getValue().getID().equals(id)) {
        return v;
      }
    }
    return null;
  }

  /**
   * Checks a if a given vertex is contained in the graph by id.
   *
   * @param id the unique ID
   * @return  True if contains.
   */
  @Override
  public boolean containsVertex(String id) {
    for (RankVertex v : nodes) {
      if (v.getValue().getID().equals(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the list of nodes contained within this graph.
   *
   * @return  List of vertices.
   */
  @Override
  public List<RankVertex> getVertices() {
    return nodes;
  }

  /**
   * Returns all the edges pointing to a specified node.
   *
   * @param target the target node
   * @return  Set of rank edges.
   */
  @Override
  public Set<RankEdge> getIncoming(RankVertex target) {
    return inboundMap.get(target);
  }

  /**
   * Run Imputation. Takes in a list of edges and a zscore value and returns a list of edges
   * culled so that only those with an edge weight above one zscore of the mean remain.
   *
   * @param edges edges to be imputed
   * @param zscore zscore threshold to be returned
   * @return  Imputed edges
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
      if (e.getWeight() > mean + stdev * zscore) {
        finalEdges.add(e);
      }
    }

    return finalEdges;
  }

  /**
   * Convert this object to byte array.
   * @param graph graph object to convert to byte array
   * @return  Byte data.
   */
  public static byte[] objToBytes(RankGraph graph) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(graph);
      oos.flush();
      return bos.toByteArray();
    } catch (Exception e) {
      System.err.println("ERROR: byte write. " + e.getMessage());
      return null;
    }
  }

  /**
   *
   * @param bytes  Byte data.
   * @return  RankGraph.
   */
  public static RankGraph byteToObj(byte[] bytes) {
    try {
      ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
      ObjectInputStream objStream = new ObjectInputStream(byteStream);
      return (RankGraph) objStream.readObject();
    } catch (Exception e) {
      System.err.println("ERROR: byte conversion. " + e.getMessage());
      return null;
    }
  }

  /**
   * Stemmer which returns the word unaltered.
   */
  private static class IdentityStemmer implements Stemmer {
    @Override
    public String stemWord(String word) {
      return word;
    }
  }
}
