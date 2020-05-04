package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

/**
 * Porter Stemming Class!
 */
final class PorterStemming implements Stemmer {
  /**
   * Private Constructor.
   */
  private PorterStemming() { }

  /**
   * Porter. Algorithm for stemming individual words to a common root.
   * @param raw raw word to be stemmed
   * @return  returns the root of the word
   */
  public static String porter(String raw) {
    String word = raw.toLowerCase();
    //Step 1a
    word = suffix(word, "sses", "ss");
    word = suffix(word, "ies", "i");
    if (!word.endsWith("ss")) {
      word = suffix(word, "s", "");
    }
    //Step 1b
    boolean bFlag = false;
    if (word.endsWith("eed") && m(word) > 0) {
      word = suffix(word, "eed", "ee");
    } else if (v(word.substring(0, word.length() - 2))) {
      word = suffix(word, "ed", "");
      bFlag = true;
    } else if (v(word.substring(0, word.length() - 3))) {
      word = suffix(word, "ing", "");
      bFlag = true;
    }
    if (bFlag) {
      word = suffix(word, "at", "ate");
      word = suffix(word, "bl", "ble");
      word = suffix(word, "iz", "ize");
      if (d(word) && !(word.endsWith("l")
              || word.endsWith("s")
              || word.endsWith("z"))) {
        word = word.substring(0, word.length() - 1);
      }
      if (m(word) == 1 && o(word)) {
        word = word + "e";
      }
    }
    //Step 1c
    if (v(word.substring(0, word.length() - 1))) {
      word = suffix(word, "y", "i");
    }
    //Step 2
    if (m(word) > 0) {
      if (word.endsWith("ational")) {
        word = suffix(word, "ational", "ate");
      } else {
        word = suffix(word, "tional", "tion");
      }
      word = suffix(word, "enci", "ence");
      word = suffix(word, "anci", "ance");
      word = suffix(word, "izer", "ize");
      word = suffix(word, "abli", "able");
      word = suffix(word, "alli", "al");
      word = suffix(word, "entli", "ent");
      word = suffix(word, "eli", "e");
      word = suffix(word, "ousli", "ous");
      word = suffix(word, "ization", "ize");
      word = suffix(word, "ation", "ate");
      word = suffix(word, "ator", "ate");
      word = suffix(word, "alism", "al");
      word = suffix(word, "iveness", "ive");
      word = suffix(word, "fulness", "ful");
      word = suffix(word, "ousness", "ous");
      word = suffix(word, "aliti", "al");
      word = suffix(word, "iviti", "ive");
      word = suffix(word, "biliti", "ble");
    }
    //Step 3
    if (m(word) > 0) {
      word = suffix(word, "icate", "ic");
      word = suffix(word, "ative", "");
      word = suffix(word, "alize", "al");
      word = suffix(word, "iciti", "ic");
      word = suffix(word, "ical", "ic");
      word = suffix(word, "ful", "");
      word = suffix(word, "ness", "");
    }
    //Step 4
    if (m(word) > 1) {
      word = suffix(word, "al", "");
      word = suffix(word, "ance", "");
      word = suffix(word, "ence", "");
      word = suffix(word, "er", "");
      word = suffix(word, "ic", "");
      word = suffix(word, "able", "");
      word = suffix(word, "ible", "");
      word = suffix(word, "ant", "");
      word = suffix(word, "ement", "");
      word = suffix(word, "ment", "");
      word = suffix(word, "ent", "");
      if (word.endsWith("tion") || word.endsWith("sion")) {
        word = suffix(word, "ion", "");
      }
      word = suffix(word, "ou", "");
      word = suffix(word, "ism", "");
      word = suffix(word, "ate", "");
      word = suffix(word, "iti", "");
      word = suffix(word, "ous", "");
      word = suffix(word, "ive", "");
      word = suffix(word, "ize", "");
    }
    //Step 5a
    if (m(word) > 1) {
      word = suffix(word, "e", "");
    } else if (m(word) == 1 && !(o(word))) {
      word = suffix(word, "e", "");
    }
    //Step 5b
    if (m(word) > 1 && d(word) && word.endsWith("l")) {
      word = word.substring(0, word.length() - 1);
    }
    return word;
  }
  /**
   * Assorted helper methods for implementing porter stemming.
   * @param word  word.
   * @return  int.
   */
  private static int m(String word) {
    //eliminate leading consonants
    while (!isVowel(word, 0)) {
      word = word.substring(1);
    }
    //eliminate trailing vowels
    while (isVowel(word, word.length() - 1)) {
      if (word.length() == 0) {
        return 0;
      }
      word = word.substring(0, word.length() - 1);
    }
    boolean lastIsVowel = false;
    int m = 0;
    for (int i = 0; i < word.length(); i++) {
      if (isVowel(word, i) ^ lastIsVowel) {
        lastIsVowel = !lastIsVowel;
        m++;
      }
    }
    return m;
  }

  /**
   *  Checks if the suffix of a word matches a predicate and if it does, replaces it.
   * @param word  word
   * @param pred  predicate to match
   * @param replace replacement to suffix
   * @return  new word with predicate replaced
   */
  private static String suffix(String word, String pred, String replace) {
    if (word.endsWith(pred)) {
      word = word.substring(0, word.length() - pred.length()) + replace;
    }
    return word;
  }

  /**
   *  checks if the character at an index is a vowel.
   * @param word  word.
   * @param index index.
   * @return  ret.
   */
  private static boolean isVowel(String word, int index) {
    if (index < 0) {
      return true;
    }
    String c = word.substring(index, index + 1);
    return c.equals("a")
            || c.equals("e")
            || c.equals("i")
            || c.equals("o")
            || c.equals("u")
            || c.equals("y") && !isVowel(word, index - 1);
  }

  /**
   *  TODO: Complete Docs.
   * @param word  word.
   * @return  ret.
   */
  private static boolean v(String word) {
    for (int i = 0; i < word.length(); i++) {
      if (isVowel(word, i)) {
        return true;
      }
    }
    return false;
  }

  /**
   *  TODO: Complete Docs.
   * @param word  word.
   * @return  ret.
   */
  private static boolean d(String word) {
    if (word.length() < 2) {
      return false;
    } else {
      return !(isVowel(word, word.length() - 1) || isVowel(word, word.length() - 2));
    }
  }

  /**
   *  TODO: Complete Docs.
   * @param word  word.
   * @return  ret.
   */
  private static boolean o(String word) {
    if (word.length() < 3) {
      return false;
    }
    String last = word.substring(word.length() - 1);
    return !(last.equals("w") || last.equals("x") || last.equals("y"))
            && !isVowel(word, word.length() - 1)
            && isVowel(word, word.length() - 2)
            && !isVowel(word, word.length() - 3);
  }

  @Override
  public String stemWord(String word) {
    return porter(word);
  }
}
