/*
 * DSPaintable.java
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

import java.awt.Graphics2D;

/**
 * A base interface defining the functions which an object must be supply in
 * order to be drawn.
 *
 * @author Barak Itkin
 * @since Version 1.0
 */
public interface DSPaintable {

	/**
	 * Given a graphics object, the node should draw itself. The graphics object
	 * will be translated so that the left top corner of the object will be
	 * placed at (0,0) (The X axis will go right, the Y axis will go down).
	 *
	 * THE CALL TO THIS METHOD MUST RETURN THE GRAPHICS OBJECT WITH THE SAME
	 * TRANSFORMATION IT RECIEVED IT. IF IT DOES ANY TRANSFORMATIONS ON THE
	 * GRAPHICS OBJECT, IT MUST RESTORE THEM BEFORE RETURNING!!! THE SAME GOES
	 * FOR CLIPPING, COLORS AND EVERYTHING ELSE!!!
	 *
	 * @param g
	 *            The supplied graphics object
	 */
	public void DSpaint(Graphics2D g);

	/**
	 * Return the width that this node wants for drawing itself. This call must
	 * return the same value each time!
	 *
	 * @return The width for drawing this node
	 */
	public int DSgetWidth();

	/**
	 * Return the height that this node wants for drawing itself. This call must
	 * return the same value each time!
	 *
	 * @return The height for this node
	 */
	public int DSgetHeight();

}
