import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSorterTests {
    @Test
    public void smallElementsSetTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        File testFile = TestUtils.getTempFile(InputType.INPUT_CASE1);
        File result = fileSorter.sort(List.of(testFile));
        TestUtils.assertFile(result, ExpectedType.EXPECTED_CASE1);
    }

    @Test
    public void multipleFilesTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        File testFile1 = TestUtils.getTempFile(InputType.INPUT_CASE1);
        File testFile2 = TestUtils.getTempFile(InputType.INPUT_CASE2);
        File result = fileSorter.sort(List.of(testFile1, testFile2));
        TestUtils.assertFile(result, ExpectedType.EXPECTED_CASE2);
    }

    @Test
    void bigFilesTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        List<File> files = new ArrayList<>();
        int filesNumber = 7;
        for (int i = 0; i < filesNumber; i++) {
            files.add(TestUtils.getTempFile(InputType.INPUT_BIG_CASE));
        }
        File result = fileSorter.sort(files);
        Scanner scanner = new Scanner(result);
        for (int i = -5000; i <= 5000; i++) {
            for (int k = 0; k < filesNumber; k++) {
                int inputInt = scanner.nextInt();
                assertEquals(i, inputInt);
            }
        }
    }

    @Test
    @Disabled
    void memoryLimitTestFileSizeBiggerThanLimit() throws Exception {
        final int memoryLimit = 4;
        FileSorter fileSorter = new FileSorter();
        fileSorter.setMemoryLimit(memoryLimit);
        List<File> files = new ArrayList<>();
        int filesNumber = 2;
        final int range = 3;
        for (int i = 0; i < filesNumber; i++) {
            files.add(TestUtils.getBigTempFile(range));
        }
        MemoryUsageChecker memoryUsageChecker = new MemoryUsageChecker();
        File result = fileSorter.sort(files);
        TestUtils.assertBigTempFile(result, range, filesNumber);
        assertTrue(memoryUsageChecker.stopAndGetUsage() <= memoryLimit);
    }

    @Test
    void multipleInputFilesWithMemoryLimit() throws Exception {
        FileSorter fileSorter = new FileSorter();
        fileSorter.setMemoryLimit(43);
        List<File> files = new ArrayList<>();
        int filesNumber = 5;
        int range = 3;
        for (int i = 0; i < filesNumber; i++) {
            files.add(TestUtils.getBigTempFile(range));
        }
        File result = fileSorter.sort(files);
        TestUtils.assertBigTempFile(result, range, filesNumber);
    }

    @Test
    void oneFileMemoryLimitTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        fileSorter.setMemoryLimit(10 * 4);
        int range = 10;
        List<File> files = List.of(TestUtils.getBigTempFile(range));
        File result = fileSorter.sort(files);
        TestUtils.assertBigTempFile(result, range, 1);
    }

    @Test
    void descendingOrderTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        fileSorter.descending(true);
        List<String> input = List.of("A", "B", "Z", "C", "D");
        File file = TestUtils.getTempFile(InputType.EMPTY_FILE);
        TestUtils.writeToFile(input, file);
        List<File> files = List.of(file);
        File result = fileSorter.sort(files);
        TestUtils.assertFile(result, List.of("Z", "D", "C", "B", "A"));
    }
}
