package nl.tudelft.ti2806.pl1.mutation;

import java.util.HashMap;

import javax.swing.JSlider;

/**
 * Keeps track of multiplier for each score category.
 * 
 * @author Marissa
 * @since 18-06-15
 */
public final class ScoreMultiplier {

	/** The multiplier value for each score category. **/
	private static HashMap<String, Double> mults = new HashMap<String, Double>();

	/** Convert it from 0-10 to 0-1 scale. **/
	private static final int MAKEPERCENTAGE = 10;

	/** Private constructor to avoid instantiation. **/
	private ScoreMultiplier() {
	}

	/**
	 * A score multiplier has changed and gets adjusted.
	 * 
	 * @param changed
	 *            The slider that has changed.
	 */
	public static void multiplierChange(final JSlider changed) {
		mults.put(changed.getName(), (double) changed.getValue()
				/ MAKEPERCENTAGE);
	}

	/**
	 * The multiplier for the score.
	 * 
	 * @param mult
	 *            The score we want to get the multiplier for.
	 * @return The multiplier for the given score.
	 */
	public static Double getMult(final String mult) {
		if (mults.containsKey(mult)) {
			return mults.get(mult);
		}
		return 1.0;
	}

	/**
	 * Set the score for a multiplier.
	 * 
	 * @param mult
	 *            The multiplier you want to set the value for.
	 * @param score
	 *            The score you want to set it to.
	 */
	public static void setMult(final String mult, final double score) {
		try {
			MutationMultipliers.valueOf(mult);
			mults.put(mult, score);
		} catch (IllegalArgumentException exception) {
			System.out.println("This multiplier does not exist");
		}
	}
}
