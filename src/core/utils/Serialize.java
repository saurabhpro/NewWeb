package core.utils;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Map;

/**
 * Created by Saurabh on 4/16/2016.
 * Class to perform Serialization and Deserialization
 *
 * @author Saurabh
 * @version 1.0
 */
public class Serialize {

    /**
     * Method to serialize the mapToBeSerialized passed as argument to pathAndFileName also passed as argument
     *
     * @param pathAndFileName   the path and file name where the serial mapToBeSerialized will be written
     * @param mapToBeSerialized the non-type bounded map that receives different maps which are to be serialized
     */
    public static void serialSave(String pathAndFileName, Map mapToBeSerialized) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(pathAndFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mapToBeSerialized);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    /**
     * Method read the serial object from fileAndPathName argument and return the object in non-specific Object form
     *
     * @param fileAndPathName the file and path from where we need to pick up the serial object
     * @return the object which were stored as Objects instead of IntialObjects during serilization
     */
    @Nullable
    public static Object serialRetrieve(String fileAndPathName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileAndPathName);
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
