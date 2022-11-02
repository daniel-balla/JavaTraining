package exercise2;

import java.util.Iterator;

public class LinkedListIterator<E> implements Iterator<E> {

	int size;
	@SuppressWarnings("unchecked")
	E[] arr = (E[]) new Object[size];
	
	public LinkedListIterator(E[] a) {
		this.arr = a;
	}
	
	@Override
	public boolean hasNext() {
		
		return false;
	}

	@Override
	public E next() {
		// TODO Auto-generated method stub
		return null;
	}

}
