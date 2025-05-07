Description
Now that it’s possible to sort numbers, it's time to implement the same functionality for words and lines. But that's not all for this stage! Let's also add a new sorting type that is often rather useful: sorting by count, a type of sorting that arranges the elements by number of occurrences.

Instead of -sortIntegers, we will use the universal -sortingType argument.

The result of sorting by count should be pairs (count, dataEntry) sorted by the count value.

Note that from this stage on, your program focuses on sorting user data, so it will no longer output information about the greatest number or the longest string.

Objectives
Update the parsing of command-line arguments to support the sorting type definition:

if the -sortingType argument is provided, it should be followed by natural or byCount, which defines the sorting type.
if the argument is not provided, then consider natural to be the default sorting type.
Implement natural sorting for words and lines, and sorting by count for all data types. Within the group, elements with equal count values should be sorted naturally.

Note: for strings (words and lines), natural order is lexicographic order, for numbers it is numeric order.

Print the line containing the total number m of elements in the input: Total ELEMENTS: m., where ELEMENTS can be numbers, words, or lines, depending on the input. Then output the sorting results:

for natural sorting of numbers or words, print elements on one line in ascending order:
Sorted data: el_1 el_2 ... el_m

for natural sorting of lines, print lexicographically sorted elements each on a new line:
Sorted data:
line_1
line_2
…
line_m

for sorting by count, print elements sorted by the number of occurrences, each on a new line, using the following format:
element: count time(s), percentage%

Here, element is a number, word, or line, count is the number of times it appears in the input, and percentage is the proportion of the count to the total number of elements in the input, given as a percentage.
Examples
Run configuration examples:

java SortingTool -sortingType natural -dataType long

java SortingTool -dataType word -sortingType byCount

Run examples

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

Example 1, for sorting longs by count:

> 1 -2   33 4
> 42
> 1                 1
Total numbers: 7.
-2: 1 time(s), 14%
4: 1 time(s), 14%
33: 1 time(s), 14%
42: 1 time(s), 14%
1: 3 time(s), 43%

Example 2, for sorting numbers naturally:

> 1 -2   33 4
> 42
> 1                 1
Total numbers: 7.
Sorted data: -2 1 1 1 4 33 42

Example 3, for sorting words naturally:

> 1 -2   33 4
> 42
> 1                 1
Total words: 7.
Sorted data: -2 1 1 1 33 4 42

Example 4, for sorting lines naturally:

> 1 -2   33 4
> 42
> 1                 1
Total lines: 3
Sorted data:
1                 1
1 -2   33 4
42

# Strategy
1 Command-line layer
Item	Action	Notes
1.1 Enum	enum SortingType { NATURAL, BY_COUNT }	One source of truth for allowed values.
1.2 Parser output DTO	Introduce class CommandOptions { SortingType sortingType; InputValidator.DataType dataType; }	Makes it painless to add more flags later.
1.3 CommandLineParser	• Remove shouldSortIntegers.
• Add parseOptions(String[] args) → CommandOptions.	Rules
• default sortingType = NATURAL if -sortingType missing.
• default dataType = WORD if -dataType missing.
• When an option appears without a following value, log an error and exit.
1.4 Error handling	Keep your existing logging pattern: un-recognised keys are ignored with a warning; missing values are fatal.	Stage doc calls for graceful handling, not termination on unknown keys.
2 Domain model changes
2.1 No more “greatest/longest” statistics
DataAnalyzer and Statistics were created only for Stage #2.
You can delete them or mark them deprecated; Stage #4 never asks for “greatest” again.

2.2 Two new sort strategies
Class	Responsibility
NaturalSorter<T>	Returns the input in natural order (Collections.sort).
CountSorter<T>	Builds Map<T,Integer>, sorts Map.Entry list by count, then by key (natural), and calculates % = count * 100 / total.
Implementation tip

java
Copy code
class NaturalSorter<T extends Comparable<? super T>> {
List<T> sort(List<T> in) {
List<T> copy = new ArrayList<>(in);
Collections.sort(copy);
return copy;
}
}

class CountSorter<T extends Comparable<? super T>> {
List<ItemCount<T>> sortByCount(List<T> in) {
Map<T,Integer> freq = new HashMap<>();
in.forEach(e -> freq.merge(e,1,Integer::sum));
int total = in.size();
return freq.entrySet()
.stream()
.map(e -> new ItemCount<>(e.getKey(), e.getValue(), total))
.sorted()          // see ItemCount#compareTo below
.toList();
}
}

record ItemCount<T extends Comparable<? super T>>
        (T item, int count, int total) implements Comparable<ItemCount<T>> {
    @Override public int compareTo(ItemCount<T> o) {
        int cmp = Integer.compare(this.count, o.count);
        return cmp != 0 ? cmp : this.item.compareTo(o.item);
    }
    int percentage() { return count * 100 / total; }
}
3 ResultReporter
Method	Signature	Behaviour
printNaturalData	void printNatural(InputValidator.DataType dt, List<?> data)	• Print Total X: m. line (replace X with “numbers / words / lines”).
• If dt == LINE ⇒ multi-line output.
• Else single line "Sorted data: el1 el2 …"
printByCount	void printByCount(InputValidator.DataType dt, List<ItemCount<?>> rows)	• Same Total … header.
• For each row:
row.item(): row.count() time(s), row.percentage()%
Why put both formats in ResultReporter?
Keeps all printing rules in one place; business logic stays testable without I/O.

4 Main.java orchestration
java
Copy code
public static void main(String[] args) {
CommandOptions opts = commandLineParser.parseOptions(args);
InputHandler   handler  = new InputHandler(logger, opts.dataType());
ResultReporter reporter = new ResultReporter(logger);

    if (opts.sortingType() == SortingType.NATURAL) {
        List<?> data = handler.readInput();          // Long or String
        List<?> sorted = new NaturalSorter<>().sort((List) data);
        reporter.printNatural(opts.dataType(), sorted);

    } else { // BY_COUNT
        List<?> data = handler.readInput();
        List<ItemCount<?>> rows =
              new CountSorter<>().sortByCount((List) data);
        reporter.printByCount(opts.dataType(), rows);
    }
}
Generics look messy but keep runtime casts centralised.

5 InputHandler
No code changes—Stage #4 still reads exactly the same three data types using whitespace rules.

6 Unit-tests (JUnit 5 snippets)
Parser – ensure -sortingType byCount + -dataType line yields correct DTO.

NaturalSorter – feed ["b","a","c"] → ["a","b","c"].

CountSorter – feed [-2,1,1,1] → rows in ascending count, then value.

ResultReporter – use ByteArrayOutputStream to assert formatting.

7 Logging
Your ILogger / ConsoleLogger stay untouched.
Add one logger.logError(...) in CommandLineParser when the value after a flag is missing.

8 Removal checklist
Delete: shouldSortIntegers, printSortedNumbers, DataAnalyzer, Statistics.

Update: documentation / comments that mention “greatest” or “-sortIntegers”.

You are now ready for Stage #4
The project will:

Parse -sortingType (NATURAL default) and -dataType (WORD default).

Read the chosen data-type from stdin.

Either naturally sort or count-sort it.

Print total and formatted output exactly as the examples show.

Keep each change tightly scoped; you’ll pass all automated checks with minimal regression risk.
