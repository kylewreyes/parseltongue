package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRank<G extends Graph<V, E, T>, V extends Vertex<E, T>, E extends Edge<V>,
    T extends VertexMetadata> {
  private final G compGraph;
  private long n;

  public PageRank(G compGraph) {
    this.compGraph = compGraph;
  }

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
    Comparator<Integer> extractTopNodes = Comparator.comparing(updatedDistribution::get);

    indices.sort(extractTopNodes);

    List<V> finalList = new ArrayList<>();
    for (Integer idx : indices) {
      finalList.add(nodes.get(idx));
    }
    return finalList;
  }

  private Double updateRank(V target, Map<V, Double> distribution, double dampener) {
    double contribution = 0.0;
    for (E edge : compGraph.getIncoming(target)) {
      contribution += distribution.get(edge.getSource()) * edge.getWeight()
          / edge.getSource().totalWeight();
    }
    return (1 - dampener) / n + dampener * contribution;

  }

  private boolean isConverged(List<Double> dist1, List<Double> dist2, double epsilon) {
    double maxChange = 0.0;
    for (int i = 0; i < n; i++) {
      maxChange = Math.max(maxChange, Math.abs(dist1.get(i) - dist2.get(i)));
    }

    return maxChange < epsilon;
  }
}
