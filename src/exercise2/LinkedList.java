package exercise2;

import java.lang.reflect.Array;
import java.util.Iterator;

public class LinkedList<E> implements List<E> {

	int size;
	@SuppressWarnings("unchecked")
	E[] arr = (E[]) new Object[size];

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
	public boolean contains(Object o) {
		if (o.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		try {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].equals(o)) {
					return true;
				}
			}
		} catch (ClassCastException e) {
			System.out.println("Element is incompatible with this list.");
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator(arr);
	}

	@Override
	public boolean add(E e) {
		if (e.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		if (size == 0) {
			@SuppressWarnings("unchecked")
			E[] arr3 = (E[]) new Object[1];
			arr3[0] = e;
		}
		@SuppressWarnings("unchecked")
		E[] arr2 = (E[]) new Object[size + 1];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = arr[i];
		}
		arr2[arr.length + 1] = e;
		arr = arr2;
		size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (o.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		for (int i = 0; i < size; i++) {
			if (arr[i].equals(o)) {
				remove(i);
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		size = 0;
		E[] arr2 = null;
		arr = arr2;
	}

	@Override
	public E get(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds");
		}
		return arr[index];
	}

	@Override
	public void add(int index, E element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds");
		}
		if (element.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		@SuppressWarnings("unchecked")
		E[] arr2 = (E[]) new Object[size + 1];
		for (int i = 0; i < index; i++) {
			arr2[i] = arr[i];
		}
		arr[index] = element;
		for (int j = index; j < arr.length; j++) {
			arr2[j + 1] = arr[j];
		}
		arr = arr2;
		size++;
	}

	@Override
	public E remove(int index) {
		E ret = arr[index];
		@SuppressWarnings("unchecked")
		E[] arr2 = (E[]) new Object[size + 1];
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds");
		}
		for (int j = 0; j < index; j++) {
			arr2[j] = arr[j];
		}
		for (int i = index; i < size - 1; i++) {
			arr2[i] = arr[i + 1];
		}
		arr = arr2;
		size--;
		return ret;
	}

	@Override
	public int indexOf(Object o) {
		if (o.equals(null)) {
			throw new NullPointerException("Element is null");
		}
		for (int i = 0; i < size; i++) {
			if (arr[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

}
