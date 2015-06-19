package nl.tudelft.ti2806.pl1.mutation;

/**
 * Multiplier for score on a mutation.
 * 
 * @author Marissa
 * @since 18-06-15
 */
public enum MutationMultipliers {

	/**
	 * Multiplier for score if mutation is in a gene.
	 */
	IN_GENE {

		@Override
		public String getGUI() {
			return "Mutation in Gene";
		}

	},

	/**
	 * Multiplier for score of its a known mutation.
	 */
	KNOWN_MUTATION {

		@Override
		public String getGUI() {
			return "Known Mutation";
		}

	},

	/** Importance INDELS. **/
	INDEL {
		@Override
		public String getGUI() {
			return "Importance INDELs";
		}
	},

	/** Importance Pointmutations. **/
	POINTMUTATION {
		@Override
		public String getGUI() {
			return "Importance Pointmutations";
		}
	};

	/**
	 * Get the GUI name for the multiplier.
	 * 
	 * @return Name we want the GUI to use.
	 */
	public abstract String getGUI();

}
