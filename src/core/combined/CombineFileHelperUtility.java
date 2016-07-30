package core.combined;

import core.appfilereader.AllEmployeesBasicData;
import core.appfilereader.BiometricFileWorker;
import core.appfilereader.HrnetFileWorker;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.attendencemodal.AttendanceStatusType;
import core.model.attendencemodal.HolidaysList;
import core.model.attendencemodal.LeaveType;
import core.model.employeemodal.BasicEmployeeDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static core.model.ProjectConstants.*;
import static core.model.attendencemodal.AttendanceStatusType.*;

/**
 * Created by Saurabh on 4/9/2016.
 * Helper Utility class for CombineFile
 *
 * @author Amrita & Saurabh
 * @version 1.3
 * @since 1.3 added boundaryDate for currentMonth efficient leave setting
 */
class CombineFileHelperUtility {
	private CombineFileHelperUtility() {
	}

	/**
	 * Method to process the Biometric File Object and update the Number of
	 * Leaves Of Each Employee, Holiday [PUBLIC_HOLIDAY] marker,
	 */
	static void leaveCountAndHolidayUpdater() {
		// set number of leaves employee took, who are present in Biometric File
		BiometricFileWorker.empBiometricMap.keySet()
				.forEach(CombineFileHelperUtility::setNumberOfLeavesForEachEmployee);
		// set holidays for that month for each employeemodal
		BiometricFileWorker.empBiometricMap.values()
				.forEach((empObj) -> CombineFileHelperUtility.holidayStatusUpdater(empObj));
	}

	/**
	 * Method to process the Biometric File Object and update the unaccounted
	 * absence status to present or half-day with Financial Force object
	 * information
	 */
	static void unaccountedAbsentStatusUpdater() {
		// this worked just fine for January
		BiometricFileWorker.empBiometricMap.values().forEach(CombineFileHelperUtility::absentStatusUpdater);
		// update number of work hours for half day
		// just replace CombineFileHelperUtility with this, if the method is npt
		// static in this class
		BiometricFileWorker.empBiometricMap.values()
				.forEach(CombineFileHelperUtility::setAbsentToUnaccountedAndHalfDayWorkTime);
	}

	private static void setNumberOfLeavesForEachEmployee(String bEmpID) {
		String salesForce = null;
		// Set the number of leaves applied

		if (AllEmployeesBasicData.allEmployeeBasicRecordMap.containsKey(bEmpID))
			salesForce = new BasicEmployeeDetails().getSalesForceId(bEmpID);

		// setting the number of leaves after counting the list
		final String finalSalesForce = salesForce;
		HrnetFileWorker.hrnetDetailsMap.keySet().stream().filter(hr -> hr.equals(finalSalesForce))
				.forEach(hr -> BiometricFileWorker.empBiometricMap.get(bEmpID)
						.setNumberOfEntriesInHrNet(HrnetFileWorker.hrnetDetailsMap.get(hr).size()));

	}

	private static void holidayStatusUpdater(EmployeeBiometricDetails empObj) {
		// set public holiday status
		for (HolidaysList h : HolidaysList.values())
			if (h.getDate().getMonth() == getMONTH())
				empObj.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(PUBLIC_HOLIDAY);
	}

	private static void absentStatusUpdater(EmployeeBiometricDetails empObj) {
		Set<Map.Entry<String, ArrayList<EmployeeHrnetDetails>>> hrDataSet = HrnetFileWorker.hrnetDetailsMap
				.entrySet();

		for (Map.Entry<String, ArrayList<EmployeeHrnetDetails>> hrEntry : hrDataSet) {
			String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());

			if (tempSalesForceId != null && tempSalesForceId.equals(hrEntry.getKey())) {
				// update emoObj using the details from hr
				for (EmployeeHrnetDetails hr : hrEntry.getValue()) {
					updateAttendanceUsingLeaveType(hr, empObj);
				}
			}
		}
		//TODO add case for present in biometric and has entry in salesforce
	}// end of function


	private static void updateAttendanceUsingLeaveType(EmployeeHrnetDetails hr,
	                                                                  EmployeeBiometricDetails empObj) {

		double leaveTime = hr.attendanceOfLeave.getAbsenceTime();
		LocalDate tempStart = hr.attendanceOfLeave.getStartDate();
		LocalDate tempEnd = hr.attendanceOfLeave.getEndDate();
		int boundaryDate = getBiometricFileGenerationDate().getDayOfYear();

		int thisDate;
		while (leaveTime > 0 && tempStart.getMonth().equals(getMONTH()) && tempStart.getDayOfYear() <= boundaryDate) {
			thisDate = tempStart.getDayOfMonth() - 1;

			if (leaveTime == 0.5) {
				empObj.attendanceOfDate[thisDate].setAttendanceStatusType(PRESENT);
				// second point of update for Half_Day

			} else if (hr.attendanceOfLeave.getLeaveType() == LeaveType.WORK_FROM_HOME_IND) {
				empObj.attendanceOfDate[thisDate].setWorkTimeForDay(WORK_HOURS_GIVEN_FOR_WORK_FROM_HOME);
				// set work from home as 9 hours
				empObj.attendanceOfDate[thisDate].setAttendanceStatusType(PRESENT);
			}

			empObj.attendanceOfDate[thisDate].setLeaveTypeForThisDate(hr.attendanceOfLeave.getLeaveType());
			// update the AttendanceStatusType to ON_LEAVE for leaves applied on salesforce
			if (!empObj.attendanceOfDate[thisDate].getAttendanceStatusType().equals(HALF_DAY))
				empObj.attendanceOfDate[thisDate].setAttendanceStatusType(AttendanceStatusType.ON_LEAVE);

			if (tempStart.equals(tempEnd))
				break;
			leaveTime--;
			tempStart = tempStart.plusDays(1);
		}
	}

	private static void setAbsentToUnaccountedAndHalfDayWorkTime(EmployeeBiometricDetails empObj) {
		for (int i = 0; i < getNumberOfDaysConsideredInRespectiveMonth(); i++) {

			// 06-03-2016 changed the Type from ABSENT to UNACCOUNTED_ABSENCE.
			if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(ABSENT)) {
				empObj.attendanceOfDate[i].setAttendanceStatusType(UNACCOUNTED_ABSENCE);
			} else if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(HALF_DAY)) {
				//if case - when employee has half day
				LocalTime time = empObj.attendanceOfDate[i].getWorkTimeForDay();


				if (time == null)
					empObj.attendanceOfDate[i].setWorkTimeForDay(WORK_HOURS_FOR_HALF_DAY);
				else if (empObj.attendanceOfDate[i].getLeaveTypeForThisDate() == LeaveType.WORK_FROM_HOME_IND)
					empObj.attendanceOfDate[i].setWorkTimeForDay(time.plusHours(WORK_HOURS_FOR_HALF_DAY.getHour()));
			}
		}
	}
}
