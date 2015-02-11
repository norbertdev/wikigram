/*
 * This file is part of WikiGram.
 *
 * Copyright 2011, 2015 Norbert
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
package norbert.wikigram.filter;

import java.io.IOException;
import java.io.Writer;

/**
 * This writer removes the characters enclosed by two characters.
 *
 * For example, if '(' and ')' are the given characters, it converts "a((b)c)d" to "ad". A call to {
 * {@link #flush()} reset the internal state. Thus, <code>write("a(b"); flush(); write("c)d")</code>
 * will produce "ac)d" instead of "ad".
 */
public class EnclosedCharRemoverWriter extends Writer {
  private final char closingChar;
  private final char openingChar;
  private final Writer nextWriter;
  private int relativeOpeningLinkCounter;

  public EnclosedCharRemoverWriter(Writer nextWriter, char openingChar, char closingChar) {
    this.nextWriter = nextWriter;

    this.openingChar = openingChar;
    this.closingChar = closingChar;

    relativeOpeningLinkCounter = 0;
  }

  @Override
  public void close() throws IOException {
    flush();
    nextWriter.close();
  }

  @Override
  public void flush() throws IOException {
    relativeOpeningLinkCounter = 0;
    nextWriter.flush();
  }

  @Override
  public void write(char buffer[], int offset, int length) throws IOException {
    int index = offset;
    while (index < length + offset) {
      if (relativeOpeningLinkCounter == 0) {
        // do write
        boolean loop = true;
        int start = index;
        while (index < length + offset && loop) {
          char c = buffer[index];
          if (c == openingChar) {
            relativeOpeningLinkCounter = 1;
            loop = false;
            break;
          } else {
            index++;
          }
        }
        nextWriter.write(buffer, start, index - start);
        index++;
      } else {
        // do not write
        while (index < length + offset && relativeOpeningLinkCounter != 0) {
          char c = buffer[index];
          if (c == openingChar) {
            relativeOpeningLinkCounter++;
          } else if (c == closingChar) {
            relativeOpeningLinkCounter--;
          }
          index++;
        }
      }
    }
  }
}
