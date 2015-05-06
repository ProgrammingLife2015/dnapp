package nl.tudelft.ti2806.pl1.aminoacids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import reader.AminoMapReader;

/**
 * 
 * @author Justin
 *
 */
public class AminoAcids {

	/**
	 * The size of a DNA codon is 3.
	 */
	static final int CODON_SIZE = 3;

	/**
	 * Converts a genome string into an ArrayList of amino acid strings.
	 * 
	 * @param genome
	 *            The genome string
	 * @return Amino acids
	 * @throws InvalidAminoException
	 *             The triplet is not valid
	 * @throws IOException
	 *             File does not exist
	 */
	public static final ArrayList<String> getAminoString(final String genome)
			throws IOException {
		ArrayList<String> triplets = new ArrayList<String>();
		char[] chars = genome.toCharArray();
		HashMap<String, String> map = AminoMapReader
				.readIntoMap("src/main/resources/amino_DNA_encoding.txt");
		int start = getStart(genome);
		if (start == -1) {
			return new ArrayList<String>();
		}
		boolean stop = false;
		while (!stop && start + 2 < chars.length) {
			String triplet = "" + chars[start] + chars[start + 1]
					+ chars[start + 2];
			String amino = getAcid(triplet, map);
			if (amino.equals("Stop")) {
				stop = true;
			} else {
				triplets.add(amino);
			}
			start = start + CODON_SIZE;
		}
		return triplets;
	}

	/**
	 * Finds the starting position of the first instance of Met in the genome.
	 * 
	 * @param genome
	 *            The genome string
	 * @return The starting position
	 */
	private static int getStart(final String genome) {
		int start = -1;
		int i = 0;
		char[] chars = genome.toCharArray();
		while (i < chars.length && start == -1) {
			if (chars[i] == 'T' && chars[i + 1] == 'A' && chars[i + 2] == 'C') {
				start = i;
			}
			i++;
		}
		return start;
	}

	/**
	 * Finds the matching amino acid of a triplet.
	 * 
	 * @param triplet
	 *            The triplet codon
	 * @param map
	 *            The map of amino acid encoding
	 * @return The amino acid
	 * @throws InvalidAminoException
	 */
	private static String getAcid(final String triplet,
			final HashMap<String, String> map) {
		return map.get(triplet);

	}

}
