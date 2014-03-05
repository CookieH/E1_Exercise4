package rp13.search.problem.puzzle;

/**
 * a simple data structure class to hold a pair of integers to be used as actions
 * @author Howard
 *
 */
public class IntPair {

	private final int x;
	private final int y;
	
	/**
	 * creates the int pair with the two integers as final
	 * @param x the first integer
	 * @param y the second integer
	 */
	IntPair(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	/**
	 * returns x
	 * @return the x value
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * returns y
	 * @return the y value
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * prints out the two values together
	 */
	public String toString()
	{
		return x + " and " + y;
	}
}
