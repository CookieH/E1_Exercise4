package gridPuzzle;

import java.util.ArrayList;
import java.util.Arrays;

import rp13.search.interfaces.Heuristic;
import rp13.search.problem.puzzle.EightPuzzle;

/**
 * Class to represent a pair of integers on an x,y coordinate grid as part of
 * the grid navigation puzzle. This class also contains details regarding the
 * goal, blocked edges and the bounds which remain constant across all instances
 * of the class and are therefore static. The robot navigates by creating a new
 * instance of this class with a move from the enumeration of possible moves
 * picked.
 * 
 * All of the bounds are inclusive. For example if the x lower bound is 0 then
 * the robot could feasibly move to any point with the x coordinate of 0.
 * 
 * This class implements the Heuristic interface, allowing an informed search to
 * get the heuristic value for this particular state. The heuristic distance for
 * this class is the Manhattan distance.
 * 
 * @author david
 * 
 */
public class GridLocation implements Heuristic {

	private final static int UPPER_X_BOUND = 9;
	private final static int UPPER_Y_BOUND = 6;
	private final static int LOWER_Y_BOUND = 0;
	private final static int LOWER_X_BOUND = 0;
	private Coordinate location;
	private final static Coordinate GOAL = new Coordinate(9, 6);
	private static ArrayList<Edge> BLOCKED = new ArrayList<Edge>();

	// ////////BLOCKED EDGES///////////////////////
	private static Edge a = new Edge(new Coordinate(0, 1), new Coordinate(1, 1));
	private static Edge b = new Edge(new Coordinate(0, 1), new Coordinate(0, 2));
	private static Edge c = new Edge(new Coordinate(1, 0), new Coordinate(2, 0));
	private static Edge d = new Edge(new Coordinate(5, 0), new Coordinate(6, 0));
	private static Edge e = new Edge(new Coordinate(4, 1), new Coordinate(5, 1));
	private static Edge f = new Edge(new Coordinate(4, 2), new Coordinate(5, 2));
	private static Edge g = new Edge(new Coordinate(5, 2), new Coordinate(5, 3));
	private static Edge h = new Edge(new Coordinate(2, 2), new Coordinate(2, 3));
	private static Edge i = new Edge(new Coordinate(2, 3), new Coordinate(3, 3));
	private static Edge j = new Edge(new Coordinate(1, 5), new Coordinate(2, 5));
	private static Edge k = new Edge(new Coordinate(3, 6), new Coordinate(4, 6));
	private static Edge l = new Edge(new Coordinate(5, 5), new Coordinate(5, 6));
	private static Edge m = new Edge(new Coordinate(4, 4), new Coordinate(5, 4));
	private static Edge n = new Edge(new Coordinate(6, 4), new Coordinate(7, 4));
	private static Edge o = new Edge(new Coordinate(6, 5), new Coordinate(7, 5));

	/**
	 * Explicit enumeration of the moves that can be possible made in the puzzle
	 * 
	 * @author david
	 * 
	 */
	public enum GridMove {
		UP(0), RIGHT(1), DOWN(2), LEFT(3);

		private final int move;

		private GridMove(int _move) {
			move = _move;
		}

		public int getValue() {
			return move;
		}
	}

	/**
	 * Constructor creates a new instance of the GridLocation with the x and y
	 * coordinate set
	 * 
	 * @param x
	 *            The x value of the location
	 * @param y
	 *            The y value of the location
	 */
	public GridLocation(int x, int y) {
		location = new Coordinate(x, y);
		addBlockedEdges();
	}

	/**
	 * Copying version of the constructor which makes an instance of this object
	 * given another one.
	 * 
	 * @param g
	 *            The GridLocation that is being copied
	 */
	public GridLocation(GridLocation g) {
		location = new Coordinate(g.getLocation().getX(), g.getLocation()
				.getY());
	}

	/**
	 * Method to add all the blocked edges to the blocked edges list so that
	 * they can be avoided during the search
	 */
	private void addBlockedEdges() {

		BLOCKED.add(a);
		BLOCKED.add(b);
		BLOCKED.add(c);
		BLOCKED.add(d);
		BLOCKED.add(e);
		BLOCKED.add(f);
		BLOCKED.add(g);
		BLOCKED.add(h);
		BLOCKED.add(i);
		BLOCKED.add(j);
		BLOCKED.add(k);
		BLOCKED.add(l);
		BLOCKED.add(m);
		BLOCKED.add(n);
		BLOCKED.add(o);

	}

	/**
	 * Method that statically returns the goal
	 * 
	 * @return The location of the goal
	 */
	public static Coordinate getGoal() {
		return GOAL;
	}

