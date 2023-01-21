import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static void showMemoryUsage() {
        long usage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        System.out.println("Usage : " + usage);
    }

    public static void main(String[] args) throws Exception {
        showMemoryUsage();
        String fileName = "./MY_FILE.txt";
        File file = new File(fileName);
        showMemoryUsage();
        Scanner scanner = new Scanner(file);
        List<Integer> arr = new ArrayList<>();
        while (scanner.hasNextInt()) {
            showMemoryUsage();
            int value = scanner.nextInt();
            System.out.println(value);
            arr.add(value);
            showMemoryUsage();
        }
    }
}
