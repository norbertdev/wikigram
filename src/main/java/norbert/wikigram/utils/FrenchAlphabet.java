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
package norbert.wikigram.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class FrenchAlphabet {
	private static final Character alphabetArray[] = { 'a', 'à', 'â', 'æ', 'b', 'c', 'ç', 'd', 'e', 'é',
			'è', 'ê', 'ë', 'f', 'g', 'h', 'i', 'î', 'ï', 'j', 'k', 'l', 'm', 'n', 'o', 'ô', 'œ', 'p',
			'q', 'r', 's', 't', 'u', 'ù', 'û', 'ü', 'v', 'w', 'x', 'y', 'ÿ', 'z' };
	private static final Collection<Character> alphabet = new HashSet<Character>(Arrays.asList(alphabetArray));

	public static boolean isLowerLetter(char c) {
		return alphabet.contains(c);
	}
}
