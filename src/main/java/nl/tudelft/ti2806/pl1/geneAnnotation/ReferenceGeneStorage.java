package nl.tudelft.ti2806.pl1.geneAnnotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeSet;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.gui.AppEvent;
import nl.tudelft.ti2806.pl1.mutation.ResistanceMutation;

/**
 * Storage location of all the genes of the TKK_REF with their information
 * included.
 * 
 * @author Chak Shun, Maarten
 * @since 2-6-2015
 */
public class ReferenceGeneStorage {

	/** Index for the start coordinate in the file. */
	private static final int INDEX_START = 3;
	/** Index for the end coordinate in the file. */
	private static final int INDEX_END = 4;
	/** Index for the score in the file. */
	private static final int INDEX_SCORE = 5;
	/** Index for the strand input in the file. */
	private static final int INDEX_STRAND = 6;
	/** Index for the attributes in the file. */
	private static final int INDEX_ATTRIBUTES = 8;
	/** Index for the index of the mutation in the file. */
	private static final int MUTATION_INDEX = 3;
	/** Index for the name of the mutation in the file. */
	private static final int MUTATION_NAME = 0;

	/** The data graph this storage belongs to. */
	private DGraph dgraph;

	/** All the genes and information extracted from the information file. */
	private TreeSet<ReferenceGene> referenceGenes;

	/** All the known drug resistance mutations. */
	private Map<Long, ResistanceMutation> drugResMuts;

	/** The list of meta data load observers. */
	private List<ResistanceMutationObserver> observers = new ArrayList<ResistanceMutationObserver>();

	/** The list of reference gene data storage observers. */
	private List<ReferenceGeneObserver> rgobservers = new ArrayList<ReferenceGeneObserver>();

	/**
	 * Registers a new meta data load observer.
	 * 
	 * @param mdlo
	 *            The new meta data load observer.
	 */
	public void registerObserver(final ResistanceMutationObserver mdlo) {
		observers.add(mdlo);
	}

	/**
	 * Registers a reference genes data storage observer.
	 * 
	 * @param rgo
	 *            The reference gene data storage observer.
	 */
	public void registerObserver(final ReferenceGeneObserver rgo) {
		rgobservers.add(rgo);
	}

	/**
	 * Initialize a new empty reference gene storage.
	 * 
	 * @param dg
	 *            The data graph belonging to this meta data storage.
	 */
	public ReferenceGeneStorage(final DGraph dg) {
		this.dgraph = dg;
	}

