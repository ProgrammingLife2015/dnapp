package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	 * Loads a graph into the window content.
	 */
	LOAD_FILE {
		/**
		 * {@inheritDoc}
		 */
		public void actionPerformed(final ActionEvent e) {
			window.content().loadGraph(
					"src/main/resources/simple_graph.node.graph",
					"src/main/resources/simple_graph.edge.graph");
			System.out.println("Graph loaded via event!");
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
