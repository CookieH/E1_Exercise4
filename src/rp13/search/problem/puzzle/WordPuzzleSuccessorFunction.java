package rp13.search.problem.puzzle;

import java.util.List;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class WordPuzzleSuccessorFunction implements
		SuccessorFunction<IntPair, WordPuzzle> {

	public void getSuccessors(ActionStatePair<IntPair, WordPuzzle> cause,
			List<ActionStatePair<IntPair, WordPuzzle>> _successors) {

		WordPuzzle puzz = (WordPuzzle)cause.getState();
		for(int i=0; i<puzz.getMoves().size(); i++)
		{
			WordPuzzle successor = (WordPuzzle)cause.getState();
			successor.makeMove(puzz.getMoves().get(i));
			_successors.add(new ActionStatePair<IntPair, WordPuzzle>(puzz.getMoves().get(i), successor, cause));
		}
		
		
	}

}
