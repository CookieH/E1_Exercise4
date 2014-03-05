/**
 * 
 */
package rp13.search.interfaces;

import java.util.List;

import rp13.search.util.ActionStatePair;
import searchStructures.ComparableSearchNode;
import searchStructures.SearchNode;

/**
 * 
 * Defines an interface that can be used by a search algorithm to get the
 * successor of a given state.
 * 
 * @author Nick Hawes
 * 
 */
public interface SuccessorFunction<ActionT, StateT extends Heuristic> {

	/**
	 * Adds each successor of the given state to the end of the _successors
	 * list, along with the action that generated it. The _successors list is
	 * not cleared by this method.
	 * 
	 * @param _state
	 * @param _successors
	 */
	public void getSuccessors(SearchNode<ActionT,StateT> cause, 
			List<SearchNode<ActionT, StateT>> _successors);
	
	public void getComparableSuccessors(ComparableSearchNode<ActionT,StateT> cause, 
			List<ComparableSearchNode<ActionT, StateT>> _successors);
}
