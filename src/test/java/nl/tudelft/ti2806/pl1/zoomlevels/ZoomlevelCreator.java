package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.Collection;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.mutation.MutatedGraph;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class ZoomlevelCreator {

	private ZoomlevelCreator() {
	}

	/**
	 * 
	 * @param graph
	 * @return
	 */
	public static DGraph RemoveSYN_SNP(final MutatedGraph graph) {
		DGraph ret = new DGraph();
		Collection<PointMutation> mutations = graph.getAllPointMutations();
		for (PointMutation mut : mutations) {
			if (mut.isSynonymous()) {
				Set<Integer> nodes = mut.getNodes();
				// TODO collapse nodes in ret.
			}
		}
		return ret;
	}

}
