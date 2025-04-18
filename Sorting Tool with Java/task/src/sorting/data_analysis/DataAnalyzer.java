package sorting.data_analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAnalyzer {

    public Statistics calculateStatistics(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new Statistics(0, 0, 0);
        }

        int totalNumbers = numbers.size();
        int greatestNumber = numbers.get(0);
        int greatestCount = 1;

        // Using a HashMap to count the occurrences of each number
        Map<Integer, Integer> countMap = new HashMap<>();

        // Looping through the numbers
        for (int number : numbers) {
            // Update the count, add the number if not added yet
            countMap.put(number, countMap.getOrDefault(number, 0) + 1);

            // Update the greatest number if we find a larger value
            if (number > greatestNumber) {
                greatestNumber = number;
            }
        }

        // Getting the count for the greatest number
        greatestCount = countMap.get(greatestNumber);

        return new Statistics(totalNumbers, greatestNumber, greatestCount);
    }
}
