package searchStructures;

import java.util.ArrayList;
import java.util.List;

import rp.util.SimpleSet;
import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.Heuristic;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

/**
 * Class that allows searching using uninformed search methods. Uses generics to
 * make the application of the framework more flexible. Works using the
 * principle of agendas.
 * 
 * @author david
 * 
 * @param <ActionT>
 *            An action -> type should represent something which transform a
 *            state to another state
 * @param <StateT>
 *            The way that the nodes are represented at a given point. Their
 *            state.
 * 
 */
public class Framework<ActionT, StateT extends Heuristic> {

	private Agenda<SearchNode<ActionT, StateT>> agenda;
	private GoalTest<StateT> gtest;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private List<SearchNode<ActionT, StateT>> successors = new ArrayList<SearchNode<ActionT, StateT>>();;
	private List<StateT> closed = new ArrayList<StateT>();
	private final SearchNode<ActionT, StateT> initial;

	/**
	 * Creates a new search framework given the objects in the parameters
	 * 
	 * @param agenda
	 *            The agenda which holds the search nodes that need to be
	 *            explored
	 * @param gtest
	 *            The goaltest function which checks wheether a state is the
	 *            goal or not
	 * @param sfunc
	 *            Successor funtion that generates all the possible successors
	 *            for a state.
	 * 
	 * @param initial
	 *            The starting position for the search
	 */
	public Framework(Agenda<SearchNode<ActionT, StateT>> agenda,
			GoalTest<StateT> gtest, SuccessorFunction<ActionT, StateT> sfunc,
			SearchNode<ActionT, StateT> initial) {

		this.agenda = agenda;
		this.gtest = gtest;
		this.sfunc = sfunc;
		this.initial = initial;
	}

	/**
	 * The main search loop, executes a search based on the fields its has.
	 * Works off of an agenda, pulling the next available item and putting its
	 * successors into the agenda if they are not already present according to
	 * the rules of the agenda. This is repeated until the agenda is exhausted
	 * or a goal is found.
	 * 
	 * @return If a goal is found a list of actions that move from the initial
	 *         state to the goal state
	 */
	public List<ActionT> searchLoop() {

		System.out.println(initial.getActionStatePair());

		if (isGoal(initial)) {
			return makeMoveList(initial);
		}
		closed.add(initial.getActionStatePair().getState());
		// Initialise with the first function
		sfunc.getSuccessors(initial, successors);
		for (SearchNode<ActionT, StateT> item : successors) {
			agenda.push(new SearchNode<ActionT, StateT>(item
					.getActionStatePair(), item.getParent()));
			closed.add(item.getActionStatePair().getState());
		}
		successors.clear();

		// Go into the main loop
		while (!agenda.isEmpty()) {
			SearchNode<ActionT, StateT> testNode = agenda.pop();

			if (isGoal(testNode)) {
				return makeMoveList(testNode);
			}

			else {
				sfunc.getSuccessors(testNode, successors);
				for (SearchNode<ActionT, StateT> pair : successors) {
					if (!closed.contains(pair.getActionStatePair().getState())) {
						agenda.push(new SearchNode<ActionT, StateT>(pair
								.getActionStatePair(), pair.getParent()));
						closed.add(pair.getActionStatePair().getState());
					}
				}
				successors.clear();
			}

		}
		return null;
	}

	/**
	 * Helper function to more neatly check whether a state is the goal or not
	 * 
	 * @param testNode
	 *            The node being checked to see whether its state matches the
	 *            goal
	 * @return Whether or not the state of the node being tested is the goal.,
	 */
	public boolean isGoal(SearchNode<ActionT, StateT> testNode) {
		if (gtest.isGoal(testNode.getActionStatePair().getState())) {
			return true;
		} else
			return false;
	}

	/**
	 * Helper to generate the list of actions that get to the goal from the
	 * initial state once a state that matches the goal has been found. Works by
	 * examining the parent nodes until one that is null is found which is the
	 * initial node. Actions are started at the front of the list otherwise it
	 * would be backward.
	 * 
	 * @param finishedNode
	 *            The search node which has been found to contain the goal state
	 * @return The list of actions that are required to get to the goal state
	 *         from the initial state
	 */
	public List<ActionT> makeMoveList(SearchNode<ActionT, StateT> finishedNode) {
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
	 * @param args
	 */
	public static void main(String[] args) {

		SearchNode<EightPuzzle.PuzzleMove, EightPuzzle> initial = new SearchNode<EightPuzzle.PuzzleMove, EightPuzzle>(
				new ActionStatePair<EightPuzzle.PuzzleMove, EightPuzzle>(null,
						EightPuzzle.randomEightPuzzle()), null);

		Agenda<SearchNode<EightPuzzle.PuzzleMove, EightPuzzle>> agenda = new BreadthFirstAgenda<EightPuzzle.PuzzleMove, EightPuzzle>();

		SuccessorFunction<EightPuzzle.PuzzleMove, EightPuzzle> sfunc = new EightPuzzleSuccessorFunction();
		GoalTest<EightPuzzle> gtest = new EqualityGoalTest<EightPuzzle>(
				EightPuzzle.orderedEightPuzzle());

		Framework<EightPuzzle.PuzzleMove, EightPuzzle> framework = new Framework<EightPuzzle.PuzzleMove, EightPuzzle>(
				agenda, gtest, sfunc, initial);

		framework.searchLoop();
	}
}
