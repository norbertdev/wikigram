package norbert.wikigram.utils;

import junit.framework.TestCase;

import norbert.wikigram.utils.LoopingIndex;

import org.junit.Test;

public class LoopingIndexTest extends TestCase {
	private LoopingIndex li2;
	private LoopingIndex li3;

	@Test
	public void testConstructors() throws Exception {
		li2 = new LoopingIndex(2);
		LoopingIndex li2a = new LoopingIndex(li2);
		assertTrue(li2.getIndex() == li2a.getIndex());
		assertFalse(li2.getIndex() != li2a.getIndex());
		li2.next();
		assertFalse(li2.getIndex() == li2a.getIndex());
		assertTrue(li2.getIndex() == li2a.getIndex() + 1);
		li2a.next();
		assertTrue(li2.getIndex() == li2a.getIndex());
		assertTrue(li2.getIndex() == 1);

		LoopingIndex li4 = new LoopingIndex(4);
		LoopingIndex li5 = new LoopingIndex(5);
		for (int i = 0; i < 4; i++) {
			assertTrue(li4.getIndex() == li5.getIndex());
			li4.next();
			li5.next();
		}
		assertFalse(li4.getIndex() == li5.getIndex());
		li5.next();
		assertTrue(li4.getIndex() == li5.getIndex());
		assertTrue(li4.getIndex() == 0);
	}

	@Test
	public void testNext() throws Exception {
		li2 = new LoopingIndex(2);
		li3 = new LoopingIndex(3);

		assertTrue(li2.getIndex() == 0);
		li2.next();
		assertTrue(li2.getIndex() == 1);
		li2.next();
		assertTrue(li2.getIndex() == 0);
		li2.next();
		assertTrue(li2.getIndex() == 1);
		assertTrue(li2.getIndex() != 0);

		assertTrue(li3.getIndex() == 0);
		li3.next();
		assertTrue(li3.getIndex() == 1);
		li3.next();
		assertTrue(li3.getIndex() == 2);
		li3.next();
		assertTrue(li3.getIndex() == 0);
		assertFalse(li3.getIndex() == 1);
	}

}
