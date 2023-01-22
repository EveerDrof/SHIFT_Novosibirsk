import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumIO {
    public <T> void write(List<T> list, OutputStream outputStream) {
        try (PrintWriter printWriter = new PrintWriter(outputStream)) {
            list.forEach(printWriter::println);
        }
    }

    public <T> void write(List<T> list, PrintWriter printWriter) {
        list.forEach(printWriter::println);
    }

    public <T> File write(List<T> list, File outputFile) throws Exception {
        write(list, new FileOutputStream(outputFile, true));
        return outputFile;
    }

    public <T> File write(List<T> list, String outputFileName) throws Exception {
        File file = new File(outputFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        write(list, file);
        return file;
    }

    public List<Object> read(File tempFile, long numbersToRead) throws Exception {
        return read(new FileInputStream(tempFile), numbersToRead);
    }

    public List<Object> read(InputStream inputStream, long numbersToRead) throws Exception {
        return read(new Scanner(inputStream), numbersToRead);
    }

    public List<Object> read(Scanner scanner, long numbersToRead) {
        boolean isInputString = !scanner.hasNextInt();
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < numbersToRead; i++) {
            Object value;
            if (isInputString) {
                if (!scanner.hasNext()) {
                    break;
                }
                value = scanner.next();
            } else {
                if (!scanner.hasNextInt()) {
                    break;
                }
                value = scanner.nextInt();
            }
            result.add(value);
        }
        return result;
    }
}
