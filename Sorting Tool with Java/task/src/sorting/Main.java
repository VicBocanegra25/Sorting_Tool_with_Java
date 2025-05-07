package sorting;

import sorting.input_handlers.InputHandler;
import sorting.input_handlers.InputValidator;
import sorting.logger.ConsoleLogger;
import sorting.logger.ILogger;
import sorting.parser.CommandLineParser;
import sorting.parser.CommandOptions;
import sorting.reporter.ResultReporter;
import sorting.sorter.Sorter;
import sorting.sorter.SorterFactory;

import java.util.List;


public class Main {

    public static void main(final String[] args) {
        // Initialize the logger
        ILogger logger = new ConsoleLogger();

        // Parse command line arguments
        CommandLineParser parser = new CommandLineParser(logger);
        CommandOptions options = parser.parseOptions(args);

        // Read input based on the specified data type
        InputHandler inputHandler = new InputHandler(logger, options.getDataType());
        List<?> inputData = inputHandler.readInput();

        // Using a factory to create the appropriate sorter based on the options
        Sorter<?> sorter = SorterFactory.createSorter(options.getSortingType());
        List<?> sortedData = sorter.sort(inputData);

        // Report the results
        ResultReporter reporter = new ResultReporter(logger);
        reporter.printResults(options.getDataType(), options.getSortingType(), sortedData);
    }

}
