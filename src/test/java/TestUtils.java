import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    protected static void writeToList(List<Integer> list, File file) throws Exception {
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
        switch (type) {
            case EMPTY_FILE -> {
                return file;
            }
            case INPUT_CASE1 -> writeToList(List.of(5, 4, 2, 3, 1), file);
        }
        return file;
    }

    public static void assertFile(File file, ExpectedType type) throws Exception {
        switch (type) {
            case EXPECTED_CASE1 -> assertEquals(List.of(1, 2, 3, 4, 5), readList(file));
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
