/*
 * DSTreeParser.java
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
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Barak Itkin
 */
public class DSTreeParser {

	public static final class DSTreeStack {

		protected LinkedList<LinkedList<DSSimpleNode>> tree;

		public DSTreeStack() {
			this.tree = new LinkedList<LinkedList<DSSimpleNode>>();
			startLevel();
		}

		public void startLevel() {
			tree.add(new LinkedList<DSSimpleNode>());
		}

		public void finishLevel() {
			LinkedList<DSSimpleNode> children = this.tree.removeLast();
			DSSimpleNode parent = this.tree.getLast().getLast();
			// if (parent.children == null)
			// parent.children = new LinkedList<DSTreeNode>();
			parent.children.addAll(children);
		}

		public void addNode(final Object val) {
			this.tree.getLast().add(
					new DSSimpleNode(val == null ? "null" : val.toString()));
		}

		public DSTreeNode getTree() {
			while (this.tree.size() > 1)
				finishLevel();
			if (this.tree.getFirst().size() > 1) {
				DSSimpleNode root = new DSSimpleNode("");
				// if (root.children == null)
				// root.children = new LinkedList<DSTreeNode>();
				root.children.addAll(tree.getFirst());
				return root;
			} else if (this.tree.getFirst().size() == 1)
				return this.tree.getFirst().getFirst();
			else
				return new DSSimpleNode("Empty...");
		}

	}

	public static class DSSimpleNode implements DSTreeNode {

		@DSValue
		public String value;
		@DSChildren(DSChildren.DSChildField.ITERABLE)
		public List<DSTreeNode> children;
		@DSColor
		public Color color;

		public DSSimpleNode(final String val, final Color c) {
			this.value = val;
			this.children = new LinkedList<DSTreeNode>();
			this.color = c;
		}

		public DSSimpleNode(final String val) {
			this(val, null);
		}

		public void add(final DSTreeNode... children) {
			for (DSTreeNode child : children) {
				this.children.add(child);
			}
		}

		public DSTreeNode[] dsGetChildren() {
			DSTreeNode[] c = new DSTreeNode[this.children.size()];
			return this.children.toArray(c);
		}

		public Object dsGetValue() {
			return this.value;
		}

		public Color dsGetColor() {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	public static DSTreeNode from(final Object o) {
		if (o instanceof DSTreeNode)
			return (DSTreeNode) o;
		else {
			StringBuffer val = new StringBuffer();
			Color color = null;
			LinkedList<DSTreeNode> children = new LinkedList<DSTreeNode>();

			for (Field field : o.getClass().getFields()) {
				try {
					if (field.getAnnotation(DSValue.class) != null)
						val.append(field.getName()).append(": ")
								.append(field.get(o)).append("\n");
					else if (field.getAnnotation(DSChildren.class) != null) {
						if (field.getAnnotation(DSChildren.class).value() == DSChildren.DSChildField.ARRAY)
							for (Object child : (Object[]) field.get(o)) {
								children.add(from(child));
							}
						else if (field.getAnnotation(DSChildren.class).value() == DSChildren.DSChildField.ITERABLE)
							for (Object child : (Iterable) field.get(o)) {
								children.add(from(child));
							}
						else
							children.add(from(field.get(o)));
					} else if (field.getAnnotation(DSColor.class) != null)
						color = (Color) field.get(o);

				} catch (IllegalArgumentException ex) {
					System.err.println(ex.getMessage());
				} catch (IllegalAccessException ex) {
					System.err.println("Could not access " + field.getName()
							+ "in " + field.getClass().getName());
				} catch (ClassCastException ex) {
					System.err.println("The annotation for " + field.getName()
							+ "in " + field.getClass().getName()
							+ " does not match it's type!");
				}
			}

			DSSimpleNode s = new DSSimpleNode(val.toString());
			s.children.addAll(children);
			s.color = color;
			return s;
		}
	}

	public static DSTreeNode parseSimpleMultiline(final String text) {
		return parseSimpleMultiline(new Scanner(text));
	}

	public static DSTreeNode parseSimpleMultiline(final File file)
			throws FileNotFoundException {
		return parseSimpleMultiline(new Scanner(file));
	}

	public static DSTreeNode parseSimpleMultiline(final Scanner sc) {
		int spaceCount;
		LinkedList<Integer> depths = new LinkedList<Integer>();
		DSTreeStack st = new DSTreeStack();

		if (!sc.hasNextLine())
			return new DSSimpleNode("Empty");

		while (sc.hasNextLine()) {
			String temp = sc.nextLine(), line = temp.trim();
			spaceCount = temp.length() - line.length();
			if (depths.isEmpty())
				depths.addLast(spaceCount);
			else
				while (depths.getLast() > spaceCount) {
					st.finishLevel();
					depths.removeLast();
				}
			if (depths.getLast() < spaceCount) {
				st.startLevel();
				depths.addLast(spaceCount);
			}
			st.addNode(line);
		}

		return st.getTree();
	}
}
