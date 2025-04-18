package sorting.data_analysis;

public class Statistics {
    private int totalNumbers;
    private int greatestNumber;
    private int greatestCount;

    public Statistics(int totalNumbers, int greatestNumber, int greatestCount) {
        this.totalNumbers = totalNumbers;
        this.greatestNumber = greatestNumber;
        this.greatestCount = greatestCount;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public int getGreatestNumber() {
        return greatestNumber;
    }

    public int getGreatestCount() {
        return greatestCount;
    }

}
