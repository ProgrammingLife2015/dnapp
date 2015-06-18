package nl.tudelft.ti2806.pl1.mutation;

public enum MutationMultipliers {

	IN_GENE {

		@Override
		public String getGUI() {
			return "Mutation in Gene";
		}

	},

	KNOWN_MUTATION {

		@Override
		public String getGUI() {
			return "Known Mutation";
		}

	};

	public abstract String getGUI();

}
