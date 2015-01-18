package norbert.wikigram.counter;

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
		assertTrue(t1.getString().equals("a"));
		assertTrue(t2.getString() == null);
		assertTrue(t3.getString() == null);
		t1.putChar('b');
		t2.putChar('b');
		t3.putChar('b');
		assertTrue(t1.getString().equals("b"));
		assertTrue(t2.getString().equals("ab"));
		assertTrue(t3.getString() == null);
		t1.putChar('c');
		t2.putChar('c');
		t3.putChar('c');
		assertTrue(t1.getString().equals("c"));
		assertTrue(t2.getString().equals("bc"));
		assertTrue(t3.getString().equals("abc"));
		t1.putChar('d');
		t2.putChar('d');
		t3.putChar('d');
		assertTrue(t1.getString().equals("d"));
		assertTrue(t2.getString().equals("cd"));
		assertTrue(t3.getString().equals("bcd"));
	}

	@Test
	public void testReset() {
		NgramGenerator t1 = new NgramGenerator(1);
		NgramGenerator t2 = new NgramGenerator(2);
		NgramGenerator t3 = new NgramGenerator(3);
		assertTrue(t2.getString() == null);
		t1.putChar('a');
		t2.putChar('a');
		t3.putChar('a');
		t1.putChar('b');
		t2.putChar('b');
		t3.putChar('b');
		assertTrue(t1.getString().equals("b"));
		assertTrue(t2.getString().equals("ab"));
		assertTrue(t3.getString() == null);
		t1.reset();
		t2.reset();
		t3.reset();
		assertTrue(t1.getString() == null);
		assertTrue(t2.getString() == null);
		assertTrue(t3.getString() == null);
		t1.putChar('c');
		t2.putChar('c');
		t3.putChar('c');
		assertTrue(t1.getString().equals("c"));
		assertTrue(t2.getString() == null);
		assertTrue(t3.getString() == null);
		t1.putChar('d');
		t2.putChar('d');
		t3.putChar('d');
		assertTrue(t1.getString().equals("d"));
		assertTrue(t2.getString().equals("cd"));
		assertTrue(t3.getString() == null);
	}
}
