package sorting.reporter;

import sorting.data_analysis.Statistics;
import sorting.input_handlers.InputValidator;
import sorting.logger.ILogger;

public class ResultReporter {
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
}
