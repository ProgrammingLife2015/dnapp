package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Maarten
 * @since 20-5-2015
 * @version 1.0
 *
 */
public class NodeContentBar extends JPanel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -1358753355960431280L;

	/**
	 * The bar fill color.
	 */
	private static final Color COLOR_A = Color.RED, COLOR_T = Color.BLUE,
			COLOR_C = Color.YELLOW, COLOR_G = Color.GREEN,
			COLOR_N = Color.GRAY;

	/**
	 * The width of the bar.
	 */
	private int width;

	/**
	 * The length of the bar.
	 */
	private int length;

	/**
	 * The length of all nucleotides types, including the unknown ones.
	 */
	private int lenA, lenC, lenT, lenG, lenN;

	/**
	 * The total length of the string.
	 */
	private int totalLen;

	/**
	 * 
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
		// System.out.println("Analyze: " + newSequence);
		// System.out.println("Analyze Total length set = " + totalLen);
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
				System.out.println("ERROR: Invalid nucleotide found (" + c
						+ ") in string: " + newSequence);
			}
		}
		// System.out.println("Analyze " + lenA + " " + lenC + " " + lenT + " "
		// + lenG + " " + lenN);
		calculated = true;
		// revalidate();
		repaint();
	}

	@Override
	protected final void paintComponent(final Graphics g) {
		super.paintComponent(g);
		// System.out.println("paint comp calc=" + calculated);
		if (calculated && totalLen > 0) {
			// System.out.println("paint totallen=" + totalLen);
			int dLenA = length * lenA / totalLen;
			int dLenC = length * lenC / totalLen;
			int dLenT = length * lenT / totalLen;
			int dLenG = length * lenG / totalLen;
			int dLenN = length * lenN / totalLen;
			g.setColor(COLOR_A);
			g.fillRect(0, 0, dLenA, width);
			g.setColor(COLOR_T);
			g.fillRect(dLenA, 0, dLenT, width);
			g.setColor(COLOR_C);
			g.fillRect(dLenA + dLenT, 0, dLenC, width);
			g.setColor(COLOR_G);
			g.fillRect(dLenA + dLenT + dLenC, 0, dLenG, width);
			g.setColor(COLOR_N);
			g.fillRect(dLenA + dLenT + dLenC + dLenG, 0, dLenN, width);
			g.setColor(Color.BLACK);
			// System.out.println("Paint comp " + dLenA + " " + dLenC + " "
			// + dLenT + " " + dLenG + " " + dLenN);
		}
		// Graphics2D g2 = (Graphics2D) g;
		// g2.setStroke(new BasicStroke(1));
		// // System.out.println("Rect len=" + length + " wid=" + width);
		// g2.drawRect(0, 0, length, width);
	}

}
