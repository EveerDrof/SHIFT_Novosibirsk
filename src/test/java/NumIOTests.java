import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumIOTests {
    protected File createInputTempFile() throws Exception {
        return File.createTempFile("MergeSortApp", ".txt");
    }

    @Test
    public void writeListTest() throws Exception {
        File tempFile = createInputTempFile();
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
        File tempFile = createInputTempFile();
        NumIO numIO = new NumIO();
        List<Integer> list = List.of(1, 2, 3, 4);
        try (PrintWriter printWriter = new PrintWriter(tempFile)) {
            list.forEach(printWriter::println);
        }
        for (int numbersToRead = 1; numbersToRead < list.size(); numbersToRead++) {
            List<Integer> fileNumbers = numIO.read(tempFile, numbersToRead);
            assertEquals(list.subList(0, numbersToRead), fileNumbers);
        }
    }
}
