import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        String str = new String("adsasdada");
        File file = new File("testaaaaa.txt");
        PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
        printWriter.println(str);
        printWriter.close();
    }
}
