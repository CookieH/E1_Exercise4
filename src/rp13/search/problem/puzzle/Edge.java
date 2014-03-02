package rp13.search.problem.puzzle;

public class Edge {
	private Coordinate first;
	private Coordinate second;
	
	public Edge(Coordinate first, Coordinate second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Equality check taking into account the isomorphism of edges. A to B is the same as B to A
	 * @return
	 */
	public boolean equals(Edge other){
		return((getFirst().equals(other.getFirst()) && (getSecond().equals(other.getSecond())))
				||
				((getFirst().equals(other.getSecond()) && (getSecond().equals(other.getFirst())))));
	}
	
	public String toString(){
		return "[" + first.toString() + ", " + second.toString() + "]"; 
	}
	
	public Coordinate getFirst(){
		return first;
	}
	
	public Coordinate getSecond(){
		return second;
	}
	
	public static void main(String [] args){
		Coordinate test1 = new Coordinate(1,1); 
		Coordinate test2 = new Coordinate(2,4);
		Coordinate test3 = new Coordinate(2,2);
		Coordinate test4 = new Coordinate(3,1);
	
		Edge edge1 = new Edge(test1,test2);
		Edge edge2 = new Edge(test2,test1);
		
		System.out.println(edge1);
		System.out.println(edge2);
		System.out.println(edge1.equals(edge2));
		
	}
	
}
