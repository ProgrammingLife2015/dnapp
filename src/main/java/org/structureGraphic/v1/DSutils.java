/*
 * DSutils.java
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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Barak Itkin
 */
public class DSutils {

	public static BufferedImage fromDS(Object val, int rectWidth, int rectHeight) {
		int oldW = DSTreeCopy.DSVTreeNode.HEIGHT;
		int oldH = DSTreeCopy.DSVTreeNode.WIDTH;

		DSTreeCopy.DSVTreeNode.WIDTH = rectWidth;
		DSTreeCopy.DSVTreeNode.HEIGHT = rectHeight;

		DSPaintable paintMe = DSTreeCopy.createVertical(DSTreeParser.from(val));
		BufferedImage im = new BufferedImage(paintMe.DSgetWidth() + 1,
				paintMe.DSgetHeight() + 1, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = (Graphics2D) im.getGraphics();
		g2.setColor(Color.black);
		g2.setFont(Font.getFont(Font.MONOSPACED));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		paintMe.DSpaint(g2);

		DSTreeCopy.DSVTreeNode.WIDTH = oldW;
		DSTreeCopy.DSVTreeNode.HEIGHT = oldH;

		return im;
	}

	public static void show(final Object val, final int rectWidth,
			final int rectHeight) {
		new DSImageView(DSutils.fromDS(val, rectWidth, rectHeight))
				.setVisible(true);
	}

}
