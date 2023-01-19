import java.io.File;

public class TmpFilesManager {
    public void prepareTemporaryDir(String dirName) {
        File tempDir = new File(dirName);
        if (tempDir.exists()) {
            for (File file : tempDir.listFiles()) {
                file.delete();
            }
            tempDir.delete();
        }
        tempDir.mkdir();
    }
}
