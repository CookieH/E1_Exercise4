package searchStructures;

import rp13.search.util.ActionStatePair;

public class SearchNode<ActionT,StateT>  {

	private ActionStatePair<ActionT,StateT> pair ;
	private SearchNode<ActionT,StateT> parent;
	
	public SearchNode(ActionStatePair<ActionT,StateT> pair, SearchNode<ActionT,StateT> parent){
		this.pair = pair;
		this.parent = parent;
	}
	
	public ActionStatePair<ActionT,StateT> getActionStatePair(){
		return this.pair;
	}
	
	public SearchNode<ActionT,StateT> getParent (){
		return this.parent;
	}
	
	
	
	
	
}
