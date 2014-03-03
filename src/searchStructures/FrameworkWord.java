package searchStructures;

import java.util.ArrayList;
import java.util.List;

import rp.util.SimpleSet;
import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.IntPair;
import rp13.search.problem.puzzle.WordPuzzle;
import rp13.search.problem.puzzle.WordPuzzleSuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class FrameworkWord<ActionT, StateT> {

	private Agenda<ActionStatePair<ActionT, StateT>> agenda;
	private GoalTest<StateT> gtest;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private List<ActionStatePair<ActionT, StateT>> successors = new ArrayList<ActionStatePair<ActionT, StateT>>();;
	private final ActionStatePair<ActionT,StateT> initial;
	private static WordPuzzle goalPuzz;

	public FrameworkWord(Agenda<ActionStatePair<ActionT, StateT>> agenda,
			GoalTest<StateT> gtest, SuccessorFunction<ActionT, StateT> sfunc,
			ActionStatePair<ActionT,StateT> initial) {

		this.agenda = agenda;
		this.gtest = gtest;
		this.sfunc = sfunc;
		this.initial = initial;
	}

	public List<ActionT> searchLoop() {

		System.out.println(initial);
		
		//Initialise with the first function
		sfunc.getSuccessors(initial, successors);
		for (ActionStatePair<ActionT, StateT> item : successors) {
			// / if(!agenda.doesContain(item))change to work with states not
			// pairs
			
			agenda.push(new ActionStatePair<ActionT,StateT>(item.getAction(),item.getState(),item.getParent()));
		}
		successors.clear();
		
		//Go into the main loop
		while (!agenda.isEmpty()) {
			 ActionStatePair<ActionT, StateT> testNode = agenda.pop();
			 if (gtest.isGoal(testNode.getState())) {
				 
				System.out.println("Found a solution! The path is:");
				
				List<ActionT> resultList = new ArrayList<ActionT>();
				
				while(testNode.getParent()!= null){
					resultList.add(0,testNode.getAction());
					testNode = testNode.getParent();
				}
				
				System.out.println(resultList);
				return resultList;
			}

			else {
				sfunc.getSuccessors(testNode, successors);
				for (ActionStatePair<ActionT, StateT> pair : successors) {
					agenda.push(new ActionStatePair<ActionT,StateT>(pair.getAction(),pair.getState(),pair.getParent()));
				}
				successors.clear();
			}

		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		String normal = "jumble";
		
		goalPuzz = new WordPuzzle(normal);
		
		ActionStatePair<IntPair,WordPuzzle> initial = new ActionStatePair<IntPair,WordPuzzle>(null,new WordPuzzle(WordPuzzle.jumble(normal)),null);
		Agenda<ActionStatePair<IntPair,WordPuzzle>> agenda = new BreadthFirstAgenda<IntPair,WordPuzzle>();

		SuccessorFunction<IntPair,WordPuzzle> sfunc = new WordPuzzleSuccessorFunction();
		EqualityGoalTest<WordPuzzle> gtest = new EqualityGoalTest<WordPuzzle>(new WordPuzzle(normal));

		FrameworkWord<IntPair,WordPuzzle> framework = new FrameworkWord<IntPair,WordPuzzle>(agenda, gtest, sfunc, initial);

		framework.searchLoop();
	}

}

