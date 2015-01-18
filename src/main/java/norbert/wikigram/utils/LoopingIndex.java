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
package norbert.wikigram.utils;

public class LoopingIndex {
	private static final int DEFAULT_LENGTH = 3;
	private int current;
	private final int length;

	public LoopingIndex() {
		this(DEFAULT_LENGTH);
	}

	public LoopingIndex(int length) {
		current = 0;
		this.length = length;
	}

	public LoopingIndex(LoopingIndex index) {
		current = index.current;
		length = index.length;
	}

	public int getIndex() {
		return current;
	}

	public void next() {
		current++;
		if (current == length) {
			current = 0;
		}
	}

	//FIXME: useless?
	public void reset() {
		current = 0;
	}
}
