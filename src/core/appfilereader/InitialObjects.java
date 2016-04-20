package core.appfilereader;

import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.employeemodal.BasicEmployeeDetails;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kumars on 4/19/2016.
 */
class InitialObjects {
    private static final long serialVersionUID = 1L;

    public static Map<String, EmployeeBiometricDetails> empBiometricMap;

    public static Map<String, ArrayList<EmployeeHrnetDetails>> hrnetDetails;

    public static Map<String, BasicEmployeeDetails> allEmployeeRecordMap;
}
