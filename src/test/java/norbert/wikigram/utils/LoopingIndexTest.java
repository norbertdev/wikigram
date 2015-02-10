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

import junit.framework.TestCase;

import org.junit.Test;

public class LoopingIndexTest extends TestCase {
	private LoopingIndex li2;
	private LoopingIndex li3;

	@Test
	public void testConstructors() throws Exception {
		li2 = new LoopingIndex(2);
		LoopingIndex li2a = new LoopingIndex(li2);
		assertTrue(li2.getValue() == li2a.getValue());
		assertFalse(li2.getValue() != li2a.getValue());
		li2.next();
		assertFalse(li2.getValue() == li2a.getValue());
		assertTrue(li2.getValue() == li2a.getValue() + 1);
		li2a.next();
		assertTrue(li2.getValue() == li2a.getValue());
		assertTrue(li2.getValue() == 1);

		LoopingIndex li4 = new LoopingIndex(4);
		LoopingIndex li5 = new LoopingIndex(5);
		for (int i = 0; i < 4; i++) {
			assertTrue(li4.getValue() == li5.getValue());
			li4.next();
			li5.next();
		}
		assertFalse(li4.getValue() == li5.getValue());
		li5.next();
		assertTrue(li4.getValue() == li5.getValue());
		assertTrue(li4.getValue() == 0);
	}

	@Test
	public void testNext() throws Exception {
		li2 = new LoopingIndex(2);
		li3 = new LoopingIndex(3);

		assertTrue(li2.getValue() == 0);
		li2.next();
		assertTrue(li2.getValue() == 1);
		li2.next();
		assertTrue(li2.getValue() == 0);
		li2.next();
		assertTrue(li2.getValue() == 1);
		assertTrue(li2.getValue() != 0);

		assertTrue(li3.getValue() == 0);
		li3.next();
		assertTrue(li3.getValue() == 1);
		li3.next();
		assertTrue(li3.getValue() == 2);
		li3.next();
		assertTrue(li3.getValue() == 0);
		assertFalse(li3.getValue() == 1);
	}
}
