import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    protected static int testsFileIndex = 0;

    public static List<Integer> getTestList(InputType inputType) {
        List<Integer> result;
        switch (inputType) {
            case INPUT_CASE1 -> result = List.of(5, 4, 2, 3, 1);
            case INPUT_CASE2 -> result = List.of(0, 6, 7);
            case INPUT_BIG_CASE -> {
                result = new ArrayList<>();
                for (int i = 5000; i >= -5000; i--) {
                    result.add(i);
                }
            }
            case INPUT_500MB -> {
                result = new ArrayList<>();
                int range = 1024 * 1024 / (2 * 4);
                for (int i = range; i >= -range; i--) {
                    result.add(i);
                }
            }
            default -> result = List.of();
        }
        return result;
    }

    protected static void writeToFile(List<?> list, File file) throws Exception {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            list.forEach(printWriter::println);
        }
    }

    protected static List<Object> readList(File file) throws Exception {
        List<Object> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                result.add(scanner.nextInt());
            }
            while (scanner.hasNext()) {
                result.add(scanner.next());
            }
        }
        return result;
    }

    public static synchronized File getTempFile(InputType type) throws Exception {
        File file = File.createTempFile("MergeSortApp_" + testsFileIndex, ".txt");
        writeToFile(getTestList(type), file);
        testsFileIndex += 1;
        return file;
    }

    public static List<Integer> getBigList(int range) {
        List<Integer> list = new LinkedList<>();
        for (int i = range; i >= -range; i--) {
            list.add(i);
        }
        return list;
    }

    public static synchronized File getBigTempFile(int range) throws Exception {
        File file = File.createTempFile("MergeSortApp_" + testsFileIndex, ".txt");
        writeToFile(getBigList(range), file);
        testsFileIndex += 1;
        return file;
    }

    public static void assertBigTempFile(File file, int range, int copies) throws Exception {
        List<Integer> expected = getBigList(range);
        Collections.reverse(expected);
        List<Object> actualList = readList(file);
        assertEquals(expected.size() * copies, actualList.size());
        expected = expected.stream().flatMap(i -> Collections.nCopies(copies, i).stream()).collect(Collectors.toList());
        assertEquals(expected, actualList);
    }

    public static void assertFile(File file, ExpectedType type) throws Exception {
        switch (type) {
            case EXPECTED_CASE1 -> assertEquals(List.of(1, 2, 3, 4, 5), readList(file));
            case EXPECTED_CASE2 -> assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7), readList(file));
        }
    }

    public static <T> void assertFile(File file, List<T> expected) throws Exception {
        List<Object> actual = readList(file);
        assertEquals(expected, actual);
    }

    public static synchronized File createTempFile(List<?> data) throws Exception {
        File file = getTempFile(InputType.EMPTY_FILE);
        writeToFile(data, file);
        return file;
    }

}
