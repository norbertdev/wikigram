/*
 * This file is part of WikiGram.
 *
 * Copyright 2011 Norbert
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

import java.io.Writer;

/**
 * This writer removes the templates that go through it.
 *
 * For this class, a template is every character between the char "{" and the char "}". Thus, this
 * writer may remove text that are not part of a template.
 */
public class TemplateRemoverWriter extends EnclosedCharRemoverWriter {
  private static final char CLOSING_TEMPLATE_CHAR = '}';
  private static final char OPENING_TEMPLATE_CHAR = '{';

  public TemplateRemoverWriter(Writer out) {
    super(out, OPENING_TEMPLATE_CHAR, CLOSING_TEMPLATE_CHAR);
  }
}
