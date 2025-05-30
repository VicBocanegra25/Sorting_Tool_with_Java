package sorting.parser;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorting.input_handlers.InputValidator;
import sorting.logger.ILogger;

import java.io.File;
import java.io.FileNotFoundException;

public class CommandLineParser {
    private static final String DATA_TYPE_FLAG = "-dataType";
    private static final String SORTING_TYPE_FLAG = "-sortingType";
    private static final String INPUT_FILE_FLAG = "-inputFile";
    private static final String OUTPUT_FILE_FLAG = "-outputFile";
    private final ILogger logger;

    public CommandLineParser(ILogger logger) {
        this.logger = logger;
    }

    @Deprecated
    public boolean shouldSortIntegers(String[] args) {
        // Safety check and looking if the flag is present
        for (String arg : args) {
            if (arg.equals("-sortIntegers")) {
                return true;
            }
        }
        return false;
    }

    private InputValidator.DataType determineDataType(String typeArg) {
        return switch (typeArg.toLowerCase()) {
            case "long" -> InputValidator.DataType.LONG;
            case "line" -> InputValidator.DataType.LINE;
            case "word" -> InputValidator.DataType.WORD;
            default -> null;
        };
    }

    private SortingType determineSortingType(String typeArg) {
        return switch (typeArg.toLowerCase()) {
            case "natural" -> SortingType.NATURAL;
            case "bycount" -> SortingType.BY_COUNT;
            default -> null;
        };
    }

    public CommandOptions parseOptions(String[] args) {
        // The default type is Natural ordering if the option is not specified
        SortingType sortingType = SortingType.NATURAL;
        // The default type is Word if the option is not specified
        InputValidator.DataType dataType = InputValidator.DataType.WORD;
        // Handling the input and output files (null by default)
        File inputFile = null;
        File outputFile = null;

        // Iterate through the args using a for loop
        for (int i = 0; i < args.length; i++) {
            String currentArg = args[i];

            if (currentArg.equals(DATA_TYPE_FLAG)) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    InputValidator.DataType determinedType = determineDataType(args[i + 1]);
                    if (determinedType != null) {
                        dataType = determinedType;
                    } else {
                        logger.logInfo("Unknown data type: " + args[i + 1]);
                    }
                    i++;
                } else {
                    logger.logError("No data type defined!");
                }
            } else if (currentArg.equals(SORTING_TYPE_FLAG)) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    SortingType determinedType = determineSortingType(args[i + 1]);
                    if (determinedType != null) {
                        sortingType = determinedType;
                    } else {
                        logger.logInfo("Unknown sorting type: " + args[i + 1]);
                    }
                    i++;
                } else {
                    logger.logInfo("No sorting type defined!");
                }
                // It does include an option, but it is not among the valid options
            } else if (currentArg.equals(INPUT_FILE_FLAG)) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    try  {
                        inputFile = new File(args[i + 1]);
                        if (!inputFile.exists()) {
                            logger.logError("Input file not found: " + args[i + 1]);
                            inputFile = null;
                        }
                    } catch (Exception fe) {
                        logger.logInfo("Error processing input file" + fe.getMessage());
                    }
                    i++;
                } else {
                    logger.logInfo("No input file defined!");
                }
            } else if (currentArg.equals(OUTPUT_FILE_FLAG)) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    try  {
                        outputFile = new File(args[i + 1]);
                    } catch (Exception fe) {
                        logger.logInfo("Error processing output file" + fe.getMessage());
                    }
                    i++;
                } else {
                    logger.logInfo("No output file defined!");
                }
            } else if (currentArg.startsWith("-")) {
                logger.logInfo("\"" + currentArg + "\" is not a valid parameter. It will be skipped.");
            }
        }
        return new CommandOptions(sortingType, dataType, inputFile, outputFile);
    }
}
