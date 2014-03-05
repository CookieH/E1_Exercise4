package demos;

import gridPuzzle.DirectionChanger;
import gridPuzzle.GridGoalTest;
import gridPuzzle.GridLocation;
import gridPuzzle.GridPuzzleSuccessorFunction;
import gridPuzzle.GridLocation.GridMove;

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
import rp13.search.util.ActionStatePair;
import searchStructures.AStarAgenda;
import searchStructures.AStarFrameWork;
import searchStructures.ComparableSearchNode;

public class GridDemoAS {

	AStarFrameWork<GridLocation.GridMove,GridLocation> framework;
	Junction model;

	public GridDemoAS(AStarFrameWork<GridLocation.GridMove,GridLocation> framework, Junction model) {
		this.model = model;
		this.framework = framework;
	} 

	

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
		
		GridDemoAS gd = new GridDemoAS(frame, model);
		gd.pathFind();
	
		}

}
