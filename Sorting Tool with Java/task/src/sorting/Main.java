package sorting;

import sorting.data_analysis.DataAnalyzer;
import sorting.data_analysis.Statistics;
import sorting.input_handlers.InputHandler;
import sorting.input_handlers.InputValidator;
import sorting.logger.ConsoleLogger;
import sorting.logger.ILogger;
import sorting.parser.CommandLineParser;
import sorting.reporter.ResultReporter;

import java.util.*;

public class Main {
    private static final ILogger logger = new ConsoleLogger();
    private static final CommandLineParser commandLineParser = new CommandLineParser();

    public static void main(final String[] args) {
        try {
            if (commandLineParser.shouldSortIntegers(args)) {
                InputHandler inputHandler = new InputHandler(logger, InputValidator.DataType.LONG);
                List<Long> numbers = inputHandler.readInput();
                // Sorting the numbers
                Collections.sort(numbers);

                // Using the ResultReporter to display results
                ResultReporter reporter = new ResultReporter(logger);
                reporter.printSortedNumbers(numbers);
            } else {
                InputValidator.DataType dataType = commandLineParser.parseDataType(args);
                // Handle input based on the data type
                InputHandler inputHandler = new InputHandler(logger, dataType);

                if (dataType == InputValidator.DataType.LONG) {
                    List<Long> numbers = inputHandler.<Long>readInput();
                    DataAnalyzer<Long> analyzer = new DataAnalyzer<>(dataType);
                    Statistics<Long> statistics = analyzer.calculateStatistics(numbers);
                    ResultReporter reporter = new ResultReporter(logger);
                    reporter.printResults(dataType, statistics);
                } else {
                    List<String> strings = inputHandler.<String>readInput();
                    DataAnalyzer<String> analyzer = new DataAnalyzer<>(dataType);
                    Statistics<String> statistics = analyzer.calculateStatistics(strings);
                    ResultReporter reporter = new ResultReporter(logger);
                    reporter.printResults(dataType, statistics);

                }
            }
        } catch (Exception e) {
            logger.logError("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
