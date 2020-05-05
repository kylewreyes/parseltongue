package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class ParselCommandsTest {
  @Test
  public void testValidInput() {
    String[] args = {"data/Uruguay.pdf", "sheep farming"};
    assertNotEquals(ParselCommands.parsel(List.of(args[0]), args[1]), null);
  }
}
