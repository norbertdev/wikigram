/*
 * This file is part of WikiGram.
 * Copyright 2011, 2015 Norbert
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

/**
 * This class provides a number that always stays in a given range.
 *
 * This class is intended to be use as an index that goes from <code>0</code> to
 * a given length. This index is incremented at each step by the {@link #next()}
 * method. If the index reaches the maximum allowed value, then the next step
 * will lower the index to 0.
 *
 * The index can be lowered to 0 by calling the {@link #reset()} method.
 */
public class LoopingIndex {
	private static final int DEFAULT_LENGTH = 3;
	private final int length;
	private int value;

	/**
	 * Constructs an index that goes from <code>0</code> to a default value. The
	 * first value is <code>0</code>.
	 */
	public LoopingIndex() {
		this(DEFAULT_LENGTH);
	}

	/**
	 * Constructs an index that goes from <code>0</code> to
	 * <code>lenght-1</code>. The first value is <code>0</code>.
	 *
	 * The given length must be greater than <code>0</code>.
	 *
	 * @param length
	 *            length allowed for the created index.
	 */
	public LoopingIndex(int length) {
		if (length < 0) {
			throw new IllegalArgumentException(
					"The length of a looping index must be greater than 0.");
		}

		value = 0;
		this.length = length;
	}

	/**
	 * Constructs an index based on another index: it will get the same length
	 * and the same current value.
	 *
	 * @param index
	 *            base index
	 */
	public LoopingIndex(LoopingIndex index) {
		value = index.value;
		length = index.length;
	}

	/**
	 * Returns the current value of the index. The returned value is always in
	 * the range [0, length-1].
	 *
	 * @return the current value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Increments the index value by <code>1</code> or sets the value to
	 * <code>0</code>.
	 *
	 * The value is set to <code>0</code> only if the incremented old value
	 * exceed the allowed range.
	 */
	public void next() {
		value++;
		if (value == length) {
			value = 0;
		}
	}

	/**
	 * Sets the current value to 0.
	 */
	public void reset() {
		value = 0;
	}
}
