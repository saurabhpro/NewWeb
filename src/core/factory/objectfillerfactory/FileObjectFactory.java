package core.factory.objectfillerfactory;

import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.employeemodal.BasicEmployeeDetails;

/**
 * Created by kumars on 4/20/2016.
 */
public class FileObjectFactory {

    public BasicEmployeeDetails dispatch(String type) {
        switch (type) {
            case "Biometric":
                return new EmployeeBiometricDetails();
            case "Hrnet":
                return new EmployeeHrnetDetails();
        }
        return null;
    }

}
