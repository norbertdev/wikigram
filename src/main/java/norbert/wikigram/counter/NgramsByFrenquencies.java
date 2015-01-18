/*
 * This file is part of WikiGram.
 * Copyright 2011 Norbert
 * 
 * WikiGram is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * WikiGram is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with WikiGram. If not, see <http://www.gnu.org/licenses/>.
 */
package norbert.wikigram.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class NgramsByFrenquencies {
	private class NgramAndFrequency implements Comparable<NgramAndFrequency> {
		private final int frequency;
		private final String ngram;

		public NgramAndFrequency(String ngram, int frequency) {
			this.ngram = ngram;
			this.frequency = frequency;
		}

		@Override
		public int compareTo(NgramAndFrequency ft) {
			if (this.equals(ft)) {
				return 0;
			}
			int difference = frequency - ft.getFrequency();
			return (difference == 0) ? ngram.compareTo(ft.getNgram()) : difference;
		}

		@Override
		public boolean equals(Object object) {
			if (object == null) {
				return false;
			}
			NgramAndFrequency ft = (NgramAndFrequency) object;
			return ngram.equals(ft.getNgram());
		}

		public int getFrequency() {
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

	private static final int DEFAULT_MAX_SIZE = 300;
	private final int maximumSize;
	private final NavigableSet<NgramAndFrequency> sortedNgrams;

	public NgramsByFrenquencies() {
		this(DEFAULT_MAX_SIZE);
	}

	public NgramsByFrenquencies(int maximumSize) {
		sortedNgrams = new TreeSet<NgramAndFrequency>();
		sortedNgrams.add(new NgramAndFrequency("dummytuple", -1));
		this.maximumSize = maximumSize;
	}

	public List<String> getMostFrequentNgrams() {
		List<String> list = new ArrayList<String>();
		for (NgramAndFrequency ft : sortedNgrams.descendingSet()) {
			list.add(ft.getNgram());
		}
		return list;
	}

	public void update(String s, Integer currentNumber) {
		if (sortedNgrams.first().getFrequency() < currentNumber) {
			NgramAndFrequency newFrequencyNgram = new NgramAndFrequency(s, currentNumber);
			sortedNgrams.remove(newFrequencyNgram);
			sortedNgrams.add(newFrequencyNgram);
			if (maximumSize == sortedNgrams.size()) {
				sortedNgrams.pollFirst();
			}
		}
	}
}
