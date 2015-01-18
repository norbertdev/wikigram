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
 * This writer lowers each character that goes through it. The
 * {@link java.lang.Character#toLowerCase(char)} method is used.
 */
public class ToLowerLetterWriter extends Writer {
	private Writer out;

	public ToLowerLetterWriter(Writer out) {
		this.out = out;
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		char[] outputBuffer = new char[len];

		for (int index = off; index < len; index++) {
			outputBuffer[index] = Character.toLowerCase(cbuf[index]);
		}
		out.write(outputBuffer, 0, len);
	}
}
