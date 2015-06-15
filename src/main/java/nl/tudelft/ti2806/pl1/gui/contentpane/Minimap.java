package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public class Minimap extends JPanel implements GraphScrollObserver,
		ViewChangeObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -5634260033871592350L;

	/** The height of the minimap. */
	private static final int HEIGHT = 30;

	/**
	 * The view width.
	 * 
	 * @see View
	 */
	private int graphWidth;

	/** The reference gene storage. */
	private ReferenceGeneStorage ref;

	/** The view area. */
	private ViewArea viewArea;

	/** The size of the reference genome. */
	private int refSize;

	/** Whether the minimap has enough data to be painted. */
	private boolean showable = false;

	/**
	 * Initialize the minimap.
	 */
	public Minimap() {
		setMinimumSize(new Dimension(2, HEIGHT));
		setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
	}

	/**
	 * @param refGeneStorage
	 *            The reference gene storage to visualize.
	 */
	public void setReferenceGene(final ReferenceGeneStorage refGeneStorage) {
		this.ref = refGeneStorage;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (showable) {
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
	}

	@Override
	public void update(final ViewArea currentViewArea) {
		this.viewArea = currentViewArea;
		repaint();
		showable = true;
	}

	@Override
	public void update(final int newViewWidth) {
		this.graphWidth = newViewWidth;
		repaint();
		showable = true;
	}

	@Override
	public String toString() {
		return "<Minimap[width=" + getWidth() + ",viewWidth=" + graphWidth
				+ ",viewArea=" + viewArea + "]>";
	}
}
