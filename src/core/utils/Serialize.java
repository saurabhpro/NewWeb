package core.utils;

import core.model.ProjectConstants;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * Created by Saurabh on 4/16/2016.
 */
public class Serialize {

    public static Object choose(Object ob, String fileName) {
        if (ob == null) {
            return serialRetrieve(fileName);
        } else serialSave(fileName, ob);
        return ob;
    }

    public static void serialSave(String fileName, Object object) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(ProjectConstants.UPDATED_RECORD_OBJECTS + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employeemodal.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    @Nullable
    public static Object serialRetrieve(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(ProjectConstants.UPDATED_RECORD_OBJECTS + fileName);
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
