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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NgramCounter {
	private final NgramGenerator ngram;
	private final Map<String, Integer> numberOfNgrams;

	public NgramCounter() {
		numberOfNgrams = new HashMap<String, Integer>();
		ngram = new NgramGenerator();
	}

	public synchronized List<String> getMostFrequent() {
		NgramsByFrenquencies mft = new NgramsByFrenquencies();
		for (Entry<String, Integer> numberOfTuple : numberOfNgrams.entrySet()) {
			mft.update(numberOfTuple.getKey(), numberOfTuple.getValue());
		}

		return mft.getMostFrequentNgrams();
	}

	public synchronized void newChar(char c) {
		ngram.putChar(c);
		String s = ngram.getString();
		if (s != null) {
			Integer currentNumber = numberOfNgrams.get(s);
			if (currentNumber == null) {
				currentNumber = 1;
			} else {
				currentNumber++;
			}
			numberOfNgrams.put(s, currentNumber);
		}
	}

	public void newWord() {
		ngram.reset();
	}
}
