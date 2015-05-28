package nl.tudelft.ti2806.pl1.gui.contentpane;

/** Decides which zoomlevel to use. **/
public class ZoomSelector {

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
			level = "oops";
			break;
		}
		return level;
	}
}
