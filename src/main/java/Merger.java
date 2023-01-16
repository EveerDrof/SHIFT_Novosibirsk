import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Merger {

    protected void addRemainingValues(List<Integer> result, Iterator<Integer> iterator) {
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
    }

    public List<Integer> merge(List<Integer> leftHalf, List<Integer> rightHalf) {
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> leftIterator = leftHalf.iterator();
        Iterator<Integer> rightIterator = rightHalf.iterator();
        int leftValue = leftIterator.next();
        int rightValue = rightIterator.next();
        while (true) {
            if (leftValue <= rightValue) {
                result.add(leftValue);
                if (!leftIterator.hasNext()) {
                    result.add(rightValue);
                    addRemainingValues(result, rightIterator);
                    break;
                }
                leftValue = leftIterator.next();
                continue;
            }
            result.add(rightValue);
            if (!rightIterator.hasNext()) {
                result.add(leftValue);
                addRemainingValues(result, leftIterator);
                break;
            }
            rightValue = rightIterator.next();
        }
        return result;
    }
}
