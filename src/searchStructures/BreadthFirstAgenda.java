package searchStructures;

import java.util.Iterator;

import rp.util.SimpleSet;
import rp13.search.interfaces.Agenda;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.ActionStatePair;

/**
 * Class that implements a BreadthFirstAgenda using a simple set. This is
 * effectively a FIFO queue. Agenda is made up of search nodes which contains
 * among other details action state pairs made up of the state and action
 * declared in the class declaration
 * 
 * @author david
 * 
 * @param <ActionT>
 *            Represents the actions that can be made from the states to
 *            generate new states.
 * @param <StateT>
 *            Represents the states of the puzzle at a given point
 * 
 */
public class BreadthFirstAgenda<ActionT, StateT> extends
		SimpleSet<SearchNode<ActionT, StateT>> implements
		Agenda<SearchNode<ActionT, StateT>> {

	/**
	 * Constructor, creates instance of this by calling super class
	 */
	public BreadthFirstAgenda() {
		super();
	}

	/**
	 * Push an item to the agenda by adding at the end
	 * 
	 * @param The
	 *            node being added to the agenda
	 */
	public void push(SearchNode<ActionT, StateT> _item) {
		super.add(_item);
	}

	/**
	 * Removes the first element from the agenda
	 * @return The first element of the agenda
	 */
	public SearchNode<ActionT, StateT> pop() {
		return super.removeFirst();
	}

	/**
	 * Checks to see whether item passed in the parameter is in the list.
	 * 
	 * @return Whether or not the node is in the agenda
	 * @param _item Item being checked
	 */
	public boolean doesContain(SearchNode<ActionT, StateT> _item) {
		return super.contains(_item);
	}

	/**
	 * Return whether or not the agenda is empty
	 * 
	 * @return Whether or not the agenda is empty.
	 */
	public boolean isEmpty() {
		return super.isEmpty();
	}

	/**
	 * Returns all the items in the agenda as a readable string.
	 * @return The agenda in a readable string form
	 */
	public String toString() {

		String result = "";
		for (SearchNode<ActionT, StateT> pair : m_inner) {
			result = result + " " + pair.toString();
		}

		return result;
	}

}
