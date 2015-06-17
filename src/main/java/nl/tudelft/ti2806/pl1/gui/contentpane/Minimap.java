package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import nl.tudelft.ti2806.pl1.geneAnnotation.ResistanceMutationObserver;
import nl.tudelft.ti2806.pl1.mutation.ResistanceMutation;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public class Minimap extends JPanel implements GraphScrollObserver,
		ViewChangeObserver, ResistanceMutationObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -5634260033871592350L;

	/** The height of the mini map. */
	private static final int HEIGHT = 20;

	/** The graph panel. */
	private GraphPanel graphPanel;

	/** The view width. */
	private int graphWidth;

	/** Resistances causing mutations. */
	private Map<Long, ResistanceMutation> drugResMuts;

	/** The view area. */
	private ViewArea viewArea;

	/**
	 * Initialize the mini map.
	 * 
	 * @param gp
	 *            The Graph panel.
	 */
	public Minimap(final GraphPanel gp) {
		this.graphPanel = gp;
		gp.registerObserver((GraphScrollObserver) this);
		gp.registerObserver((ViewChangeObserver) this);

		setMinimumSize(new Dimension(2, HEIGHT));
		setPreferredSize(new Dimension(2, HEIGHT));
		setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
	}

	/**
	 * @param newDrugResMuts
	 *            The new resistances causing mutations.
	 */
	public void setDrugResMuts(
			final Map<Long, ResistanceMutation> newDrugResMuts) {
		this.drugResMuts = newDrugResMuts;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (isShowable()) {
			drawMap(g);
		}
	}

	/**
	 * Draws the content of the mini map.
	 * 
	 * @param g
	 *            The Graphics object to protect.
	 */
	private void drawMap(final Graphics g) {
		int mapWidth = getWidth();
		int boxWidth = (viewArea.getWidth() * mapWidth) / (graphWidth + 1);
		g.setColor(Color.RED);
		g.fillRect(viewArea.getLeftBoundary() * mapWidth / (graphWidth + 1), 0,
				boxWidth, HEIGHT);
		if (isMutsShowable()) {
			g.setColor(Color.GREEN);
			for (Long r : drugResMuts.keySet()) {
				g.fillRect(calculateX(r), 0, 2, HEIGHT);
			}
		}
	}

	/**
	 * Calculates the horizontal position of a reference genome index pointer.
	 * 
	 * @param refIndex
	 *            The position on the reference genome.
	 * @return The horizontal position on the mini map.
	 */
	private int calculateX(final long refIndex) {
		return (int) (getWidth() * ((double) refIndex / refSize()));
	}

	/**
	 * @return The size of the reference genome.
	 */
	private int refSize() {
		return graphPanel.getDgraph().getReferenceLength();
	}

	@Override
	public void update(final ViewArea currentViewArea) {
		this.viewArea = currentViewArea;
		repaint();
	}

	@Override
	public void update(final int newViewWidth) {
		this.graphWidth = newViewWidth;
		repaint();
	}

	@Override
	public void update(final Map<Long, ResistanceMutation> rgs) {
		System.out.println("Minimap update ResMuts");
		setDrugResMuts(rgs);
	}

	/**
	 * @return true iff the minibar is able to show anything.
	 */
	private boolean isShowable() {
		return graphWidth > 0 && viewArea != null;
	}

	/**
	 * @return true iff the minibar is able to show the known mutations.
	 */
	private boolean isMutsShowable() {
		boolean ret = isShowable() && refSize() > 0 && drugResMuts != null;
		System.out.println("isMutsShowable = " + ret);
		return ret;
	}

	@Override
	public String toString() {
		return "<Minimap[width=" + getWidth() + ",viewWidth=" + graphWidth
				+ ",viewArea=" + viewArea + "]>";
	}
}
