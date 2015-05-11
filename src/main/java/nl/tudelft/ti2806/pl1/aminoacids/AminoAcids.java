package nl.tudelft.ti2806.pl1.aminoacids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import reader.AminoMapReader;
import reader.Reader;

/**
 * 
 * @author Justin
 *
 */
public class AminoAcids {

	/**
	 * The size of a DNA codon is 3.
	 */
	static final int CODON_SIZE = 3;

	public static void main(final String[] args) throws IOException {
		Graph graph = new SingleGraph("simple test graph");
		Graph g = Reader.read("src/main/resources/nodes.txt",
				"src/main/resources/edges.txt");
		graph.addNode("start");
		graph.addNode("end");
		graph.addNode("middle");
		graph.addEdge("1", "start", "middle");
		graph.addEdge("2", "middle", "end");
		// graph.display();
		// convertGraph(graph);
		Collection<Node> nodes = g.getNodeSet();
		Node start = getStart(nodes);
		Node end = getEnd(nodes);
		String gcontent = getGenomeString(start, "TKK-1");
		System.out.println(gcontent);
	}

	/**
	 * Convert a nucloid graph into a amino acid graph.
	 * 
	 * @param nucloids
	 *            Nucloid graph
	 * @return Amino acid graph
	 */
	public static final Graph convertGraph(final Graph nucloids) {
		Graph graph = new SingleGraph("Amino Acid Graph");
		Collection<Node> nodes = nucloids.getNodeSet();
		Node start = getStart(nodes);
		Node end = getEnd(nodes);

		return graph;
	}

	/**
	 * Finds the indexes of all the relevant start and stop codons of a genome.
	 * 
	 * @param genome
	 *            The genome string
	 * @return An ArrayList of indexes
	 * @throws IOException
	 *             File error
	 */
	public static ArrayList<Integer> getExonIndexes(final String genome)
			throws IOException {
		HashMap<String, String> map = AminoMapReader
				.readIntoMap("src/main/resources/amino_DNA_encoding.txt");
		return getNextStartIndex(genome, 0, new ArrayList<Integer>(), map);
	}

	/**
	 * Finds the index of the next Start codon of a genome.
	 * 
	 * @param genome
	 *            The genome string
	 * @param index
	 *            The index from where the searching starts
	 * @param indexes
	 *            The collection of indexes
	 * @param map
	 *            Map of amino acid encoding
	 * @return If the string ends, returns the collection of indexes, if an
	 *         index is found: add it and find the next stop codon
	 */
	private static ArrayList<Integer> getNextStartIndex(final String genome,
			final int index, final ArrayList<Integer> indexes,
			final HashMap<String, String> map) {
		int ind = -1;
		int i = index;
		char[] chars = genome.toCharArray();
		while (i < chars.length && ind == -1) {
			if (chars[i] == 'T' && chars[i + 1] == 'A' && chars[i + 2] == 'C') {
				ind = i;
			}
			i++;
		}
		if (ind == -1) {
			return indexes;
		} else {
			indexes.add(ind);
			return getNextStopIndex(genome, ind, indexes, map);
		}
	}

	/**
	 * Finds the index of the next Stop codon of a genome.
	 * 
	 * @param genome
	 *            The genome string
	 * @param index
	 *            The index from where the searching starts
	 * @param indexes
	 *            The collection of indexes
	 * @param map
	 *            Map of amino acid encoding
	 * @return If the string ends, returns the collection of indexes, if an
	 *         index is found: add it and find the next start codon
	 */
	private static ArrayList<Integer> getNextStopIndex(final String genome,
			final int index, final ArrayList<Integer> indexes,
			final HashMap<String, String> map) {
		int ind = -1;
		int i = index;
		char[] chars = genome.toCharArray();
		while (i < chars.length && ind == -1) {
			if (getAcid("" + chars[i] + chars[i + 1] + chars[i + 2], map)
					.equals("Stop")) {
				ind = i;
			}
			i += CODON_SIZE;
		}
		if (ind == -1) {
			return indexes;
		} else {
			indexes.add(ind);
			return getNextStartIndex(genome, ind, indexes, map);
		}
	}

