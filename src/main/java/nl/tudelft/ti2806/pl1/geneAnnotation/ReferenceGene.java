/**
 * 
 */
package nl.tudelft.ti2806.pl1.geneAnnotation;

import java.util.HashMap;

/**
 * Storage location for information about a gene.
 * 
 * @author ChakShun
 * @since 2-6-2015
 */
public class ReferenceGene {

	/** Coordinates of the gene. */
	private int start, end;

	/** Score of the gene. */
	private double score;

	/**
	 * Strand of the feature. + for positive strand, - for minus strand, . for
	 * non-stranded and ? for relevant but unknown.
	 */
	private String strand;

	/** Name of the feature. */
	private String name;

	/** Indices where mutation causes a drug resistance. */
	private HashMap<Integer, String> mutations;

	/**
	 * Constructor for storing all the needed information about a gene.
	 * 
	 * @param startIn
	 *            Begin coordinate of the gene.
	 * @param endIn
	 *            End coordinate of the gene.
	 * @param scoreIn
	 *            Score of the gene feature.
	 * @param strandIn
	 *            Strand of the feature.
	 * @param nameIn
	 *            Name of the feature.
	 */
	public ReferenceGene(final int startIn, final int endIn,
			final double scoreIn, final String strandIn, final String nameIn) {
		this.start = startIn;
		this.end = endIn;
		this.score = scoreIn;
		this.strand = strandIn;
		this.name = nameIn;
		this.mutations = new HashMap<Integer, String>();
	}

	/**
	 * Checks for the given position whether it falls into the gene's region.
	 * 
	 * @param index
	 *            Index of the position to be checked.
	 * @return Whether the index is in the gene's region.
	 */
	public boolean isIntragenic(final int index) {
		if (index >= this.getStart() && index <= this.getEnd()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for the given position whether it is a mutation resulting in a
	 * change of the current gene.
	 * 
	 * @param index
	 *            Index of the position to be checked
	 * @return Whether the index results in a change of the current gene.
	 */
	public boolean containsMutationIndex(final int index) {
		return this.getMutations().keySet().contains(index);
	}

	/**
	 * @return the start
	 */
	public final int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public final int getEnd() {
		return end;
	}

	/**
	 * @return the score
	 */
	public final double getScore() {
		return score;
	}

	/**
	 * @return the strand
	 */
	public final String getStrand() {
		return strand;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/** @return The mutations in this gene. */
	public final HashMap<Integer, String> getMutations() {
		return mutations;
	}

	/**
	 * Adds the mutation to the list of mutations of this gene.
	 * 
	 * @param location
	 *            Location of the mutation.
	 * @param mutName
	 *            Name of the mutation.
	 */
	public final void addMutation(final int location, final String mutName) {
		this.getMutations().put(location, mutName);
	}

	@Override
	public String toString() {
		return "Gene[" + start + ", " + end + ", " + score + ", " + strand
				+ ", " + name + "," + this.getMutations().toString() + "]";
	}
}
