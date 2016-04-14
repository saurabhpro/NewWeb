package core.jxcel;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Saurabh on 4/14/2016.
 */
public class FileFolderWorker {
    public static void main(String[] args) {
        File currentDir = new File("C:\\ProjectFiles"); // current directory
        //  getDirectoryContents(currentDir, extensions);
        cleanDirectory(currentDir);
    }

    @Contract("_, _ -> !null")
    public static List<File> getDirectoryContents(File dir, String[] extensions) {
        /* try{
        for (File file : files) {
            System.out.println("file: " + file.getCanonicalPath());
        }} catch (IOException e) {
            e.printStackTrace();
        }*/
        return (List<File>) FileUtils.listFiles(dir, extensions, true);
    }

    public static void cleanDirectory(File currentDir) {
        try {
            FileUtils.cleanDirectory(currentDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPathToFile(String directoryPath) {
        File currentDir = new File(directoryPath);

        String[] extensions = new String[]{"xls", "xlsx"};
        List<File> files = getDirectoryContents(currentDir, extensions);
        String pathToFile = null;
        try {
            pathToFile = files.get(0).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathToFile;
    }

    public static long getLastModifiedDateOfFile(String filePath) {
        File file = new File(filePath);
        return file.lastModified();
    }

    @NotNull
    public static String getName(String filePath) {
        return new File(filePath).getName();
    }
}
