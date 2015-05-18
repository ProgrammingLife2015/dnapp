package nl.tudelft.ti2806.pl1.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Maarten
 * @since 18-5-2015
 * @version 1.0
 *
 */
public class GraphNodeFileFilter extends FileFilter {

	@Override
	public final boolean accept(final File f) {
		return f.isDirectory() || f.getName().endsWith(".node.graph");
	}

	@Override
	public final String getDescription() {
		return "Graph nodes file";
	}

}