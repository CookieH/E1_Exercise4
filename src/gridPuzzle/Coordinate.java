package gridPuzzle;

/**
 * Class used to represent a coordinate on an x,y coordinate plane.
 * @author david
 *
 */
public class Coordinate {

	private int x;
	private int y;
	
	/**
	 * Constructor creates an instance of the coordinate given an x and y coordinate.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x value
	 * @return The value of the x coordinate
	 */
	public int getX() {
		return x;
	}
	

	/**
	 * Returns the x value
	 * @return The value of the x coordinate
	 */
	public int getY() {
		return y;
	}
	
	public void setX(int x){
		this.x =x;
	}
	
	public void setY(int y){
		this.y =y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Coordinate other){
		return ((getX() == other.getX()) && (getY() == other.getY()));
	}
	
	
}
