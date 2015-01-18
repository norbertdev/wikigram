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

import norbert.wikigram.utils.FrenchAlphabet;

/**
 * This writer removes all but the words.
 * 
 * Each character that is not part of an alphabet is removed. Two words are separated by a space. For example, it converts "a1   bc2d" to "a bc d".
 * 
 * TODO: the alphabet must be given to the constructor. Currently, only lowercase letters from the French alphabet are accepted.
 */
public class OnlyWordFilterWriter extends FilterWriter {
	private boolean precedingCharIsALetter;

	public OnlyWordFilterWriter(Writer out) {
		super(out);
		precedingCharIsALetter = false;
	}

	@Override
	public void flush() throws IOException {
		precedingCharIsALetter = false;
		super.flush();
	}

	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		char[] outputBuffer = new char[len];
		int outputBufferIndex = 0;

		for (int index = off; index < len; index++) {
			char currentChar = cbuf[index];
			if (FrenchAlphabet.isLowerLetter(currentChar)) {
				outputBuffer[outputBufferIndex] = currentChar;
				outputBufferIndex++;
				precedingCharIsALetter = true;
			} else {
				if (precedingCharIsALetter) {
					outputBuffer[outputBufferIndex] = ' ';
					outputBufferIndex++;
					precedingCharIsALetter = false;
				}
			}
		}
		out.write(outputBuffer, 0, outputBufferIndex);
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