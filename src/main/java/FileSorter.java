import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileSorter {
    private final Sorter sorter;
    private final NumIO numIO;

    public FileSorter() {
        this.sorter = new Sorter();
        this.numIO = new NumIO();
    }

    public File sort(List<File> testFile) throws Exception {
        List<Integer> unsorted = new ArrayList<>();
        testFile.forEach((file) -> {
            try {
                unsorted.addAll(numIO.read(file, 10));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        List<Integer> sorted = sorter.sort(unsorted);
        File outputFile = new File("output.txt");
        outputFile.createNewFile();
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            sorted.forEach(printWriter::println);
        }
        return outputFile;
    }
}
