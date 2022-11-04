package exercise3;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda implements LambdaProvider {

	@Override
	public <R> Function<Predicate<R>, List<R>> matchingElementsProvider(List<R> listToFilter) {
		Function<Predicate<R>, List<R>> lambda;
		lambda = a -> {
			List<R> ret;
			ret = listToFilter.stream().filter(a).toList();
			return ret;
		};
		return lambda;
	}

	@Override
	public <R> Consumer<Predicate<R>> removeMatchingElementsProvider(List<R> listToRemoveFrom) {
		Consumer<Predicate<R>> lambda;
		lambda = a -> {
			List<R> ret;
			ret = listToRemoveFrom.stream().filter(a.negate()).toList();
			listToRemoveFrom.clear();
			for (R r : ret) {
				listToRemoveFrom.add(r);
			}
		};

		return lambda;
	}
}
