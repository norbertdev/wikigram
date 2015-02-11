/*
 * This file is part of WikiGram.
 *
 * Copyright 2011, 2015 Norbert
 *
 * WikiGram is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * WikiGram is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with WikiGram. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package norbert.wikigram.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class provides a counter for ngrams.
 *
 * The characters are given through the {@link #newChar(char)} method. The begin of a new word is
 * notified by calling the {@link #newWord()} method. The most frequent ngrams are retrieved by
 * calling the {@link #getMostFrequent()} method.
 */
public class NgramCounter {
  private final NgramGenerator ngram;
  private final Map<String, Long> numberOfNgrams;

  public NgramCounter(int ngramSize) {
    numberOfNgrams = new HashMap<>();
    ngram = new NgramGenerator(ngramSize);
  }

  /**
   * Returns a list of ngrams. The list is ordered by frequency. If two ngrams have the same
   * frequency, then they follow the alphabetical order.
   *
   * The returned list can be empty. <code>null</code> is never returned.
   *
   * @param maxElement the maximum number of ngrams in the returned list
   * @return ngrams ordered by frequency.
   */
  public synchronized List<String> getMostFrequent(int maxElement) {
    MostFrequentNgramSet set = new MostFrequentNgramSet(maxElement);
    for (Entry<String, Long> numberOfTuples : numberOfNgrams.entrySet()) {
      set.add(numberOfTuples.getKey(), numberOfTuples.getValue());
    }
    return set.generateList();
  }

  /**
   * Returns the current frequency of the given ngram.
   */
  public long getFrenquency(String ngram) {
    return numberOfNgrams.get(ngram);
  }

  /**
   * Adds a character to the previous given characters.
   *
   * @param c character to add
   */
  public synchronized void newChar(char c) {
    ngram.putChar(c);
    String s = ngram.getString();
    if (s != null) {
      Long currentNumber = numberOfNgrams.get(s);
      if (currentNumber == null) {
        currentNumber = 1L;
      } else {
        currentNumber++;
      }
      numberOfNgrams.put(s, currentNumber);
    }
  }

  /**
   * Drops the previous given characters.
   */
  public synchronized void newWord() {
    ngram.reset();
  }
}
