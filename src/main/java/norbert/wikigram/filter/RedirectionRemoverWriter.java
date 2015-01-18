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
package norbert.wikigram.filter;

import java.io.IOException;
import java.io.Writer;

/**
 * This writer removes the redirections that go through it.
 *
 * A redirection is detected by the "#redirect" string after a flush, or at the
 * start.
 */
public class RedirectionRemoverWriter extends Writer {
	private static final String REDIRECTION_STRING = "#redirect";
	private static final int BUFFER_CAPACITY = REDIRECTION_STRING.length();
	private final char[] buffer;
	private int bufferIndex;
	private boolean canWrite;
	private Writer out;

	public RedirectionRemoverWriter(Writer out) {
		this.out = out;
		buffer = new char[BUFFER_CAPACITY];
		bufferIndex = 0;
		canWrite = false;
	}

	@Override
	public void close() throws IOException {
		flush();
		out.close();
	}

	@Override
	public void flush() throws IOException {
		if (bufferIndex < BUFFER_CAPACITY) {
			out.write(buffer, 0, bufferIndex);
		}
		bufferIndex = 0;
		canWrite = false;
		out.flush();
	}

	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		if (bufferIndex < BUFFER_CAPACITY) {
			int cbufRelativeIndex = 0;
			while (bufferIndex < BUFFER_CAPACITY && cbufRelativeIndex < len) {
				buffer[bufferIndex] = cbuf[off + cbufRelativeIndex];
				bufferIndex++;
				cbufRelativeIndex++;
			}
			if (bufferIndex == BUFFER_CAPACITY) {
				if (!REDIRECTION_STRING.equals(new String(buffer))) {
					canWrite = true;
				}
			}
		}
		// FIXME: if len < BUFFER_CAPACITY, the cbuf might not be passed to the
		// next writer
		// for example: write("abc"); write("defghijklmn"); will only output
		// "defghijklmn"
		// the buffer must also be written
		if (canWrite) {
			out.write(cbuf, off, len);
		}
	}
}
