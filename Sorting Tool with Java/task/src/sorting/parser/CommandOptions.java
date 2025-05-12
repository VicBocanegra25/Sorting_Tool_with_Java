package sorting.parser;

import sorting.input_handlers.InputValidator;

import java.io.File;

public class CommandOptions {
    private SortingType sortingType;
    private InputValidator.DataType dataType;
    private File inputFile;
    private File outputFile;

    public CommandOptions(SortingType sortingType, InputValidator.DataType dataType) {
        this.sortingType = sortingType;
        this.dataType = dataType;
    }

    public CommandOptions(SortingType sortingType, InputValidator.DataType dataType, File inputFile, File outputFile) {
        this.sortingType = sortingType;
        this.dataType = dataType;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
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

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
}
