public class Comparator {
    public static <T> boolean isGreater(T a, T b) {
        if (a instanceof String) {
            return ((String) a).compareTo((String) b) > 0;
        }
        return (Integer) a > (Integer) b;
    }

    public static <T> boolean isGreaterOrEqual(T a, T b) {
        if (a instanceof String) {
            return ((String) a).compareTo((String) b) >= 0;
        }
        return (Integer) a >= (Integer) b;
    }
}
