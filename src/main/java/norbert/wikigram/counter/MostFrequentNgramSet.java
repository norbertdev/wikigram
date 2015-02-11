/*
 * This file is part of WikiGram.
 *
 * Copyright 2015 Norbert
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
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This set retains only the lowest elements that are inserted. The set retains all elements until
 * the maximum allowed number of elements is reached. Then, if an element to add is lower than the
 * greatest contained element, then this greatest element is removed and the new element is
 * retained. Otherwise the element to add is ignored.
 */
public class MostFrequentNgramSet {
  private final SortedSet<ComparableNgram> sortedSet;
  private final int maximumSize;

  public MostFrequentNgramSet(int maximumSize) {
    this.maximumSize = maximumSize;
    sortedSet = new TreeSet<>();
  }

  /**
   * Adds the given element. If the maximum size is reach, the given element is retains only if it
   * is greater than the smallest element.
   *
   * @param element element to add
   */
  public void add(String ngram, long frequency) {
    ComparableNgram element = new ComparableNgram(ngram, frequency);

    if (sortedSet.size() < maximumSize) {
      sortedSet.add(element);
      return;
    }

    sortedSet.add(element);
    sortedSet.remove(sortedSet.last());
  }

  /**
   * Returns a list of the elements. The list is sorted. The first element is the greatest, the last
   * element is the lowest.
   *
   * @return the list of sorted elements from the greatest to the lowest
   */
  public List<String> generateList() {
    List<String> result = new ArrayList<>();

    for (ComparableNgram element : sortedSet) {
      result.add(element.getNgram());
    }

    return result;
  }
}
