import java.io.File;
import java.util.*;

public class Sorter {
    private final Merger merger;
    private final NumIO numIO;
    private TmpFilesManager tmpFilesManager;
    private int tempFilesNumber;
    private boolean descending;

    public Sorter() {
        this.descending = false;
        this.merger = new Merger();
        this.numIO = new NumIO();
        this.tmpFilesManager = new TmpFilesManager();
    }

    protected <T> List<T> sortRecursive(List<T> values) {
        if (values.size() <= 1) {
            return values;
        }
        if (values.size() == 2) {
            T value0 = values.get(0);
            T value1 = values.get(1);

            if (Comparator.isGreater(value0, value1)) {
                if (descending) {
                    return List.of(value0, value1);
                } else {
                    return List.of(value1, value0);
                }
            }
        }
        List<T> firstHalf = sortRecursive(values.subList(0, values.size() / 2));
        List<T> secondHalf = sortRecursive(values.subList(values.size() / 2, values.size()));
        return merger.merge(firstHalf, secondHalf);
    }

    public <T> List<T> sort(List<T> values) {
        return sortRecursive(values);
    }

    public Queue<File> generateFilesForMerging(Queue<Scanner> inputScanners, long memoryLimit) throws Exception {
        tmpFilesManager.prepareTemporaryDir("./tempSorter");
        long numbersToRead = memoryLimit / 4;
        long overhead = numbersToRead / 10;
        numbersToRead -= overhead;
        List<Object> unsorted;
        Queue<File> filesForMerging = new LinkedList<>();
        while (!inputScanners.isEmpty()) {
            unsorted = new ArrayList<>();
            long remainingNumbersToRead = numbersToRead;
            while (remainingNumbersToRead > 0 && !inputScanners.isEmpty()) {
                List<Object> numbers = numIO.read(inputScanners.peek(), remainingNumbersToRead);
                if (numbers.size() < remainingNumbersToRead) {
                    inputScanners.remove().close();
                }
                remainingNumbersToRead -= numbers.size();
                unsorted.addAll(numbers);
            }
            List<Object> sorted = sort(unsorted);
            filesForMerging.add(numIO.write(sorted, "./tempSorter/SortedFile_" + tempFilesNumber + ".txt"));
            tempFilesNumber++;
        }
        return filesForMerging;
    }

    public void descending(boolean b) {
        descending = b;
        merger.descending(b);
    }
}

