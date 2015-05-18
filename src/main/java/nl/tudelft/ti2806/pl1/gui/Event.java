package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	EXAMPLE_EVENT {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			System.out.println("Example event fired. Actioncommand = \""
					+ e.getActionCommand() + "\"");
			window.optionPanel().fillGenomeList(new ArrayList<String>(), true,
					true);
		}
	},

	/**
	 * 
	 */
	ANOTHER_EVENT {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			System.out.println("Another event fired.");
		}
	},

	/**
	 * 
	 */
	PRINT_APP_NAME {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			System.out.println(window.getTitle());
		}
	},

	/**
	 * 
	 */
	SHOW_WINDOW {
		/**
		 * 
		 */
		public void actionPerformed(final ActionEvent e) {
			window.setVisible(true);
		}

	},

	/**
	 * 
	 */
	HIDE_WINDOW {

		/**
		 * 
		 */
		public void actionPerformed(final ActionEvent e) {
			window.setVisible(false);

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
	 * 
	 */
	GENOME_SELECT {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			System.out.println("The user clicked the checkbox for genome: "
					+ e.getActionCommand());
		}
	},

	/**
	 * Loads a graph into the window content.
	 */
	LOAD_FILE {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			// (new Thread(new Runnable() {
			// public void run() {
			window.optionPanel().enableBtnLoadGraph(false);
			window.toolBar().enableBtnLoadGraph(false);
			window.content().loadGraph(
					"src/main/resources/simple_graph.node.graph",
					"src/main/resources/simple_graph.edge.graph");
			System.out.println("Graph loaded via event!");
			window.revalidate();
			// }
			// })).start();
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
				window.statusBar().info(
						"Writing graph to file "
								+ fs.getSelectedFile().getName() + " in "
								+ fs.getCurrentDirectory());

				System.out.println("dir=" + fs.getCurrentDirectory());
				System.out.println("file=" + fs.getSelectedFile());

				window.content().writeGraph(writePath);

				System.out.println("Graph printed to " + writePath);
				window.statusBar().info("Graph file written to " + writePath);
			} else {
				window.statusBar().error("No folder selected!");
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

}
