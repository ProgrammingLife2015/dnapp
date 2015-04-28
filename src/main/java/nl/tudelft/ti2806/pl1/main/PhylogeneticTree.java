package nl.tudelft.ti2806.pl1.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;

public class PhylogeneticTree {

	public static void main(final String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		File f = new File("src/main/resources/nj_tree_10_strains.nwk");
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			TreeParser tp = new TreeParser(r);
			Tree t = tp.tokenize(f.length(), f.getName(), null);
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find file: "
					+ "src/main/resources/nj_tree_10_strains.nwk");
		}
		System.out.println("Parsed in "
				+ ((System.currentTimeMillis() - start) / 1000.0) + " s");
		System.exit(0);
	}
}
