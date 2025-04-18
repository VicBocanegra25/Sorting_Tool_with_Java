package sorting.input_handlers;

import sorting.logger.ILogger;

import java.util.*;

public class InputHandler {
    private final InputValidator inputValidator = new InputValidator();
    private final ILogger logger;
    private final InputValidator.DataType dataType;

    public InputHandler(ILogger logger, InputValidator.DataType dataType) {
        this.logger = logger;
        this.dataType = dataType;
    }

    public <T> List<T> readInput() {
        Scanner scanner = new Scanner(System.in);
        List<T> elements = new ArrayList<>();
        
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
                return elements;
            }
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
                    logger.logError("\"" + token + "\" is not a valid number.");
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
