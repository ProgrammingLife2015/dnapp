package nl.tudelft.ti2806.pl1.gui;

import java.awt.Color;

/**
 * @author Maarten
 * @since 8-6-2015
 */
public enum NucleoBase {

	/** ADENINE. */
	A {
		@Override
		public Color getColor() {
			return COLOR_A;
		}
	},

	/** GUANINE. */
	G {
		@Override
		public Color getColor() {
			return COLOR_G;
		}
	},

	/** THYMINE. */
	T {
		@Override
		public Color getColor() {
			return COLOR_T;
		}
	},

	/** CYTOSINE. */
	C {
		@Override
		public Color getColor() {
			return COLOR_C;
		}
	},

	/** Uracil. */
	U {
		@Override
		public Color getColor() {
			return COLOR_U;
		}
	},

	/** Unknown. */
	N {
		@Override
		public Color getColor() {
			return COLOR_N;
		}
	};

	/** The bar color for the relative amount of Adenine. */
	private static final Color COLOR_A = Color.RED;

	/** The bar color for the relative amount of Thymine . */
	private static final Color COLOR_T = Color.BLUE;

	/** The bar color for the relative amount of Cytosine. */
	private static final Color COLOR_C = Color.YELLOW;

	/** The bar color for the relative amount of Guanine. */
	private static final Color COLOR_G = Color.GREEN;

	/** The bar color for the relative amount of Uracil. */
	private static final Color COLOR_U = Color.PINK;

	/** The bar color for the relative amount of Adenine. */
	private static final Color COLOR_N = Color.GRAY;

	/**
	 * @return The color representing the base type.
	 */
	public abstract Color getColor();
}
