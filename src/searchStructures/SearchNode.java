package searchStructures;

import rp13.search.util.ActionStatePair;

/**
 * Class to wrap up action state pairs in order to make them easier work with
 * when providing additional fields. Makes it easier to add a field which
 * contains a search node which is the parent of this
 * 
 * @author david
 * 
 * @param <ActionT>
 *            An action -> type should represent something which transform a
 *            state to another state
 * @param <StateT>
 *            The way that the nodes are represented at a given point. Their
 *            state.
 */
public class SearchNode<ActionT, StateT> {

	private ActionStatePair<ActionT, StateT> pair;
	private SearchNode<ActionT, StateT> parent;

	/**
	 * Constructor, creates an instance of the class with the fields given to it
	 * in the constructor.
	 * 
	 * @param pair
	 *            The action state pair that this search node contains
	 * @param parent
	 *            The parent search node of this search node
	 */
	public SearchNode(ActionStatePair<ActionT, StateT> pair,
			SearchNode<ActionT, StateT> parent) {
		this.pair = pair;
		this.parent = parent;
	}

	/**
	 * Get the action state pair that this search node contains
	 * 
	 * @return The action state pair in the search node
	 */
	public ActionStatePair<ActionT, StateT> getActionStatePair() {
		return this.pair;
	}

	/**
	 * Get the search node that is the parent of this search node.
	 * 
	 * @return The search node that is the parent of this one.
	 */
	public SearchNode<ActionT, StateT> getParent() {
		return this.parent;
	}

}
