/*
 * SimpleNode.java
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
import java.util.LinkedList;
import java.util.List;

public class SimpleNode {

	@DSValue
	public String value;
	@DSChildren(DSChildren.DSChildField.ITERABLE)
	public List<SimpleNode> children;
	@DSColor
	public Color color;

	public SimpleNode(String val, Color c) {
		this.value = val;
		this.children = new LinkedList<SimpleNode>();
		this.color = c;
	}

	public SimpleNode(String val) {
		this(val, null);
	}

	public void add(SimpleNode... children) {
		for (SimpleNode child : children) {
			this.children.add(child);
		}
	}

}
