package searchStructures;

import rp13.search.interfaces.Heuristic;
import rp13.search.util.ActionStatePair;

public class ComparableSearchNode<ActionT,StateT extends Heuristic> extends SearchNode<ActionT,StateT> implements Comparable<ComparableSearchNode<ActionT,StateT >> {

	private int costToNode;
	private int aStarValue;
	private ComparableSearchNode<ActionT,StateT> parent;
	
	public ComparableSearchNode(ActionStatePair<ActionT,StateT> pair,ComparableSearchNode<ActionT,StateT> parent, int cost){
		super(pair,parent);
		this.costToNode = cost;
		this.parent = parent;
	}



	@Override
	public int compareTo(ComparableSearchNode<ActionT, StateT> o) {
		return this.getAStar() - o.getAStar();
	}


	public int getAStar() {
		return getActionStatePair().getState().getHeuristicValue() + getCostToNode();
	}
	
	public int getCostToNode(){
		return costToNode;
	}
	
	public void setCostToNode(int cost){
		costToNode = cost;
	}
	
	@Override
	public ComparableSearchNode<ActionT,StateT> getParent(){
		return parent;
	}

	
	
}
