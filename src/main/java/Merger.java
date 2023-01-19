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

    protected void addRemainingValues(List<Integer> result, Iterator<Integer> iterator) {
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
    }

    public List<Integer> merge(List<Integer> leftHalf, List<Integer> rightHalf) {
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> leftIterator = leftHalf.iterator();
        Iterator<Integer> rightIterator = rightHalf.iterator();
        if (!leftIterator.hasNext()) {
            addRemainingValues(result, rightIterator);
            return result;
        }
        if (!rightIterator.hasNext()) {
            addRemainingValues(result, leftIterator);
            return result;
        }
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

    public List<Integer> merge(InputStream inputStream1, InputStream inputStream2, long valuesToRead) throws Exception {
        return merge(numIO.read(inputStream1, valuesToRead), numIO.read(inputStream2, valuesToRead));
    }

    public List<Integer> merge(Scanner scanner1, Scanner scanner2, long valuesToRead) {
        return merge(numIO.read(scanner1, valuesToRead), numIO.read(scanner2, valuesToRead));
    }

    public File merge(Queue<File> filesForMerging, long memoryLimit) throws Exception {
        String tmpDirName = "./tmpMerger";
        tmpFilesManager.prepareTemporaryDir(tmpDirName);
        int tempFilesNumber = 0;
        while (filesForMerging.size() != 1) {
            File firstFile = filesForMerging.remove();
            File secondFile = filesForMerging.remove();
            System.out.println("Files for merging size : " + filesForMerging.size());
            Scanner firstScanner = new Scanner(firstFile);
            Scanner secondScanner = new Scanner(secondFile);
            File mergedFile = new File(tmpDirName + "/MergedFile_" + tempFilesNumber + ".txt");
            PrintWriter mergingPrintWriter = new PrintWriter(new FileOutputStream(mergedFile, true));
            mergedFile.createNewFile();
            while (firstScanner.hasNextInt() || secondScanner.hasNextInt()) {
                List<Integer> merged = merge(firstScanner, secondScanner, memoryLimit);
                System.out.println("Merged : ");
                System.out.println(merged);
                numIO.write(merged, mergingPrintWriter);
                System.out.println("Cycle continues");
            }
            System.out.println("=============Cycle ended");
            mergingPrintWriter.close();
            filesForMerging.add(mergedFile);
            tempFilesNumber++;
//            firstFile.delete();
//            secondFile.delete();
        }
        File result = filesForMerging.remove();
        result.renameTo(new File(tmpDirName + "/Result.txt"));
        return new File(tmpDirName + "/Result.txt");
    }
}
