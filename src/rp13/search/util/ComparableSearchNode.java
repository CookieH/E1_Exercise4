package rp13.search.util;

public class ComparableSearchNode<ActionT,StateT extends Comparable<StateT>> extends SearchNode<ActionT,StateT> implements Comparable<ComparableSearchNode<ActionT,StateT >> {

	public ComparableSearchNode(ActionStatePair<ActionT,StateT> pair,ActionStatePair<ActionT,StateT> parent){
		super(pair,parent);
	}



	@Override
	public int compareTo(ComparableSearchNode<ActionT, StateT> o) {
		// TODO Auto-generated method stub
		return o.getActionStatePair().getState().compareTo(getActionStatePair().getState());
	}
	
	
}
