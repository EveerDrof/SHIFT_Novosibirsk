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
    void memoryLimitTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        long memoryLimit = 10000000;
        fileSorter.setMemoryLimit(memoryLimit);
        List<File> files = new ArrayList<>();
        int filesNumber = 350;
        for (int i = 0; i < filesNumber; i++) {
            files.add(TestUtils.getTempFile(InputType.INPUT_BIG_CASE));
        }
        fileSorter.sort(files);
//        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long memoryUsed = 0;
        //heapMemoryUsage.getUsed();
        memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Heap memory usage : " + (memoryUsed));
        assertTrue(memoryUsed <= memoryLimit, "Memory usage is " + (memoryUsed / 1024 / 1024) +
                " It's higher than needed because memory limit is " + (memoryLimit / 1024 / 1024));
    }
}
