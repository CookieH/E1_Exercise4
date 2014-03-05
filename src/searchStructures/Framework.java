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

public class Framework<ActionT, StateT extends Heuristic> {

	private Agenda<SearchNode<ActionT, StateT>> agenda;
	private GoalTest<StateT> gtest;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private List<SearchNode<ActionT, StateT>> successors = new ArrayList<SearchNode<ActionT, StateT>>();;
	private List<StateT> closed = new ArrayList<StateT>();
	private final SearchNode<ActionT, StateT> initial;

	public Framework(Agenda<SearchNode<ActionT, StateT>> agenda,
			GoalTest<StateT> gtest, SuccessorFunction<ActionT, StateT> sfunc,
			SearchNode<ActionT, StateT> initial) {

		this.agenda = agenda;
		this.gtest = gtest;
		this.sfunc = sfunc;
		this.initial = initial;
	}

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

	public boolean isGoal(SearchNode<ActionT,StateT> testNode){
		if(gtest.isGoal(testNode.getActionStatePair().getState())){
			return true;
		}
			else return false;
	}
	
	public List<ActionT> makeMoveList(SearchNode<ActionT,StateT> finishedNode){
		System.out.println("Found a solution! The path is:");

		List<ActionT> resultList = new ArrayList<ActionT>();

		while (finishedNode.getParent() != null) {
			resultList
					.add(0, finishedNode.getActionStatePair().getAction());
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
