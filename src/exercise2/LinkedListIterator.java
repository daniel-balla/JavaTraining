package exercise2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<E> implements Iterator<E> {

	Element<E> current;

	@Override
	public boolean hasNext() {

		return current != null;
	}

	@Override
	public E next() {
		if (current == null) {
			throw new NoSuchElementException("NoSuchElementException");
		}
		E val = current.val;
		current = current.next;
		return val;
	}

}
