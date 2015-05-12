/*
 * TreePrinter.java
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

import org.structureGraphic.v1.DSTreeCopy.DSSimpleGraphicalNode;

/**
 *
 * @author user
 */
public class TreePrinter {

	private static void print(final DSTreeNode n, String indent) {
		System.out.println(indent + "val: " + n.dsGetValue());
		if (n instanceof DSTreeCopy.DSSimpleGraphicalNode) {
			DSTreeCopy.DSSimpleGraphicalNode n2 = (DSSimpleGraphicalNode) n;
			System.out.println(indent + "width: " + n2.DSgetWidth());
			System.out.println(indent + "height: " + n2.DSgetHeight());
		}

		indent = indent + "-";
		for (DSTreeNode child : n.dsGetChildren()) {
			print(child, indent);
		}
	}

	public static void print(final DSTreeNode n) {
		print(n, "");
	}

}
