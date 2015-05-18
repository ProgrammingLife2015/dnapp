package nl.tudelft.ti2806.pl1.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Maarten
 * @since 18-5-2015
 * @version 1.0
 */
public class GraphEdgeFileFilter extends FileFilter {

	@Override
	public final boolean accept(final File f) {
		return f.isDirectory() || f.getName().endsWith(".edge.graph");
	}

	@Override
	public final String getDescription() {
		return "Graph edges file";
	}

}