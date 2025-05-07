package sorting.sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Strategy implementation that sorts elements in their natural order.
 * @param <T> The type of elements to be sorted, must be Comparable
 */
public class NaturalSortStrategy <T extends Comparable<T>> implements SortingStrategy<T> {

    /**
     * Sorts the input list using natural ordering of elements
     * @param input List of elements to be sorted
     * @return A new list with elements sorted in their natural order
     */
    @Override
    public List<T> sort(List<T> input) {
        // Safety check for null or empty list
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }

        // Creating a copy of the input list to avoid modifying the input in place
        List<T> sortedList = new ArrayList<>(input);
        // Sorting the list using the Collections sorting method
        Collections.sort(sortedList);
        return sortedList;
    }
}
