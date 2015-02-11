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

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * This class maintains a collection of ngrams sorted by frequency.
 *
 * The only method to add a ngram is {@link #update(String, Integer)}. The only method to get the
 * ngrams is {@link #getMostFrequentNgrams()}.
 */
public class NgramsByFrequency {
  /**
   * This class provides a string (a ngram) and a number attached to it (a frequency).
   *
   * The order is first based on the frequency. If the frequencies are equals, then the order is the
   * String order of the ngrams.
   *
   * Two ngrams are equal if their texts are equal.
   */
  private class NgramAndFrequency implements Comparable<NgramAndFrequency> {
    private final long frequency;
    private final String ngram;

    public NgramAndFrequency(String ngram, Long frequency) {
      this.ngram = ngram;
      this.frequency = frequency;
    }

    @Override
    public int compareTo(NgramAndFrequency nf) {
      if (this.equals(nf)) {
        return 0;
      }

      long otherFrequency = nf.getFrequency();

      if (frequency < otherFrequency) {
        return -1;
      } else if (otherFrequency < frequency) {
        return 1;
      } else {
        return ngram.compareTo(nf.getNgram());
      }
    }

    @Override
    public boolean equals(Object object) {
      if (object == null) {
        return false;
      }
      NgramAndFrequency ft = (NgramAndFrequency) object;
      return ngram.equals(ft.getNgram());
    }

    public long getFrequency() {
      return frequency;
    }

    public String getNgram() {
      return ngram;
    }

    @Override
    public String toString() {
      return ngram + "(" + frequency + ")";
    }
  }

  private static final int DEFAULT_MAX_NGRAM = 300;
  private final int maximumNgrams;
  private final NavigableSet<NgramAndFrequency> sortedNgrams;

  public NgramsByFrequency() {
    this(DEFAULT_MAX_NGRAM);
  }

  /**
   * Constructs a set of ngrams that can hold the given number of elements at most.
   *
   * @param maximumNgrams maximum number of elements to hold
   */
  public NgramsByFrequency(int maximumNgrams) {
    sortedNgrams = new TreeSet<NgramAndFrequency>();
    sortedNgrams.add(new NgramAndFrequency("dummytuple", -1L));
    this.maximumNgrams = maximumNgrams;
  }

  /**
   * Returns a list of the ngrams, the list is ordered by frequencies. If two frequencies are
   * equals, they are ordered by alphabetical order.
   *
   * The first ngram of the list is the most frequent. The last ngram of the list is the less
   * frequent.
   *
   * @return the ngrams ordered by frequency
   */
  public List<String> getMostFrequentNgrams() {
    List<String> list = new ArrayList<>();
    for (NgramAndFrequency ft : sortedNgrams.descendingSet()) {
      list.add(ft.getNgram());
    }
    return list;
  }

  /**
   * Updates a ngrams already contained, or adds it.
   *
   * @param ngram the ngram to add
   * @param frequency the frequency of the ngram
   */
  public void update(String ngram, Long frequency) {
    if (sortedNgrams.first().getFrequency() < frequency) {
      NgramAndFrequency newFrequencyNgram = new NgramAndFrequency(ngram, frequency);
      sortedNgrams.remove(newFrequencyNgram);
      sortedNgrams.add(newFrequencyNgram);
      if (maximumNgrams == sortedNgrams.size()) {
        sortedNgrams.pollFirst();
      }
    }
  }
}
