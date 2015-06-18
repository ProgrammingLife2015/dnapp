/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.gui.NucleoBase;
import nl.tudelft.ti2806.pl1.mutation.ResistanceMutation;

/**
 * @author Maarten
 * @since 8-6-2015
 *
 */
public class NodeContentBox extends JPanel implements NodeSelectionObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = 3092702168114777481L;

	/** The font size of the node content text pane. */
	private static final int FONT_SIZE = 12;

	/** The background color of nucleotides involved in resistance mutations. */
	private static final Color RES_NUCLEO_BG_COLOR = new Color(150, 255, 150);

	/** The text area document. */
	private StyledDocument doc = new DefaultStyledDocument();

	/** The text pane where the nucleotide contents is shown. */
	private JTextPane text = new JTextPane(doc);

	/** The scroll pane containing the text pane. */
	private JScrollPane scroll = new JScrollPane(text);

	/**
	 * The label showing information about the user's selection within the text
	 * pane.
	 */
	private JLabel selInfo = new JLabel();

	/**
	 * Initializes the node content box.
	 */
	public NodeContentBox() {
		setLayout(new BorderLayout());
		text.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
		text.setEditable(false);
		text.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(final CaretEvent e) {
				int left = Math.min(e.getDot(), e.getMark());
				int right = Math.max(e.getDot(), e.getMark());
				if (right - left == 1) {
					selInfo.setText(" Selected nucleotide: " + right);
				} else if (left != right) {
					selInfo.setText(" Selected nucleotides: " + (left + 1)
							+ " to " + right);
				} else {
					selInfo.setText(" ");
				}
			}
		});
		add(selInfo, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * {@inheritDoc} Changes to the graph graphics based on the selected node.
	 */
	@Override
	public final void update(final HashSet<DNode> newSelectedNodes) {
		SimpleAttributeSet set = new SimpleAttributeSet();
		if (newSelectedNodes.size() == 1) {
			DNode dn = newSelectedNodes.iterator().next();
			String content = dn.getContent();
			text.setText(content);
			for (int i = 0; i < text.getDocument().getLength(); i++) {
				Color nucleoColor = Color.BLACK;
				try {
					nucleoColor = NucleoBase.valueOf(doc.getText(i, 1))
							.getColor().darker().darker();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				StyleConstants.setForeground(set, nucleoColor);
				doc.setCharacterAttributes(i, 1, set, true);
			}
			System.out.println("\t start=" + dn.getStart() + "\t end="
					+ dn.getEnd());
			if (dn.hasResMuts()) {
				set = new SimpleAttributeSet();
				for (ResistanceMutation rm : dn.getResMuts()) {
					StyleConstants.setBackground(set, RES_NUCLEO_BG_COLOR);
					int loc = (int) rm.getRefIndex() - dn.getStart();
					System.out.println("loc=" + loc + "\t start="
							+ dn.getStart() + "\t refIndex=" + rm.getRefIndex()
							+ "\t end=" + dn.getEnd());
					doc.setCharacterAttributes(loc, 1, set, false);
				}
			}
		} else {
			text.setText("This is a collapsed section consisting containing "
					+ newSelectedNodes.size()
					+ " nodes.\nPlease zoom in to gain more information about this section.");
			StyleConstants.setForeground(set, Color.BLACK);
			doc.setCharacterAttributes(0, text.getText().length(), set, true);
		}
		text.setCaretPosition(0);
	}
}
