package gui;

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
		@Override
		void action() {
			System.out.println("Event NONE fired.");
		}
	},

	/**
	 * 
	 */
	EXAMPLE_EVENT {
		@Override
		void action() {
			System.out.println("Example event fired.");
		}
	},

	/**
	 * 
	 */
	ANOTHER_EVENT {
		@Override
		void action() {
			System.out.println("Another event fired.");
		}
	},

	/**
	 * 
	 */
	PRINT_APP_NAME {
		@Override
		void action() {
			System.out.println(window.getTitle());
		}
	},

	/**
	 * 
	 */
	EXIT_APP {
		@Override
		void action() {
			System.exit(0);
		}

	};

	/**
	 * 
	 */
	private static Window window;

	/**
	 * 
	 * @param w
	 */
	public static void setWindow(final Window w) {
		window = w;
	}

	/**
	 * 
	 */
	abstract void action();

	/**
	 * {@inheritDoc}
	 */
	public void actionPerformed(final ActionEvent e) {
		valueOf(e.getActionCommand()).action();
	}

	/**
	 * 
	 * @param action
	 * @return the name of the enum constant <code>action</code>, or null
	 */
	public static String stringify(final Event action) {
		if (action == null) {
			return null;
		} else {
			return action.toString();
		}
	}

}
