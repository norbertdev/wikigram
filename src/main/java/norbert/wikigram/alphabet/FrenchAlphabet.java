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
package norbert.wikigram.alphabet;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class represents the French alphabet.
 * 
 * This alphabet only contains the lower case characters.
 */
public class FrenchAlphabet implements Alphabet {
	private static final Character ALPHABET_ARRAY[] = { 'a', 'à', 'â', 'æ', 'b', 'c', 'ç', 'd', 'e', 'é',
			'è', 'ê', 'ë', 'f', 'g', 'h', 'i', 'î', 'ï', 'j', 'k', 'l', 'm', 'n', 'o', 'ô', 'œ', 'p',
			'q', 'r', 's', 't', 'u', 'ù', 'û', 'ü', 'v', 'w', 'x', 'y', 'ÿ', 'z' };
	private static final Collection<Character> ALPHABET = new HashSet<Character>(Arrays.asList(ALPHABET_ARRAY));

	/**
	 * Returns <code>true</code> if the given character belongs to the French alphabet, <code>false</code> otherwise.
	 * 
	 * The alphabet only contains the lower case characters.
	 * 
	 * @param c a character to test
	 * @return <code>true</code> if the given character belongs to the French alphabet, <code>false</code> otherwise.
	 */
	@Override
	public boolean constains(char c) {
		return ALPHABET.contains(c);
	}
}
