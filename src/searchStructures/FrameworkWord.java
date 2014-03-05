package searchStructures;

import java.util.ArrayList;
import java.util.List;

import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.Heuristic;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import wordPuzzle.IntPair;
import wordPuzzle.WordPuzzle;
import wordPuzzle.WordPuzzleSuccessorFunction;

/**
 * A seperate Framework (a copy of the original) to store the word puzzle implementation in
 * @author Howard
 *
 * @param <ActionT> the actions will be int pairs
 * @param <StateT> the states will be word puzzles
 */
public class FrameworkWord<ActionT, StateT extends Heuristic> {

	private Agenda<SearchNode<ActionT, StateT>> agenda;
	private GoalTest<StateT> gtest;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private List<SearchNode<ActionT, StateT>> successors = new ArrayList<SearchNode<ActionT, StateT>>();;
	private final SearchNode<ActionT, StateT> initial;
	private static WordPuzzle goalPuzz;
	private List<StateT> closed = new ArrayList<StateT>();

	/**
	 * Receives the objects and assigns them
	 * @param agenda the list of nodes to explore
	 * @param gtest the desired state to check against
	 * @param sfunc the function that returns the successors
	 * @param initial the first node to be explored
	 */
	public FrameworkWord(Agenda<SearchNode<ActionT, StateT>> agenda,
			GoalTest<StateT> gtest, SuccessorFunction<ActionT, StateT> sfunc,
			SearchNode<ActionT, StateT> initial) {

		this.agenda = agenda;
		this.gtest = gtest;
		this.sfunc = sfunc;
		this.initial = initial;
	}

	/**
	 * checks if the current node is the goal
	 * @param testNode the node to be checked
	 * @return true if the goal has been reach, false if not
	 */
	public boolean isGoal(SearchNode<ActionT,StateT> testNode){
		if(gtest.isGoal(testNode.getActionStatePair().getState())){
			return true;
		}
			else return false;
	}
	
	/**
	 * the main search loop, it takes a node from the agenda and expands it until we find
	 * the goal state
	 * @return the list of actions made to get to the goal form the initial state
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
	 * once the goal has been found this function prints out the actions required to get there
	 * @param finishedNode the node that matched the goal node
	 * @return the list of actions taken
	 */
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
	 * here is the code that we change to use different agendas or puzzles,
	 * the search loop is also called from the main method
	 * @param args
	 */
	public static void main(String[] args) {

		
		String normal = "jumble";
		
		goalPuzz = new WordPuzzle(normal);
	
		SearchNode<IntPair, WordPuzzle> initial = new SearchNode<IntPair, WordPuzzle>(
				new ActionStatePair<IntPair, WordPuzzle>(null,
						new WordPuzzle(WordPuzzle.jumble(normal))), null);
		
		Agenda<SearchNode<IntPair,WordPuzzle>> agenda = new BreadthFirstAgenda
													<IntPair,WordPuzzle>();

		SuccessorFunction<IntPair,WordPuzzle> sfunc = new WordPuzzleSuccessorFunction();
		EqualityGoalTest<WordPuzzle> gtest = new EqualityGoalTest<WordPuzzle>(new WordPuzzle(normal));

		FrameworkWord<IntPair,WordPuzzle> framework = new FrameworkWord<IntPair,WordPuzzle>(agenda, gtest, sfunc, initial);
		
		framework.searchLoop();
	}

}

