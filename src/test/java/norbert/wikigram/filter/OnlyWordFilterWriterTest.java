/*
 * This file is part of WikiGram.
 *
 * Copyright 2015 Norbert
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;

import norbert.wikigram.alphabet.FrenchAlphabet;

import org.junit.Test;

public class OnlyWordFilterWriterTest {

  @Test
  public void simple() {

    String expected;
    String provided;

    // "" => ""
    provided = "";
    expected = "";
    assertEquals(expected, write(provided));

    // a => a
    provided = "a";
    expected = "a";
    assertEquals(expected, write(provided));

    // a!? => "a "
    provided = "a!? ";
    expected = "a ";
    assertEquals(expected, write(provided));

    // a b => a b
    provided = "a b";
    expected = "a b";
    assertEquals(expected, write(provided));

    // "a  b" => a b
    provided = "a  b";
    expected = "a b";
    assertEquals(expected, write(provided));

    // a b c => a b c
    provided = "a b c";
    expected = "a b c";
    assertEquals(expected, write(provided));

    // ab! !cd => ab cd
    provided = "ab! !cd";
    expected = "ab cd";
    assertEquals(expected, write(provided));
  }

  private String write(String s) {
    StringWriter sw = new StringWriter();

    try (OnlyWordFilterWriter tested = new OnlyWordFilterWriter(sw, new FrenchAlphabet())) {
      tested.write(s);
    } catch (IOException e) {
      fail();
    }

    return sw.toString();
  }
}