	/**
	 * Gets the genome string from the start/end nodes and the genome name.
	 * 
	 * @param start
	 *            Start node
	 * @param genomeName
	 *            Genome Name
	 * @return Genome string
	 */
	private static String getGenomeString(final Node start,
			final String genomeName) {
		StringBuilder builder = new StringBuilder();
		Node current = start;
		while (!isEndNode(current)) {
			Iterator<Edge> it = current.getEachLeavingEdge().iterator();
			while (it.hasNext()) {
				Edge edge = it.next();
				if (edge.getSourceNode() == current) {
					Node next = edge.getTargetNode();
					if (((Collection<String>) next.getAttribute("sources"))
							.contains(genomeName)) {
						builder.append(current.getAttribute("content"));
						current = next;
					}
				}
			}
		}
		builder.append(current.getAttribute("content"));
		return builder.toString();
	}

	/**
	 * This method checks if a given node is an end node.
	 * 
	 * @param node
	 *            The node to check
	 * @return A boolean indicating if the given node is an end node
	 */
	public static boolean isEndNode(final Node node) {
		Iterator<Edge> it = node.getEachLeavingEdge().iterator();
		int leavingEdgeCount = 0;
		while (it.hasNext()) {
			Edge edge = it.next();
			if (edge.getSourceNode() == node) {
				leavingEdgeCount++;
			}
		}
		return leavingEdgeCount == 0;
	}

	/**
	 * Gets the start Node of a collection of nodes.
	 * 
	 * @param nodes
	 *            Collection of nodes
	 * @return Start node
	 */
	private static Node getStart(final Collection<Node> nodes) {
		for (Node node : nodes) {

			Iterator<Edge> it = node.getEachLeavingEdge().iterator();
			ArrayList<Edge> leaving = new ArrayList<Edge>();

			while (it.hasNext()) {
				Edge e = it.next();
				if (e.getTargetNode().getId().equals(node.getId())) {
					leaving.add(e);
				}
			}

			if (leaving.isEmpty()) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Gets the end Node of a collection of nodes.
	 * 
	 * @param nodes
	 *            Collection of nodes
	 * @return End node
	 */
	private static Node getEnd(final Collection<Node> nodes) {
		for (Node node : nodes) {
			Iterator<Edge> it = node.getEachLeavingEdge().iterator();
			ArrayList<Edge> ingoing = new ArrayList<Edge>();

			while (it.hasNext()) {
				Edge e = it.next();
				if (e.getSourceNode().getId().equals(node.getId())) {
					ingoing.add(e);
				}
			}

			if (ingoing.isEmpty()) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Converts a genome string into an ArrayList of amino acid strings.
	 * 
	 * @param genome
	 *            The genome string
	 * @return Amino acids
	 * @throws InvalidAminoException
	 *             The triplet is not valid
	 * @throws IOException
	 *             File does not exist
	 */
	public static final ArrayList<String> getAminoString(final String genome)
			throws IOException {
		ArrayList<String> triplets = new ArrayList<String>();
		char[] chars = genome.toCharArray();
		HashMap<String, String> map = AminoMapReader
				.readIntoMap("src/main/resources/amino_DNA_encoding.txt");
		int start = getStart(genome);
		if (start == -1) {
			return new ArrayList<String>();
		}
		boolean stop = false;
		while (!stop && start + 2 < chars.length) {
			String triplet = "" + chars[start] + chars[start + 1]
					+ chars[start + 2];
			String amino = getAcid(triplet, map);
			if (amino.equals("Stop")) {
				stop = true;
			} else {
				triplets.add(amino);
			}
			start = start + CODON_SIZE;
		}
		return triplets;
	}

	/**
	 * Finds the starting position of the first instance of Met in the genome.
	 * 
	 * @param genome
	 *            The genome string
	 * @return The starting position
	 */
	private static int getStart(final String genome) {
		int start = -1;
		int i = 0;
		char[] chars = genome.toCharArray();
		while (i < chars.length && start == -1) {
			if (chars[i] == 'T' && chars[i + 1] == 'A' && chars[i + 2] == 'C') {
				start = i;
			}
			i++;
		}
		return start;
	}

	/**
	 * Finds the matching amino acid of a triplet.
	 * 
	 * @param triplet
	 *            The triplet codon
	 * @param map
	 *            The map of amino acid encoding
	 * @return The amino acid
	 * @throws InvalidAminoException
	 */
	private static String getAcid(final String triplet,
			final HashMap<String, String> map) {
		return map.get(triplet);

	}

}
