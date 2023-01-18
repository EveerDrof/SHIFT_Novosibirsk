import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumIO {
    public File write(List<Integer> list, File outputFile) throws Exception {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            list.forEach(printWriter::println);
        }
        return outputFile;
    }

    public File write(List<Integer> list, String outputFileName) throws Exception {
        File file = new File(outputFileName);
        file.createNewFile();
        write(list, file);
        return file;
    }

    public List<Integer> read(File tempFile, long numbersToRead) throws Exception {
        return read(new FileInputStream(tempFile), numbersToRead);
    }

    public List<Integer> read(InputStream inputStream, long numbersToRead) throws Exception {
        List<Integer> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputStream)) {
            for (int i = 0; i < numbersToRead; i++) {
                if (!scanner.hasNextInt()) {
                    break;
                }
                result.add(scanner.nextInt());
            }
        }
        return result;
    }
}
