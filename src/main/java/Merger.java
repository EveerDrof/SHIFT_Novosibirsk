import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class Merger {
    private NumIO numIO;
    private TmpFilesManager tmpFilesManager;

    public Merger() {
        numIO = new NumIO();
        tmpFilesManager = new TmpFilesManager();
    }

    protected <T> void addRemainingValues(List<T> result, Iterator<T> iterator) {
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
    }

    public <T> List<T> merge(List<T> leftHalf, List<T> rightHalf) {
        List<T> result = new ArrayList<>();
        Iterator<T> leftIterator = leftHalf.iterator();
        Iterator<T> rightIterator = rightHalf.iterator();
        if (!leftIterator.hasNext()) {
            addRemainingValues(result, rightIterator);
            return result;
        }
        if (!rightIterator.hasNext()) {
            addRemainingValues(result, leftIterator);
            return result;
        }
        T leftValue = leftIterator.next();
        T rightValue = rightIterator.next();
        while (true) {
            if (Comparator.isGreaterOrEqual(rightValue, leftValue)) {
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

    public List<Object> merge(InputStream inputStream1, InputStream inputStream2, long valuesToRead) throws Exception {
        return merge(numIO.read(inputStream1, valuesToRead), numIO.read(inputStream2, valuesToRead));
    }

    public List<Object> merge(Scanner scanner1, Scanner scanner2, long valuesToRead) {
        return merge(numIO.read(scanner1, valuesToRead), numIO.read(scanner2, valuesToRead));
    }

    public File merge(Queue<File> filesForMerging, long memoryLimit) throws Exception {
        String tmpDirName = "./tmpMerger";
        tmpFilesManager.prepareTemporaryDir(tmpDirName);
        int tempFilesNumber = 0;
        while (filesForMerging.size() != 1) {
            File firstFile = filesForMerging.remove();
            File secondFile = filesForMerging.remove();
            Scanner firstScanner = new Scanner(firstFile);
            Scanner secondScanner = new Scanner(secondFile);
            File mergedFile = new File(tmpDirName + "/MergedFile_" + tempFilesNumber + ".txt");
            PrintWriter mergingPrintWriter = new PrintWriter(new FileOutputStream(mergedFile, true));
            mergedFile.createNewFile();
            while (firstScanner.hasNextInt() || secondScanner.hasNextInt()) {
                List<Object> merged = merge(firstScanner, secondScanner, memoryLimit);
                numIO.write(merged, mergingPrintWriter);
            }
            mergingPrintWriter.close();
            filesForMerging.add(mergedFile);
            tempFilesNumber++;
        }
        File result = filesForMerging.remove();
        result.renameTo(new File(tmpDirName + "/Result.txt"));
        return new File(tmpDirName + "/Result.txt");
    }
}
