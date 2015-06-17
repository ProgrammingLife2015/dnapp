package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.geneAnnotation.MetaDataLoadObserver;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public class Minimap extends JPanel implements GraphScrollObserver,
		ViewChangeObserver, MetaDataLoadObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -5634260033871592350L;

	/** The height of the mini map. */
	private static final int HEIGHT = 20;

	/** The graph panel. */
	private GraphPanel graphPanel;

	/** The list of known resistant mutation locations. */
	private List<Integer> locations;

	/**
	 * The view width.
	 * 
	 * @see View
	 */
	private int graphWidth;

	// /** The reference gene storage. */
	// private ReferenceGeneStorage ref;

	/** Resistances causing mutations. */
	private Map<Integer, String> drugResMuts;

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
		locations = new ArrayList<Integer>();
	}

	/**
	 * @param newDrugResMuts
	 *            The new resistances causing mutations.
	 */
	public void setDrugResMuts(final Map<Integer, String> newDrugResMuts) {
		this.drugResMuts = newDrugResMuts;
		calculateMutLocs();
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
	 * @see {@link #paintComponent(Graphics)}
	 */
	private void drawMap(final Graphics g) {
		int mapWidth = getWidth();
		int boxWidth = (viewArea.getWidth() * mapWidth) / (graphWidth + 1);
		g.setColor(Color.RED);
		g.fillRect(viewArea.getLeftBoundary() * mapWidth / (graphWidth + 1), 0,
				boxWidth, HEIGHT);
		g.setColor(Color.GREEN);
		for (Integer r : locations) {
			g.fillRect(r, 0, 2, HEIGHT);
		}
	}

	/**
	 * @return The size of the reference genome.
	 */
	private int refSize() {
		return graphPanel.getDgraph().getReferenceLength();
	}

	/**
	 * @return The collection of nodes of the reference genome.
	 */
	private Collection<DNode> refGenome() {
		return graphPanel.getDgraph().getRefGenome();
	}

	/**
	 * 
	 */
	private void calculateMutLocs() {
		locations.clear();
		if (isMutsShowable()) {
			Set<Integer> resist = drugResMuts.keySet();
			System.out.println("DrugResMuts keys = " + resist);
			for (DNode dn : refGenome()) {
				for (Integer r : resist) {
					if (r < 0) {
						System.out.println(r);
					}
					if (dn.getStart() <= r && r <= dn.getEnd()) {
						System.out.println("START=" + dn.getStart() + " | END="
								+ dn.getEnd() + " | " + "R=" + r);
						locations.add(r * getWidth() / refSize());
					}
				}
			}
		}
		System.out.println("calc locs = " + locations);
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
	public void update(final Map<Integer, String> rgs) {
		System.out.println("Minimap update REF GEN STO");
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
