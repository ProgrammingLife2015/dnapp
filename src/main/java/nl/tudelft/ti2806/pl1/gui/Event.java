package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.ti2806.pl1.exceptions.InvalidNodePlacementException;
import nl.tudelft.ti2806.pl1.file.ExportDialog;
import nl.tudelft.ti2806.pl1.file.ImportDialog;
import nl.tudelft.ti2806.pl1.file.ImportDialog.ImportType;

/**
 * @author Maarten
 *
 */
public enum Event implements ActionListener {

	/**
	 * Exits the application.
	 */
	EXIT_APP {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			System.out.println("Bye bye!");
			window.dispose();
			System.exit(0);
		}

	},

	/**
	 * Loads a graph into the window content.
	 */
	IMPORT_FILE {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final ImportDialog fsNode = new ImportDialog(ImportType.NODES);
			if (fsNode.showDialog(window, "Load nodes") == JFileChooser.APPROVE_OPTION) {
				final File nodes = fsNode.getSelectedFile();
				final ImportDialog fsEdge = new ImportDialog(ImportType.EDGES,
						nodes);
				if (fsEdge.showDialog(window, "Load edges") == JFileChooser.APPROVE_OPTION) {
					final File edges = fsEdge.getSelectedFile();
					try {
						window.getContent().loadGraph(nodes, edges);
					} catch (InvalidNodePlacementException e1) {
						e1.printStackTrace();
					}
					return;
				}
			}
			dialogCanceled();
		}
	},

	/** Loads a phylogenetic tree into the window content. */
	IMPORT_PHYLO {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final ImportDialog fcPhylo = new ImportDialog(ImportType.PHYLO);
			if (fcPhylo.showDialog(window, "Load Newick") == JFileChooser.APPROVE_OPTION) {
				final File newick = fcPhylo.getSelectedFile();
				window.getContent().loadTree(newick);
			} else {
				dialogCanceled();
			}
		}
	},

	/** Load gene annotation file. */
	IMPORT_GENE_ANN {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final ImportDialog fcGeneAnn = new ImportDialog(
					ImportType.GENE_ANNOTATION);
			if (fcGeneAnn.showDialog(window, "Load gene annotation") == JFileChooser.APPROVE_OPTION) {
				final File geneAnn = fcGeneAnn.getSelectedFile();
				window.getContent().getGraphPanel().getDgraph()
						.getReferenceGeneStorage().setGeneAnnotation(geneAnn);
			} else {
				dialogCanceled();
			}
		}
	},

	/** Load resistance causing mutations file. */
	IMPORT_RES_CAUS_MUT {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final ImportDialog fcKnownResMut = new ImportDialog(
					ImportType.KNOWN_RES_MUT);
			if (fcKnownResMut.showDialog(window, "Load gene annotation") == JFileChooser.APPROVE_OPTION) {
				final File knownResMut = fcKnownResMut.getSelectedFile();
				window.getContent().getGraphPanel().getDgraph()
						.getReferenceGeneStorage()
						.setDrugRestistantMutations(knownResMut);
				RESET_GRAPH.actionPerformed(null);
			} else {
				dialogCanceled();
			}
		}
	},

	/**  */
	IMPORT_INFECTED {
		@Override
		public void actionPerformed(final ActionEvent e) {
		}
	},

	/**
	 * Export the visual graph representation to DGS format.
	 */
	WRITE_TO_DGS {
		@Override
		public void actionPerformed(final ActionEvent e) {
			ExportDialog fs = new ExportDialog();
			if (fs.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
				String writePath = fs.getSelectedFile().getAbsolutePath();
				if (!writePath.endsWith(".dgs")) {
					writePath += ".dgs";
					statusBarInfo("Appended file extension .dgs");
				}
				window.getContent().writeGraph(writePath);
				statusBarInfo("Graph file written to " + writePath);
			} else {
				dialogCanceled();
			}
		}
	},

	/** Reset graph to original representation. **/
	RESET_GRAPH {
		@Override
		public void actionPerformed(final ActionEvent e) {
			window.getContent().getGraphPanel().applyZoomLevel(0);
		}
	},

	/** Show the help dialog. */
	HELP {
		@Override
		public void actionPerformed(final ActionEvent e) {
			window.getHelpDialog().setVisible(true);
		}
	},

	/**
	 * 
	 */
	NEXTZOOMLEVEL {
		@Override
		public void actionPerformed(final ActionEvent e) {
			window.getContent()
					.getGraphPanel()
					.applyZoomLevel(
							window.getContent().getGraphPanel().getZoomLevel() + 1);
		}
	},

	/**
	 * 
	 */
	PREVIOUSZOOMLEVEL {
		@Override
		public void actionPerformed(final ActionEvent e) {
			window.getContent()
					.getGraphPanel()
					.applyZoomLevel(
							window.getContent().getGraphPanel().getZoomLevel() - 1);
		}
	};

	/**
	 * The window effected by the actions performed by these events.
	 */
	private static Window window;

	/**
	 * @param w
	 *            The window that should be effected.
	 */
	public static void setWindow(final Window w) {
		window = w;
	}

	/**
	 * Called when the user closes a file chooser dialog box without specifying
	 * a file to load or to save.
	 */
	private static void dialogCanceled() {
		System.out
				.println("User closed file chooser dialog without choosing a file.");
	}

	/**
	 * Creates an action event to hold a command.
	 * 
	 * @param command
	 *            The command.
	 * @return the created action event.
	 */
	@SuppressWarnings("unused")
	private static ActionEvent mkCommand(final String command) {
		return new ActionEvent(null, ActionEvent.ACTION_FIRST, command);
	}

	/**
	 * Shows an informative message in the status bar.
	 * 
	 * @param message
	 *            The informative message to show.
	 * @see StatusBar#main(String,
	 *      nl.tudelft.ti2806.pl1.gui.StatusBar.MessageType)
	 */
	public static void statusBarInfo(final String message) {
		if (window != null) {
			window.getStatusBar().main(message, StatusBar.MessageType.INFO);
		}
	}

	/**
	 * Shows an error message in the status bar.
	 * 
	 * @param message
	 *            The error message to show.
	 * @see StatusBar#main(String,
	 *      nl.tudelft.ti2806.pl1.gui.StatusBar.MessageType)
	 */
	public static void statusBarError(final String message) {
		if (window != null) {
			window.getStatusBar().main(message, StatusBar.MessageType.ERROR);
		}
	}

	/**
	 * 
	 * Shows a message on the right side of the status bar.
	 * 
	 * @param message
	 *            The message to show.
	 * @see StatusBar#right(String)
	 */
	public static void statusBarRight(final String message) {
		if (window != null) {
			window.getStatusBar().right(message);
		}
	}

	/**
	 * Shows a message on the left side of the status bar if there is no message
	 * shown on the left, otherwise shows the message in the center of the
	 * status bar.
	 * 
	 * @param message
	 *            The message to show.
	 * @see StatusBar#mid(String)
	 */
	public static void statusBarMid(final String message) {
		if (window != null) {
			window.getStatusBar().mid(message);
		}
	}

}
