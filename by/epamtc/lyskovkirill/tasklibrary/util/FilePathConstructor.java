package by.epamtc.lyskovkirill.tasklibrary.util;

import java.io.File;

public class FilePathConstructor {
    public static File computeFilePath(File path, String fileName) {
        if (path.isDirectory()) {
            File[] arr = path.listFiles();
            File srcPath = null;
            if (arr != null) {
                for (File f : arr) {
                    if (f.getName().equals("src"))
                        srcPath = f;
                }
            }
            if (srcPath != null)
                return computeFilePath(srcPath, fileName);
            else if (arr != null) {
                for (File f : arr) {

                    File found = computeFilePath(f, fileName);
                    if (found != null)
                        return found;
                }
            }
        } else {
            if (path.toPath().endsWith(fileName)) {
                return path;
            }
        }
        return null;
    }
}
