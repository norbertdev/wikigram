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
package norbert.wikigram.counter;

import java.io.IOException;
import java.io.Writer;

public class NgramCounterWriter extends Writer {
	private int articleCounter;
	private final NgramCounter counter;
	private boolean displayCurrentResult;
	private boolean displayStatistics;
	private int letterCounter;
	private final Thread userInputThread;
	private int wordCounter;

	public NgramCounterWriter() {
		counter = new NgramCounter();

		letterCounter = 0;
		wordCounter = 0;
		articleCounter = 0;
		displayCurrentResult = false;
		displayStatistics = false;

		userInputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					char c;
					do {
						c = (char) System.in.read();
						switch (c) {
						case 'a':
							System.out.println("article = " + articleCounter);
							break;

						case 'w':
							System.out.println("word = " + wordCounter);
							break;

						case 'l':
							System.out.println("letter = " + letterCounter);
							break;

						case 'd':
							displayCurrentResult = true;
							break;

						case 'q':
							printSummary();
							System.exit(0);
							break;

						case 'h':
							System.out.println("a: show the number of article processed.");
							System.out.println("w: show the number of word processed.");
							System.out.println("l: show the number of letter processed.");
							System.out.println("d: show a summary of article, word and letter processed, and the most used trigrams.");
							System.out.println("s: activate or deactivate statistics. Show every 10 seconds the current statistics.");
							System.out.println("h: show this help.");
							System.out.println("q: quit the program.");
							break;

						case 's':
							displayStatistics = !displayStatistics;
							if (displayStatistics) {
								System.out.println("display statistics: on");
							} else {
								System.out.println("display statistics: off");
							}
							break;

						default:
							break;
						}
						System.out.flush();
					} while (true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		userInputThread.start();

		Thread performanceEvalution = new Thread(new Runnable() {
			@Override
			public void run() {
				int iterationNumber = 1;
				int lastArticleCounter = articleCounter;
				while (true) {
					try {
						Thread.sleep(10000);
						if (displayStatistics) {
							System.out.println(iterationNumber + ". "
									+ "article processed: avg=" + articleCounter / (iterationNumber * 10)
									+ ", last=" + (articleCounter - lastArticleCounter)
									+ " (total: article=" + articleCounter
									+ ", word=" + wordCounter
									+ ")");
							lastArticleCounter = articleCounter;
						}
						iterationNumber++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		performanceEvalution.start();

		displayUsage();
	}

	@Override
	public void close() throws IOException {
		// TODO Stop the thread
	}

	private void displayUsage() {
		System.out.println("Type 'h' for help.");
	}

	@Override
	public void flush() throws IOException {
		counter.newWord();
		wordCounter++;
		articleCounter++;
	}

	private void printSummary() {
		System.out.println("article = " + articleCounter
				+ ", word = " + wordCounter
				+ ", letter = " + letterCounter);
		System.out.println(counter.getMostFrequent());
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if (displayCurrentResult) {
			printSummary();
			displayCurrentResult = false;
		}
		for (int index = off; index < len; index++) {
			char c = cbuf[index];
			if (c == ' ') {
				counter.newWord();
				wordCounter++;
			} else {
				counter.newChar(c);
				letterCounter++;
			}
		}
	}
}
