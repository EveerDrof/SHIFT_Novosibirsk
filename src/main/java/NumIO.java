import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumIO {
    public void write(List<Integer> list, OutputStream outputStream) throws Exception {
        try (PrintWriter printWriter = new PrintWriter(outputStream)) {
            list.forEach(printWriter::println);
        }
    }

    public File write(List<Integer> list, File outputFile) throws Exception {
        write(list, new FileOutputStream(outputFile, true));
        return outputFile;
    }

    public File write(List<Integer> list, String outputFileName) throws Exception {
        File file = new File(outputFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        write(list, file);
        return file;
    }

    public List<Integer> read(File tempFile, long numbersToRead) throws Exception {
        return read(new FileInputStream(tempFile), numbersToRead);
    }

    public List<Integer> read(InputStream inputStream, long numbersToRead) throws Exception {
        return read(new Scanner(inputStream), numbersToRead);
    }

    public List<Integer> read(Scanner scanner, long numbersToRead) throws Exception {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbersToRead; i++) {
            if (!scanner.hasNextInt()) {
                break;
            }
            result.add(scanner.nextInt());
        }
        return result;
    }
}
