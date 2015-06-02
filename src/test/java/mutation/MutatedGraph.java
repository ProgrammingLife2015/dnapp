package mutation;

import java.util.Collection;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 * @version 1.0
 *
 */
public interface MutatedGraph {

	// Collection<Mutation> getAllMutations();

	/**
	 * @return A collection of all the point mutations in the graph.
	 */
	Collection<PointMutation> getAllPointMutations();

}
