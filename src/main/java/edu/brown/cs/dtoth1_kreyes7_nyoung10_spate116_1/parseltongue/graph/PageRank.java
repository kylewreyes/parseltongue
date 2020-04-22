package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

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
    T extends VertexMetadata> {
  private final G compGraph;
  private long n;

  /**
   * Constructor. TODO: Complete Docs
   *
   * @param compGraph Graph to search on.
   */
  public PageRank(G compGraph) {
    this.compGraph = compGraph;
  }

  /**
   * PageRank init values. TODO: Complete Docs
   *
   * @return List of Vertexes.
   */
  public List<V> pageRank() {
    return pageRank(0.85, 0.001);
  }

  /**
   * PageRank Algorithm. TODO: Complete Docs
   *
   * @param dampener d.
   * @param epsilon  e.
   * @return List of Vertexes.
   */
  public List<V> pageRank(double dampener, double epsilon) {
    List<V> nodes = compGraph.getVertices();
    n = nodes.size();

    Map<V, Double> distribution;
    Map<V, Double> updatedDistribution = new HashMap<>();
    for (int i = 0; i < n; i++) {
      updatedDistribution.put(nodes.get(i), 1.0 / n);
    }

    do {
      distribution = new HashMap<>(updatedDistribution);
      for (int i = 0; i < n; i++) {
        updatedDistribution.put(nodes.get(i), updateRank(nodes.get(i),
            distribution, dampener));
      }
    } while (!isConverged(List.copyOf(distribution.values()),
        List.copyOf(updatedDistribution.values()), epsilon));

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
   * Update Rank. TODO: Complete Docs
   *
   * @param target       target.
   * @param distribution distribution.
   * @param dampener     dampener.
   * @return new value.
   */
  private Double updateRank(V target, Map<V, Double> distribution, double dampener) {
    double contribution = 0.0;
    for (E edge : compGraph.getIncoming(target)) {
      contribution += distribution.get(edge.getSource()) * edge.getWeight()
          / edge.getSource().totalWeight();
    }
    return (1 - dampener) / n + dampener * contribution;

  }

  /**
   * isConverged. TODO: Complete Docs
   *
   * @param dist1   Distance 1.
   * @param dist2   Distance 2.
   * @param epsilon epsilon.
   * @return True if converged. False otherwise.
   */
  private boolean isConverged(List<Double> dist1, List<Double> dist2, double epsilon) {
    double maxChange = 0.0;
    for (int i = 0; i < n; i++) {
      maxChange = Math.max(maxChange, Math.abs(dist1.get(i) - dist2.get(i)));
    }

    return maxChange < epsilon;
  }
}
