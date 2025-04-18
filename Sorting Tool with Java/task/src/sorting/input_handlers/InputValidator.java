package sorting.input_handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputValidator {

    public ValidationResult validateWithDetails(List<?> items) {
        if (items == null) {
            return new ValidationResult(false, getValidationErrors(null, ValidationErrorType.NULL_INPUT));
        }

        if (items.isEmpty()) {
            return new ValidationResult(false, getValidationErrors(null, ValidationErrorType.EMPTY_LIST));
        }

        for (Object item : items) {
            try {
                Integer.parseInt(String.valueOf(item));
            } catch (NumberFormatException e) {
                return new ValidationResult(false, getValidationErrors(item, ValidationErrorType.NON_INTEGER_VALUES));
            }
        }
        return new ValidationResult(true, Collections.emptyList());
    }

    public boolean validate(List<?> items) {
        return validateWithDetails(items).isValid();
    }

    /**
     * Returns a list of validation error messages based on the validation context.
     *
     * @param input The input that failed validation
     * @param errorType The type of validation error
     * @return A list of detailed validation error messages
     */
    public List<String> getValidationErrors(Object input, ValidationErrorType errorType) {
        List<String> errors = new ArrayList<>();

        switch (errorType) {
            case NON_INTEGER_VALUES:
                errors.add(String.format("Validation failed: List contains non-integer values. Problematic input: %s", input));
                break;
            case EMPTY_LIST:
                errors.add("Validation failed: Empty list provided.");
                break;
            case NULL_INPUT:
                errors.add("Validation failed: Null input provided.");
                break;
            default:
                errors.add("Validation failed: Unknown validation error.");
        }

        return errors;
    }

    /**
     * Enum representing different validation error types
     */
    public enum ValidationErrorType {
        NON_INTEGER_VALUES,
        EMPTY_LIST,
        NULL_INPUT
    }

    public static class ValidationResult {
        private final boolean valid;
        private final List<String> errors;

        public ValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }
    }
}
