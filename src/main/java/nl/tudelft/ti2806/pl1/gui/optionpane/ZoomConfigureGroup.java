package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.mutation.MutationMultipliers;
import nl.tudelft.ti2806.pl1.mutation.ScoreMultiplier;

/**
 * @author Maarten
 * @since 15-5-2015
 *
 */
public class ZoomConfigureGroup extends JPanel {

	/** The serial version UID. */
	private static final long serialVersionUID = -3307531710544343583L;

	/** The size of the group. */
	private static final Dimension SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH, 200);

	/**
	 * The number of sliders.
	 */
	private static final int NUM_SLIDERS = MutationMultipliers.values().length;

	/** The default visible title. */
	private static final String DEFAULT_TITLE = "Zoom settings.";

	/** Slider options. **/
	private static final MutationMultipliers[] SLIDERS = MutationMultipliers
			.values();

	/** How many options the slider can take. **/
	private static final int SLIDERVALUES = 10;

	/**
	 * Initialize the zoom configure group.
	 */
	public ZoomConfigureGroup() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(SIZE);
		setAlignmentY(TOP_ALIGNMENT);
		setBorder(BorderFactory.createTitledBorder(DEFAULT_TITLE));
		addComponents();
	}

	/**
	 * Add the sliders.
	 */
	private void addComponents() {
		for (int i = 0; i < NUM_SLIDERS; i++) {
			JSlider slider = new JSlider(0, SLIDERVALUES, SLIDERVALUES);
			slider.setName(SLIDERS[i].name());
			slider.setPaintTicks(true);
			slider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(final ChangeEvent e) {
					JSlider changed = (JSlider) e.getSource();
					Event.statusBarInfo("Slider to "
							+ ((double) changed.getValue() / SLIDERVALUES));
					ScoreMultiplier.multiplierChange(changed);
				}
			});
			add(new JLabel(SLIDERS[i].getGUI()));
			add(slider);
		}
	}
}
