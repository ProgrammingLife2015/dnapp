package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author https://community.oracle.com/thread/1482163.
 *
 */
public class GenomeList extends JList<JCheckBox> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8122220717077360177L;

	/**
	 * 
	 */
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	/**
	 * 
	 */
	public GenomeList() {
		setCellRenderer(new CheckBoxCellRenderer());

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index != -1) {
					JCheckBox checkbox = getModel().getElementAt(index);
					checkbox.setSelected(!checkbox.isSelected());
					repaint();
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					int index = getSelectedIndex();
					if (index != -1) {
						JCheckBox checkbox = getModel().getElementAt(index);
						checkbox.setSelected(!checkbox.isSelected());
						repaint();
					}
				}
			}
		});

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * @author https://community.oracle.com/thread/1482163.
	 */
	protected class CheckBoxCellRenderer implements ListCellRenderer<JCheckBox> {

		/**
		 * {@inheritDoc}
		 */
		public final Component getListCellRendererComponent(
				final JList<? extends JCheckBox> list, final JCheckBox value,
				final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			value.setBackground(isSelected ? getSelectionBackground()
					: getBackground());
			value.setForeground(isSelected ? getSelectionForeground()
					: getForeground());

			value.setEnabled(isEnabled());
			value.setFont(getFont());
			value.setFocusPainted(false);

			value.setBorderPainted(true);
			value.setBorder(isSelected ? UIManager
					.getBorder("List.focusCellHighlightBorder") : noFocusBorder);

			return value;
		}

	}

	/**
	 * 
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		GenomeList cbList = new GenomeList();

		JCheckBox[] cbArray = new JCheckBox[3];
		cbArray[0] = new JCheckBox("one");
		cbArray[1] = new JCheckBox("two");
		cbArray[2] = new JCheckBox("three");

		cbList.setListData(cbArray);

		frame.getContentPane().add(cbList);
		frame.pack();
		frame.setVisible(true);
	}
}