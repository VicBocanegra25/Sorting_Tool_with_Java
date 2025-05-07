package sorting.reporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorting.data_analysis.Statistics;
import sorting.input_handlers.InputValidator;
import sorting.logger.ILogger;
import sorting.parser.SortingType;

import java.util.*;

public class ResultReporter {
    private static final Logger log = LoggerFactory.getLogger(ResultReporter.class);
    private ILogger logger;

    public ResultReporter(ILogger logger) {
        this.logger = logger;
    }

    /**
     * Print the results based on the data type and sorting type
     * @param dataType The type of data being processed
     * @param sortingType The type of sorting applied
     * @param data the sorted data to be reported
     * @param <T> The type of elements in the data
     */
    public <T> void printResults(InputValidator.DataType dataType, SortingType sortingType,
                                 List<T> data) {
        // Print the total count first
        printTotalCount(dataType, data.size());

        // Then print the sorted data based on the sorting type
        if (sortingType == SortingType.NATURAL) {
            printNaturallySorted(dataType, data);
        } else if (sortingType == SortingType.BY_COUNT) {
            printSortedByCount(dataType, data);
        }
    }

    /**
     * Print the total count of elements
     *
     * @param dataType The type of data
     * @param count The total count
     */
    private void printTotalCount(InputValidator.DataType dataType, int count) {
        switch(dataType) {
            case LONG -> logger.logInfo(String.format("Total numbers %d.", count));
            case WORD -> logger.logInfo(String.format("Total words: %d.", count));
            case LINE -> logger.logInfo(String.format("Total lines: %d", count));
        }
    }

    /**
     * Print naturally sorted data
     *
     * @param dataType The type of data
     * @param data The sorted data
     * @param <T> The type of elements
     */
    private <T> void printNaturallySorted(InputValidator.DataType dataType, List<T> data) {
        // For lines, print each line on a new line
        if (dataType == InputValidator.DataType.LINE) {
            logger.logInfo("Sorted data:");
            for (T line : data) {
                System.out.println(line.toString());
            }
        } else {
            StringBuilder sortedData = new StringBuilder("Sorted data: ");
            for (T element : data) {
                sortedData.append(element).append(" ");
            }
            // Remove the trailing space if there are elements
            if (!data.isEmpty()) {
                sortedData.setLength(sortedData.length() - 1);
            }
            logger.logInfo(sortedData.toString());
        }
    }

    /**
     * Print data sorted by count (frequency)
     * Assumes data has already been sorted by the CountBasedSortStrategy
     *
     * @param dataType The type of data
     * @param sortedData The data already sorted by count
     * @param <T> The type of elements
     */
    private <T> void printSortedByCount(InputValidator.DataType dataType, List<T> sortedData) {
        // Calculate frequency of each element
        Map<T, Integer> frequencyMap = getFrequencyMap(sortedData);

        // Preparing a set of processed elements
        Set<T> processedElements = new HashSet<>();
        int totalElements = sortedData.size();

        // Looping through the data and printing it
        for (T element : sortedData) {
            // Printing each unique element once
            if (!processedElements.contains(element)) {
                processedElements.add(element);
                int count = frequencyMap.get(element);
                int percentage = (count * 100) / totalElements;

                if (dataType == InputValidator.DataType.LINE) {
                    // For lines, print the element on its own before the count info
                    System.out.printf("%s: ", element);
                    logger.logInfo(String.format("%d time(s), %d%%", count, percentage));
                } else {
                    // For numbers and words, print the element, count, and percentage
                    logger.logInfo(String.format("%s: %d time(s), %d%%", element, count, percentage));
                }
            }
        }
    }

    /**
     * Calculate a frequency map from a list of elements
     *
     * @param data The list of elements
     * @param <T> The type of elements
     * @return A map of elements to their frequencies
     */
    private <T> Map<T, Integer> getFrequencyMap(List<T> data) {
        Map<T, Integer> frequencyMap = new HashMap<>();
        for (T element : data) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }
        return  frequencyMap;
    }

    @Deprecated
    private <T> void printNumberResults(Statistics<T> statistics) {
        logger.logInfo(String.format("Total numbers: %d", statistics.getTotalElements()));
        logger.logInfo(String.format("The greatest number: %s (%d time(s), %d%%).",
                statistics.getGreatestElement(),
                statistics.getGreatestCount(),
                statistics.getPercentage()));
    }

    @Deprecated
    private <T> void printLineResults(Statistics<T> statistics) {
        logger.logInfo(String.format("Total lines: %d", statistics.getTotalElements()));
        logger.logInfo("The longest line:");
        System.out.printf("%s\n", statistics.getGreatestElement().toString());
        logger.logInfo(String.format("(%d time(s), %d%%).",
                statistics.getGreatestCount(),
                statistics.getPercentage()));
    }

    @Deprecated
    private <T> void printWordResults(Statistics<T> statistics) {
        logger.logInfo(String.format("Total words: %d", statistics.getTotalElements()));
        logger.logInfo(String.format("The longest word: %s (%d time(s), %d%%).",
                statistics.getGreatestElement(),
                statistics.getGreatestCount(),
                statistics.getPercentage()));
    }

    @Deprecated
    public void printSortedNumbers(List<Long> numbers) {
        logger.logInfo(String.format("Total numbers: %d.", numbers.size()));

        // Format the sorted numbers as a space-separated string
        StringBuilder sortedNumbers = new StringBuilder("Sorted data: ");
        for (Long number : numbers) {
            sortedNumbers.append(number).append(" ");
        }
        // Remove trailing spaces
        if (numbers.size() > 0) {
            sortedNumbers.setLength(sortedNumbers.length() - 1);
        }
        logger.logInfo(sortedNumbers.toString());
    }

}
