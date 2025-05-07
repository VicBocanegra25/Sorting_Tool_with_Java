package sorting.sorter;

import java.util.List;

/**
 * Strategy: common interface for all sorting strategies (Natural or Count-Based)
 * @param <T> The type of elements to be sorted
 */

public interface SortingStrategy<T> {
    /**
     * Sorts the given input according to the strategy's algorithm
     * @param input List of elements to be sorted
     * @return Sorted list according to the strategy's rules
     */
    List<T> sort(List<T> input);
}
