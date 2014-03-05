package gridPuzzle;

/**
 * Class that represents an edge on the grid. An edge is between two coordinates
 * and is bidirectional in that [(1,1)(2,1)] would be the same as [(2,1)(1,1)]
 * which is taken into account in the equals method. Therefore it does not
 * matter which coordinate is the first and which is second. The first and
 * second are not explicitly barred from being the same as this would have
 * no effect on any path finding.
 * 
 * @author david
 * 
 */
public class Edge {
	private Coordinate first;
	private Coordinate second;

	/**
	 * Constructor creates an instance of this class given two coordinates
	 * 
	 * @param first
	 *            The first coordinate of the edge
	 * @param second
	 *            The second coordinate of the edge
	 */
	public Edge(Coordinate first, Coordinate second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Equality check taking into account the isomorphism of edges. A to B is
	 * the same as B to A
	 * 
	 * @return Whether the edge is the same as another edge.
	 */
	public boolean equals(Edge other) {
		return ((getFirst().equals(other.getFirst()) && (getSecond()
				.equals(other.getSecond()))) || ((getFirst().equals(
				other.getSecond()) && (getSecond().equals(other.getFirst())))));
	}

	/**
	 * To string method that convers this into a readable form.
	 */
	public String toString() {
		return "[" + first.toString() + ", " + second.toString() + "]";
	}

	/**
	 * Returns the first coordinate
	 * @return The first coordinate
	 */
	public Coordinate getFirst() {
		return first;
	}

	/**
	 * Returns the second coordinate
	 * @return The second coordinate
	 */
	public Coordinate getSecond() {
		return second;
	}

	/**
	 * Main method. for testing....
	 * @param args
	 */
	public static void main(String[] args) {
		Coordinate test1 = new Coordinate(1, 1);
		Coordinate test2 = new Coordinate(2, 4);
		Coordinate test3 = new Coordinate(2, 2);
		Coordinate test4 = new Coordinate(3, 1);

		Edge edge1 = new Edge(test1, test2);
		Edge edge2 = new Edge(test2, test1);

		System.out.println(edge1);
		System.out.println(edge2);
		System.out.println(edge1.equals(edge2));

	}

}
