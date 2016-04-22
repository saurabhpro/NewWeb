package core.utils;

import core.model.appfilereadermodal.EmployeeBiometricDetails;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Map;

/**
 * Created by Saurabh on 4/16/2016.
 */
public class Serialize {

    public static void serialSave(String fileName, Map<String, EmployeeBiometricDetails> object) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName + ".ser");
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
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object ob = in.readObject();
            Map<String, EmployeeBiometricDetails> bmp = (Map<String, EmployeeBiometricDetails>) ob;
            bmp.values().forEach(EmployeeBiometricDetails::printEmpBiometricDetails);

            System.out.println("\ndone\n");
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
