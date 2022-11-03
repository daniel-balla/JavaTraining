package exercise2;

import java.util.Iterator;

public class LinkedList<E> implements List<E> {

	private Element<E> head;
	private Element<E> last;
	private int size = 0;

	@Override
	public String toString() {
		String ret = "[";

		if (head != null) {
			ret += head.toString();

			Element<E> next = head;
			while (next != last) {
				next = next.next;
				ret += ", " + next.toString();
			}
		}
		ret += "]";
		return ret;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(E o) {
		if (o.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		if (head != last) {
			for (Element<E> e = head; e != last; e = e.next) {
				if (e.val == o) {
					return true;
				}
			}
			return last.val.equals(o);
		} else if (head != null) {
			return head.val.equals(o);
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		LinkedListIterator<E> it = new LinkedListIterator<>();
		it.current = head;
		return it;
	}

	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException("Element is null");
		}
		append(e);
		return true;
	}

	@Override
	public boolean remove(E o) {
		if (o.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		int i = indexOf(o);
		remove(i);
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			remove(i);
			i = -1;
		}
		size = 0;
	}

	@Override
	public E get(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds");
		}
		Element<E> ret = head;
		for (int i = 0; i < index; i++) {
			ret = ret.next;
		}
		return ret.val;
	}

	@Override
	public void add(int index, E element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds");
		}
		if (element.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		if (index <= 0) {
			prepend(element);
		} else if (index >= size) {
			append(element);
		} else {
			Element<E> newElement = new Element<>();
			newElement.val = element;
			Element<E> replaceNext = head;
			for (int i = 0; i < index - 1; i++) {
				replaceNext = replaceNext.next;
			}
			newElement.next = replaceNext.next;
			replaceNext.next = newElement;
			size++;
		}
	}

	@Override
	public E remove(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds");
		}
		Element<E> ret = null;
		if (index == 0) {
			if (head == last) {
				ret = head;
				last = last.next;
				head = last;
			} else {
				ret = head;
				this.head = head.next;
			}
		} else {
			if (size == 1) {
				ret = this.last;
				this.last = null;
			} else {
				Element<E> removeNext = head;
				for (int i = 0; i < index - 1; i++) {
					removeNext = removeNext.next;
				}
				ret = removeNext.next;
				if (ret.next == last) {
					removeNext.next = last;
					removeNext.next.next = null;
				} else {
					removeNext.next = removeNext.next.next;
				}
			}
		}
		size--;
		return ret.val;
	}

	@Override
	public int indexOf(E o) {
		if (o.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		Element<E> ret = head;
		if (head.val.equals(o)) {
			return 0;
		}
		for (int i = 0; i < size; i++) {
			ret = ret.next;
			if (ret == null) {
				return -1;
			}
			if (ret.val.equals(o)) {
				return i + 1;
			}
		}
		return -1;
	}

	public void prepend(E val) {
		if (head != null) {
			Element<E> newHead = new Element<>();
			newHead.val = val;
			newHead.next = head;
			head = newHead;
		} else {
			Element<E> newHead = new Element<>();
			newHead.val = val;
			head = newHead;
			last = newHead;
		}
		size++;
	}

	public void append(E val) {
		if (last != null) {
			Element<E> newLast = new Element<>();
			newLast.val = val;
			last.next = newLast;
			last = newLast;
		} else {
			Element<E> newLast = new Element<>();
			newLast.val = val;
			head = newLast;
			last = newLast;
		}
		size++;
	}
}
