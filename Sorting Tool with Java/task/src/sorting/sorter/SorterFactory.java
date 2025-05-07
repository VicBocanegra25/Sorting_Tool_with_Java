package sorting.sorter;

import sorting.parser.SortingType;

// Factory class for creating sorters
public class SorterFactory {
    public static <T extends Comparable<T>> Sorter<T> createSorter(SortingType sortingType) {
        SortingStrategy<T> sortingStrategy;

        if (sortingType == SortingType.NATURAL) {
            sortingStrategy = new NaturalSortStrategy<>();
        } else {
            sortingStrategy = new CountBasedSortStrategy<>();
        }
        return new Sorter<>(sortingStrategy);
    }
}
