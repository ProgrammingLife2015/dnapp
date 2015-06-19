package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import nl.tudelft.ti2806.pl1.gui.contentpane.ContentTab;

/**
 * The main tool bar of the application showing at the top of the window (right
 * below the menu bar), or floating around.
 * 
 * @author Maarten
 */
public class ToolBar extends JToolBar {

	/** The serial version UID. */
	private static final long serialVersionUID = 7980283436568336682L;

	/** The window. */
	private Window window;

	/** The label in front of the import buttons. */
	private JLabel lblImport = new JLabel("Import: ");

	/** The import phylogenetic tree button. */
	private JButton btnImportPhylo = makeButton("Phylogenetic tree", null, AppEvent.IMPORT_PHYLO,
			"Click to load a phylogenetic tree from a newick file format.");

	/** Button for importing a gene annotation file. */
	private JButton btnImportGeneAnn = makeButton("Gene annotation", null, AppEvent.IMPORT_GENE_ANN,
			"Click to load gene annotation information from a gff file.");

	/** Button for importing resistance causing mutations. */
	private JButton btnImportResCausMut = makeButton("Known resistant mutations", null,
			AppEvent.IMPORT_RES_CAUS_MUT,
			"Click to load information about known resistant mutations from a krm file.");

	/**
	 * Initializes the tool bar.
	 * 
	 * @param w
	 *            The window.
	 */
	public ToolBar(final Window w) {
		super("DN/App toolbar", JToolBar.HORIZONTAL);
		this.window = w;
		setRollover(true);
		setVisible(false);
		addGeneralButtons();
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && window.getContent().isGraphLoaded();
	}

	/**
	 * 
	 * @param text
	 *            The text to show.
	 * @param imageName
	 *            File name of the image icon.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * @return The created button.
	 */
	public static JButton makeButton(final String text, final String imageName, final ActionListener action,
			final String toolTipText) {
		JButton button = new JButton();
		button.setText(text);
		button.setToolTipText(toolTipText);
		button.addActionListener(action);
		if (imageName != null) {
			String imgLocation = "images/" + imageName;
			URL imageURL = ToolBar.class.getResource(imgLocation);
			if (imageURL != null) {
				button.setIcon(new ImageIcon(imageURL, text));
			} else {
				System.err.println("Resource not found: " + imgLocation);
			}
		}
		return button;
	}

	/** Adds the control elements to the tool bar. */
	private void addGeneralButtons() {
		this.removeAll();
		add(lblImport);
		add(btnImportPhylo);
		add(btnImportGeneAnn);
		add(btnImportResCausMut);
		addSeparator();
	}

	/**
	 * @param contentTab
	 *            The content tab which tool bar buttons should now be shown.
	 */
	public void viewContextChanged(final ContentTab contentTab) {
		removeAll();
		addGeneralButtons();
		for (JComponent c : contentTab.getToolBarControls()) {
			add(c);
		}
		repaint();
	}

}
