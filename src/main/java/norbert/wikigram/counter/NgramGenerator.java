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

import norbert.wikigram.utils.LoopingIndex;

/**
 * This class provides a transformation from several ordered characters to strings of a determined
 * length.
 *
 * The characters are added one by one, via the {@link #putChar(char)} method. The string is
 * retrieved by the {@link #getString()} method. The number of characters is given to the
 * constructor.
 *
 * In the initial state, the string is empty. A generator is in the initial state after the
 * constructor call, and after the call of the {@link #reset()} method.
 *
 * For example, for a length of 2, putting 'a', 'b' and 'c' results in "bc".
 */
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

  /**
   * Constructs a generator that will generate strings of the given number of characters.
   *
   * @param length number of characters
   */
  public NgramGenerator(int length) {
    endIndex = new LoopingIndex(length);
    startIndex = new LoopingIndex(length);
    currentTuple = new char[length];
    size = 0;
    this.length = length;
  }

  /**
   * Returns a string composed of the last characters put in the generator. If not enough characters
   * were inserted before, <code>null</code> is returned.
   *
   * The returned strings always contain the same number of characters. This number is given to the
   * constructor.
   *
   * The insertion order is preserved: for a length of 3,
   * <code>putChar('a'); putChar('b'); putChar('c');</code> results in the "abc" string.
   *
   * @return string of the previous inserted characters, or null.
   */
  public String getString() {
    if (size == length) {
      LoopingIndex currentIndex = new LoopingIndex(startIndex);
      StringBuffer s = new StringBuffer();
      for (int i = 0; i < length; i++) {
        s.append(currentTuple[currentIndex.getValue()]);
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
    currentTuple[endIndex.getValue()] = c;
    endIndex.next();
  }

  public void reset() {
    size = 0;
    startIndex.reset();
    endIndex.reset();
  }
}
