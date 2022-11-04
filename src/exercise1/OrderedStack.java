package exercise1;

import java.util.Stack;

public class OrderedStack<T extends Comparable<T>> extends Stack<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public T push(T val) {
		if (size() == 0 || peek().compareTo(val) > 0) {
			super.push(val);
			return val;
		}
		throw new IllegalStateException("Cannot push");
	}
}
