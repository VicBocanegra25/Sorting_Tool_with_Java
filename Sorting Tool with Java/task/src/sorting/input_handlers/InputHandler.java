package sorting.input_handlers;

import sorting.logger.ILogger;

import java.util.*;

public class InputHandler {
    private final InputValidator inputValidator = new InputValidator();
    private final ILogger logger;

    public InputHandler(ILogger logger) {
        this.logger = logger;
    }

    public List<Integer> readInput() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();

        while (scanner.hasNextLine()) {
           String line = scanner.nextLine().trim();
            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }
            // Split the line into tokens
            String[] tokens = line.split("\\s+");
            List<Object> tokenObjects = new ArrayList<>();
            for (String token: tokens) {
                tokenObjects.add(token);
            }

            // Using the validator to check the input
            if (!inputValidator.validate(tokenObjects)) {
                // Process each token and report specific errors
                processTokensWithValidation(tokens, numbers);
            } else {
               // Add all valid integers
                for (String token: tokens) {
                    numbers.add(Integer.parseInt(token));
                }
            }
        }

    return numbers;
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
