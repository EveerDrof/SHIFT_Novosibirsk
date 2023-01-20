import java.io.File;
import java.util.*;

public class Sorter {
    private final Merger merger;
    private final NumIO numIO;
    private TmpFilesManager tmpFilesManager;
    private int tempFilesNumber;

    public Sorter() {
        this.merger = new Merger();
        this.numIO = new NumIO();
        this.tmpFilesManager = new TmpFilesManager();
    }

    protected List<Integer> sortRecursive(List<Integer> values) {
        if (values.size() <= 1) {
            return values;
        }
        if (values.size() == 2) {
            if (values.get(1) < values.get(0)) {
                return List.of(values.get(1), values.get(0));
            }
            return values;
        }
        List<Integer> firstHalf = sortRecursive(values.subList(0, values.size() / 2));
        List<Integer> secondHalf = sortRecursive(values.subList(values.size() / 2, values.size()));
        return merger.merge(firstHalf, secondHalf);
    }

    public List<Integer> sort(List<Integer> values) {
        return sortRecursive(values);
    }

    public Queue<File> generateFilesForMerging(Queue<Scanner> inputScanners, long memoryLimit) throws Exception {
        tmpFilesManager.prepareTemporaryDir("./tempSorter");
        long numbersToRead = memoryLimit;
        long overhead = numbersToRead / 10;
        numbersToRead -= overhead;
        List<Integer> unsorted;
        Queue<File> filesForMerging = new LinkedList<>();
        while (!inputScanners.isEmpty()) {
            unsorted = new ArrayList<>();
            long remainingNumbersToRead = numbersToRead;
            while (remainingNumbersToRead > 0 && !inputScanners.isEmpty()) {
                List<Integer> numbers = numIO.read(inputScanners.peek(), remainingNumbersToRead);
                System.out.println("Numbers are");
                System.out.println(numbers);
                if (numbers.size() < remainingNumbersToRead) {
                    inputScanners.remove().close();
                }
                remainingNumbersToRead -= numbers.size();
                unsorted.addAll(numbers);
            }
            List<Integer> sorted = sort(unsorted);
            System.out.println("Sorted size : " + sorted.size());
            filesForMerging.add(numIO.write(sorted, "./tempSorter/SortedFile_" + tempFilesNumber + ".txt"));
            tempFilesNumber++;
        }
        return filesForMerging;
    }
}

