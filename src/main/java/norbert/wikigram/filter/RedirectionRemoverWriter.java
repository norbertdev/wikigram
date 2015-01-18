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
package norbert.wikigram.filter;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class RedirectionRemoverWriter extends FilterWriter {
	private static final String REDIRECTION_STRING = "#redirect";
	private static final int BUFFER_CAPACITY = REDIRECTION_STRING.length();
	private final char[] buffer;
	private int bufferIndex;
	private boolean canWrite;

	public RedirectionRemoverWriter(Writer out) {
		super(out);
		buffer = new char[BUFFER_CAPACITY];
		bufferIndex = 0;
		canWrite = false;
	}

	@Override
	public void flush() throws IOException {
		if (bufferIndex < BUFFER_CAPACITY) {
			out.write(buffer, 0, bufferIndex);
		}
		bufferIndex = 0;
		canWrite = false;
		super.flush();
	}

	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		if (bufferIndex < BUFFER_CAPACITY) {
			int cbufRelativeIndex = 0;
			while (bufferIndex < BUFFER_CAPACITY
					&& cbufRelativeIndex < len) {
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
		if (canWrite) {
			out.write(cbuf, off, len);
		}
	}

	@Override
	public void write(int c) throws IOException {
		char[] array = new char[1];
		array[0] = (char) c;
		write(array, 0, 1);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		write(str.toCharArray(), off, len);
	}
}
