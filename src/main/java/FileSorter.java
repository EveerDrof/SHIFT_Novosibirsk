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
    private boolean descending;
    private Merger merger;

    public FileSorter() {
        descending = false;
        sorter = new Sorter();
        memoryLimit = Long.MAX_VALUE;
        merger = new Merger();
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


    public File sort(List<File> testFiles) throws Exception {
        Queue<Scanner> inputScanners = filesToScanners(testFiles);
        Queue<File> filesForMerging = sorter.generateFilesForMerging(inputScanners, memoryLimit);
        merger.descending(descending);
        return merger.merge(filesForMerging, memoryLimit);
    }


    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public void descending(boolean b) {
        descending = b;
        sorter.descending(b);
        merger.descending(b);
    }
}
