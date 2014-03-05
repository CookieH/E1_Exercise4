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
 *            state.
 */
public class AStarFrameWork<ActionT, StateT extends Heuristic> {

	private SortedAgenda<ComparableSearchNode<ActionT, StateT>> agenda;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private GoalTest<StateT> gtest;
	private List<ComparableSearchNode<ActionT, StateT>> successors = new ArrayList<ComparableSearchNode<ActionT, StateT>>();
	private List<ComparableSearchNode<ActionT, StateT>> closed = new ArrayList<ComparableSearchNode<ActionT, StateT>>();
	private final ComparableSearchNode<ActionT, StateT> initial;

	/**
	 * Constructor creates a new instance of 
	 * @param agenda
	 * @param gtest
	 * @param sfunc
	 * @param initial
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
