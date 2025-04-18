package sorting.reporter;

import sorting.data_analysis.Statistics;

public class ResultReporter {

    public void printResults(Statistics statistics) {
        System.out.printf("Total numbers: %d.\n", statistics.getTotalNumbers());
        System.out.printf("The greatest number: %d (%d time(s)).\n",
                statistics.getGreatestNumber(),
                statistics.getGreatestCount());
    }
}
