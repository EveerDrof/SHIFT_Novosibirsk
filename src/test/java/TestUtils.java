import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    public static List<Integer> getTestList(InputType inputType) {
        List<Integer> result;
        switch (inputType) {
            case INPUT_CASE1 -> result = List.of(5, 4, 2, 3, 1);
            case INPUT_CASE2 -> result = List.of(0, 6, 7);
            default -> result = List.of();
        }
        return result;
    }

    protected static void writeToFile(List<Integer> list, File file) throws Exception {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            list.forEach(printWriter::println);
        }
    }

    protected static List<Integer> readList(File file) throws Exception {
        List<Integer> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                result.add(scanner.nextInt());
            }
        }
        return result;
    }

    public static File getTempFile(InputType type) throws Exception {
        File file = File.createTempFile("MergeSortApp", ".txt");
        writeToFile(getTestList(type), file);
        return file;
    }

    public static void assertFile(File file, ExpectedType type) throws Exception {
        switch (type) {
            case EXPECTED_CASE1 -> assertEquals(List.of(1, 2, 3, 4, 5), readList(file));
            case EXPECTED_CASE2 -> assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7), readList(file));
        }
    }
//    public static File get() throws Exception{
//        File file = getTempFile();
//        List<Integer> testNumbers = List.of(1,0,1,1,0,1);
//        try(PrintWriter printWriter = new PrintWriter(file)){
//            for
//        }
//    }
}
