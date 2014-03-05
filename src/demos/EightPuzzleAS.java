package demos;

import gridPuzzle.DirectionChanger;
import gridPuzzle.GridGoalTest;
import gridPuzzle.GridLocation;
import gridPuzzle.GridPuzzleSuccessorFunction;

import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import robotFiles.Junction;
import robotFiles.TakeTurn;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.SortedAgenda;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import searchStructures.AStarAgenda;
import searchStructures.AStarFrameWork;
import searchStructures.ComparableSearchNode;

/**
 * Eight puzzle using A* demo.
 * @author david
 *
 */
public class EightPuzzleAS {

	AStarFrameWork<EightPuzzle.PuzzleMove, EightPuzzle> framework;

	public EightPuzzleAS(AStarFrameWork<EightPuzzle.PuzzleMove,EightPuzzle> framework) {
		this.framework = framework;
	} 
	
	
	
	public static void main (String[] args) {
		ComparableSearchNode<EightPuzzle.PuzzleMove, EightPuzzle> initial = new ComparableSearchNode<EightPuzzle.PuzzleMove, EightPuzzle>(
				new ActionStatePair<EightPuzzle.PuzzleMove, EightPuzzle>(null,
						new EightPuzzle(EightPuzzle.randomEightPuzzle())), null,0);
		
		SortedAgenda<ComparableSearchNode<EightPuzzle.PuzzleMove,EightPuzzle>> agenda =
				new AStarAgenda<EightPuzzle.PuzzleMove,EightPuzzle>();
		
		SuccessorFunction<EightPuzzle.PuzzleMove,EightPuzzle> sfunc = new EightPuzzleSuccessorFunction();
		
		GoalTest<EightPuzzle> gtest = new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle());
		
		AStarFrameWork<EightPuzzle.PuzzleMove, EightPuzzle> frame =
				new AStarFrameWork<EightPuzzle.PuzzleMove,EightPuzzle>(agenda,gtest,sfunc,initial); 
	
	
		EightPuzzleAS demo = new EightPuzzleAS(frame);
		demo.framework.searchLoop();

		
		
	}

}
