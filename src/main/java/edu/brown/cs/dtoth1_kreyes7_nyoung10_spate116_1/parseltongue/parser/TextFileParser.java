package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A {@link SourceParser} specific for text files.
 */
public class TextFileParser implements SourceParser {
  private File source;
  private BufferedReader reader;
  /**
   * Creates a new TextFileParser.
   * @param path the location of the text file
   */
  public TextFileParser(String path) {
    this(new File(path));
  }

  /**
   * Creates a new TextFileParser.
   * @param file the text file
   */
  public TextFileParser(File file) {
    source = file;
    try {
      reader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("ERROR: Invalid text file");
    }
  }

  @Override
  public String getText() {
    return null;
  }

  @Override
  public void close() throws IOException {
    reader.close();
  }
}
