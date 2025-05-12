package sorting.logger;

import java.io.File;

public class LoggerFactory {
    public enum LoggerType {
        CONSOLE,
        FILE
    }

    public static ILogger createLogger(LoggerType type) {
        return switch (type) {
            case CONSOLE -> new ConsoleLogger();
            case FILE -> throw new IllegalArgumentException("File logger requires additional parameters");
        };
    }

    public static <IL> ILogger createLogger(LoggerType type, File outputFile, ILogger fallbackLogger) {
        return switch (type) {
            case CONSOLE -> new ConsoleLogger();
            case FILE -> new FileLogger(outputFile, fallbackLogger);
        };
    }
}
