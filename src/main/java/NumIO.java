import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumIO {
    public void write(List<Integer> list, File outputFile) throws Exception {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            list.forEach(printWriter::println);
        }
    }

    public List<Integer> read(File tempFile, int numbersToRead) throws Exception {
        List<Integer> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(tempFile)) {
            for (int i = 0; i < numbersToRead; i++) {
                result.add(scanner.nextInt());
            }
        }
        return result;
    }
}
