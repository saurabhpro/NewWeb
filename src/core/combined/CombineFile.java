package core.combined;

import core.appfilereader.BiometricFileWorker;
import core.appfilereader.HrnetFileWorker;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.employeemodal.BasicEmployeeDetails;
import core.model.viewmodal.FinalObjectModel;
import core.utils.RevalIdComparator;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import static core.model.ProjectConstants.getMONTH;
import static core.model.ProjectConstants.getNumberOfDaysConsideredInRespectiveMonth;
import static core.model.attendencemodal.AttendanceStatusType.*;

/**
 * Created by kumars on 2/16/2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 *
 * @author Saurabh
 * @version 1.5
 * @since 1.4 added revalidcomparator and loop till getNumberOfDaysConsideredInRespectiveMonth()
 */
public class CombineFile extends FinalObject {
	/**
	 * Constructor that initializes the Final Object Map with a Treemap with
	 * string Comparator
	 *
	 * @see FinalObject
	 */
	public CombineFile() {
		EmpCombinedMap = new TreeMap<>(new RevalIdComparator());
	}

	/**
	 * Method that updates Biometric File Object's missing data with information
	 * extracted from Financial Force File
	 *
	 * @see CombineFileHelperUtility
	 */
	public final void combineFiles() {

		// update the employeemodal in biometric file
		CombineFileHelperUtility.leaveCountAndHolidayUpdater();
		CombineFileHelperUtility.unaccountedAbsentStatusUpdater();

		// Combine Financial Force and Biometric Files object
		for (EmployeeBiometricDetails empObj : BiometricFileWorker.empBiometricMap.values()) {
			if (empObj.getNumberOfEntriesInHrNet() == 0) {
				EmpCombinedMap.put(empObj.getEmpId(),
						new FinalObjectModel(empObj.getEmpId(), 0, empObj.attendanceOfDate, null));
			} else {
				Set<String> hrKeySet = HrnetFileWorker.hrnetDetailsMap.keySet();

				for (String hrKey : hrKeySet) {
					String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());

					if (tempSalesForceId != null && hrKey.equals(tempSalesForceId)) {

						ArrayList<EmployeeHrnetDetails> hrnet = HrnetFileWorker.hrnetDetailsMap.get(hrKey);
						EmpCombinedMap.put(empObj.getEmpId(), new FinalObjectModel(empObj.getEmpId(),
								empObj.getNumberOfEntriesInHrNet(), empObj.attendanceOfDate, hrnet));
					}
				}
			}
		}

		EmpCombinedMap.values().forEach(this::countAttendanceStatusType);
	}

	/**
	 * Method to display the result of combining Biometric and Financial Force
	 * File Objects
	 */
	public void displayCombineFiles() {
		System.out.println(getMONTH());
		EmpCombinedMap.values().forEach(FinalObjectModel::displayFinalList);
	}

	private void countAttendanceStatusType(FinalObjectModel emp) {
		// to be removed
		for (int j = 0; j < getNumberOfDaysConsideredInRespectiveMonth(); j++) {

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

}