package sorting.sorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy implementation that sorts elements based on their frequency (count) in the list.
 * Elements with higher frequency appear first.
 * @params <T> the type of element to be sorted
 */
public class CountBasedSortStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    /**
     * Sorts the input list based on element frequency (most frequent first).
     * @param input List of elements to be sorted
     * @return A new list with elements sorted by frequency
     */
    public List<T> sort(List<T> input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }

        // Count frequencies of each element
        Map<T, Integer> frequencyMap = new HashMap<>();

        for (T element : input) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }

        // Get unique elements
        List<T> uniqueElements = new ArrayList<>(frequencyMap.keySet());

        // Sort elements by:
        // 1. Frequency (ascending)
        // 2. Natural order (ascending) when frequencies are the same
        uniqueElements.sort((a, b) -> {
            int freqCompare = frequencyMap.get(a).compareTo(frequencyMap.get(b));
            if (freqCompare == 0) {
                return a.compareTo(b); // Natural ordering
            }
            return freqCompare;
        });

        // Create a new list with duplicates preserved according to frequency
        List<T> result = new ArrayList<>();
        for (T element : uniqueElements) {
            int count = frequencyMap.get(element);
            for (int i = 0; i < count; i++) {
                result.add(element);
            }
        }
        return result;
    }

}
