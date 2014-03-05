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
import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.util.ActionStatePair;
import searchStructures.BreadthFirstAgenda;
import searchStructures.Framework;
import searchStructures.SearchNode;

/**
 * BF demonstration of the grid puzzle. To be uploaded to a robot.
 * @author david
 *
 */
public class GridDemoBF {
	
	private Framework<GridLocation.GridMove,GridLocation> framework;
	private Junction model;

	public GridDemoBF(Framework<GridLocation.GridMove,GridLocation> framework, Junction model){
		this.framework = framework;
		this.model = model;
	}

	/**
	 * Method hands converted instructions to the robot
	 */
	public void pathFind(){
		ArrayList<Integer> directions = DirectionChanger.changeDirectionForm(framework.searchLoop());
		TakeTurn turner = model.getTurner();
		//System.out.println(directions);
		for(int  i=0; i<directions.size(); i++){
			turner.addDirections(directions.get(i));
		}
		System.out.println(turner.getDirections());
		model.run();
	}

	public static void main (String[] args) {
			SearchNode<GridLocation.GridMove, GridLocation> initial = new SearchNode<GridLocation.GridMove, GridLocation>(
					new ActionStatePair<GridLocation.GridMove, GridLocation>(null,
							new GridLocation(0,0)), null);
			
			Agenda<SearchNode<GridLocation.GridMove,GridLocation>> agenda =
					new BreadthFirstAgenda<GridLocation.GridMove,GridLocation>();
			
			SuccessorFunction<GridLocation.GridMove,GridLocation> sfunc = new GridPuzzleSuccessorFunction();
			
			GoalTest<GridLocation> gtest = new GridGoalTest();
			
			Framework<GridLocation.GridMove,GridLocation> frame =
					new Framework<GridLocation.GridMove,GridLocation>(agenda,gtest,sfunc,initial); 
		
			Junction model =   new Junction(new DifferentialPilot(5.6, 12.9, Motor.C,
					Motor.B), new LightSensor(SensorPort.S2), new LightSensor(SensorPort.S4));
			
			GridDemoBF gd = new GridDemoBF(frame, model);
			gd.pathFind();
		
	}
	
	
	
	
}

