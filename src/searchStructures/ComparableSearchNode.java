package searchStructures;

import rp13.search.interfaces.Heuristic;
import rp13.search.util.ActionStatePair;

/**
 * Extension of the search node class to allow comparison. Requires all of the
 * state that it stores within its action-state pairs to be able to provide a
 * heuristic value which can then be used for the implementation of the
 * comparable interface.
 * 
 * @author david
 * 
 * @param <ActionT>
 *            Type that represents the actions which can manipulate the state.
 * @param <StateT>
 *            Type that can represent the state of a problem at a particular
 *            instance in a particular configuration
 */

public class ComparableSearchNode<ActionT, StateT extends Heuristic> extends
		SearchNode<ActionT, StateT> implements
		Comparable<ComparableSearchNode<ActionT, StateT>> {

	private int costToNode;
	private ComparableSearchNode<ActionT, StateT> parent;

	/**
	 * Constructor creates a new instance of this class given the parameters
	 * passed to it.
	 * 
	 * @param pair
	 *            The action state-pair that is contained within this search
	 *            node
	 * @param parent
	 *            The comparable search node that is the parent of this search
	 *            node
	 * @param cost
	 *            The cost up to this node so far. NOT THE HEURISTIC VALUE OF
	 *            THE STATE
	 */
	public ComparableSearchNode(ActionStatePair<ActionT, StateT> pair,
			ComparableSearchNode<ActionT, StateT> parent, int cost) {
		super(pair, parent);
		this.costToNode = cost;
		this.parent = parent;
	}

	@Override
	/**
	 * Compare to method compares two search nodes based on their A Star values. Follows the normal convention of the compare to method
	 */
	public int compareTo(ComparableSearchNode<ActionT, StateT> o) {
		return this.getAStar() - o.getAStar();
	}

	/**
	 * Method that works out the AStar value by retrieving the heuristic value
	 * from the state and combining it with the cost to the node.
	 * 
	 * @return The AStar value for this search node
	 * 
	 */
	public int getAStar() {
		return getActionStatePair().getState().getHeuristicValue()
				+ getCostToNode();
	}

	/**
	 * Returns the cost that it has taken to get to this search node.
	 * 
	 * @return The cost taken to get to this node.
	 */
	public int getCostToNode() {
		return costToNode;
	}

	/**
	 * Functions sets the cost to this node.
	 * 
	 * @param cost
	 *            The cost to the node
	 */
	public void setCostToNode(int cost) {
		costToNode = cost;
	}

	/**
	 * Returns the parent as a comparable search node.
	 * @return The parent node of this node
	 */
	@Override
	public ComparableSearchNode<ActionT, StateT> getParent() {
		return parent;
	}

}
