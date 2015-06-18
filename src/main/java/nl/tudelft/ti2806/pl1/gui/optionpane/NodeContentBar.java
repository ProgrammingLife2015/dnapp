package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.NucleoBase;

/**
 * 
 * @author Maarten
 * @since 20-5-2015
 * @version 1.0
 *
 */
public class NodeContentBar extends JPanel {

	/** The serial version UID. */
	private static final long serialVersionUID = -1358753355960431280L;

	/** The vertical position of the sub bar labels. */
	private static final int Y_LABEL = 12;

	/** The horizontal offset of labels relative to the center of the sub bar. */
	private static final int X_OFFSET_LABEL = -3;

	/** The width of the bar. */
	private int width;

	/** The length of the bar. */
	private int length;

	/** The length of all nucleotides types, including the unknown ones. */
	private int lenA, lenC, lenT, lenG, lenN;

	/** The total length of the analyzed string. */
	private int totalLen;

	/**
	 * Whether a string has already been analyzed and so the bar is able to be
	 * shown.
	 */
	private boolean calculated;

	/**
	 * Initializes the bar chart.
	 * 
	 * @param widthIn
	 *            The width of the bar.
	 * @param lengthIn
	 *            The length of the bar.
	 */
	public NodeContentBar(final int widthIn, final int lengthIn) {
		super();
		this.width = widthIn;
		this.length = lengthIn;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(lengthIn, widthIn));
		setMinimumSize(new Dimension(lengthIn, widthIn));
		setMaximumSize(new Dimension(lengthIn, widthIn));
	}

	/**
	 * Analyze a new DNA sequence string and update the bar chart.
	 * 
	 * @param newSequence
	 *            The sequence to analyze.
	 */
	public final void analyseString(final String newSequence) {
		lenA = 0;
		lenC = 0;
		lenT = 0;
		lenG = 0;
		lenN = 0;
		this.totalLen = newSequence.length();
		for (char c : newSequence.toCharArray()) {
			switch (c) {
			case 'A':
				lenA++;
				break;
			case 'C':
				lenC++;
				break;
			case 'T':
				lenT++;
				break;
			case 'G':
				lenG++;
				break;
			case 'N':
				lenN++;
				break;
			default:
				Event.statusBarError("invalid nucleotide base \'" + c
						+ "\' found in selected node.");
				System.out.println("ERROR: Invalid nucleotide found (" + c
						+ ") in string: " + newSequence);
			}
		}
		calculated = true;
		repaint();
	}

	@Override
	protected final void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (calculated && totalLen > 0) {
			int dLenA = length * lenA / totalLen;
			int dLenC = length * lenC / totalLen;
			int dLenT = length * lenT / totalLen;
			int dLenG = length * lenG / totalLen;
			int dLenN = length * lenN / totalLen;
			drawSubBar(g, NucleoBase.A, 0, dLenA);
			drawSubBar(g, NucleoBase.T, dLenA, dLenT);
			drawSubBar(g, NucleoBase.C, dLenA + dLenT, dLenC);
			drawSubBar(g, NucleoBase.G, dLenA + dLenT + dLenC, dLenG);
			drawSubBar(g, NucleoBase.N, dLenA + dLenT + dLenC + dLenG, dLenN);
			g.setColor(Color.BLACK);
		}
	}

	/**
	 * Draws the sub bars and labels.
	 * 
	 * @param g
	 *            The Graphics object to protect.
	 * @param nuc
	 *            The nucleotide type.
	 * @param start
	 *            The left (start) position of the sub bar.
	 * @param barLength
	 *            The length of the sub bar.
	 */
	private void drawSubBar(final Graphics g, final NucleoBase nuc,
			final int start, final int barLength) {
		g.setColor(nuc.getColor());
		g.fillRect(start, 0, barLength, width);
		if (barLength > 0) {
			g.setColor(nuc.getColor().darker().darker());
			g.drawString(nuc.toString(),
					start + barLength / 2 + X_OFFSET_LABEL, Y_LABEL);
		}
	}

}
