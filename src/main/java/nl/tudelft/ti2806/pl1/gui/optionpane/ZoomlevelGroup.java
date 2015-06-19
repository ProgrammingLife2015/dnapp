package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Zoom level information box in the option pane.
 * 
 * @author Chak Shun
 * @since 18-6-2015
 */
public class ZoomlevelGroup extends JPanel implements ZoomlevelObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = 8506736750408563126L;

	/** The default visible title. */
	private static final String DEFAULT_TITLE = "Current zoom level";

	/** The size of the group. */
	private static final Dimension SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH, 120);

	/** The total number of nodes in the original graph. */
	private int totalNodes;

	/** The number of nodes in the visual graph. */
	private int visibleNodes;

	/** The current zoom level threshold. */
	private int zoomLevel;

	/** The label showing the content. */
	private JLabel info;

	/**
	 * Initialize the zoom level group.
	 */
	public ZoomlevelGroup() {
		setLayout(new BorderLayout());
		setMinimumSize(SIZE);
		setPreferredSize(SIZE);
		setBorder(BorderFactory.createTitledBorder(DEFAULT_TITLE));
		info = new JLabel();
		add(info, BorderLayout.CENTER);
	}

	@Override
	public void update(final int totalNodesCount, final int visualNodesCount,
			final int zoomLevelIndex) {
		this.totalNodes = totalNodesCount;
		this.visibleNodes = visualNodesCount;
		this.zoomLevel = zoomLevelIndex;
		updateContent();
	}

	/**
	 * 
	 */
	private void updateContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><table>");
		mkRow(sb, "Zoom level:", String.valueOf(zoomLevel));
		mkRow(sb, "# visible nodes:", String.valueOf(visibleNodes));
		mkRow(sb, "# total nodes:", String.valueOf(totalNodes));
		mkRow(sb,
				"% collapsed:",
				asPercentage(((double) (totalNodes - visibleNodes))
						/ totalNodes, 2));
		sb.append("</table></html>");
		info.setText(sb.toString());
		System.out.println(this);
		System.out.println(info);
	}

	/**
	 * @param sb
	 *            The string builder.
	 * @param label
	 *            The row label.
	 * @param value
	 *            The row value.
	 */
	private void mkRow(final StringBuilder sb, final String label,
			final String value) {
		sb.append("<tr><td>");
		sb.append(label);
		sb.append("</td><td>");
		sb.append(value);
		sb.append("</td></tr>");
	}

	/**
	 * @param val
	 *            The value to convert.
	 * @param decimals
	 *            The number of decimal places
	 * @return The percentage string representation of <code>val</code>.
	 */
	private static String asPercentage(final double val, final int decimals) {
		final int perc = 100;
		final int dec = (int) Math.pow(10, decimals);
		return String.valueOf((double) Math.round(val * perc * dec) / dec)
				+ "%";
	}

	@Override
	public String toString() {
		return "<ZoomlevelGroup[total=" + totalNodes + ",visible="
				+ visibleNodes + ",level=" + zoomLevel + "]>";
	}
}
