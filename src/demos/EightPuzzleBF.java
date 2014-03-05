package demos;

import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import searchStructures.BreadthFirstAgenda;
import searchStructures.Framework;
import searchStructures.SearchNode;

public class EightPuzzleBF {

	/**
	 * Eight puzzle using BF search demo
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