	/**
	 * Method that gives the upper bound of the x coordinate
	 * 
	 * @return The x coordinate upper bound
	 */
	public static int getUpperXBound() {
		return UPPER_X_BOUND;
	}

	/**
	 * Method that gives the upper bound of the y coordinate
	 * 
	 * @return The y coordinate lower bound
	 */
	public static int getUpperYBound() {
		return UPPER_Y_BOUND;
	}

	/**
	 * Method that gives the lower bound of the y coordinate
	 * 
	 * @return The y coordinate lower bound
	 */
	public static int getLowerYBound() {
		return LOWER_Y_BOUND;
	}

	/**
	 * Method that gives the lower bound of the x coordinate
	 * 
	 * @return The x coordinate lower bound
	 */
	public static int getLowerXBound() {
		return LOWER_X_BOUND;
	}

	/**
	 * Returns the location that is held in this class. Represents the location
	 * on the grid
	 * 
	 * @return The location on the grid
	 */
	public Coordinate getLocation() {
		return location;
	}

	/**
	 * Boolean returns whether or not a route between points in the grid (an
	 * edge) is blocked based upon the list of blocked edges)
	 * 
	 * @param e
	 *            The edge that is being tested to see if it is blocked
	 * @return Whether or not the edge is blocked.
	 */
	public boolean isNotBlocked(Edge e) {
		for (Edge testEdge : BLOCKED) {
			if (e.equals(testEdge)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether a move is possible. Checks this by seeing if the move is
	 * within the bounds of the grid and then checking if the edge that is being
	 * moved along is part of the list of blocked items.
	 * 
	 * @param _move
	 *            The move being checked
	 * @return Whether or not the move is possible
	 */
	public boolean isPossibleMove(GridMove _move) {

		Coordinate c;

		switch (_move) {
		case UP:
			c = new Coordinate(location.getX(), location.getY() + 1);
			break;
		case DOWN:
			c = new Coordinate(location.getX(), location.getY() - 1);
			break;
		case LEFT:
			c = new Coordinate(location.getX() - 1, location.getY());
			break;
		case RIGHT:
			c = new Coordinate(location.getX() + 1, location.getY());
			break;
		default:
			c = new Coordinate(location.getX(), location.getY());
			break;
		}

		Edge testEdge = new Edge(location, c);

		if (location.getX() >= LOWER_X_BOUND
				&& location.getX() <= UPPER_X_BOUND
				&& location.getY() >= LOWER_Y_BOUND
				&& location.getY() <= UPPER_Y_BOUND && isNotBlocked(testEdge)) {
			return true;
		} else
			return false;

	}

	/**
	 * Makes a move, returns false if it cannot be completed
	 * 
	 * @param move
	 *            The move being applied
	 * @return Whether or not the move can be completed
	 */
	public boolean makeMove(GridMove move) {
		if (isPossibleMove(move)) {

			switch (move) {
			case UP:
				location.setY(location.getY() + 1);
				break;
			case DOWN:
				location.setY(location.getY() - 1);
				break;
			case LEFT:
				location.setX(location.getX() - 1);
				break;
			case RIGHT:
				location.setX(location.getX() + 1);
				break;
			default:
				break;
			}

			return true;
		} else
			return false;
	}

	/**
	 * The cost to move from this node. This is currently 0 because the cost is
	 * currently uniform
	 * 
	 * @return The cost to move FROM this node!
	 */
	@Override
	public int getCostToMove() {
		return 1;
	}

	/**
	 * The the manhattan distance between the location and the goal is used as a
	 * heuristic value.
	 * 
	 * Manhattan distance is the "taxi cab" distance that must be taken to get
	 * to a place.
	 * 
	 * @return The heuristic value of this location in relation to the goal
	 */
	@Override
	public int getHeuristicValue() {

		int x = Math.abs(GOAL.getX() - location.getX());
		int y = Math.abs(GOAL.getY() - location.getY());

		return x + y;

	}

	/**
	 * Readable string version of infomration in this class
	 * 
	 * @return The location field of this class in readable form
	 */
	public String toString() {
		return "Location: " + location.toString();
	}

	/**
	 * Compares the states of two instances of this class and returns true if
	 * they are the same and false if they are not.
	 * 
	 * @param _that
	 *            Another object being compared
	 * @return Whether or not the location of the thing being compared is equal
	 */
	@Override
	public boolean equals(Object _that) {
		if (_that instanceof GridLocation) {
			GridLocation that = (GridLocation) _that;

			return that.getLocation().equals(location);

		}

		return false;
	}

}
