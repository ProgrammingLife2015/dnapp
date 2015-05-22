/*
 GBC - A convenience class to tame the GridBagLayout

 Copyright (C) 2002 Cay S. Horstmann (http://horstmann.com)

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.corejava;

import java.awt.GridBagConstraints;

/**
 * This class simplifies the use of the GridBagConstraints class.
 */
public class GBC extends GridBagConstraints {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2251643988521430752L;

	/**
	 * Constructs a GBC with a given gridx and gridy position and all other grid
	 * bag constraint values set to the default.
	 * 
	 * @param gridx
	 *            the gridx position
	 * @param gridy
	 *            the gridy position
	 */
	public GBC(final int gridx, final int gridy) {
		this.gridx = gridx;
		this.gridy = gridy;
	}

	/**
	 * Sets the cell spans.
	 * 
	 * @param gridwidth
	 *            the cell span in x-direction
	 * @param gridheight
	 *            the cell span in y-direction
	 * @return this object for further modification
	 */
	public final GBC setSpan(final int gridwidth, final int gridheight) {
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
		return this;
	}

	/**
	 * Sets the anchor.
	 * 
	 * @param anchor
	 *            the anchor value
	 * @return this object for further modification
	 */
	public final GBC setAnchor(final int anchor) {
		this.anchor = anchor;
		return this;
	}

	/**
	 * Sets the fill direction.
	 * 
	 * @param fill
	 *            the fill direction
	 * @return this object for further modification
	 */
	public final GBC setFill(final int fill) {
		this.fill = fill;
		return this;
	}

	/**
	 * Sets the cell weights.
	 * 
	 * @param weightx
	 *            the cell weight in x-direction
	 * @param weighty
	 *            the cell weight in y-direction
	 * @return this object for further modification
	 */
	public final GBC setWeight(final double weightx, final double weighty) {
		this.weightx = weightx;
		this.weighty = weighty;
		return this;
	}

	/**
	 * Sets the insets of this cell.
	 * 
	 * @param distance
	 *            the spacing to use in all directions
	 * @return this object for further modification
	 */
	public final GBC setInsets(final int distance) {
		this.insets = new java.awt.Insets(distance, distance, distance,
				distance);
		return this;
	}

	/**
	 * Sets the insets of this cell.
	 * 
	 * @param top
	 *            the spacing to use on top
	 * @param left
	 *            the spacing to use to the left
	 * @param bottom
	 *            the spacing to use on the bottom
	 * @param right
	 *            the spacing to use to the right
	 * @return this object for further modification
	 */
	public final GBC setInsets(final int top, final int left, final int bottom,
			final int right) {
		this.insets = new java.awt.Insets(top, left, bottom, right);
		return this;
	}

	/**
	 * Sets the internal padding.
	 * 
	 * @param ipadx
	 *            the internal padding in x-direction
	 * @param ipady
	 *            the internal padding in y-direction
	 * @return this object for further modification
	 */
	public final GBC setIpad(final int ipadx, final int ipady) {
		this.ipadx = ipadx;
		this.ipady = ipady;
		return this;
	}
}
