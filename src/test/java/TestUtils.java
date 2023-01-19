import java.io.File;
import java.io.PrintWriter;
import java.util.*;

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
        System.out.println("Expected : ");
        System.out.println(expected);
        List<Integer> actualList = readList(file);
        System.out.println("Actual : ");
        System.out.println(actualList);
        assertEquals(expected.size() * copies, actualList.size());
        int actualIndex = 0;
        for (Integer integer : expected) {
            for (int i = 0; i < copies; i++) {
                System.out.println("Comparing : " + integer + " : " + actualList.get(actualIndex) + " index " + actualIndex);
                assertEquals(integer, actualList.get(actualIndex));
                actualIndex += 1;
            }
        }
    }

    public static void assertFile(File file, ExpectedType type) throws Exception {
        switch (type) {
            case EXPECTED_CASE1 -> assertEquals(List.of(1, 2, 3, 4, 5), readList(file));
            case EXPECTED_CASE2 -> assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7), readList(file));
        }
    }

    public static synchronized File createTempFile(List<Integer> data) throws Exception {
        File file = getTempFile(InputType.EMPTY_FILE);
        writeToFile(data, file);
        return file;
    }

}
