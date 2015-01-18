package norbert.wikigram.filter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;

import norbert.wikigram.filter.LinkRemoverWriter;

import org.junit.Test;

public class LinkRemoverWriterTest {
	@Test
	public void testWriteCharArrayIntIntFlush() {
		StringWriter sw = new StringWriter();
		LinkRemoverWriter tested = new LinkRemoverWriter(sw);

		String sIn1;
		String sIn2;
		String sIn3;
		String sIn4;
		String sOut1;
		String sOut2;

		try {
			sIn1 = "a";
			sIn2 = "b";
			sIn3 = "c";
			sIn4 = "d";
			sOut1 = "a";
			sOut2 = "bcd";
			tested.write(sIn1);
			tested.flush();
			assertTrue(sw.toString().equals(sOut1));
			sw.getBuffer().delete(0, sw.getBuffer().length());
			tested.write(sIn2);
			tested.write(sIn3);
			tested.write(sIn4);
			tested.flush();
			assertTrue(sw.toString().equals(sOut2));
			sw.getBuffer().delete(0, sw.getBuffer().length());

			sIn1 = "a[1[2[3]]][4[5";
			sIn2 = "b[[][";
			sIn3 = "]]c[6";
			sIn4 = "78]d";
			sOut1 = "a";
			sOut2 = "bcd";
			tested.write(sIn1);
			tested.flush();
			assertTrue(sw.toString().equals(sOut1));
			sw.getBuffer().delete(0, sw.getBuffer().length());
			tested.write(sIn2);
			tested.write(sIn3);
			tested.write(sIn4);
			tested.flush();
			assertTrue(sw.toString().equals(sOut2));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteCharArrayIntIntOneCall() {
		String sIn;
		String sOut;

		sIn = "";
		sOut = "";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "abcd";
		sOut = "abcd";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "ab[123]cd";
		sOut = "abcd";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "[123]";
		sOut = "";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "[[1]]";
		sOut = "";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "a[1]b";
		sOut = "ab";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "abc[1[2]3]ghi";
		sOut = "abcghi";
		assertTrue(writeInOneCall(sIn).equals(sOut));

		sIn = "[][]abc[[1]]d[2[3]4[[5]6] ]ef[]";
		sOut = "abcdef";
		assertTrue(writeInOneCall(sIn).equals(sOut));
	}

	@Test
	public void testWriteCharArrayIntIntSeveralCalls() {
		String sIn1;
		String sIn2;
		String sIn3;
		String sIn4;
		String sOut;

		sIn1 = "a";
		sIn2 = "b";
		sIn3 = "c";
		sIn4 = "d";
		sOut = "abcd";
		assertTrue(writeInThreeCall(sIn1, sIn2, sIn3, sIn4).equals(sOut));

		sIn1 = "[";
		sIn2 = "1";
		sIn3 = "2";
		sIn4 = "]";
		sOut = "";
		assertTrue(writeInThreeCall(sIn1, sIn2, sIn3, sIn4).equals(sOut));

		sIn1 = "[[";
		sIn2 = "1";
		sIn3 = "]2[";
		sIn4 = "]3]";
		sOut = "";
		assertTrue(writeInThreeCall(sIn1, sIn2, sIn3, sIn4).equals(sOut));

		sIn1 = "a[[1";
		sIn2 = "2[][][][][][[[]]]";
		sIn3 = "3]4]b[5";
		sIn4 = "67]c";
		sOut = "abc";
		assertTrue(writeInThreeCall(sIn1, sIn2, sIn3, sIn4).equals(sOut));
	}

	private String writeInOneCall(String s) {
		StringWriter sw = new StringWriter();
		LinkRemoverWriter tested = new LinkRemoverWriter(sw);
		try {
			tested.write(s);
			tested.flush();
			tested.close();
		} catch (IOException e) {
			fail();
		}
		return sw.toString();
	}

	private String writeInThreeCall(String s1, String s2,
			String s3, String s4) {
		StringWriter sw = new StringWriter();
		LinkRemoverWriter tested = new LinkRemoverWriter(sw);
		try {
			tested.write(s1);
			tested.write(s2);
			tested.write(s3);
			tested.write(s4);
			tested.flush();
			tested.close();
		} catch (IOException e) {
			fail();
		}
		return sw.toString();
	}
}
