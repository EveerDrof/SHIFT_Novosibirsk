import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class FileSorter {
    private final Sorter sorter;
    private final NumIO numIO;
    private long memoryLimit;

    public FileSorter() {
        this.sorter = new Sorter();
        this.numIO = new NumIO();
        memoryLimit = Long.MAX_VALUE;
    }

    public File sort(List<File> testFiles) throws Exception {
        Queue<InputStream> inputStreams = testFiles.stream().map((file) -> {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(LinkedList::new));
        List<Integer> unsorted = new ArrayList<>();
        final long integerSize = 4;
        long tempFilesNumber = 0;
        Queue<File> filesForMerging = new LinkedList<>();
        long numbersToRead = memoryLimit / integerSize;
        long overhead = numbersToRead * 70 / 100;
        numbersToRead -= overhead;
        while (!inputStreams.isEmpty()) {
            long remainingNumbersToRead = numbersToRead;
            while (remainingNumbersToRead > 0 && !inputStreams.isEmpty()) {
                List<Integer> numbers = numIO.read(inputStreams.peek(), remainingNumbersToRead);
                if (numbers.size() < remainingNumbersToRead) {
                    inputStreams.remove();
                }
                remainingNumbersToRead -= numbers.size();
                unsorted.addAll(numbers);
            }
            List<Integer> sorted = sorter.sort(unsorted);
            filesForMerging.add(numIO.write(sorted, "TempFileForMerging_" + tempFilesNumber + ".txt"));
            tempFilesNumber++;
        }
        Merger merger = new Merger();
        while (filesForMerging.size() != 1) {
            InputStream firstStream = new FileInputStream(filesForMerging.remove());
            InputStream secondStream = new FileInputStream(filesForMerging.remove());
            for (int i = 0; i < 2; i++) {
                List<Integer> firstFileNumbers = numIO.read(firstStream, numbersToRead / 5);
                List<Integer> secondFileNumbers = numIO.read(secondStream, numbersToRead / 5);
                List<Integer> merged = merger.merge(firstFileNumbers, secondFileNumbers);
                filesForMerging.add(numIO.write(merged, "TempFileForMerging_" + tempFilesNumber + ".txt"));
            }
        }
        return filesForMerging.remove();
    }


    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }
}
