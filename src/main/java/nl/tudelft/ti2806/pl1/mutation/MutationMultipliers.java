package nl.tudelft.ti2806.pl1.mutation;

/**
 * Multiplier for score on a mutation.
 * 
 * @author Marissa
 * @since 18-06-15
 */
public enum MutationMultipliers {
	/** Multiplier for score if mutation is in a gene. */
	IN_GENE("Mutation in Gene"),

	/** Multiplier for score of its a known mutation. */
	KNOWN_MUTATION("Known Mutation"),

	/** Importance INDELS. **/
	INDEL("Importance INDELs"),

	/** Importance Pointmutations. **/
	POINTMUTATION("Importance Pointmutations"),
	/** Use the Phylogenetic tree. **/
	PHYLO("Use phylogenetic tree");

	/** The GUI name for the multiplier. **/
	private final String guiname;

	/**
	 * Score multipliers for the mutation.
	 * 
	 * @param guiname
	 *            The GUI name for the multiplier.
	 */
	MutationMultipliers(final String guiname) {
		this.guiname = guiname;
	}

	/**
	 * Get the GUI name for the multiplier.
	 * 
	 * @return Name we want the GUI to use.
	 */
	public String getGUI() {
		return guiname;
	}

}
