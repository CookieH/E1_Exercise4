package searchStructures;
import java.util.Iterator;

import rp.util.SimpleSet;
import rp13.search.interfaces.Agenda;
//TODO fix this class!!!
/**
 * Uses stack for agenda, implements depth first
 * @author lfg332
 *
 */
public class DepthFirstAgenda<ActionT,StateT> extends SimpleSet<SearchNode<ActionT,StateT>> implements Agenda<SearchNode<ActionT,StateT>> {

	/**
	 * Add an item to the agenda
	 */
	@Override
	public void push(SearchNode<ActionT, StateT> _item) {
		add(_item);
	}

	/**
	 * Remove the last added item from the agenda
	 */
	@Override
	public SearchNode<ActionT, StateT> pop() {
		return removeLast();
	}

	/**
	 * Checked whether an item is in the agenda.
	 * 
	 * @param _item The item being checked
	 * @return Whether or not the item it in the agenda
	 */
	@Override
	public boolean doesContain(SearchNode<ActionT, StateT> _item) {
		return super.contains(_item);
	}



}
