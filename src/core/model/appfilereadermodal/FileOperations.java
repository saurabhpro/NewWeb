package core.model.appfilereadermodal;

import core.model.employeemodal.BasicEmployeeDetails;

/**
 * Created by Saurabh on 3/6/2016.
 */
public interface FileOperations {
    void displayFile();

    /**
     * @param ob The object is supposed to be an generic object that can be down-casted to any
     *           desirable file type object
     */
    void readFile(BasicEmployeeDetails ob);
}
