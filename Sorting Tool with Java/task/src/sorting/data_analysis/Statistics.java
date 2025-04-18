package sorting.data_analysis;

public class Statistics<T> {
    private int totalElements;
    private T greatestElement;
    private int greatestCount;
    private int percentage;

    public Statistics(int totalElements, T greatestElement, int greatestCount, int percentage) {
        this.totalElements = totalElements;
        this.greatestElement = greatestElement;
        this.greatestCount = greatestCount;
        this.percentage = percentage;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public T getGreatestElement() {
        return greatestElement;
    }

    public int getGreatestCount() {
        return greatestCount;
    }

    public int getPercentage() {
        return percentage;
    }
}
