package demos;

import java.util.ArrayList;
import java.util.List;

import robotFiles.Junction;
import rp13.search.problem.puzzle.GridLocation;
import rp13.search.problem.puzzle.GridLocation.GridMove;
import searchStructures.AStarFrameWork;

public class GridDemonstration {

	AStarFrameWork<GridLocation.GridMove,GridLocation> framework;
	Junction model;

	public GridDemonstration(AStarFrameWork<GridLocation.GridMove,GridLocation> framework, Junction model) {
		this.model = model;
		this.framework = framework;
	}

	public List<Integer> changeDirectionForm(
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

			} else {// If 3 (turning left) then +1 from all following, but 3->0
					// not 4
				for (int j = i + 1; j < modifiedDirections.size(); j++) {
					int replace = modifiedDirections.get(j);
					replace++;
					if (replace == 4) {
						replace = 0;
					}
					modifiedDirections.set(j, replace);
				}

			}
			
		}
		return modifiedDirections;
	}

	public void pathFind(){
		model.giveDirections(changeDirectionForm(framework.searchLoop()));
		model.run();
	}
	
	public static void main (String[] args) {
		
	}

}
