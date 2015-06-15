package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

import org.graphstream.ui.view.View;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public class Minimap extends JPanel implements GraphScrollObserver,
		GraphChangeObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -5634260033871592350L;

	/** The height of the minimap. */
	private static final int HEIGHT = 25;

	/**
	 * The view.
	 * 
	 * @see View
	 */
	private View graphView;

	/** The reference gene storage. */
	private ReferenceGeneStorage ref;

	/** The view area. */
	private ViewArea viewArea;

	/** The size of the reference genome. */
	private int refSize;

	/**
	 * Initialize the minimap.
	 */
	public Minimap() {
		setMinimumSize(new Dimension(2, HEIGHT));
		setBackground(Color.ORANGE);
		setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
	}

	/**
	 * 
	 * @param refGeneStorage
	 *            The reference gene storage to visualize.
	 */
	public void setReferenceGene(final ReferenceGeneStorage refGeneStorage) {
		this.ref = refGeneStorage;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
	}

	@Override
	public void update(final ViewArea currentViewArea) {
		this.viewArea = currentViewArea;
	}

	@Override
	public void update(final View view) {
		this.graphView = view;
	}

}
