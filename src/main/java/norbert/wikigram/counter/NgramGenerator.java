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

import norbert.wikigram.utils.LoopingIndex;

public class NgramGenerator {
	private static final int DEFAULT_LENGTH = 3;
	private final char[] currentTuple;
	private final LoopingIndex endIndex;
	private final int length;
	private int size;
	private final LoopingIndex startIndex;

	public NgramGenerator() {
		this(DEFAULT_LENGTH);
	}

	public NgramGenerator(int length) {
		endIndex = new LoopingIndex(length);
		startIndex = new LoopingIndex(length);
		currentTuple = new char[length];
		size = 0;
		this.length = length;
	}

	public String getString() {
		if (size == length) {
			LoopingIndex currentIndex = new LoopingIndex(startIndex);
			StringBuffer s = new StringBuffer();
			for (int i = 0; i < length; i++) {
				s.append(currentTuple[currentIndex.getIndex()]);
				currentIndex.next();
			}
			return s.toString();
		}
		return null;
	}

	public void putChar(char c) {
		if (size == length) {
			startIndex.next();
		} else {
			size++;
		}
		currentTuple[endIndex.getIndex()] = c;
		endIndex.next();
	}

	public void reset() {
		size = 0;
		startIndex.reset();
		endIndex.reset();
	}
}
