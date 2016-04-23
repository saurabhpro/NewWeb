package core.utils;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Map;

/**
 * Created by Saurabh on 4/16/2016.
 */
public class Serialize {

    public static void serialSave(String fileName, Map object) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    @Nullable
    public static Object serialRetrieve(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object ob = in.readObject();
            in.close();
            fileIn.close();
            return ob;
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
