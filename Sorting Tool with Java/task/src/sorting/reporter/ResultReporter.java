package sorting.reporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorting.data_analysis.Statistics;
import sorting.input_handlers.InputValidator;
import sorting.logger.ILogger;

import java.util.List;

public class ResultReporter {
    private static final Logger log = LoggerFactory.getLogger(ResultReporter.class);
    private ILogger logger;

    public ResultReporter(ILogger logger) {
        this.logger = logger;
    }

    public <T> void printResults(InputValidator.DataType dataType, Statistics<T> statistics) {
        switch (dataType) {
            case LONG -> {printNumberResults(statistics);}
            case LINE -> {printLineResults(statistics);}
            case WORD -> {printWordResults(statistics);}
        }
    }

    private <T> void printNumberResults(Statistics<T> statistics) {
        logger.logInfo(String.format("Total numbers: %d", statistics.getTotalElements()));
        logger.logInfo(String.format("The greatest number: %s (%d time(s), %d%%).",
                statistics.getGreatestElement(),
                statistics.getGreatestCount(),
                statistics.getPercentage()));
    }

    private <T> void printLineResults(Statistics<T> statistics) {
        logger.logInfo(String.format("Total lines: %d", statistics.getTotalElements()));
        logger.logInfo("The longest line:");
        System.out.printf("%s\n", statistics.getGreatestElement().toString());
        logger.logInfo(String.format("(%d time(s), %d%%).",
                statistics.getGreatestCount(),
                statistics.getPercentage()));
    }

    private <T> void printWordResults(Statistics<T> statistics) {
        logger.logInfo(String.format("Total words: %d", statistics.getTotalElements()));
        logger.logInfo(String.format("The longest word: %s (%d time(s), %d%%).",
                statistics.getGreatestElement(),
                statistics.getGreatestCount(),
                statistics.getPercentage()));
    }

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
