package sorting.data_analysis;

import sorting.input_handlers.InputValidator;
import java.util.List;

@Deprecated
public class DataAnalyzer<T> {
    private final InputValidator.DataType dataType;

    public DataAnalyzer(InputValidator.DataType dataType) {
        this.dataType = dataType;
    }

    public Statistics<T> calculateStatistics(List<T> elements) {
        if (elements == null || elements.isEmpty()) {
            return new Statistics<>(0, null, 0, 0);
        }

        int totalElements = elements.size();
        T greatest = null;

        // Find the greatest element based on data type
        if (dataType == InputValidator.DataType.LONG) {
            // For number type, find maximum value
            greatest = findMaxNumber(elements);
        } else {
            // For string types, find the longest string
            greatest = findLongestString(elements);
        }

        // Count occurrences of the greatest element
        int greatestCount = 0;
        for (T element : elements) {
            if (element.equals(greatest)) {
                greatestCount++;
            }
        }

        // Calculate percentage
        int percentage = (int) Math.round((double) greatestCount / totalElements * 100);

        return new Statistics<>(totalElements, greatest, greatestCount, percentage);
    }


    @SuppressWarnings("unchecked")
    private T findMaxNumber(List<T> elements) {
        // Safe because we know the list contains numbers when dataType is LONG
        T max = elements.get(0);
        for (T element : elements) {
            long current = Long.parseLong(element.toString());
            long maxValue = Long.parseLong(max.toString());
            if (current > maxValue) {
                max = element;
            }
        }
        return max;
    }


    @SuppressWarnings("unchecked")
    private T findLongestString(List<T> elements) {
        // Find the longest string
        T longest = elements.get(0);
        int maxLength = longest.toString().length();

        for (T element : elements) {
            int currentLength = element.toString().length();
            if (currentLength > maxLength) {
                longest = element;
                maxLength = currentLength;
            } else if (currentLength == maxLength) {
                // If same length, compare lexicographically
                if (element.toString().compareTo(longest.toString()) < 0) {
                    longest = element;
                }
            }
        }

        return longest;
    }
}