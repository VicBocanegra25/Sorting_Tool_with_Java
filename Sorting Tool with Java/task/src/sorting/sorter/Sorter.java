package sorting.sorter;

import java.util.List;

/**
 * Context class - delegates sorting to whichever strategy is supplied
 * @param <T> The type of elements to be sorted
 */
public class Sorter<T> {
    // The strategy can change dynamically
    private SortingStrategy<T> strategy;

    /**
     * Creates a sorter with the given strategy
     * @param strategy The sorting strategy to use
     */
    public Sorter(SortingStrategy<T> strategy) {
        this.strategy = strategy;
    }

    /**
     * Changes the sorting strategy at runtime
     * @param strategy The new sorting strategy to use
     */
    public void setStrategy(SortingStrategy<T> strategy) {
        this.strategy = strategy;
    }

    /**
     * Delegates the sorting to the current strategy.
     * @param input: List of elements to be sorted
     * @return: Sorted list according to the current strategy's rules
     */
    public List<T> sort(List<?> input) {
        return strategy.sort((List<T>) input);
    }

}
