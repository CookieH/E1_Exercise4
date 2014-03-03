package rp13.search.interfaces;

/**
 * Interface that requires a class to provide a heuristic value.
 * @author david
 *
 */
public interface Heuristic {

	 int getCostToMove();
	 int getHeuristicValue();
	
}
