package searchStructures;
import java.util.Iterator;

import rp.util.SimpleSet;
import rp13.search.interfaces.Agenda;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.ActionStatePair;


public class BreadthFirstAgenda<ActionT,StateT>
extends SimpleSet<SearchNode<ActionT,StateT>>
implements Agenda<SearchNode<ActionT,StateT>>  
{

	public BreadthFirstAgenda(){
		super();
	}
	
	
	public void push(SearchNode<ActionT, StateT> _item) {
		super.add(_item);
	}

	
	public SearchNode<ActionT, StateT> pop() {
		return super.removeFirst();
	}

	
	public boolean doesContain(SearchNode<ActionT, StateT> _item) {
		return super.contains(_item);
	}
	
	public boolean isEmpty(){
		return super.isEmpty();
	}

	public String toString(){
		
		String result = "";
		for (SearchNode<ActionT,StateT> pair : m_inner) {
			 result = result + " " + pair.toString();
		}
		
		return result;
	}


}
