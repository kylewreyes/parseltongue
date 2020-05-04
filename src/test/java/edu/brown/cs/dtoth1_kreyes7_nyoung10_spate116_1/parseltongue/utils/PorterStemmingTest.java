package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class PorterStemmingTest {
  @Test
  public void testFromTestFiles() {
    PorterStemming p = new PorterStemming();
    File input = new File("data/PorterStemmingTestVocabInput.txt");
    File output = new File("data/PorterStemmingTestVocabOutput.txt");
    try {
      String in;
      String out;
      BufferedReader inReader = new BufferedReader(new FileReader(input));
      BufferedReader outReader = new BufferedReader(new FileReader(output));
      while ((in = inReader.readLine()) != null && (out = outReader.readLine()) != null) {
        assertEquals(out,p.stemWord(in));
      }
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: Porter Stemming Test files not found");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
