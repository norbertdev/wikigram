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

public class EnclosedCharRemoverWriter extends FilterWriter {
	private final char closingChar;
	private final char openingChar;
	private int relativeOpeningLinkCounter;

	public EnclosedCharRemoverWriter(Writer out, char openingChar, char closingChar) {
		super(out);

		this.openingChar = openingChar;
		this.closingChar = closingChar;

		relativeOpeningLinkCounter = 0;
	}

	@Override
	public void flush() throws IOException {
		relativeOpeningLinkCounter = 0;
		super.flush();
	}

	//FIXME: remove this debug method (optimized)
	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		//char[] outputBuffer = new char[len];
		int outputBufferIndex = off;
		int maximumIndex = len + off;

		if (relativeOpeningLinkCounter == 0) {
			while (outputBufferIndex < maximumIndex
					&& cbuf[outputBufferIndex] != openingChar) {
				outputBufferIndex++;
			}
		}

		for (int index = outputBufferIndex; index < maximumIndex; index++) {
			char currentChar = cbuf[index];

			if (currentChar == openingChar) {
				relativeOpeningLinkCounter++;
			} else if (currentChar == closingChar) {
				if (relativeOpeningLinkCounter != 0) {
					relativeOpeningLinkCounter--;
				}
			} else if (relativeOpeningLinkCounter == 0) {
				//outputBuffer[outputBufferIndex] = currentChar;
				cbuf[outputBufferIndex] = cbuf[index];
				outputBufferIndex++;
			}
		}
		out.write(cbuf, off, outputBufferIndex - off);
	}

	/*
	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		char[] outputBuffer = new char[len];
		int nbOfCharsToCopy = 0;
		int dstStartingIndex = 0;

		for (int index = off; index < len; index++) {
			char currentChar = cbuf[index];

			if (currentChar == openingChar) {
				if (relativeOpeningLinkCounter == 0) {
					System.arraycopy(cbuf, index - nbOfCharsToCopy,
								outputBuffer, dstStartingIndex, nbOfCharsToCopy);
					dstStartingIndex += nbOfCharsToCopy;
					nbOfCharsToCopy = 0;
				}
				relativeOpeningLinkCounter++;
			} else if (currentChar == closingChar) {
				if (relativeOpeningLinkCounter != 0) {
					relativeOpeningLinkCounter--;
				}
			} else if (relativeOpeningLinkCounter == 0) {
				nbOfCharsToCopy++;
			}
		}
		System.arraycopy(cbuf, len - nbOfCharsToCopy,
				outputBuffer, dstStartingIndex, nbOfCharsToCopy);

		out.write(outputBuffer, 0, dstStartingIndex + nbOfCharsToCopy);
	}
	*/
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

	//@Override
	public void writeOrig(char cbuf[], int off, int len) throws IOException {
		char[] outputBuffer = new char[len];
		int outputBufferIndex = 0;

		for (int index = off; index < len + off; index++) {
			char currentChar = cbuf[index];

			if (currentChar == openingChar) {
				relativeOpeningLinkCounter++;
			} else if (currentChar == closingChar) {
				if (relativeOpeningLinkCounter != 0) {
					relativeOpeningLinkCounter--;
				}
			} else if (relativeOpeningLinkCounter == 0) {
				outputBuffer[outputBufferIndex] = currentChar;
				outputBufferIndex++;
			}
		}
		out.write(outputBuffer, 0, outputBufferIndex);
	}

	//FIXME: remove this debug method
	//@Override
	public void writeSameBuffer(char cbuf[], int off, int len) throws IOException {
		//char[] outputBuffer = new char[len];
		int outputBufferIndex = off;

		for (int index = off; index < len + off; index++) {
			char currentChar = cbuf[index];

			if (currentChar == openingChar) {
				relativeOpeningLinkCounter++;
			} else if (currentChar == closingChar) {
				if (relativeOpeningLinkCounter != 0) {
					relativeOpeningLinkCounter--;
				}
			} else if (relativeOpeningLinkCounter == 0) {
				//outputBuffer[outputBufferIndex] = currentChar;
				cbuf[outputBufferIndex] = cbuf[index];
				outputBufferIndex++;
			}
		}
		out.write(cbuf, off, outputBufferIndex - off);
	}

}
