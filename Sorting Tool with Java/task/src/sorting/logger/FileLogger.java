package sorting.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger implements ILogger{
    private final File outputFile;
    private final ILogger consoleLogger;

    public FileLogger(File outputFile, ILogger consoleLogger) {
        this.outputFile = outputFile;
        this.consoleLogger = consoleLogger;
    }

    @Override
    public void logInfo(String message) {
        // The outputs go to a file using the PrintWriter class for format.
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true))) {
            writer.println(message);
        } catch (IOException e) {
            consoleLogger.logError("Error writing to output file: " + e.getMessage());
        }
    }

    @Override
    public void logError(String message) {
        consoleLogger.logError(message);
    }
}
