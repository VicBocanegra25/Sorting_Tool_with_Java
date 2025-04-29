package sorting.parser;


import sorting.input_handlers.InputValidator;

public class CommandLineParser {
    public boolean shouldSortIntegers(String[] args) {
        // Safety check and looking if the flag is present
        for (String arg : args) {
            if (arg.equals("-sortIntegers")) {
                return true;
            }
        }
        return false;
    }


        public InputValidator.DataType parseDataType(String[] args) {
        // The default type is Word if the option is not specified
        InputValidator.DataType dataType = InputValidator.DataType.WORD;

        // Checking the arguments using a for loop
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-dataType")) {
                String typeArg = args[i + 1].toLowerCase();
                switch (typeArg) {
                    case "long" -> dataType = InputValidator.DataType.LONG;
                    case "line" -> dataType = InputValidator.DataType.LINE;
                    case "word" -> dataType = InputValidator.DataType.WORD;
                    default -> dataType = null;
                }
            }
            break;
        }
        return dataType;
    }
}
