/* Copyright (c) 2021 innoWake GmbH Germany All rights reserved. */
package exercise2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for your implementation of LinkedList.
 * 
 * The task is to make all tests contained in this class green by implementing your own linked list implementation.
 * Please create a class that implements the training.exercise3.List interface and all its methods.
 * The List interface you're going to implement is copied from java.util.List, but I removed all unnecessary methods
 * so that you can focus on the core logic of the LinkedList. 
 * 
 * If you don't know what a linked list is, please read up on that concept, but please avoid copying code from stackoverflow,
 * I wan't you to be able to write this by yourself.
 * 
 * If you need any help feel free to ask!
 */
class TestLinkedList {

	List<String> list;

	private <T> List<T> getNewListInstance() {
		return null;
	}

	@BeforeEach
	public void resetList() {
		list = getNewListInstance();
	}

	@Test
	void testAdd() {
		list.add("1");
		list.add("2");
		list.add("3");
		String actualContent = concatenateElements(list);
		assertEquals("123", actualContent);
	}

	@Test
	void testIterateThroughEmptyList() {
		String actualContent = concatenateElements(list);
		assertEquals("", actualContent);
	}

	@Test
	void testAdd1() {
		list.add("1");
		String actualContent = concatenateElements(list);
		assertEquals("1", actualContent);
	}

	@Test
	void testRemoveAtIndexFirst() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove(0);
		String actualContent = concatenateElements(list);
		assertEquals("23", actualContent);
	}

	@Test
	void testRemoveAtIndexLast() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove(2);
		String actualContent = concatenateElements(list);
		assertEquals("12", actualContent);
	}

	@Test
	void testRemoveAtIndexmiddle() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove(1);
		String actualContent = concatenateElements(list);
		assertEquals("13", actualContent);
	}

	@Test
	void testRemoveAtIndexThree() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.remove(3);
		String actualContent = concatenateElements(list);
		assertEquals("1235", actualContent);
	}

	@Test
	void testRemoveObject() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove("1");
		String actualContent = concatenateElements(list);
		assertEquals("23", actualContent);
	}

	@Test
	void testRemoveObjectAtPosition2() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove("2");
		String actualContent = concatenateElements(list);
		assertEquals("13", actualContent);
	}

	@Test
	void testRemoveObjectAtTheLastPosition() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove("3");
		String actualContent = concatenateElements(list);
		assertEquals("12", actualContent);
	}

	@Test
	void testIndexOutOfBound() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add("1");
			list.add("2");
			list.add("3");
			list.remove(4);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(-1);
		});
	}

	@Test
	void testClear() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.clear();
		String actualContent = concatenateElements(list);
		assertEquals("", actualContent);
	}

	@Test
	void testAddIndex() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.add(0, "0");
		String actualContent = concatenateElements(list);
		assertEquals("0123", actualContent);
	}

	@Test
	void testAddIndex2() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.add(2, "x");
		String actualContent = concatenateElements(list);
		assertEquals("12x3", actualContent);
	}

	@Test
	void testAddIndex0() {
		list.add("1");
		list.add("2");
		list.add("3");
		list.add(0, "x");
		String actualContent = concatenateElements(list);
		assertEquals("x123", actualContent);
	}

	@Test
	void testIndexOf() {
		list.add("1");
		list.add("2");
		list.add("3");
		int index = list.indexOf("1");
		assertEquals(0, index);
	}

	@Test
	void testIndexOfUnavailable() {
		list.add("1");
		list.add("2");
		list.add("3");
		int index = list.indexOf("4");
		assertEquals(-1, index);
	}

	@Test
	void testGet() {
		list.add("1");
		list.add("2");
		list.add("3");
		String element = list.get(2);
		assertEquals("3", element);
	}

	@Test
	void testGetAtIndex0() {
		list.add("1");
		list.add("2");
		list.add("3");
		String element = list.get(0);
		assertEquals("1", element);
	}

	@Test
	void testGetOutOfBoundExeption() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add("1");
			list.add("2");
			list.add("3");
			list.get(5);
		});
	}

	@Test
	void testContainFirst() {
		list.add("1");
		list.add("2");
		list.add("3");
		boolean contain = list.contains("2");
		assertEquals(true, contain);
	}

	@Test
	void testContainLast() {
		list.add("1");
		list.add("2");
		list.add("3");
		boolean contain = list.contains("3");
		assertEquals(true, contain);
	}

	@Test
	void testAdd10k() {
		for (int i = 0; i < 10_000; i++) {
			list.add(String.valueOf(i));
		}
		assertEquals("3", list.get(3));
		assertEquals(10000, list.size());
		assertEquals("9999", list.get(9999));
	}

	@Test
	void testIndexOutOfBounds() {
		list.add("1");
		list.add("2");
		list.add("3");
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(4);
		});
	}

	@Test
	void testWithPointClass() {
		List<Point> list = getNewListInstance();
		for (int i = 0; i < 10; i++) {
			list.add(new Point(i, i));
		}
		assertEquals(3, list.get(3).x);
		assertEquals(10, list.size());
		list.remove(9);
		assertEquals(9, list.size());
	}

	@Test
	public void testContainsNot() {
		assertFalse(list.contains("a"));
		list.add("a");
		assertTrue(list.contains("a"));
		list.remove("a");
		assertFalse(list.contains("a"));
	}

	@Test
	public void testIsEmpty() {
		assertTrue(list.isEmpty());
		list.add("a");
		assertFalse(list.isEmpty());
		list.remove(0);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testNoNexElement() {
		list.add("a");
		Iterator<String> iter = list.iterator();
		assertTrue(iter.hasNext());
		assertEquals("a", iter.next());
		assertFalse(iter.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}

	@Test
	public void testAddIndexOutOfBounds() {
		list.add(0, "a");
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(2, "b");
		});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(-1, "c");
		});
		assertEquals(1, list.size());
	}

	private String concatenateElements(final List<String> list) {
		final StringBuilder sb = new StringBuilder();

		for (String s : list) {
			sb.append(s);
		}

		return sb.toString();
	}
}
