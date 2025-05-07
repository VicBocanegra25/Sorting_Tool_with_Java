package sorting.parser;

import sorting.input_handlers.InputValidator;

public class CommandOptions {
    private SortingType sortingType;
    private InputValidator.DataType dataType;

    public CommandOptions(SortingType sortingType, InputValidator.DataType dataType) {
        this.sortingType = sortingType;
        this.dataType = dataType;
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public void setSortingType(SortingType sortingType) {
        this.sortingType = sortingType;
    }

    public InputValidator.DataType getDataType() {
        return dataType;
    }

    public void setDataType(InputValidator.DataType dataType) {
        this.dataType = dataType;
    }
}
