package nl.tudelft.ti2806.pl1.gui.contentpane;

/** Decides which zoomlevel to use. **/
public final class ZoomSelector {

	/** private constructor. **/
	private ZoomSelector() {

	}

	/**
	 * 
	 * @param zoomlevel
	 *            The zoomlevel for which we want to get the graph.
	 * @return Returns the correct graph.
	 */
	public static String getGraph(final int zoomlevel) {
		String level;
		switch (zoomlevel) {
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
