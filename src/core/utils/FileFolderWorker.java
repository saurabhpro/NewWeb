package core.utils;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Saurabh on 4/14/2016.
 *
 * @author Saurabh
 * @version 1.0
 */
public class FileFolderWorker {
    private FileFolderWorker() {
    }


    /**
     * Get exact path inclusive of the filename passed as argument
     *
     * @param directoryPath the Top Level path inside which out file is stored
     * @return the canonical/absolute path to the desired file
     */
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


    /**
     * @param dir        the Top level directory which is to be parsed for listing the file inside it
     * @param extensions the filter on extentions a file has, only these filtered files are listed
     * @return the list of all the files in the Top Level directory
     */
    @Contract("_, _ -> !null")
    public static List<File> getDirectoryContents(File dir, String[] extensions) {
        return (List<File>) FileUtils.listFiles(dir, extensions, true);
    }

    /**
     * Method to clean the drive to only persist one recently uploaded file
     *
     * @param currentDir the Top level directory on which we perform the cleaning.
     */
    public static void cleanDirectory(File currentDir) {
        try {
            FileUtils.cleanDirectory(currentDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDirectory(File pathToDir) {
        try {
            FileUtils.forceMkdir(pathToDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
