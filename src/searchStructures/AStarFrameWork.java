package searchStructures;

import gridPuzzle.GridGoalTest;
import gridPuzzle.GridLocation;
import gridPuzzle.GridPuzzleSuccessorFunction;

import java.util.ArrayList;
import java.util.List;

import rp.util.Collections;
import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.Heuristic;
import rp13.search.interfaces.SortedAgenda;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

/**
 * Class that implements the A* search algorithm as a framework which can work
 * with generics
 * 
 * @author david
 * 
 * @param <ActionT>
 *            An action -> type should represent something which transform a
 *            state to another state
 * @param <StateT>
 *            The way that the nodes are represented at a given point. Their
 *            state. State must be able to generate a Heuristic value as given
 *            by the Heuristic interface
 */
public class AStarFrameWork<ActionT, StateT extends Heuristic> {

	private SortedAgenda<ComparableSearchNode<ActionT, StateT>> agenda;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private GoalTest<StateT> gtest;
	private List<ComparableSearchNode<ActionT, StateT>> successors = new ArrayList<ComparableSearchNode<ActionT, StateT>>();
	private List<ComparableSearchNode<ActionT, StateT>> closed = new ArrayList<ComparableSearchNode<ActionT, StateT>>();
	private final ComparableSearchNode<ActionT, StateT> initial;

	/**
	 * Constructor creates a new instance of the framework using the parameters
	 * 
	 * @param agenda
	 *            The agenda that is being used, must be a sorted one for A*
	 * @param gtest
	 *            The function that checks whether a state is the goal or not
	 * @param sfunc
	 *            Functions that returns the successors given a state
	 * @param initial
	 *            The initial state of the problem
	 */
	public AStarFrameWork(
			SortedAgenda<ComparableSearchNode<ActionT, StateT>> agenda,
			GoalTest<StateT> gtest, SuccessorFunction<ActionT, StateT> sfunc,
			ComparableSearchNode<ActionT, StateT> initial) {

		this.gtest = gtest;
		this.agenda = agenda;
		this.sfunc = sfunc;
		this.initial = initial;
	}

	/**
	 * Main search loop that carries out the search according to the A*
	 * algorithm
	 * 
	 * @return A list of actions of the type declared in the type declaration
	 *         for the class that reach the goal state from the original state.
	 */
	public List<ActionT> searchLoop() {
		System.out.println(initial.getActionStatePair().getState());
		if (isGoal(initial)) {
			return makeMoveList(initial);
		}

		addSuccessorsToAgenda(initial);
		while (!agenda.isEmpty()) {
			ComparableSearchNode<ActionT, StateT> testNode = agenda.pop();
			if (isGoal(testNode)) {
				return makeMoveList(testNode);
			} else
				addSuccessorsToAgenda(testNode);
		}

		return null;
	}

	/**
	 * Helper method that adds successors to the agenda. Get all the successors
	 * for a given search state and then compare their state against the closed
	 * list to see if they go onto the agenda
	 * 
	 * @param parentNode
	 */
	private void addSuccessorsToAgenda(
			ComparableSearchNode<ActionT, StateT> parentNode) {
		sfunc.getComparableSuccessors(parentNode, successors);

		for (ComparableSearchNode<ActionT, StateT> item : successors) {

			boolean addStatus = true;

			for (ComparableSearchNode<ActionT, StateT> closedItem : closed) {
				if (closedItem.getActionStatePair().getState()
						.equals(item.getActionStatePair().getState())
						&& closedItem.getAStar() < item.getAStar())

					addStatus = false;
			}

			if (addStatus) {
				agenda.push(item);
				closed.add(item);
			}

		}
		successors.clear();
		agenda.sort();
	}

	/**
	 * Helper function that generates the list of moves required to get to a
	 * ndoe by going up through the parent state until the initial one (has
	 * parent of null) is found.
	 * 
	 * @param finishedNode
	 *            The node found in the search that is the goal
	 * @return A list of actions that can be taken from the initial state to
	 *         reach the goal state
	 */
	private List<ActionT> makeMoveList(SearchNode<ActionT, StateT> finishedNode) {
		System.out.println("Found a solution! The path is:");

		List<ActionT> resultList = new ArrayList<ActionT>();

		while (finishedNode.getParent() != null) {
			resultList.add(0, finishedNode.getActionStatePair().getAction());
			finishedNode = finishedNode.getParent();
		}

		System.out.println(resultList);
		return resultList;
	}

	/**
	 * Method to call the goal test in the main loop more neatly
	 * 
	 * @param testNode
	 *            Node being tested to see if it is the goal
	 * @return Whether or not the node is the goal
	 */
	public boolean isGoal(ComparableSearchNode<ActionT, StateT> testNode) {
		if (gtest.isGoal(testNode.getActionStatePair().getState())) {
			return true;
		} else
			return false;
	}

	public static void main(String[] args) {

		ComparableSearchNode<GridLocation.GridMove, GridLocation> initial = new ComparableSearchNode<GridLocation.GridMove, GridLocation>(
				new ActionStatePair<GridLocation.GridMove, GridLocation>(null,
						new GridLocation(0, 0)), null, 0);

		SortedAgenda<ComparableSearchNode<GridLocation.GridMove, GridLocation>> agenda = new AStarAgenda<GridLocation.GridMove, GridLocation>();

		SuccessorFunction<GridLocation.GridMove, GridLocation> sfunc = new GridPuzzleSuccessorFunction();

		GoalTest<GridLocation> gtest = new GridGoalTest();

		AStarFrameWork<GridLocation.GridMove, GridLocation> frame = new AStarFrameWork<GridLocation.GridMove, GridLocation>(
				agenda, gtest, sfunc, initial);

		frame.searchLoop();
	}

}
