package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter;
import nl.tudelft.ti2806.pl1.file.GraphNodeFileFilter;

/**
 * @author Maarten
 *
 */
public enum Event implements ActionListener {

	/**
	 * Instead of null, use NONE to indicate no event will be fired.
	 */
	NONE {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			System.out.println("Event NONE fired.");
		}
	},

	/**
	 * 
	 */
	SHOW_TOOLBAR {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			window.toolBar().setVisible(true);
		}

	},

	/**
	 * 
	 */
	HIDE_TOOLBAR {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			window.toolBar().setVisible(false);
		}

	},

	/**
	 * Exits the application.
	 */
	EXIT_APP {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			System.out.println("Bye bye!");
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
		public void actionPerformed(final ActionEvent e) {
			FileFilter extNode = new GraphNodeFileFilter();
			FileFilter extEdge = new GraphEdgeFileFilter();

			JFileChooser fs = new JFileChooser();
			fs.setDialogType(JFileChooser.OPEN_DIALOG);
			fs.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fs.setCurrentDirectory(null);
			// fs.setAcceptAllFileFilterUsed(false);

			fs.setDialogTitle("Please locate and open the NODE file.");
			fs.setFileFilter(extNode);
			if (fs.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
				final String nodePath = fs.getSelectedFile().getAbsolutePath();
				fs.setCurrentDirectory(new File(nodePath));
				fs.setFileFilter(extEdge);
				fs.setDialogTitle("Please locate and open the EDGE file.");
				if (fs.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
					final String edgePath = fs.getSelectedFile()
							.getAbsolutePath();
					// (new Thread(new Runnable() {
					// public void run() {
					window.content().loadGraph(nodePath, edgePath);
					// window.content()
					// .loadGraph(
					// "src/main/resources/simple_graph.node.graph",
					// "src/main/resources/simple_graph.edge.graph");
					// System.out.println("Graph loaded via event!");
					// }
					// })).start();
					window.revalidate();
				}
			}

		}
	},

	/**
	 * 
	 */
	WRITE_FILE {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			JFileChooser fs = new JFileChooser();
			fs.setDialogTitle("Choose a folder to write the graph file to");
			fs.setDialogType(JFileChooser.SAVE_DIALOG);
			fs.setFileSelectionMode(JFileChooser.FILES_ONLY); // default
			fs.setCurrentDirectory(new java.io.File("."));
			fs.setCurrentDirectory(null);
			fs.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter extensions = new FileNameExtensionFilter(
					"DGS graph format", "dgs");
			fs.setFileFilter(extensions);
			if (fs.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
				String writePath = fs.getSelectedFile().getAbsolutePath();
				if (!writePath.endsWith(".dgs")) {
					writePath += ".dgs";
					System.out.println("Changed file format to dgs");
				}
				statusBarInfo("Writing graph to file "
						+ fs.getSelectedFile().getName() + " in "
						+ fs.getCurrentDirectory());

				System.out.println("dir=" + fs.getCurrentDirectory());
				System.out.println("file=" + fs.getSelectedFile());

				window.content().writeGraph(writePath);

				System.out.println("Graph printed to " + writePath);
				statusBarInfo("Graph file written to " + writePath);
			} else {
				statusBarError("No folder selected!");
				System.out.println("No Selection ");
			}
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
	 * Creates an action event to hold a command.
	 * 
	 * @param command
	 *            The command.
	 * @return the created action event.
	 */
	public static ActionEvent mkCommand(final String command) {
		return new ActionEvent(null, ActionEvent.ACTION_FIRST, command);
	}

	/**
	 * @param message
	 *            The informative message to show.
	 */
	public static void statusBarInfo(final String message) {
		window.statusBar().main(message, StatusBar.MessageType.INFO);
	}

	/**
	 * @param message
	 *            The error message to show.
	 */
	public static void statusBarError(final String message) {
		window.statusBar().main(message, StatusBar.MessageType.ERROR);
	}

	/**
	 * @param message
	 *            The message to show.
	 */
	public static void statusBarRight(final String message) {
		window.statusBar().right(message);
	}

	/**
	 * @param message
	 *            The message to show.
	 */
	public static void statusBarMid(final String message) {
		window.statusBar().mid(message);
	}

}
