package core.combined;

import core.jxcel.BiometricFileWorker;
import core.jxcel.HrnetFileWorker;
import core.model.FinalObjectModel;
import core.model.empl.BasicEmployeeDetails;
import core.model.uploadedfiles.EmpBiometricDetails;
import core.model.uploadedfiles.HrnetDetails;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static core.model.ProjectConstants.getMONTH;
import static core.model.attendence.AttendanceStatusType.*;

/**
 * Created by kumars on 2/16/2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 */
public class CombineFile {

    // Comparator needs string as Type
    public static Map<String, FinalObjectModel> EmpCombinedMap;

    static Map<String, ArrayList<HrnetDetails>> empHrnetDetails;
    static Map<String, EmpBiometricDetails> empBiometricDetails;

    public CombineFile() {
        EmpCombinedMap = new TreeMap<>(String::compareTo);
        empBiometricDetails = BiometricFileWorker.empList;
        empHrnetDetails = HrnetFileWorker.hrnetDetails;
    }

    public void combineFiles() {

        CombineFileHelperUtility.preRequiredTasks();

        // update the basic employee biometric file
        BiometricFileWorker.empList = empBiometricDetails;

        // Combine Hrnet and Biometric Files
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            if (empObj.getNumberOfEntriesInHrNet() == 0) {
                EmpCombinedMap.put(empObj.getEmpId(),
                        new FinalObjectModel(empObj.getEmpId(), empObj.numberOfEntriesInHrNet, empObj.attendanceOfDate, null));
            } else {
                Set<String> hrKeySet = empHrnetDetails.keySet();

                for (String hrKey : hrKeySet) {
                    String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());

                    if (tempSalesForceId != null && hrKey.equals(tempSalesForceId)) {

                        ArrayList<HrnetDetails> hrnet = empHrnetDetails.get(hrKey);
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