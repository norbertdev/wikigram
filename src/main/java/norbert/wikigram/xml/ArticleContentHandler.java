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

import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * This class is intended to be used by a SAX engine. It provides an XML handler that extracts the articles and write their content to a given {@link java.io.Writer}.
 * 
 * The writer is {@link java.io.Writer#flush() flushed} at the end of each article. 
 */
public class ArticleContentHandler implements ContentHandler {
	private static final String ARTICLES_ATTRIBUTE_VALUE = "0";
	private static final String KEY_ATTRIBUTE_NAME = "key";
	private static char NAMESPACE_SEPARATOR = ':';
	private static final String NAMESPACE_TAG = "namespace";
	private static final String NAMESPACES_TAG = "namespaces";
	private static final String TEXT_TAG = "text";
	private static final String TITLE_TAG = "title";

	private boolean insideNamepace;
	private boolean insideNamepaces;
	private boolean insideText;
	private boolean insideTitle;
	private boolean isAnArticle;
	private final CharBuffer namespaceBuffer;
	private final List<String> notAnArticleTitle;
	private final CharBuffer titleBuffer;

	private final Writer writer;

	/**
	 * Do not use.
	 */
	@SuppressWarnings("unused")
	private ArticleContentHandler() {
		this(null);
	}

	public ArticleContentHandler(Writer w) {
		if(w == null){
			throw new IllegalArgumentException("The writer given to the article content handler is null. It shouldn't.");
		}
		
		writer = w;
		titleBuffer = CharBuffer.allocate(1024);
		notAnArticleTitle = new ArrayList<String>();
		namespaceBuffer = CharBuffer.allocate(128);
		isAnArticle = false;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (insideTitle) {
			titleBuffer.put(ch, start, length);
		} else if (insideText && isAnArticle) {
			try {
				writer.write(ch, start, length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (insideNamepace) {
			namespaceBuffer.put(ch, start, length);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("End of the wikipedia dump.");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (insideText) {
			assert (localName.equals(TEXT_TAG));
			insideText = false;
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (insideTitle) {
			assert (localName.equals(TITLE_TAG));
			insideTitle = false;

			// check if there is a separator
			boolean hasANamespace = false;
			for (int index = 0; index < titleBuffer.position(); index++) {
				if (titleBuffer.get(index) == NAMESPACE_SEPARATOR) {
					hasANamespace = true;
				}
			}

			if (hasANamespace) {
				String titleName = new String(titleBuffer.array(), 0, titleBuffer.position());
				isAnArticle = true;
				for (String s : notAnArticleTitle) {
					if (titleName.startsWith(s)) {
						isAnArticle = false;
					}
				}
			} else {
				isAnArticle = true;
			}
			titleBuffer.clear();
		}
		if (insideNamepaces) {
			if (insideNamepace) {
				assert (localName.equals(NAMESPACE_TAG));
				insideNamepace = false;
				namespaceBuffer.put(NAMESPACE_SEPARATOR);
				notAnArticleTitle.add(new String(namespaceBuffer.array(), 0, namespaceBuffer.position()));
				namespaceBuffer.clear();
			}
			if (localName.equals(NAMESPACES_TAG)) {
				insideNamepaces = false;
			}
		}
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {}

	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {}

	@Override
	public void setDocumentLocator(Locator arg0) {}

	@Override
	public void skippedEntity(String arg0) throws SAXException {}

	@Override
	public void startDocument() throws SAXException {}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals(TEXT_TAG)) {
			insideText = true;
		} else if (localName.equals(TITLE_TAG)) {
			insideTitle = true;
		} else if (localName.equals(NAMESPACES_TAG)) {
			insideNamepaces = true;
		} else if (localName.equals(NAMESPACE_TAG)) {
			if (!atts.getValue(KEY_ATTRIBUTE_NAME).equals(ARTICLES_ATTRIBUTE_VALUE)) {
				insideNamepace = true;
			}
		}
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {}
}
