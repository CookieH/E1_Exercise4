package rp13.search.problem.puzzle;

import java.util.ArrayList;

import rp13.search.interfaces.Heuristic;

/**
 * Class to represent a pair of integers on an x,y coordinate grid
 * 
 * @author david
 * 
 */
public class GridLocation implements Heuristic {

	private final static int UPPER_X_BOUND = 15;
	private final static int UPPER_Y_BOUND = 15;
	private final static int LOWER_Y_BOUND = 0;
	private final static int LOWER_X_BOUND = 0;
	private  Coordinate location;
	private final static Coordinate GOAL = new Coordinate(10, 10);
	private static ArrayList<Edge> BLOCKED = new ArrayList<Edge>();

	// ////////BLOCKED EDGES///////////////////////
	private static Edge a = new Edge(new Coordinate(1, 1), new Coordinate(1, 2));
	private static Edge b = new Edge(new Coordinate(1,1),new Coordinate(2,1));
	private static Edge c = new Edge(new Coordinate(1,1),new Coordinate(0,1));
	

	public enum GridMove {
		UP(0), RIGHT(1), DOWN(2), LEFT(3);

		private final int move;

		private GridMove(int _move) {
			move = _move;
		}
		
		public int getValue(){
			return move;
		}
	}

	public GridLocation(int x, int y) {
		location = new Coordinate(x, y);
		addBlockedEdges();
	}
	
	/**
	 * Copying version
	 * @param g 
	 */
	public GridLocation(GridLocation g){
		location = new Coordinate(g.getLocation().getX(),g.getLocation().getY());
	}
	

	private void addBlockedEdges() {
		BLOCKED.add(a);
		BLOCKED.add(b);
		BLOCKED.add(c);
	}

	public static Coordinate getGoal() {
		return GOAL;
	}

	public static int getUpperXBound() {
		return UPPER_X_BOUND;
	}

	public static int getUpperYBound() {
		return UPPER_Y_BOUND;
	}

	public static int getLowerYBound() {
		return LOWER_Y_BOUND;
	}

	public static int getLowerXBound() {
		return LOWER_X_BOUND;
	}

	public Coordinate getLocation() {
		return location;
	}
	
	public boolean isNotBlocked(Edge e){
		for (Edge testEdge : BLOCKED) {
			if (e.equals(testEdge)){
				return false;
			}
		}
		return true;
	}

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
			c = new Coordinate(location.getX(),location.getY());
			break;
		}
		
		
		Edge testEdge = new Edge(location,c);
	
		if(location.getX() >= LOWER_X_BOUND && location.getX()<= UPPER_X_BOUND
		&& location.getY()>=LOWER_Y_BOUND && location.getY()<= UPPER_Y_BOUND && isNotBlocked(testEdge)){
			return true;
		}
		else
			return false;
		
	}
	
	
	public boolean makeMove(GridMove move){
		if(isPossibleMove(move)){
			
			switch (move) {
			case UP:
				location.setY(location.getY()+1);
				break;
			case DOWN:
				location.setY(location.getY()-1);
				break;
			case LEFT:
				location.setX(location.getX()-1);
				break;
			case RIGHT:
				location.setX(location.getX()+1);
				break;
			default:
				break;
			}
		
			return true;
		}
		else return false;
	}

	@Override
	public int getCostToMove() {
		return 0;
	}

	/**
	 * The the minimum "angular" distance between the point at the moment and the goal.
	 */
	@Override
	public int getHeuristicValue() {
		
		int x = Math.abs(GOAL.getX() - location.getX());
		int y = Math.abs(GOAL.getY() - location.getY());
		
		return x+y;
		
	}
	
	public String toString(){
		return "Location: " + location.toString(); 
	}

}
