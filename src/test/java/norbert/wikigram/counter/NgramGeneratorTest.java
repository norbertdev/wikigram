/*
 * This file is part of WikiGram.
 *
 * Copyright 2011 Norbert
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NgramGeneratorTest {
  @Test
  public void testGetString() {
    NgramGenerator t1 = new NgramGenerator(1);
    NgramGenerator t2 = new NgramGenerator(2);
    NgramGenerator t3 = new NgramGenerator(3);
    assertTrue(t2.getString() == null);
    t1.putChar('a');
    t2.putChar('a');
    t3.putChar('a');
    assertEquals(t1.getString(), "a");
    assertNull(t2.getString());
    assertNull(t3.getString());
    t1.putChar('b');
    t2.putChar('b');
    t3.putChar('b');
    assertEquals(t1.getString(), "b");
    assertEquals(t2.getString(), "ab");
    assertNull(t3.getString());
    t1.putChar('c');
    t2.putChar('c');
    t3.putChar('c');
    assertEquals(t1.getString(), "c");
    assertEquals(t2.getString(), "bc");
    assertEquals(t3.getString(), "abc");
    t1.putChar('d');
    t2.putChar('d');
    t3.putChar('d');
    assertEquals(t1.getString(), "d");
    assertEquals(t2.getString(), "cd");
    assertEquals(t3.getString(), "bcd");
  }

  @Test
  public void testReset() {
    NgramGenerator t1 = new NgramGenerator(1);
    NgramGenerator t2 = new NgramGenerator(2);
    NgramGenerator t3 = new NgramGenerator(3);
    assertNull(t2.getString());
    t1.putChar('a');
    t2.putChar('a');
    t3.putChar('a');
    t1.putChar('b');
    t2.putChar('b');
    t3.putChar('b');
    assertEquals(t1.getString(), "b");
    assertEquals(t2.getString(), "ab");
    assertNull(t3.getString());
    t1.reset();
    t2.reset();
    t3.reset();
    assertNull(t1.getString());
    assertNull(t2.getString());
    assertNull(t3.getString());
    t1.putChar('c');
    t2.putChar('c');
    t3.putChar('c');
    assertEquals(t1.getString(), "c");
    assertNull(t2.getString());
    assertNull(t3.getString());
    t1.putChar('d');
    t2.putChar('d');
    t3.putChar('d');
    assertEquals(t1.getString(), "d");
    assertEquals(t2.getString(), "cd");
    assertNull(t3.getString());
  }
}
