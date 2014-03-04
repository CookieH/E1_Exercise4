package gridPuzzle;

import java.util.ArrayList;

import rp13.search.interfaces.Heuristic;

/**
 * Class to represent a pair of integers on an x,y coordinate grid
 * 
 * @author david
 * 
 */
public class GridLocation implements Heuristic {

	private final static int UPPER_X_BOUND = 9;
	private final static int UPPER_Y_BOUND = 6;
	private final static int LOWER_Y_BOUND = 0;
	private final static int LOWER_X_BOUND = 0;
	private  Coordinate location;
	private final static Coordinate GOAL = new Coordinate(9, 6);
	private static ArrayList<Edge> BLOCKED = new ArrayList<Edge>();

	// ////////BLOCKED EDGES///////////////////////
	private static Edge a = new Edge(new Coordinate(0, 1), new Coordinate(1, 1));
	private static Edge b = new Edge(new Coordinate(0,1),new Coordinate(0,2));
	private static Edge c = new Edge(new Coordinate(1,0),new Coordinate(2,0));
	private static Edge d = new Edge(new Coordinate(5,0),new Coordinate(6,0));
	private static Edge e = new Edge(new Coordinate(4,1),new Coordinate(5,1));
	private static Edge f = new Edge(new Coordinate(4,2),new Coordinate(5,2));
	private static Edge g = new Edge(new Coordinate(5,2),new Coordinate(5,3));
	private static Edge h = new Edge(new Coordinate(2,2),new Coordinate(2,3));
	private static Edge i = new Edge(new Coordinate(2,3),new Coordinate(3,3));
	private static Edge j = new Edge(new Coordinate(1,5),new Coordinate(2,5));
	private static Edge k = new Edge(new Coordinate(3,6),new Coordinate(4,6));
	private static Edge l = new Edge(new Coordinate(5,5),new Coordinate(5,6));
	private static Edge m = new Edge(new Coordinate(4,4),new Coordinate(5,4));
	private static Edge n = new Edge(new Coordinate(6,4),new Coordinate(7,4));
	private static Edge o = new Edge(new Coordinate(6,5),new Coordinate(7,5));
	

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
