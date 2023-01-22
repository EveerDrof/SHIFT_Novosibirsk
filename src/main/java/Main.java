import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        boolean ascending = true;
        boolean integers = false;
        String outputFileName = null;
        List<String> inputFilesNames = new LinkedList<>();
        for (String currentArg : args) {
            switch (currentArg) {
                case "-d": {
                    ascending = false;
                    break;
                }
                case "-a": {
                    ascending = true;
                    break;
                }
                case "-i": {
                    integers = true;
                    break;
                }
                case "-s": {
                    integers = false;
                    break;
                }
                default: {
                    if (outputFileName == null) {
                        outputFileName = currentArg;
                    } else {
                        inputFilesNames.add(currentArg);
                    }
                }
            }
        }
        if (inputFilesNames.size() == 0) {
            System.err.println("No input files specified");
            System.exit(1);
        }
        if (outputFileName == null) {
            System.err.println("No output file specified");
            System.exit(1);
        }
        FileSorter fileSorter = new FileSorter();
        fileSorter.descending(!ascending);
        List<File> files = inputFilesNames.stream().map(File::new).collect(Collectors.toList());
        File result = null;
        try {
            result = fileSorter.sort(files);
        } catch (Exception ex) {
            System.err.println("There was an exception : ");
            System.err.println(ex.getMessage());
        }
        result.renameTo(new File(outputFileName));
    }
}
