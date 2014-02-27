package rp13.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import rp13.search.interfaces.SortedAgenda;

public class AStarAgenda<ActionT,StateT extends Comparable<StateT>> implements SortedAgenda<ComparableSearchNode<ActionT,StateT>> {

private ArrayList<ComparableSearchNode<ActionT,StateT>> inner = new ArrayList<ComparableSearchNode<ActionT,StateT>>();

/**
 * Add to the list, this method does not sort. That is the job of the sort method down the page.
 */
@Override
public void push(ComparableSearchNode<ActionT, StateT> _item) {
	inner.add(_item);
}

/**
 * Return and remove the first element of the list
 */
@Override
public ComparableSearchNode<ActionT, StateT> pop() {
	return inner.remove(0);
}

/**
 * Returns true if the agenda is empty and false if it is not
 */
@Override
public boolean isEmpty() {
	return inner.size() ==0;
}

@Override
public boolean doesContain(ComparableSearchNode<ActionT, StateT> _item) {
	return inner.contains(_item);
}

@Override
public Iterator<ComparableSearchNode<ActionT, StateT>> iterator() {
	// TODO Auto-generated method stub
	return inner.iterator();
}

@Override
public void sort() {
	Collections.sort(inner);
}

	
}
