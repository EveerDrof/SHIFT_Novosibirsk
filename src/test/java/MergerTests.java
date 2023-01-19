import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergerTests {
    @Test
    public void mergeTwoArraysTest() {
        Merger merger = new Merger();
        List<Integer> result = merger.merge(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), result);
    }

    @Test
    public void mergeOneOrMoreEmptyArraysTest() {
        Merger merger = new Merger();
        List<Integer> result = merger.merge(List.of(), List.of(100, 7, 6, 2, 8));
        assertEquals(List.of(100, 7, 6, 2, 8), result);
        result = merger.merge(List.of(9, 2, 0, 100), List.of());
        assertEquals(List.of(9, 2, 0, 100), result);
        result = merger.merge(List.of(), List.of());
        assertEquals(List.of(), result);
    }

    @Test
    public void mergeTwoStreams() throws Exception {
        List<Integer> input1 = List.of(1, 3, 5, 7);
        List<Integer> input2 = List.of(2, 4, 6, 8);
        File file1 = TestUtils.getTempFile(InputType.EMPTY_FILE);
        File file2 = TestUtils.getTempFile(InputType.EMPTY_FILE);
        TestUtils.writeToFile(input1, file1);
        TestUtils.writeToFile(input2, file2);
        InputStream inputStream1 = new FileInputStream(file1);
        InputStream inputStream2 = new FileInputStream(file2);
        Merger merger = new Merger();
        List<Integer> mergedLists = merger.merge(inputStream1, inputStream2, 100);
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), mergedLists);
    }

    @Test
    public void mergeFiles() throws Exception {
        int filesNumber = 12;
        List<List<Integer>> filesContents = new ArrayList<>();
        List<Integer> allNumbers = new ArrayList<>();
        for (int k = 0; k < filesNumber; k++) {
            filesContents.add(new LinkedList<>());
        }
        int totalNumbers = filesNumber * 20;
        for (int i = 0; i < totalNumbers; i++) {
            filesContents.get(i % filesNumber).add(i);
            allNumbers.add(i);
        }
        NumIO numIO = new NumIO();
        Queue<File> fileQueue = new LinkedList<>();

        for (List<Integer> contents : filesContents) {
            fileQueue.add(TestUtils.createTempFile(contents));
        }
        Merger merger = new Merger();
        File file = merger.merge(fileQueue, 9999);
        assertEquals(allNumbers, TestUtils.readList(file));
    }
}
