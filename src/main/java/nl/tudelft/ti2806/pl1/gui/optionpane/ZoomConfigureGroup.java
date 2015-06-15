package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.ti2806.pl1.gui.Event;

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
	private static final int NUM_SLIDERS = 5;

	/** The default visible title. */
	private static final String DEFAULT_TITLE = "Zoom settings.";

	/**
	 * 
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
	 * 
	 */
	private void addComponents() {
		for (int i = 0; i < NUM_SLIDERS; i++) {
			JSlider slider = new JSlider(-10, 10);
			slider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(final ChangeEvent e) {
					Event.statusBarInfo("Slider to "
							+ ((JSlider) e.getSource()).getValue());
				}
			});
			add(slider);
		}
	}

}