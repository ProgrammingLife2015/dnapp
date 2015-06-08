package nl.tudelft.ti2806.pl1.gui.contentpane;

/**
 * Decides which zoom level to use.
 */
public final class ZoomSelector {

	/** Private constructor. **/
	private ZoomSelector() {
	}

	/**
	 * 
	 * @param zoomLevel
	 *            The zoom level for which we want to get the graph.
	 * @return Returns the correct graph.
	 */
	public static String getGraph(final int zoomLevel) {
		String level;
		switch (zoomLevel) {
		case 0:
			level = "standard";
			break;
		case 1:
			level = "zoomin 1";
			break;
		case -1:
			level = "zoomout 1";
			break;
		default:
			level = "special";
			break;
		}
		return level;
	}
}
