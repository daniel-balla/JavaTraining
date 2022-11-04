/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise3;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Tests the LambdaProvider Implementation.
 * 
 * Please implement the LambdaProvier interface and make these tests green :)
 */
class TestLambdaProvider {
	
	private final LambdaProvider provider = new Lambda();
	/* No code modifications needed below */
	@Test
	void testMatchingElementsProvider() {
		List<String> elements = Arrays.asList("a", "ab", "ba");
		Function<Predicate<String>, List<String>> lambda = provider.matchingElementsProvider(elements);
		assertEquals(2, lambda.apply(x -> x.startsWith("a")).size());
		assertEquals(2, lambda.apply(x -> x.length() == 2).size());
		assertEquals(1, lambda.apply(x -> x.length() == 1).size());
	}

	@Test
	public void testRemovingMatchingElementsProvider() {
		List<String> elements = Stream.of("a", "ab", "ba").collect(Collectors.toList());
		Consumer<Predicate<String>> lambda = provider.removeMatchingElementsProvider(elements);
		assertEquals(3, elements.size());
		lambda.accept(x -> x.startsWith("b"));
		assertEquals(2, elements.size());
		lambda.accept(x -> x.length() == 2);
		assertEquals(1, elements.size());
		lambda.accept(x -> x.length() == 1);
		assertEquals(0, elements.size());
	}
}
