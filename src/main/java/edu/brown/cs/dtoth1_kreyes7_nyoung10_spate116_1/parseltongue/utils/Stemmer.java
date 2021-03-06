package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

import java.io.Serializable;

/**
 * Interface for stemming strategies which reduce words to their root.
 */
public interface Stemmer extends Serializable {
  /**
   * Takes in a word and returns its root word.
   * @param word word to stem
   * @return     root of word
   */
  String stemWord(String word);
}
