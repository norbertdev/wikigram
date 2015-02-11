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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class NgramCounterTest {
  private static final int LARGE_MAXIMUM = 1024;

  @Test
  public void testNgramWithOneChar() {
    NgramCounter counter = new NgramCounter(1);

    assertTrue(counter.getMostFrequent(LARGE_MAXIMUM).isEmpty());
    List<String> mostFrequent;

    // a=1
    counter.newChar('a');
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(mostFrequent.get(0), "a");

    // a=1 c=1
    counter.newChar('c');
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(mostFrequent.get(0), "a");
    assertEquals(mostFrequent.get(1), "c");

    // a=1 b=1 c=1
    counter.newChar('b');
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(mostFrequent.get(0), "a");
    assertEquals(mostFrequent.get(1), "b");
    assertEquals(mostFrequent.get(2), "c");

    // a=1 b=2 c=1
    counter.newChar('b');
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(mostFrequent.get(0), "b");
    assertEquals(mostFrequent.get(1), "a");
    assertEquals(mostFrequent.get(2), "c");

    // a=1 b=2 c=2
    counter.newChar('c');
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(mostFrequent.get(0), "b");
    assertEquals(mostFrequent.get(1), "c");
    assertEquals(mostFrequent.get(2), "a");

    // a=1 b=2 c=3
    counter.newChar('c');
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(mostFrequent.get(0), "c");
    assertEquals(mostFrequent.get(1), "b");
    assertEquals(mostFrequent.get(2), "a");
  }

  @Test
  public void testMaximum() {
    NgramCounter counter = new NgramCounter(1);

    // a=2 b=1 c=3 d=1
    counter.newChar('a');
    counter.newChar('a');
    counter.newChar('b');
    counter.newChar('c');
    counter.newChar('c');
    counter.newChar('c');
    counter.newChar('d');

    // maximum=1
    List<String> mostFrequent = counter.getMostFrequent(1);
    assertEquals("c", mostFrequent.get(0));
    assertEquals(1, mostFrequent.size());

    // maximum=2
    mostFrequent = counter.getMostFrequent(2);
    assertEquals("c", mostFrequent.get(0));
    assertEquals("a", mostFrequent.get(1));
    assertEquals(2, mostFrequent.size());

    // maximum=3
    mostFrequent = counter.getMostFrequent(3);
    assertEquals("c", mostFrequent.get(0));
    assertEquals("a", mostFrequent.get(1));
    assertEquals("b", mostFrequent.get(2));
    assertEquals(3, mostFrequent.size());

    // maximum=4
    mostFrequent = counter.getMostFrequent(4);
    assertEquals("c", mostFrequent.get(0));
    assertEquals("a", mostFrequent.get(1));
    assertEquals("b", mostFrequent.get(2));
    assertEquals("d", mostFrequent.get(3));
    assertEquals(4, mostFrequent.size());
  }

  /**
   * Calls the the {@link NgramCounter#newChar(char) newChar} method of the given counter for each
   * character of the given string.
   */
  private void callNewChar(NgramCounter counter, String string) {
    for (char c : string.toCharArray()) {
      counter.newChar(c);
    }
  }

  @Test
  public void testDifferentLengths() {
    NgramCounter counter = new NgramCounter(2);

    // empty
    callNewChar(counter, "a");
    List<String> mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertTrue(mostFrequent.isEmpty());

    // cd=1
    counter.newWord();
    callNewChar(counter, "cd");
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(1, mostFrequent.size());
    assertEquals("cd", mostFrequent.get(0));

    // ab=1 bc=1 cd=2
    counter.newWord();
    callNewChar(counter, "abcd");
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(3, mostFrequent.size());
    assertEquals("cd", mostFrequent.get(0));
    assertEquals("ab", mostFrequent.get(1));
    assertEquals("bc", mostFrequent.get(2));

    // ab=1 bc=1 cd=2
    counter.newWord();
    callNewChar(counter, "a");
    counter.newWord();
    callNewChar(counter, "b");
    counter.newWord();
    callNewChar(counter, "c");
    mostFrequent = counter.getMostFrequent(LARGE_MAXIMUM);
    assertEquals(3, mostFrequent.size());
    assertEquals("cd", mostFrequent.get(0));
    assertEquals("ab", mostFrequent.get(1));
    assertEquals("bc", mostFrequent.get(2));
  }
}
