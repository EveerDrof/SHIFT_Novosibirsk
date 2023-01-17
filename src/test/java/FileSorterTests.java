import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
