package rp13.search.problem.puzzle;

import rp13.search.interfaces.GoalTest;

public class GridGoalTest implements GoalTest<GridLocation>{

	
	
	public GridGoalTest(){
	}
	
	@Override
	public boolean isGoal(GridLocation _state) {
		if(_state.getLocation().equals(GridLocation.getGoal()))
			return true;
		else return false;
	}

}
