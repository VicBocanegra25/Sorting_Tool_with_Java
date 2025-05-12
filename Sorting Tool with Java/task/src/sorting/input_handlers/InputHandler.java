package sorting.input_handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorting.logger.ILogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputHandler {
    private static final Logger log = LoggerFactory.getLogger(InputHandler.class);
    private final InputValidator inputValidator = new InputValidator();
    private final ILogger logger;
    private final File inputFile;
    private final InputValidator.DataType dataType;

    public InputHandler(ILogger logger, InputValidator.DataType dataType, File inputFile) {
        this.logger = logger;
        this.dataType = dataType;
        this.inputFile = inputFile;
    }

    public <T> List<T> readInput() {
        List<T> elements = new ArrayList<>();
        try (Scanner scanner = createScanner()) {
            switch (dataType) {
                case LONG -> {
                    return (List<T>) readLongInput(scanner);
                }
                case LINE -> {
                    return (List<T>) readLineInput(scanner);
                }
                case WORD -> {
                    return (List<T>) readWordInput(scanner);
                }
                default -> {
                    logger.logInfo("Unknown data type: " + dataType);
                    return elements;
                }
            }
        } catch (FileNotFoundException e) {
                logger.logError("File nto found: " + e.getMessage());
            }
        return elements;
    }

    private Scanner createScanner() throws FileNotFoundException {
        if (inputFile != null) {
            return new Scanner(inputFile);
        } else {
            return new Scanner(System.in);
        }
    }

    private List<Long> readLongInput(Scanner scanner) {
        List<Long> numbers = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) { continue; }

            String[] tokens = line.split("\\s+");
            for (String token : tokens) {
                try {
                    numbers.add(Long.parseLong(token));
                } catch (NumberFormatException e) {
                    logger.logInfo("\"" + token + "\" is not a long. It will be skipped.");
                }
            }
        }

        return numbers;
    }

    private List<String> readLineInput(Scanner scanner) {
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        return lines;
    }

    private List<String> readWordInput(Scanner scanner) {
        List<String> words = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) { continue; }

            String[] tokens = line.split("\\s+");
            words.addAll(Arrays.asList(tokens));
        }
        return words;
    }

    @Deprecated
    private void processTokensWithValidation(String[] tokens, List<Integer> numbers) {
        for (String token: tokens) {
            try {
                int number = Integer.parseInt(token);
                numbers.add(number);
            } catch (NumberFormatException e) {
                List<String> errors = inputValidator.getValidationErrors(
                        token,
                        InputValidator.ValidationErrorType.NON_INTEGER_VALUES
                );
                logger.logError(errors.get(0));
            }
        }
    }
}
