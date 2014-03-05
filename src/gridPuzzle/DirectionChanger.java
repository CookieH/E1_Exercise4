package gridPuzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains a method that allows the output of directions from the
 * grid puzzle search to be converted into the correct form for a robot
 * 
 * @author david
 * 
 */
public class DirectionChanger {

	/**
	 * A method which converts the raw output from searching the grid for a
	 * solution and converts them to a list of instructions that can be followed
	 * to the goal by the robot.
	 * 
	 * This is necessary because the two are different. For example in the birds
	 * eye view we may see the route as up right right which would be up right
	 * up for the robot as rotation is not taken into account.
	 * 
	 * This is handled by going through the list of instructions and adding a
	 * transformation to every one that follows it in a crude manner. Adding a
	 * number based on the preceeding direction and changing it if it goes out
	 * of bounds.
	 * 
	 * A better solution would have been to take into account rotation in either
	 * the robot or the search but this works and is relatively given the size
	 * of the solutions that will be generated.
	 * 
	 * @param movements
	 *            Raw movements from the grid searching which presents movements
	 *            as a top down view and are therefore not completely able to be
	 *            followed by a robot
	 * 
	 * @return A list of movements that can be followed by a robot to take it to
	 *         the intended destination
	 */
	public static ArrayList<Integer> changeDirectionForm(
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

			} else {// If 3 (turning left)
					// thenchangeDirectionForm(framework.searchLoop( +1 from all
					// following, but 3->0
					// not 4
				for (int j = i + 1; j < modifiedDirections.size(); j++) {
					int replace = modifiedDirections.get(j);
					replace++;
					if (replace == 4) {
						replace = 0;
					}
					modifiedDirections.set(j, replace);
				}

			} // the arbitrator controls which behaviour to do

		}
		return modifiedDirections;
	}

}
