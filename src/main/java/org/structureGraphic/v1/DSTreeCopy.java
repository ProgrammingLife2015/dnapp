/*
 * DSTreeCopy.java
 * Written by Barak Itkin <lightningismyname@gmail.com>, 2010
 *
 * This file is part of StructureGraphic.
 *
 * StructureGraphic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * StructureGraphic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with StructureGraphic. If not, see <http://www.gnu.org/licenses/>.
 */
package org.structureGraphic.v1;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author user
 */
public class DSTreeCopy {

	public static final Color DEFAULT_COLOR = Color.BLACK;

	/*
	 * 
	 * public static class DepthStack<T extends DSSimpleGraphicalNode> { public
	 * final ArrayList<LinkedList<T>> depthMap; public int initDepth; public int
	 * filledDepth;
	 * 
	 * public DepthStack() { this.depthMap = new ArrayList<LinkedList<T>>();
	 * this.depthMap.add(new LinkedList<T>()); this.initDepth = 0;
	 * this.filledDepth = 0; }
	 * 
	 * public void pushNode(T node) { this.depthMap.get(filledDepth).add(node);
	 * }
	 * 
	 * public void pushChild(T node) { if (this.initDepth < ++this.filledDepth)
	 * this.depthMap.add(new LinkedList<T>());
	 * this.depthMap.get(filledDepth).add(node); }
	 * 
	 * public void popNode() { T[] children =
	 * (T[])(this.depthMap.get(filledDepth).toArray());
	 * this.depthMap.get(filledDepth--).clear();
	 * this.depthMap.get(filledDepth).getLast().children = (DSVTreeNode[])
	 * children; } }
	 */

	public static abstract class DSSimpleGraphicalNode implements
			DSGraphicalTreeNode {
		public String value;
		public Color color;
		public DSVTreeNode[] children;
		/**
		 * Ths display size of this node
		 */
		public int width, height;

		/**
		 * The children will not be initialized by this constructor - this is in
		 * order to avoid recursion. The responsibility for doing this belongs
		 * to someone else.
		 * 
		 * @param n
		 */
		public DSSimpleGraphicalNode(final DSTreeNode n, final int width,
				final int height) {
			this.value = n.dsGetValue() == null ? "null" : n.dsGetValue()
					.toString();
			this.color = n.dsGetColor() == null ? DEFAULT_COLOR : n
					.dsGetColor();
			// Children are not initialized
			this.width = width;
			this.height = height;
		}

		public DSTreeNode[] dsGetChildren() {
			return children;
		}

		public Color dsGetColor() {
			return color;
		}

		public String dsGetValue() {
			return value;
		}

		public int DSgetWidth() {
			return width;
		}

		public int DSgetHeight() {
			return height;
		}

	}

	public static class DSVTreeNode extends DSSimpleGraphicalNode {
		public static int HSPACE = 4, VSPACE = 15;
		public static int WIDTH = 60, HEIGHT = 25;

		public int[] centerXOffsets;

		public DSVTreeNode(final DSTreeNode n, final int width, final int height) {
			super(n, width, height);
		}

		/**
		 * +-+ |a| +-+
		 *
		 * +-------+ +-------+ +-------+ |child_0| |child_0| |child_2| +-------+
		 * +-------+ +-------+
		 *
		 * @param g
		 */
		public void DSpaint(final Graphics2D g) {
			int xOff = (this.width - DSVTreeNode.WIDTH) / 2, temp;
			Color originalColor = g.getColor();
			g.setColor(this.color != null ? this.color : Color.BLACK);

			g.translate(xOff, 0);
			{
				g.drawRect(0, 0, DSVTreeNode.WIDTH, DSVTreeNode.HEIGHT);
				if (this.value.length() > 0) {
					TextUtils.drawCenteredText(g, this.value,
							DSVTreeNode.WIDTH / 2, DSVTreeNode.HEIGHT / 2);
				}
			}
			g.translate(-xOff, 0);

			g.setColor(originalColor);

			if (this.children.length != 0) {
				g.translate(0, DSVTreeNode.HEIGHT + DSVTreeNode.VSPACE);
				{
					xOff = 0;

					for (DSVTreeNode child : children) {
						child.DSpaint(g);
						g.translate(temp = (child.width + DSVTreeNode.HSPACE),
								0);
						xOff += temp;
					}

					g.translate(-xOff, 0);

					for (int i = 0; i < children.length; i++) {
						g.drawLine(this.width / 2, -DSVTreeNode.VSPACE,
								this.centerXOffsets[i], 0);

					}

				}
				g.translate(0, -(DSVTreeNode.HEIGHT + DSVTreeNode.VSPACE));
			}
		}
	}

	public static DSVTreeNode createVertical(DSTreeNode r) {
		{
			if (r == null)
				r = new DSTreeParser.DSSimpleNode("null");
			// DSVTreeNode root = new DSVTreeNode(r);
			int width = -DSVTreeNode.HSPACE, height = 0;
			DSTreeNode[] children = r.dsGetChildren();
			DSVTreeNode[] rootChildren = new DSVTreeNode[children.length];
			int[] centerXOffsets = new int[children.length];
			for (int i = 0; i < rootChildren.length; i++) {
				rootChildren[i] = createVertical(children[i]);
				width += rootChildren[i].DSgetWidth() + DSVTreeNode.HSPACE;
				centerXOffsets[i] = width - rootChildren[i].DSgetWidth() / 2;
				height = Math.max(height, rootChildren[i].DSgetHeight());
			}
			height += DSVTreeNode.HEIGHT;
			if (children.length != 0)
				height += DSVTreeNode.VSPACE;

			width = Math.max(width, DSVTreeNode.WIDTH);

			DSVTreeNode root = new DSVTreeNode(r, width, height);
			root.children = rootChildren;
			root.centerXOffsets = centerXOffsets;
			return root;
		}
	}
}