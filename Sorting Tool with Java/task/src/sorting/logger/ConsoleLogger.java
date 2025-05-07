package sorting.logger;

public class ConsoleLogger implements ILogger {
    
    @Override
    public void logInfo(String message) {
        System.out.println("" + message);
    }
    
    @Override
    public void logError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
