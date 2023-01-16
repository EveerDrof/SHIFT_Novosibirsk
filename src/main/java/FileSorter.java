import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class FileSorter {
    private final Sorter sorter;

    public FileSorter() {
        this.sorter = new Sorter();
    }

    public File sort(List<File> testFile) throws Exception {
        File file = new File("output.txt");
        file.createNewFile();
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            list.forEach(printWriter::println);
        }
        return file;
    }
}
