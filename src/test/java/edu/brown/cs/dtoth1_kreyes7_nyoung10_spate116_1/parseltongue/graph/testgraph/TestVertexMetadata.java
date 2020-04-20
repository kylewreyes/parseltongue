package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.testgraph;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.VertexMetadata;

import java.util.Objects;

public class TestVertexMetadata implements VertexMetadata {
  private String id;

  public TestVertexMetadata(String id) {
    this.id = id;
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestVertexMetadata that = (TestVertexMetadata) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
