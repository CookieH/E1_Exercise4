package rp13.search.problem.puzzle;

import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.ComparableSearchNode;
import rp13.search.util.SearchNode;

/**
 * Gets the successors from the current parent state and then returns them in a list
 * @author Howard
 *
 */
public class WordPuzzleSuccessorFunction implements
		SuccessorFunction<IntPair, WordPuzzle> {

	/**
	 * gets the legal successors for the current state
	 * @param cause the current state
	 * @param _successors the list of successors to be added to
	 */
	@Override
	public void getSuccessors(SearchNode<IntPair, WordPuzzle> cause,
			List<SearchNode<IntPair, WordPuzzle>> _successors) {

		WordPuzzle puzz = (WordPuzzle)cause.getActionStatePair().getState();
		String curr = puzz.getString();
		for(int i=0; i<puzz.getMoves().size(); i++)
		{
			WordPuzzle successor = new WordPuzzle(curr);
			//create a duplicated each time to avoid overwriting
			successor.makeMove(puzz.getMoves().get(i));
			//makes each available move on at a time
			_successors.add(new SearchNode<IntPair, WordPuzzle>(
					new ActionStatePair<IntPair, WordPuzzle>(
							puzz.getMoves().get(i), successor), cause));
			//adds the new successor to the list
		}
		
		
	}

	/**
	 * a very similar version of the above method but altered to allow heuristic function
	 * @param cause the parent node from which successors will be found
	 * @param _successors the list of successors to be added to
	 * @param goal the wordpuzzle with which states will be checks against to calculate
	 * 			the heuristics
	 */
	@Override
	public void getComparableSuccessors(
			ComparableSearchNode<IntPair, WordPuzzle> cause,
			List<ComparableSearchNode<IntPair, WordPuzzle>> _successors, WordPuzzle goal) {
		

		WordPuzzle puzz = (WordPuzzle)cause.getActionStatePair().getState();
		String curr = puzz.getString();
		for(int i=0; i<puzz.getMoves().size(); i++)
		{
			WordPuzzle successor = new WordPuzzle(curr, goal);
			//creates a duplicate each time to avoid overwriting and sends the goal
			//so that the successor can check the heuristic
			successor.makeMove(puzz.getMoves().get(i));
			//makes each possible move one at a time
			_successors.add(new ComparableSearchNode<IntPair, WordPuzzle>(
					new ActionStatePair<IntPair, WordPuzzle>(
							puzz.getMoves().get(i), successor), cause, cause
							.getCostToNode()
							+ cause.getActionStatePair()
									.getState().getCostToMove()));
			//adds the succesor to the list with the heuristic
		}
		
	}

	/**
	 * empty method with different parameters that is only present to honor the interface
	 * had to do this as the successor function when using a star needs the goal state to
	 * send to the word puzzle
	 */
	@Override
	public void getComparableSuccessors(
			ComparableSearchNode<IntPair, WordPuzzle> cause,
			List<ComparableSearchNode<IntPair, WordPuzzle>> _successors) {
		// TODO Auto-generated method stub
		
	}

}
