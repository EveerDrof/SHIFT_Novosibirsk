import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SorterTests {
    @Test
    void fiveElementsTest() {
        Sorter sorter = new Sorter();
        List<Integer> values = List.of(1, 20, 50, 5, 0);
        List<Integer> sorted = sorter.sort(values);
        assertEquals(List.of(0, 1, 5, 20, 50), sorted);
    }

    @Test
    void testTwoValues() {
        Sorter sorter = new Sorter();
        assertEquals(List.of(1, 2), sorter.sort(List.of(2, 1)));
    }

    @Test
    void fourElementsTest() {
        Sorter sorter = new Sorter();
        List<Integer> values = List.of(1, 20, 50, 5);
        List<Integer> sorted = sorter.sort(values);
        assertEquals(List.of(1, 5, 20, 50), sorted);
    }

    @Test
    void oneElementTest() {
        Sorter sorter = new Sorter();
        List<Integer> values = List.of(1);
        List<Integer> sorted = sorter.sort(values);
        assertEquals(List.of(1), sorted);
    }

    @Test
    void reversedSorted() {
        Sorter sorter = new Sorter();
        List<Integer> values = new ArrayList<>();
        for (int i = 300; i >= 0; i--) {
            values.add(i);
        }
        List<Integer> reversed = new ArrayList<>(values);
        Collections.reverse(reversed);
        List<Integer> sorted = sorter.sort(values);
        assertEquals(reversed, sorted);
    }

    @Test
    void sameValuesAfterSplit() {
        Sorter sorter = new Sorter();
        List<Integer> values = List.of(1, 0, 1, 1, 0, 1);
        assertEquals(List.of(0, 0, 1, 1, 1, 1), sorter.sort(values));
    }

    @Test
    void emptyListTest() {
        Sorter sorter = new Sorter();
        List<Integer> values = List.of();
        assertEquals(List.of(), sorter.sort(values));
    }

    @Test
    void generateFilesForMergingTest() throws Exception {
        Sorter sorter = new Sorter();
        Queue<File> fileQueue = new LinkedList<>();
        List<Integer> values1 = new ArrayList<>();
        List<Integer> values2 = new ArrayList<>();
        for (int i = 10; i > 0; i -= 2) {
            values1.add(i);
            values2.add(i - 1);
        }
        fileQueue.add(TestUtils.createTempFile(values1));
        fileQueue.add(TestUtils.createTempFile(values2));
        Queue<Scanner> scannerQueue = fileQueue.stream().map((file) -> {
            try {
                return new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(LinkedList::new));
        long memoryLimit = 20;
        Queue<File> filesForMerging = sorter.generateFilesForMerging(scannerQueue, memoryLimit);
        NumIO numIO = new NumIO();
        List<Object> ints = numIO.read(filesForMerging.remove(), 999);
        Collections.reverse(values1);
        Collections.reverse(values2);
        assertEquals(values1, ints);
        ints = numIO.read(filesForMerging.remove(), 999);
        assertEquals(values2, ints);
    }

    @Test
    void sorterGenerationMemoryLimitTest() throws Exception {
        File input = TestUtils.getTempFile(InputType.INPUT_BIG_CASE);
        Queue<Scanner> inputList = new LinkedList<>();
        inputList.add(new Scanner(input));
        long memoryLimit = 100;
        Sorter sorter = new Sorter();
        MemoryUsageChecker memoryUsageChecker = new MemoryUsageChecker();
        sorter.generateFilesForMerging(inputList, memoryLimit);
        long memoryUsage = memoryUsageChecker.stopAndGetUsage();
        assertTrue(memoryLimit >= memoryUsage);
    }

    @Test
    void stringSortingTest() {
        Sorter sorter = new Sorter();
        List<String> unsorted = new ArrayList<>(List.of("A", "H", "B", "Z", "I"));
        List<String> sorted = sorter.sort(unsorted);
        assertEquals(List.of("A", "B", "H", "I", "Z"), sorted);
    }

    @Test
    void reversedStringSorting() {
        Sorter sorter = new Sorter();
        sorter.descending(true);
        List<String> unsorted = new ArrayList<>(List.of("A", "H", "B", "Z", "I"));
        List<String> sorted = sorter.sort(unsorted);
        assertEquals(List.of("Z", "I", "H", "B", "A"), sorted);
    }
}
