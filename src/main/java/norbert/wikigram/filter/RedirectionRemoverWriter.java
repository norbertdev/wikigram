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
 * This writer removes the redirections that go through it.
 *
 * A redirection is detected by the "#redirect" string after a flush, or at the start.
 */
public class RedirectionRemoverWriter extends Writer {
  private static final String REDIRECTION_STRING = "#redirect";
  private static final int BUFFER_CAPACITY = REDIRECTION_STRING.length();
  /**
   * This buffer is filled by the characters to write just after a call to {@link #flush()} or at
   * the start.
   */
  private final char[] beginBuffer;
  /** Index referencing the place to write in the {@link #beginBuffer}. */
  private int beginBufferIndex;
  private boolean canWrite;
  private final Writer nextWriter;

  public RedirectionRemoverWriter(Writer nextWriter) {
    this.nextWriter = nextWriter;
    beginBuffer = new char[BUFFER_CAPACITY];
    beginBufferIndex = 0;
    canWrite = false;
  }

  @Override
  public void close() throws IOException {
    flush();
    nextWriter.close();
  }

  @Override
  public void flush() throws IOException {
    if (beginBufferIndex < BUFFER_CAPACITY) {
      nextWriter.write(beginBuffer, 0, beginBufferIndex);
    }
    beginBufferIndex = 0;
    canWrite = false;
    nextWriter.flush();
  }

  @Override
  public void write(char buffer[], int offset, int length) throws IOException {
    if (beginBufferIndex < BUFFER_CAPACITY) {
      int bufferRelativeIndex = 0;
      while (beginBufferIndex < BUFFER_CAPACITY && bufferRelativeIndex < length) {
        beginBuffer[beginBufferIndex] = buffer[offset + bufferRelativeIndex];
        beginBufferIndex++;
        bufferRelativeIndex++;
      }
      if (beginBufferIndex == BUFFER_CAPACITY) {
        if (!REDIRECTION_STRING.equals(new String(beginBuffer))) {
          canWrite = true;
        }
      }
    }

    if (canWrite) {
      nextWriter.write(buffer, offset, length);
    }
  }
}