	/**
	 * Extracts all the mutation information given file.
	 * 
	 * @param file
	 *            File containing the drug resistance mutations.
	 * @return Returns a map containing the indices on the reference genome as
	 *         key and de ResistanceMutation as value.
	 */
	private Map<Long, ResistanceMutation> extractMutations(final File file) {
		Map<Long, ResistanceMutation> ret = new HashMap<Long, ResistanceMutation>();
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(file), "UTF-8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (!line.contains("##")) {
					String[] columns = line.split("\\t");
					String[] linesplit = columns[0].split(":");
					String[] info = linesplit[1].split(",");
					Long genomePos = Long.parseLong(info[MUTATION_INDEX]);
					ret.put(genomePos, new ResistanceMutation(genomePos, String.valueOf(info[MUTATION_NAME])));
				}
			}
			sc.close();
			findDNodes(ret);
		} catch (FileNotFoundException e) {
			AppEvent.statusBarError(file.getAbsolutePath()
					+ " for known resistant mutations could not be found!");
		}
		return ret;
	}

	/**
	 * Adds the corresponding data node to the resistance mutations which do not
	 * yet know to which data node they belong. Also saves the resistance
	 * mutation in the corresponding data node.
	 * 
	 * @param map
	 *            The map containing the indices of the resistance mutations and
	 *            the corresponding resistance mutation.
	 */
	private void findDNodes(final Map<Long, ResistanceMutation> map) {
		if (dgraph != null) {
			Collection<ResistanceMutation> resMuts = map.values();
			for (DNode dn : dgraph.getNodes().values()) {
				for (ResistanceMutation mut : resMuts) {
					if (dn.getStart() <= mut.getRefIndex() && mut.getRefIndex() < dn.getEnd()) {
						mut.setDnode(dn);
						dn.addResistantMutationIndex(mut);
					}
				}
			}
		}
	}

	/**
	 * Extracts all the references genes from the decorationV5_20130412.gff
	 * file.
	 * 
	 * @param file
	 *            File from which gene information to be extracted.
	 * @return HashSet containing all the genes for the reference genome.
	 */
	private TreeSet<ReferenceGene> extractReferenceGenes(final File file) {
		TreeSet<ReferenceGene> ret = new TreeSet<ReferenceGene>(new RefGeneCompare());
		Scanner sc;
		try {
			sc = new Scanner(new FileInputStream(file), "UTF-8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.contains("CDS")) {
					String[] info = line.split("\\t");
					String name = info[INDEX_ATTRIBUTES].split(";")[1].replace("Name=", "");
					ret.add(new ReferenceGene(Integer.parseInt(info[INDEX_START]), Integer
							.parseInt(info[INDEX_END]), Double.parseDouble(info[INDEX_SCORE]),
							info[INDEX_STRAND], name));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			AppEvent.statusBarError(file.getAbsolutePath() + " for gene annotation could not be found!");
		}
		return ret;
	}

	/**
	 * @return the name genes.
	 */
	public TreeSet<ReferenceGene> getReferenceGenes() {
		return referenceGenes;
	}

	/**
	 * Checks for the given position whether it falls into a intragenic region.
	 * 
	 * @param index
	 *            Index of the position to be checked.
	 * @return Whether the index is in a intragenic region.
	 */
	public boolean isIntragenic(final int index) {
		if (getReferenceGenes() != null) {
			for (ReferenceGene rg : getReferenceGenes()) {
				if (rg.isIntragenic(index)) {
					return true;
				}
			}
		}
		return false;
	}

	/** Connect the mutations with each referenceGene object. */
	private void connectGeneMutations() {
		if (getReferenceGenes() != null && getDrugResistanceMutations() != null) {
			for (ReferenceGene rg : this.getReferenceGenes()) {
				for (Entry<Long, ResistanceMutation> e : drugResMuts.entrySet()) {
					if (rg.isIntragenic(e.getKey())) {
						rg.addMutation(e.getKey(), e.getValue());
					}
				}
			}
		}
	}

	/**
	 * Checks for the given position whether it is a mutation resulting in a
	 * change of gene.
	 * 
	 * @param index
	 *            Index of the position to be checked.
	 * @return Whether the index results in a change of gene.
	 */
	public boolean containsMutationIndex(final int index) {
		for (ReferenceGene rg : this.getReferenceGenes()) {
			if (rg.containsMutationIndex(index)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the map containing the known drug resistance mutations.
	 */
	public Map<Long, ResistanceMutation> getDrugResistanceMutations() {
		return drugResMuts;
	}

	/**
	 * @param knownResMut
	 *            The known resistance causing mutations file.
	 */
	public void setDrugRestistantMutations(final File knownResMut) {
		if (knownResMut != null) {
			this.drugResMuts = extractMutations(knownResMut);
			connectGeneMutations();
			notifyResistanceMutationObservers();
		}
	}

	/**
	 * Notifies the meta data load observers when the meta data contained in
	 * this instance has changed.
	 */
	private void notifyResistanceMutationObservers() {
		for (ResistanceMutationObserver mdlo : observers) {
			mdlo.update(this.getDrugResistanceMutations());
		}
	}

	/**
	 * Notifies the meta data load observers when the meta data contained in
	 * this instance has changed.
	 */
	private void notifyReferenceGeneObservers() {
		for (ReferenceGeneObserver rgo : rgobservers) {
			rgo.update();
		}
	}

	/**
	 * @param geneAnn
	 *            The gene annotation file
	 */
	public void setGeneAnnotation(final File geneAnn) {
		if (geneAnn != null) {
			this.referenceGenes = extractReferenceGenes(geneAnn);
			connectGeneMutations();
			notifyReferenceGeneObservers();
		}
	}
}
