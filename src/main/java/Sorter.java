import java.util.ArrayList;
import java.util.List;

public class Sorter {
    protected List<Integer> merge(List<Integer> leftHalf, List<Integer> rightHalf) {
        int totalSize = leftHalf.size() + rightHalf.size();
        List<Integer> result = new ArrayList<>(totalSize);
        int firstArrIndex = 0;
        int secondArrIndex = 0;
        for (int i = 0; i < totalSize; i++) {
            int firstArrValue = leftHalf.get(firstArrIndex);
            int secondArrValue = rightHalf.get(secondArrIndex);
            if (firstArrValue < secondArrValue) {
                result.add(firstArrValue);
                firstArrIndex += 1;
                if (firstArrIndex == leftHalf.size()) {
                    for (; secondArrIndex < rightHalf.size(); secondArrIndex++) {
                        result.add(rightHalf.get(secondArrIndex));
                    }
                    break;
                }
            } else {
                result.add(secondArrValue);
                secondArrIndex += 1;
                if (secondArrIndex == rightHalf.size()) {
                    for (; firstArrIndex < leftHalf.size(); firstArrIndex++) {
                        result.add(leftHalf.get(firstArrIndex));
                    }
                    break;
                }
            }
        }
        return result;
    }

    protected List<Integer> sortRecursive(List<Integer> values) {
        if (values.size() == 1) {
            return values;
        }
        if (values.size() == 2) {
            if (values.get(1) < values.get(0)) {
                return List.of(values.get(1), values.get(0));
            }
            return values;
        }
        List<Integer> firstHalf = sortRecursive(values.subList(0, values.size() / 2));
        List<Integer> secondHalf = sortRecursive(values.subList(values.size() / 2, values.size()));
        return merge(firstHalf, secondHalf);
    }

    public List<Integer> sort(List<Integer> values) {
        return sortRecursive(values);
    }
}
