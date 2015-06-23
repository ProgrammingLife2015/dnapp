package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.graph.DNode;

/**
 * @author Maarten
 * @since 17-6-2015
 */
public class ResistanceMutation {

	/** The index on the reference genome. */
	private long refIndex;

	/** The mutation description. */
	private String description;

	/** The reference genome data node this mutation takes place in. */
	private DNode dnode;

	/**
	 * Initialize a new resistance caused mutation.
	 * 
	 * @param refIndexIn
	 *            The index on the reference genome.
	 * @param descriptionIn
	 *            The mutation description.
	 */
	public ResistanceMutation(final long refIndexIn, final String descriptionIn) {
		this.refIndex = refIndexIn;
		this.description = descriptionIn;
	}

	/**
	 * @return the reference index.
	 */
	public final long getRefIndex() {
		return refIndex;
	}

	/**
	 * @return the mutation description.
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @return the data node.
	 */
	public final DNode getDnode() {
		return dnode;
	}

	/**
	 * @param dn
	 *            the data node to set.
	 */
	public final void setDnode(final DNode dn) {
		this.dnode = dn;
	}

}
