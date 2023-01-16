import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
