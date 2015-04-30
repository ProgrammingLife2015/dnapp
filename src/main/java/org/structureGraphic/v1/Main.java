package org.structureGraphic.v1;

/*
 * Main.java
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
public class Main {

	public static void main(String[] args) {
		String m = "LibStructureGraphic\n" + " Version 1.0\n"
				+ "  Released on:\n" + "   January 1st, 2011\n"
				+ "  Written by:\n" + "   Barak Itkin\n"
				+ "    lightningismyname\n" + "    @gmail.com\n"
				+ " First public release!";

		DSutils.show(DSTreeParser.parseSimpleMultiline(m), 130, 25);
	}
}
