package exercise3;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda implements LambdaProvider {

	@Override
	public <R> Function<Predicate<R>, List<R>> matchingElementsProvider(List<R> listToFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R> Consumer<Predicate<R>> removeMatchingElementsProvider(List<R> listToRemoveFrom) {
		// TODO Auto-generated method stub
		return null;
	}

}
