package gridPuzzle;

import rp13.search.interfaces.GoalTest;

/**
 * Goal test class for the grid method
 * 
 * @author david
 * 
 */
public class GridGoalTest implements GoalTest<GridLocation> {

	/**
	 * Constructor creates instance of the class. No field containing the goal
	 * as this is held staticaly in the GridLocation class, it would be possible
	 * to do this differently
	 */
	public GridGoalTest() {
	}

	/**
	 * Method returns true if the state passed as a parameter is the same as the
	 * goal.
	 * 
	 * @param _state
	 *            The state that is being checked to see whether or not it is
	 *            the goal.
	 * @return Whether or not the state is the goal.
	 */
	@Override
	public boolean isGoal(GridLocation _state) {
		if (_state.getLocation().equals(GridLocation.getGoal()))
			return true;
		else
			return false;
	}

}
