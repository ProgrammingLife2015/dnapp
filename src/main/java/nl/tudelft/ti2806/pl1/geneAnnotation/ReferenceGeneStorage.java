/**
 * 
 */
package nl.tudelft.ti2806.pl1.geneAnnotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Storage location of all the genes of the TKK_REF with their information
 * included.
 * 
 * @author ChakShun
 * @since 2-6-2015
 */
public class ReferenceGeneStorage {

	/** Index for the start coordinate in the file. */
	private static final int INDEX_START = 3;
	/** Index for the end coordinate in the file. */
	private static final int INDEX_END = 4;
	/** Index for the score in the file. */
	private static final int INDEX_SCORE = 5;
	/** Index for the strand input in the file. */
	private static final int INDEX_STRAND = 6;
	/** Index for the attributes in the file. */
	private static final int INDEX_ATTRIBUTES = 8;

	/** All the genes and information extracted from the information file. */
	private TreeSet<ReferenceGene> referenceGenes;

	/**
	 * Constructor reading the gff3 gene files, extracting all the possible
	 * genes in the reference genome.
	 * 
	 * @param filePath
	 *            Path to the file from which information should be extracted.
	 */
	public ReferenceGeneStorage(final String filePath) {
		this.referenceGenes = extractReferenceGenes(filePath);
	}

	/**
	 * Extracts all the r eferences genes from the decorationV5_20130412.gff
	 * file.
	 * 
	 * @param file
	 *            File from which gene information to be extracted.
	 * @return HashSet containing all the genes for the reference genome.
	 */
	private TreeSet<ReferenceGene> extractReferenceGenes(final String file) {
		TreeSet<ReferenceGene> ret = new TreeSet<ReferenceGene>(
				new RefGeneCompare());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					file)));
			Scanner sc = new Scanner(reader);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.contains("CDS")) {
					String[] info = line.split("\\t");
					String name = info[INDEX_ATTRIBUTES].split(";")[1].replace(
							"Name=", "");
					ret.add(new ReferenceGene(Integer
							.valueOf(info[INDEX_START]), Integer
							.valueOf(info[INDEX_END]), Double
							.valueOf(info[INDEX_SCORE]), info[INDEX_STRAND],
							name));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/** @return the name genes. */
	public TreeSet<ReferenceGene> getReferenceGenes() {
		return referenceGenes;
	}

	/**
	 * Checks for the given position whether it falls into a intragenic region.
	 * 
	 * @param index
	 *            Index of the position to be checked.
	 * @return Whether the index is in a intragenic region.
	 */
	public boolean isIntragenic(final int index) {
		for (ReferenceGene rg : getReferenceGenes()) {
			if (index >= rg.getStart()) {
				if (index <= rg.getEnd()) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

}
