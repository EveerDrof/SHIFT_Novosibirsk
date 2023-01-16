import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class FileSorterTests {
    @Test
    public void smallElementsSetTest() throws Exception {
        FileSorter fileSorter = new FileSorter();
        File testFile = TestUtils.getTempFile(InputType.INPUT_CASE1);
        File result = fileSorter.sort(List.of(testFile));
        TestUtils.assertFile(result, ExpectedType.EXPECTED_CASE1);
    }
}
