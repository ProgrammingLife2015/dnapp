package nl.tudelft.ti2806.pl1.gui;

/**
 * @author Maarten
 * @since 5-6-2015
 */
public enum ViewContext {

	/** View context used when the phylotree tab is active. */
	PHYLOTREE {
		@Override
		void addButtons(final ToolBar tb) {
			tb.addPhyloButtons();
		}
	},

	/** View context used when the graph tab is active. */
	GRAPH {
		@Override
		void addButtons(final ToolBar tb) {
			tb.addGraphButtons();
		}
	};

	/**
	 * @param tb
	 *            The tool bar.
	 */
	abstract void addButtons(ToolBar tb);
}