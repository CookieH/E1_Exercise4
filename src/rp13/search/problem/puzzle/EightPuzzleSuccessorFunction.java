package rp13.search.problem.puzzle;

import java.util.ArrayList;
import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.ActionStatePair;
import rp13.search.util.ComparableSearchNode;
import rp13.search.util.SearchNode;

/**
 * An example eight-puzzle successor function.
 * 
 * @author Nick Hawes
 * 
 * @param <ActionT>
 * @param <StateT>
 */
public class EightPuzzleSuccessorFunction implements
		SuccessorFunction<PuzzleMove, EightPuzzle> {

	/**
	 * 
	 * Get the possible successors of an eight-puzzle state. Only returns legal
	 * moves.
	 * 
	 */
	@Override
	public void getSuccessors(SearchNode<PuzzleMove, EightPuzzle> _cause,
			List<SearchNode<PuzzleMove, EightPuzzle>> _successors) {

		assert (_successors != null);

		// for each of the moves that are available
		for (PuzzleMove move : PuzzleMove.values()) {

			// check if it is possible
			if (_cause.getActionStatePair().getState().isPossibleMove(move)) {

				// create a copy of the input state as we don't want to change
				// it
				EightPuzzle successor = new EightPuzzle(_cause
						.getActionStatePair().getState());
				// apply the move
				successor.makeMove(move);
				// store the move and action together in a pair and add to
				// successor list
				_successors
						.add(new SearchNode<EightPuzzle.PuzzleMove, EightPuzzle>(
								new ActionStatePair<PuzzleMove, EightPuzzle>(
										move, successor), _cause));
			}

		}

	}

	@Override
	public void getComparableSuccessors(
			ComparableSearchNode<PuzzleMove, EightPuzzle> _cause,
			List<ComparableSearchNode<PuzzleMove, EightPuzzle>> _successors) {

		assert (_successors != null);

		// for each of the moves that are available
		for (PuzzleMove move : PuzzleMove.values()) {

			// check if it is possible
			if (_cause.getActionStatePair().getState().isPossibleMove(move)) {

				// create a copy of the input state as we don't want to change
				// it
				EightPuzzle successor = new EightPuzzle(_cause
						.getActionStatePair().getState());
				// apply the move
				successor.makeMove(move);
				// store the move and action together in a pair and add to
				// successor list
				_successors
						.add(new ComparableSearchNode<EightPuzzle.PuzzleMove, EightPuzzle>(
								new ActionStatePair<PuzzleMove, EightPuzzle>(
										move, successor), _cause, _cause
										.getCostToNode()
										+ _cause.getActionStatePair()
												.getState().getCostToMove()));
			}

		}
	}

}

/*
 * public static void main(String[] args) { EightPuzzle state =
 * EightPuzzle.orderedEightPuzzle();
 * ActionStatePair<EightPuzzle.PuzzleMove,EightPuzzle> pair = new
 * SearchNode<EightPuzzle.PuzzleMove,EightPuzzle>(null,state,null);
 * 
 * EightPuzzleSuccessorFunction successorFn = new
 * EightPuzzleSuccessorFunction();
 * 
 * // successors are added to the end of this list by the successor // function
 * List<ActionStatePair<PuzzleMove, EightPuzzle>> successors = new
 * ArrayList<ActionStatePair<PuzzleMove, EightPuzzle>>(); ; // get the
 * successors of the given state successorFn.getSuccessors(pair, successors);
 * 
 * for (ActionStatePair<PuzzleMove, EightPuzzle> successor : successors) {
 * 
 * System.out.println("Applied move " + successor.getAction() + " and got:");
 * 
 * System.out.println(successor.getState());
 * 
 * }
 * 
 * }
 */

