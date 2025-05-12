package sorting;

import sorting.input_handlers.InputHandler;
import sorting.input_handlers.InputValidator;
import sorting.logger.ConsoleLogger;
import sorting.logger.FileLogger;
import sorting.logger.ILogger;
import sorting.logger.LoggerFactory;
import sorting.parser.CommandLineParser;
import sorting.parser.CommandOptions;
import sorting.reporter.ResultReporter;
import sorting.sorter.Sorter;
import sorting.sorter.SorterFactory;

import java.util.List;


public class Main {

    public static void main(final String[] args) {
        // Initialize the console logger
        ILogger consoleLogger = LoggerFactory.createLogger(LoggerFactory.LoggerType.CONSOLE);

        // Parse command line arguments
        CommandLineParser parser = new CommandLineParser(consoleLogger);
        CommandOptions options = parser.parseOptions(args);

        // Determine the logger to use based on the output file
        ILogger logger;
        if (options.getOutputFile() != null) {
            logger = LoggerFactory.createLogger(LoggerFactory.LoggerType.FILE, options.getOutputFile(), consoleLogger);
        } else {
            logger = consoleLogger;
        }

        // Read input based on the specified data type
        InputHandler inputHandler = new InputHandler(logger, options.getDataType(), options.getInputFile());
        List<?> inputData = inputHandler.readInput();

        // Using a factory to create the appropriate sorter based on the options
        Sorter<?> sorter = SorterFactory.createSorter(options.getSortingType());
        List<?> sortedData = sorter.sort(inputData);

        // Report the results
        ResultReporter reporter = new ResultReporter(logger);
        reporter.printResults(options.getDataType(), options.getSortingType(), sortedData);
    }

}
