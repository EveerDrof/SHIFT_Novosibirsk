import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumIOTests {

    @Test
    public void writeListTest() throws Exception {
        File tempFile = TestUtils.getTempFile(InputType.EMPTY_FILE);
        NumIO numIO = new NumIO();
        List<Integer> list = List.of(1, 2, 3, 4);
        numIO.write(list, tempFile);
        Scanner scanner = new Scanner(tempFile);
        list.forEach((number) -> {
            assertTrue(scanner.hasNextInt());
            int val = scanner.nextInt();
            assertEquals(number, val);
        });
    }

    @Test
    public void readListTest() throws Exception {
        File tempFile = TestUtils.getTempFile(InputType.EMPTY_FILE);
        NumIO numIO = new NumIO();
        List<Integer> list = List.of(1, 2, 3, 4);
        try (PrintWriter printWriter = new PrintWriter(tempFile)) {
            list.forEach(printWriter::println);
        }
        for (int numbersToRead = 1; numbersToRead < list.size(); numbersToRead++) {
            List<Object> fileNumbers = numIO.read(tempFile, numbersToRead);
            assertEquals(list.subList(0, numbersToRead), fileNumbers);
        }
    }

    @Test
    public void stopAtTheEndTest() throws Exception {
        File tempFile = TestUtils.getTempFile(InputType.INPUT_CASE2);
        NumIO numIO = new NumIO();
        List<Object> list = numIO.read(tempFile, 1000);
        assertEquals(TestUtils.getTestList(InputType.INPUT_CASE2), list);
    }

    @Test
    public void readFromInputStream() throws Exception {
        File tempFile = TestUtils.getTempFile(InputType.INPUT_CASE1);
        NumIO numIO = new NumIO();
        InputStream inputStream = new FileInputStream(tempFile);
        List<Object> list = numIO.read(inputStream, 1000);
        assertEquals(TestUtils.getTestList(InputType.INPUT_CASE1), list);
    }

    @Test
    public void writeStringList() throws Exception {
        File tempFile = TestUtils.getTempFile(InputType.EMPTY_FILE);
        NumIO numIO = new NumIO();
        List<String> list = List.of("A", "BB", "ZZZ");
        numIO.write(list, tempFile);
        Scanner scanner = new Scanner(tempFile);
        list.forEach((number) -> {
            assertTrue(scanner.hasNext());
            String val = scanner.next();
            assertEquals(number, val);
        });
    }
}
