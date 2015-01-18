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
package norbert.wikigram;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Writer;

import norbert.wikigram.counter.NgramCounterWriter;
import norbert.wikigram.filter.HtmlTagRemoverWriter;
import norbert.wikigram.filter.LinkRemoverWriter;
import norbert.wikigram.filter.OnlyWordFilterWriter;
import norbert.wikigram.filter.RedirectionRemoverWriter;
import norbert.wikigram.filter.TemplateRemoverWriter;
import norbert.wikigram.filter.ToLowerLetterWriter;
import norbert.wikigram.xml.ArticleExtractor;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

/**
 * This class provides a main method that compute the number of ngrams from a XML dump of Wikipedia.
 * 
 * It only takes articles in account, thus ignore templates, discussion pages…
 */
public class WikiGram {
	public static void count(String filename) throws FileNotFoundException {
		FileInputStream fileStream = null;
		BufferedInputStream bufferedStream = null;
		BZip2CompressorInputStream decompressorStream = null;

		try {
			fileStream = new FileInputStream(filename);
			bufferedStream = new BufferedInputStream(fileStream, (int) Math.pow(2, 20));
			decompressorStream = new BZip2CompressorInputStream(bufferedStream);

			Writer ngramCounterWriter = new NgramCounterWriter();
			Writer onlyWordFilterWriter = new OnlyWordFilterWriter(ngramCounterWriter);
			Writer redirectionRemoverWriter = new RedirectionRemoverWriter(onlyWordFilterWriter);
			Writer toLowerLetterWriter = new ToLowerLetterWriter(redirectionRemoverWriter);
			Writer htmlTagRemoverWriter = new HtmlTagRemoverWriter(toLowerLetterWriter);
			Writer templateRemoverWriter = new TemplateRemoverWriter(htmlTagRemoverWriter);
			Writer linkRemoverWriter = new LinkRemoverWriter(templateRemoverWriter);
			Writer bufferWriter = new BufferedWriter(linkRemoverWriter);

			new ArticleExtractor(decompressorStream, bufferWriter);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fileStream != null) {
					fileStream.close();
				}
				if (bufferedStream != null) {
					bufferedStream.close();
				}
				if (decompressorStream != null) {
					decompressorStream.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage:\ntrigram [filename]");
		} else {
			String filename = args[0];
			try {
				WikiGram.count(filename);
			} catch (FileNotFoundException e) {
				System.err.println(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void testInputStreamOutputStream(String filename) {
		FileInputStream fileStream = null;
		BZip2CompressorInputStream decompressorStream = null;
		BufferedInputStream bufferedStream = null;
		InputStreamReader reader = null;
		try {
			int buffersize = 2048;
			fileStream = new FileInputStream(filename);
			bufferedStream = new BufferedInputStream(fileStream);
			decompressorStream = new BZip2CompressorInputStream(bufferedStream);
			reader = new InputStreamReader(decompressorStream, "UTF8");

			final char[] buffer = new char[buffersize];
			for (int i = 0; i < 3; i++) {
				reader.read(buffer);
				for (char b : buffer) {
					System.out.print(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (decompressorStream != null) {
					decompressorStream.close();
				}
				if (fileStream != null) {
					fileStream.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}