/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

class TestCollections {

	private final Collection<String> withUniqueElements = new LinkedHashSet<String>();
	private final Collection<String> preservingInsertionOrder = new ArrayList<String>();
	private final Collection<Integer> notPreservingInsertionOrder = new HashSet<Integer>();
	private final Collection<String> uniqueValuesPreservingInsertionOrder = new LinkedHashSet<String>();
	private final Collection<String> uniqueElementsSelfOrdering = new TreeSet<String>();
	
	@Test
	void testUniqueElements() {
		withUniqueElements.clear();
		withUniqueElements.add("1");
		withUniqueElements.add("2");
		withUniqueElements.add("3");
		withUniqueElements.add("1");
		assertEquals(3, withUniqueElements.size());
	}

	@Test
	void testSameOrder() {
		preservingInsertionOrder.clear();
		preservingInsertionOrder.add("1");
		preservingInsertionOrder.add("2");
		preservingInsertionOrder.add("3");
		
		String result = "";
		for (final String string : preservingInsertionOrder) {
			result += string;
		}
		
		assertEquals("123", result);
	}
	
	@Test
	void testOtherOrder() {
		notPreservingInsertionOrder.clear();
		notPreservingInsertionOrder.add(3);
		notPreservingInsertionOrder.add(2);
		notPreservingInsertionOrder.add(1);
		
		String result = "";
		for (final Integer i : notPreservingInsertionOrder) {
			result += i;
		}
		
		/* Insertion Order was 321, but we expect order by hashcode. */
		assertEquals("123", result);
	}
	
	@Test
	void testUniqueElementsInOrder() {
		uniqueValuesPreservingInsertionOrder.clear();
		uniqueValuesPreservingInsertionOrder.add("3");
		uniqueValuesPreservingInsertionOrder.add("2");
		uniqueValuesPreservingInsertionOrder.add("1");
		uniqueValuesPreservingInsertionOrder.add("2");
		uniqueValuesPreservingInsertionOrder.add("3");
		
		String result = "";
		for (final String string : uniqueValuesPreservingInsertionOrder) {
			result += string;
		}
		
		assertEquals("321", result);
	}
	
	@Test
	void testUniqueSelfOrdering() {
		uniqueElementsSelfOrdering.clear();
		uniqueElementsSelfOrdering.add("5");
		uniqueElementsSelfOrdering.add("4");
		uniqueElementsSelfOrdering.add("1");
		uniqueElementsSelfOrdering.add("2");
		uniqueElementsSelfOrdering.add("3");
		
		String result = "";
		for (final String string : uniqueElementsSelfOrdering) {
			result += string;
		}
		
		assertEquals("12345", result);
	}
}
