package nl.tudelft.ti2806.pl1.gui;

import java.awt.Color;

/**
 * @author Maarten
 * @since 8-6-2015
 */
public enum NucleoBase {

	/** ADENINE. */
	A(Color.RED),

	/** GUANINE. */
	G(Color.GREEN),

	/** THYMINE. */
	T(Color.BLUE),

	/** CYTOSINE. */
	C(Color.YELLOW),

	/** URACIL. */
	U(Color.PINK),

	/** Unknown. */
	N(Color.GRAY);

	/** The base color for the nucleotide type. */
	private Color color;

	/**
	 * 
	 * @param colorIn
	 *            The base color to be used for the nucleotide type.
	 */
	NucleoBase(final Color colorIn) {
		this.color = colorIn;
	}

	/**
	 * @return The color representing the base type.
	 */
	public Color getColor() {
		return color;
	}
}
