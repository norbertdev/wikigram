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
package norbert.wikigram.xml;

import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * This class provides a parsing mechanism that extracts the articles from an
 * input stream, and writes them in a given writer.
 */
public class ArticleExtractor {
	/**
	 * Extracts the articles from the given input stream, and writes them to the
	 * given writer.
	 *
	 * @param is
	 *            input stream to read
	 * @param writer
	 *            writer to write
	 */
	public static void extract(InputStream is, Writer writer) {
		// create SAX stream
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			SAXParser saxParser = spf.newSAXParser();

			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(new ArticleContentHandler(writer));
			xmlReader.parse(new InputSource(is));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
