package demos;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

import robotFiles.Junction;
import robotFiles.TakeTurn;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.SortedAgenda;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.GridGoalTest;
import rp13.search.problem.puzzle.GridLocation;
import rp13.search.problem.puzzle.GridPuzzleSuccessorFunction;
import rp13.search.problem.puzzle.GridLocation.GridMove;
import rp13.search.util.AStarAgenda;
import rp13.search.util.ActionStatePair;
import rp13.search.util.ComparableSearchNode;
import searchStructures.AStarFrameWork;

public class GridDemo {

	AStarFrameWork<GridLocation.GridMove,GridLocation> framework;
	Junction model;

	public GridDemo(AStarFrameWork<GridLocation.GridMove,GridLocation> framework, Junction model) {
		this.model = model;
		this.framework = framework;
	} 

	public ArrayList<Integer> changeDirectionForm(
			List<GridLocation.GridMove> movements) {
		ArrayList<Integer> modifiedDirections = new ArrayList<Integer>();

		for (GridLocation.GridMove action : movements) {
			int a = action.getValue();// add the numeric form to array list
			modifiedDirections.add(a);
		}	
		
		
		// Now we have them in plain numeric form that can be read by robot we
		// must change them so they are correct for the robot!

		for (int i = 0; i < modifiedDirections.size(); i++) {
			int test = modifiedDirections.get(i);

			if (test == 0) {// If 0 (going straight on) do nothing.

			} else if (test == 1) {// If 1 (turning right) then -1 from all
									// following, but 0->3 not -1

				// Loop for every one after i'th index
				for (int j = i + 1; j < modifiedDirections.size(); j++) {
					int replace = modifiedDirections.get(j);
					replace--;
					if (replace == -1) {
						replace = 3;
					}
					modifiedDirections.set(j, replace);
				}

			} else if (test == 2) {// if 2 (backwards) then everything is
									// inversed so + 2.
				for (int j = i + 1; j < modifiedDirections.size(); j++) {
					int replace = modifiedDirections.get(j);
					replace = replace + 2;
					if (replace == 4) {
						replace = 0;
					}
					if (replace == 5) {
						replace = 1;
					}
					modifiedDirections.set(j, replace);
				}

			} else {// If 3 (turning left) thenchangeDirectionForm(framework.searchLoop( +1 from all following, but 3->0
					// not 4
				for (int j = i + 1; j < modifiedDirections.size(); j++) {
					int replace = modifiedDirections.get(j);
					replace++;
					if (replace == 4) {
						replace = 0;
					}
					modifiedDirections.set(j, replace);
				}

			}		//the arbitrator controls which behaviour to do

			
		}
		return modifiedDirections;
	}

	public void pathFind(){
		ArrayList<Integer> directions = changeDirectionForm(framework.searchLoop());
		TakeTurn turner = model.getTurner();
		//System.out.println(directions);
		for(int  i=0; i<directions.size(); i++){
			turner.addDirections(directions.get(i));
		}
		System.out.println(turner.getDirections());
		model.run();
	}
	
	public static void main (String[] args) {
		ComparableSearchNode<GridLocation.GridMove, GridLocation> initial = new ComparableSearchNode<GridLocation.GridMove, GridLocation>(
				new ActionStatePair<GridLocation.GridMove, GridLocation>(null,
						new GridLocation(0,0)), null,0);
		
		SortedAgenda<ComparableSearchNode<GridLocation.GridMove,GridLocation>> agenda =
				new AStarAgenda<GridLocation.GridMove,GridLocation>();
		
		SuccessorFunction<GridLocation.GridMove,GridLocation> sfunc = new GridPuzzleSuccessorFunction();
		
		GoalTest<GridLocation> gtest = new GridGoalTest();
		
		AStarFrameWork<GridLocation.GridMove,GridLocation> frame =
				new AStarFrameWork<GridLocation.GridMove,GridLocation>(agenda,gtest,sfunc,initial); 
	
		Junction model =  new Junction(new DifferentialPilot(5.6, 12.9, Motor.C,
				Motor.B), new LightSensor(SensorPort.S2), new LightSensor(SensorPort.S4));
		
		GridDemo gd = new GridDemo(frame, model);
		gd.pathFind();
	
		
		
	}

}
