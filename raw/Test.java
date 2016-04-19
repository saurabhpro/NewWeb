package core.appfilereader;

import servlets.filegenerator.excel.CreateSingleRecordExcel;

import java.io.File;
import java.io.IOException;

/**
 * Created by Saurabh on 4/8/2016.
 */
public class Test {
    public static void showPaths(String pathName) throws IOException {
        File file = new File(pathName);
        System.out.println("path: " + file.getPath());
        System.out.println("absolute path: " + file.getAbsolutePath());
        System.out.println("canonical path: " + file.getCanonicalPath());
        System.out.println();
    }

    public static void main(String[] s) throws IOException {
      /*  File file = new File(new File("test.txt").getAbsolutePath());
        String parent = file.getParent();
        File parentFile = new File(parent);
        String parentName = parentFile.getName();
        String grandparent = parentFile.getParent();
        file.createNewFile();

        showPaths("test.txt");
        showPaths("TEST.TXT");
        showPaths("." + File.separator + "TEST.TXT");

        showPaths(parent
                + File.separator + "."
                + File.separator + "test.txt");

        showPaths(grandparent
                + File.separator + parentName
                + File.separator + ".."
                + File.separator + parentName
                + File.separator + "test.txt");*/

        CreateSingleRecordExcel.fromJsonToExcel("C:\\ProjectFiles\\text.xlsx", "R1", "AllWorkers");
    }
}