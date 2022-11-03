package exercise2;

public class Element<E> {

	E val;
	Element<E> next;

	@Override
	public String toString() {
		return val.toString();
	}
}
