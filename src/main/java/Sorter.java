import java.util.List;

public class Sorter {
    private final Merger merger;

    public Sorter() {
        this.merger = new Merger();
    }

    protected List<Integer> sortRecursive(List<Integer> values) {
        if (values.size() <= 1) {
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
        return merger.merge(firstHalf, secondHalf);
    }

    public List<Integer> sort(List<Integer> values) {
        return sortRecursive(values);
    }
}
