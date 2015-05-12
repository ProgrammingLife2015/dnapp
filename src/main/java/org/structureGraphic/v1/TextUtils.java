/*
 * TextUtils.java
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

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Barak Itkin
 */
public class TextUtils {

	public static final int fontSize = 12;
	public static final String fontName = Font.SANS_SERIF;
	public static final int fontStyle = Font.PLAIN;
	public static final boolean antialias = true;
	public static final boolean fractionalMetrics = false;

	public static final Font font = new Font(fontName, fontStyle, fontSize);
	public static final FontRenderContext fc = new FontRenderContext(null,
			antialias, fractionalMetrics);

	public static Rectangle2D measureText(String text) {
		double width = 0, height = 0;
		double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
		for (String line : text.split("\n")) {
			TextLayout layout = new TextLayout(line, font, fc);
			Rectangle2D rect = layout.getBounds();
			width = Math.max(width, rect.getWidth());
			height += rect.getHeight();
			minX = Math.min(minX, rect.getX());
			minY = Math.min(minX, rect.getY());
		}
		return new Rectangle2D.Double(minX, minY, width, height);
	}

	public static void drawCenteredText(Graphics2D g2, String text, float cx,
			float cy) {
		Rectangle2D r = measureText(text);
		float xOff = (float) (cx - r.getWidth() / 2.0 - r.getX());
		float YOff = (float) (cy - r.getHeight() / 2.0 - r.getY());
		for (String line : text.split("\n")) {
			TextLayout layout = new TextLayout(line, font, fc);
			layout.draw(g2, xOff + 0, YOff);
			YOff += layout.getBounds().getHeight();
		}
	}

}
