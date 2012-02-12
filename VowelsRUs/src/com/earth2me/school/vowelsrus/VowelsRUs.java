package com.earth2me.school.vowelsrus;

import java.io.*;

/**
 * ACSL is a planet with four vowels: A, C, S, and L.
 * 
 * @author Paul Buonopane
 * @category APCS
 * @version 1.00 2011/12/8
 * @school Medfield High School
 */
public class VowelsRUs
{
	// Look, Ma, no globals!

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            Command-line parameters.
	 */
	public static void main(String[] args)
	{
		try
		{
			run(new File("../vowels.txt"));
		}
		catch (Throwable ex)
		{
			// All your base are belong to us.
			ex.printStackTrace();
		}
	}

	/**
	 * Run the (unsafe) main loop. Exceptions must be caught elsewhere.
	 * 
	 * @param file
	 *            Indicates the file from which words and suffixes should be
	 *            read.
	 */
	private static void run(final File file)
			throws IOException
	{
		// Read each line in the file.
		BufferedReader rx = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		for (String line; (line = rx.readLine()) != null;)
		{
			// Ignore blank lines.
			if ("".equals(line))
			{
				continue;
			}

			// Tokenize by spaces. We should only have two tokens.
			String[] tok = line.toUpperCase().split("\\s+", 2);
			assert tok.length == 2;

			// Print results.
			printWord(tok[0], tok[1]);
		}
	}

	/**
	 * Prints a word along with its plural and suffixed variations
	 * 
	 * @param word
	 *            The word to print
	 * @param suffix
	 *            The suffix to append to the word in one variation
	 */
	private static void printWord(String word, String suffix)
	{
		Endings ending = getEnding(word);

		// Dump everything.
		System.out.print("Word: ");
		System.out.println(word);
		System.out.print("Ending: ");
		System.out.print("Suffix: ");
		System.out.println(suffix);
		System.out.print("Plural: ");
		System.out.println(getPlural(word, ending));
		System.out.print("Suffixed: ");
		System.out.println(getSuffixed(word, suffix, ending));
		System.out.println();
	}

	/**
	 * Determines whether a given character is a vowel in ACSL.
	 * 
	 * @param c
	 *            The character to evaluate
	 * @returns true if the character is A, C, S, or L; otherwise, false
	 */
	private static boolean isVowel(char c)
	{
		switch (c)
		{
		case 'A':
		case 'C':
		case 'S':
		case 'L':
			// We found an ACSL vowel.
			return true;

		default:
			// ...or not.
			return false;
		}
	}

	/**
	 * Precalculates the ending type of a word.
	 * 
	 * @param sword
	 *            The word to use
	 * @returns the ending type of the word
	 */
	private static Endings getEnding(String sword)
	{
		char[] word = sword.toCharArray();
		boolean v1 = isVowel(word[word.length - 1]);
		boolean v2 = isVowel(word[word.length - 2]);

		if (v1 == v2)
		{ // Word ends in two vowels or two consonants.
			return Endings.Two;
		}
		else if (v1)
		{ // Word ends in one vowel.
			return Endings.OneVowel;
		}
		else
		{ // Word ends in (at least) one consonant.
			return Endings.OneConsonant;
		}
	}

	/**
	 * Takes a singular word and turns it into its plural form.
	 * 
	 * @param sword
	 *            A singular word
	 * @param ending
	 *            The pre-calculated ending type of the singular word
	 * @returns a plural version of the word
	 */
	private static String getPlural(String sword, Endings ending)
	{
		StringBuilder word = new StringBuilder(sword);

		switch (ending)
		{
		case OneConsonant:
			// Append "GH".
			word.append("GH");
			break;

		case OneVowel:
			// Replace the last character with "G".
			word.setCharAt(word.length() - 1, 'G');
			break;

		case Two:
			// Repeat the last character and append "H".
			word.append(word.charAt(word.length() - 1));
			word.append('H');
			break;
		}

		return word.toString();
	}

	/**
	 * Add a suffix to a word.
	 * 
	 * @param word
	 *            The base word
	 * @param suffix
	 *            The suffix to append to the word
	 * @param ending
	 *            The pre-calculated ending type of the word
	 * @returns a suffixed version of the word
	 */
	private static String getSuffixed(String word, String suffix, Endings ending)
	{
		if (ending == Endings.OneConsonant)
		{
			// Simply append the suffix.
			return word + suffix;
		}

		final char first = suffix.charAt(0);
		final boolean isv = isVowel(first);

		if (!isv && ending == Endings.OneVowel || isv && ending == Endings.Two)
		{
			// Append the first letter of the suffix, then the whole suffix.
			return word + first + suffix;
		}

		if (isv)
		{
			// Append the suffix excluding its first character.
			return word + suffix.substring(1);
		}

		// Delete the second to last character, then append the suffix.
		StringBuilder bld = new StringBuilder(word);
		bld.deleteCharAt(bld.length() - 2);
		bld.append(suffix);
		return bld.toString();
	}

	/**
	 * Define the various ending formats for the words.
	 */
	private enum Endings
	{
		OneConsonant,
		OneVowel,
		Two
	}
}
