package sorting;

import sorting.data_analysis.DataAnalyzer;
import sorting.data_analysis.Statistics;
import sorting.input_handlers.InputHandler;
import sorting.logger.ConsoleLogger;
import sorting.logger.ILogger;
import sorting.reporter.ResultReporter;

import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static final DataAnalyzer dataAnalyzer = new DataAnalyzer();
    private static final ResultReporter resultReporter = new ResultReporter();
    private static final ILogger logger = new ConsoleLogger();
    private static final InputHandler inputHandler = new InputHandler(logger);

    public static void main(final String[] args) {
        try {
            // Reading input from the console
            logger.logInfo("Reading input...");
            List<Integer> numbers = inputHandler.readInput();

            // Calculate statistics
            logger.logInfo("Calculating statistics...");
            Statistics statistics = dataAnalyzer.calculateStatistics(numbers);

            // Print results
            logger.logInfo("Printing results...");
            resultReporter.printResults(statistics);
        } catch (Exception e) {
            logger.logError("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
