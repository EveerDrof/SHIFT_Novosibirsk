import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileSorter {
    private final Sorter sorter;
    private long memoryLimit;

    public FileSorter() {
        this.sorter = new Sorter();
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


    public File sort(List<File> testFiles) throws Exception {
        Queue<Scanner> inputScanners = filesToScanners(testFiles);
        Queue<File> filesForMerging = sorter.generateFilesForMerging(inputScanners, memoryLimit);
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
