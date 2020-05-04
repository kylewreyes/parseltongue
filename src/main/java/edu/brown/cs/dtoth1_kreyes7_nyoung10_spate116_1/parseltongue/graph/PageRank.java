package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PageRank Algorithm Class!
 *
 * @param <G> Graph Type.
 * @param <V> Vertex Type.
 * @param <E> Edge Type.
 * @param <T> Metadata Type.
 */
public class PageRank<G extends Graph<V, E, T>, V extends Vertex<E, T>, E extends Edge<V>,
    T extends VertexMetadata> implements Serializable, Rankable<G, V, E, T> {
  private final G compGraph;
  private long n;
  private final double dampDef = 0.85;
  private final double epislonDef = 0.001;

  /**
   * Constructor for a page rank object.
   *
   * @param compGraph Graph to search on.
   */
  public PageRank(G compGraph) {
    this.compGraph = compGraph;
  }

  /**
   * A convenient method to call pagerank on default values.
   *
   * @return List of Vertexes.
   */
  public List<V> rank() {
    return pageRank(dampDef, epislonDef);
  }

  /**
   * PageRank Algorithm.
   *
   * @param dampener the probability that a surfer will continue through the graph.
   * @param epsilon  the threshold for convergence.
   * @return List of Vertexes in decreasing score.
   */
  public List<V> pageRank(double dampener, double epsilon) {
    List<V> nodes = compGraph.getVertices();
    n = nodes.size();

    Map<V, Double> distribution;
    Map<V, Double> updatedDistribution = new HashMap<>();
    // Initialize distribution to uniform across all nodes.
    for (int i = 0; i < n; i++) {
      updatedDistribution.put(nodes.get(i), 1.0 / n);
    }

    // While the distributions have not converged keep updating
    do {
      distribution = new HashMap<>(updatedDistribution);
      for (int i = 0; i < n; i++) {
        updatedDistribution.put(nodes.get(i), updateRank(nodes.get(i),
            distribution, dampener));
      }
    } while (!isConverged(List.copyOf(distribution.values()),
        List.copyOf(updatedDistribution.values()), epsilon));

    // Sort in decreasing rank score
    List<Integer> indices = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      indices.add(i);
    }
    Comparator<Integer> extractTopNodes = Comparator.comparing(key ->
        updatedDistribution.get(nodes.get(key)));

    indices.sort(extractTopNodes);
    Collections.reverse(indices);

    List<V> finalList = new ArrayList<>();
    for (Integer idx : indices) {
      finalList.add(nodes.get(idx));
      nodes.get(idx).setScore(updatedDistribution.get(nodes.get(idx)));
    }
    return finalList;
  }

  /**
   * Iterative step to perform updates on the rank score of a node.
   *
   * @param target       target node to be updated.
   * @param distribution distribution the probability distribution to this point.
   * @param dampener     the probability that a surfer will continue through the graph.
   * @return new rank score value.
   */
  private Double updateRank(V target, Map<V, Double> distribution, double dampener) {
    double contribution = 0.0;
    // Weigh the contribution of each edge by its weight
    for (E edge : compGraph.getIncoming(target)) {
      contribution += distribution.get(edge.getSource()) * edge.getWeight()
          / edge.getSource().totalWeight();
    }
    // Use dampener to ensure quicker convergence
    return (1 - dampener) / n + dampener * contribution;

  }

  /**
   * Checks for convergence in the algorithm.
   *
   * @param dist1   previous iteration distribution.
   * @param dist2   current iteration distribution.
   * @param epsilon tolerance level for convergence.
   * @return True if converged. False otherwise.
   */
  private boolean isConverged(List<Double> dist1, List<Double> dist2, double epsilon) {
    double maxChange = 0.0;
    // The most any given element in the distributions can change must be < epsilon for convergence
    for (int i = 0; i < n; i++) {
      maxChange = Math.max(maxChange, Math.abs(dist1.get(i) - dist2.get(i)));
    }

    return maxChange < epsilon;
  }
}
