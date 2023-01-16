import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergerTests {
    @Test
    public void mergeTwoArraysTest() {
        Merger merger = new Merger();
        List<Integer> result = merger.merge(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), result);
    }
}
