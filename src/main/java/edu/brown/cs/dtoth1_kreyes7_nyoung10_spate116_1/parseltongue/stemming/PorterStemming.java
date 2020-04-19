package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.stemming;

public class PorterStemming {
  public static String porter(String raw) {
    raw = raw.toLowerCase();
    return null;
  }
  private int m(String word) {
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
  private boolean isVowel(String word, int index) {
    if (index < 0) {
      return true;
    }
    String c = word.substring(index, index + 1);
    return c.equals("a")
            || c.equals("e")
            || c.equals("i")
            || c.equals("o")
            || c.equals("u")
            || (c.equals("y") && !isVowel(word, index - 1));
  }
  private boolean v(String word) {
    for (int i = 0; i < word.length(); i++) {
      if (isVowel(word, i)) {
        return true;
      }
    }
    return false;
  }

  private boolean d(String word) {
    if (word.length() < 2) {
      return false;
    } else {
      return !(isVowel(word, word.length() - 1) || isVowel(word, word.length() - 2));
    }
  }
}
