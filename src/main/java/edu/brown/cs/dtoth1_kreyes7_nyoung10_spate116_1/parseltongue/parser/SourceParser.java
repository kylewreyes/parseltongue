package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;

import java.io.Closeable;

/**
 * An interface for classes that acquire from a data source.
 */
public interface SourceParser extends Closeable {
  /**
   * Acquires the text from a given source.
   * @return the text from the SourceParser
   */
  String getText();
}
