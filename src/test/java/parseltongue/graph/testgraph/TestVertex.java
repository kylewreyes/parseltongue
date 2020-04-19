package parseltongue.graph.testgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.Vertex;

import java.util.Objects;
import java.util.Set;

public class TestVertex implements Vertex<TestEdge, TestVertexMetadata> {
  TestVertexMetadata meta;
  Set<TestEdge> adj;

  public TestVertex(TestVertexMetadata meta) {
    this.meta = meta;
  }

  public void setAdj(Set<TestEdge> adj) {
    this.adj = adj;
  }

  @Override
  public TestVertexMetadata getValue() {
    return meta;
  }

  @Override
  public Set<TestEdge> getEdges() {
    return adj;
  }

  @Override
  public Double totalWeight() {
    double sum = 0.0;
    for (TestEdge e : adj) {
      sum += e.getWeight();
    }
    return sum;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestVertex that = (TestVertex) o;
    return meta.equals(that.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta);
  }
}
