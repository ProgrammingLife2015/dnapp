/*
 * DSTreeNode.java
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

/**
 * Specify the basic methods that a node in a Tree-Like data structure should
 * have.
 *
 * Assumption - The graph is a directed tree: - No two nodes share the same
 * child - There are no circles in the data structure
 * 
 * @author Barak Itkin
 * @since Version 1.0
 */
public interface DSTreeNode {

	/**
	 * Return the children of the given node inside an array. THE ORDER OF THE
	 * NODES MATTER!
	 *
	 * @return An ordered array containing the children of this node. THIS VALUE
	 *         MUST NOT BE NULL! RETURN AN ARRAY OF LENGTH 0 TO SPECIFY THAT
	 *         THERE ARE NO CHILDREN!
	 */
	public DSTreeNode[] dsGetChildren();

	/**
	 * Get the value of the node. The value will be treated as a string, using
	 * the object's toString() method. This is good for all the primitive types
	 * (strings, numbers, booleans and chars) and also for more complex objects
	 * which have their own toString() method. If the value returned is null, it
	 * will be converted to the string "null"
	 *
	 * @return The node's value
	 */
	public Object dsGetValue();

	/**
	 * Return the color for drawing this node (Useful for Red Black Trees).
	 * Return null for specifying the default color
	 *
	 * @return the color to be used for this node
	 */
	public java.awt.Color dsGetColor();
}
