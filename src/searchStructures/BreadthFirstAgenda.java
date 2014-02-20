package searchStructures;
import java.util.Iterator;

import rp.util.SimpleSet;
import rp13.search.interfaces.Agenda;


public class BreadthFirstAgenda<Node> extends SimpleSet implements Agenda {

	public BreadthFirstAgenda(){
		super();
	}
	
	/**
	 * Adds to back of set
	 */
	@Override
	public void push(Object _item) {
		add(_item);
	}

	/**
	 * Return the first element removed from the stack.
	 */
	@Override
	public Object pop() {
		Object a = getFirst(); 
		remove(a);
		return a;
	}


	

}
