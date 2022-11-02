/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise3;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides different utility lambdas for different use cases.
 */
public interface LambdaProvider {
	
	/**
	 * Returns a Function that takes a predicate and returns the elements of {@code listToFilter} that match that predicate.
	 *
	 * @param <R> the type contained in the List
	 * @param listToFilter the list of which a subset is returned upon calling the here returned Function.
	 * @return a Function that filters the list according to the Predicate passed to the function.
	 */
	public <R> Function<Predicate<R>, List<R>> matchingElementsProvider(List<R> listToFilter);
	
	
	/**
	 * Creates a Consumer that removes the elements from the mentioned list if the Predicate matches.
	 *
	 * @param <R> the list type
	 * @param listToRemoveFrom list to remove the elements from
	 * @return a consumer that consumes predicates that decide what shall be removed from the list.
	 */
	public <R> Consumer<Predicate<R>> removeMatchingElementsProvider(List<R> listToRemoveFrom);
}