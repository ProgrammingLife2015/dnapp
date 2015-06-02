/**
 * 
 */
package nl.tudelft.ti2806.pl1.geneAnnotation;

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

	@Override
	public String toString() {
		return "[" + start + ", " + end + ", " + score + ", " + strand + ", "
				+ name + "]";
	}
}
