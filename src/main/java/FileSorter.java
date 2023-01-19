import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class FileSorter {
    private final Sorter sorter;
    private final NumIO numIO;
    private int tempFilesNumber;
    private long memoryLimit;
    private TmpFilesManager tmpFilesManager;

    public FileSorter() {
        this.sorter = new Sorter();
        this.numIO = new NumIO();
        this.tempFilesNumber = 0;
        this.tmpFilesManager = new TmpFilesManager();

        memoryLimit = Long.MAX_VALUE;
    }


    private Queue<Scanner> filesToScanners(List<File> testFiles) {
        return testFiles.stream().map((file) -> {
            try {
                return new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    private long computeNumbersToRead() {
        final long integerSize = 4;
        long numbersToRead = memoryLimit / integerSize;
        long overhead = numbersToRead * 70 / 100;
        numbersToRead -= overhead;
        return numbersToRead;
    }

    private Queue<File> generateFilesForMerging(Queue<Scanner> inputScanners) throws Exception {
        long numbersToRead = computeNumbersToRead();
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
            List<Integer> sorted = sorter.sort(unsorted);
            System.out.println("Sorted size : " + sorted.size());
            filesForMerging.add(numIO.write(sorted, "./tempSorter/SortedFile_" + tempFilesNumber + ".txt"));
            tempFilesNumber++;
        }
        return filesForMerging;
    }


    public File sort(List<File> testFiles) throws Exception {
        tmpFilesManager.prepareTemporaryDir("./tempSorter");
        Queue<Scanner> inputScanners = filesToScanners(testFiles);
        Queue<File> filesForMerging = generateFilesForMerging(inputScanners);
        System.out.println("Now we merging: " + filesForMerging.size());
        for (File f : filesForMerging) {
            System.out.println(f.getName());
        }
        Merger merger = new Merger();
        return merger.merge(filesForMerging, memoryLimit);
    }


    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }
}
