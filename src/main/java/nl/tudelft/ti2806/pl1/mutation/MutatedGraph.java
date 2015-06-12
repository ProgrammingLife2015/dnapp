package nl.tudelft.ti2806.pl1.mutation;

import java.util.Collection;

import nl.tudelft.ti2806.pl1.DGraph.DGraphInterface;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 * @version 1.0
 *
 */
public interface MutatedGraph extends DGraphInterface {

	// Collection<Mutation> getAllMutations();

	/**
	 * @return A collection of all the point mutations in the graph.
	 */
	Collection<PointMutation> getAllPointMutations();

	/**
	 * @return A collection of all the InDel mutations in the graph.
	 */
	Collection<PointMutation> getAllInDelMutations();

}
