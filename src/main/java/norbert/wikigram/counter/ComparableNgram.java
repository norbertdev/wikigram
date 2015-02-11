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

/**
 * This class provides a string (a ngram) and a number attached to it (a frequency).
 *
 * The order is first based on the frequency: the most frequent is first, the least frequent is
 * last. If the frequencies are equals, then the order is the String order of the ngrams.
 *
 * Two ngrams are equal if their texts are equal.
 */
class ComparableNgram implements Comparable<ComparableNgram> {
  private final long frequency;
  private final String ngram;

  public ComparableNgram(String ngram, Long frequency) {
    this.ngram = ngram;
    this.frequency = frequency;
  }

  @Override
  public int compareTo(ComparableNgram cn) {
    if (this.equals(cn)) {
      return 0;
    }

    long otherFrequency = cn.getFrequency();

    if (otherFrequency < frequency) {
      return -1;
    } else if (frequency < otherFrequency) {
      return 1;
    } else {
      return ngram.compareTo(cn.getNgram());
    }
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    ComparableNgram ft = (ComparableNgram) object;
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
