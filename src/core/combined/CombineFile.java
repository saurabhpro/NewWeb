package core.combined;

import core.appfilereader.BiometricFileWorker;
import core.appfilereader.HrnetFileWorker;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.employeemodal.BasicEmployeeDetails;
import core.model.viewmodal.FinalObjectModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static core.model.ProjectConstants.getMONTH;
import static core.model.attendencemodal.AttendanceStatusType.*;

/**
 * Created by kumars on 2/16/2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 */
public class CombineFile extends FinalObject implements Serializable {

    static Map<String, ArrayList<EmployeeHrnetDetails>> empHrnetDetails;
    static Map<String, EmployeeBiometricDetails> empBiometricDetails;

    public CombineFile() {
        EmpCombinedMap = new TreeMap<>(String::compareTo);
        empBiometricDetails = BiometricFileWorker.empBiometricMap;
        empHrnetDetails = HrnetFileWorker.hrnetDetails;
    }

    public void combineFiles() {

        CombineFileHelperUtility.preRequiredTasks();

        // update the basic employeemodal biometric file
        //  BiometricFileWorker.empBiometricMap = empBiometricDetails;

        // Combine Hrnet and Biometric Files
        for (EmployeeBiometricDetails empObj : empBiometricDetails.values()) {
            if (empObj.getNumberOfEntriesInHrNet() == 0) {
                EmpCombinedMap.put(empObj.getEmpId(),
                        new FinalObjectModel(empObj.getEmpId(), empObj.numberOfEntriesInHrNet, empObj.attendanceOfDate, null));
            } else {
                Set<String> hrKeySet = empHrnetDetails.keySet();

                for (String hrKey : hrKeySet) {
                    String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());

                    if (tempSalesForceId != null && hrKey.equals(tempSalesForceId)) {

                        ArrayList<EmployeeHrnetDetails> hrnet = empHrnetDetails.get(hrKey);
                        EmpCombinedMap.put(empObj.getEmpId(), new FinalObjectModel(empObj.getEmpId(), empObj.numberOfEntriesInHrNet,
                                empObj.attendanceOfDate, hrnet));
                    }
                }
            }
        }

        EmpCombinedMap.values().forEach(this::countAttendanceStatusType);
    }

    private void countAttendanceStatusType(FinalObjectModel emp) {
        // to be removed today
        for (int j = 0; j < getMONTH().maxLength(); j++) {

            // AMRITA
            if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE))
                emp.setCount(0);
            else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT))
                emp.setCount(1);
            else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(PUBLIC_HOLIDAY))
                emp.setCount(2);
            else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(WEEKEND_HOLIDAY))
                emp.setCount(3);
            else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY))
                emp.setCount(4);
        }

    }

    public void displayCombineFiles() {
        System.out.println(getMONTH());
        EmpCombinedMap.values().forEach(FinalObjectModel::displayFinalList);
    }

}