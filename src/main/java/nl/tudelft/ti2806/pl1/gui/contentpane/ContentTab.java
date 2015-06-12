package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.util.List;

import javax.swing.JComponent;

/**
 * @author Maarten
 * @since 5-6-2015
 */
public interface ContentTab {

	/**
	 * @return A list of tool bar controls to be used in the view context of the
	 *         implementer.
	 */
	List<JComponent> getToolBarControls();

}
