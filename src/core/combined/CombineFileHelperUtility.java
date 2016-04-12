package core.combined;

import core.emplmasterrecord.EmployeeMasterData;
import core.model.ProjectConstants;
import core.model.attendence.AttendanceStatusType;
import core.model.attendence.HolidaysList;
import core.model.attendence.LeaveType;
import core.model.empl.BasicEmployeeDetails;
import core.model.uploadedfiles.EmpBiometricDetails;
import core.model.uploadedfiles.HrnetDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static core.combined.CombineFile.empBiometricDetails;
import static core.combined.CombineFile.empHrnetDetails;
import static core.model.attendence.AttendanceStatusType.*;

/**
 * Created by Saurabh on 4/9/2016.
 */
class CombineFileHelperUtility {
    private CombineFileHelperUtility() {
    }

    static void preRequiredTasks() {
        empBiometricDetails.keySet().forEach(CombineFileHelperUtility::setNumberOfLeavesForEachEmployee);
        //set holidays for that month for each employee
        empBiometricDetails.values().forEach((empObj) -> CombineFileHelperUtility.holidayStatusUpdater(empObj));
        // this worked just fine for January
        empBiometricDetails.values().forEach(CombineFileHelperUtility::absentStatusUpdater);
        // update number of work hours for half day
        //just replace CombineFileHelperUtility with this, if the method is in this class
        empBiometricDetails.values().forEach(CombineFileHelperUtility::setAbsentToUnaccountedAndHalfDayWorkTime);
    }


    private static void setNumberOfLeavesForEachEmployee(String bEmpID) {
        String salesForce = null;
        // Set the number of leaves applied

        if (EmployeeMasterData.allEmployeeRecordMap.containsKey(bEmpID))
            salesForce = new BasicEmployeeDetails().getSalesForceId(bEmpID);

        // setting the number of leaves after counting the list
        final String finalSalesForce = salesForce;
        empHrnetDetails.keySet().stream().filter(hr -> hr.equals(finalSalesForce))
                .forEach(hr -> empBiometricDetails.get(bEmpID).setNumberOfEntriesInHrNet(empHrnetDetails.get(hr).size()));

    }

    private static void holidayStatusUpdater(EmpBiometricDetails empObj) {
        // set public holiday status
        for (HolidaysList h : HolidaysList.values())
            if (h.getDate().getMonth() == ProjectConstants.getMONTH())
                empObj.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(PUBLIC_HOLIDAY);
    }

    private static void absentStatusUpdater(EmpBiometricDetails empObj) {
        for (int i = 0; i < ProjectConstants.getMONTH().maxLength(); i++) {

            switch (empObj.attendanceOfDate[i].getAttendanceStatusType()) {
                case ABSENT:
                    // check for Work from home and half day
                    Set<Map.Entry<String, ArrayList<HrnetDetails>>> hrDataSet = empHrnetDetails.entrySet();

                    for (Map.Entry<String, ArrayList<HrnetDetails>> hrEntry : hrDataSet) {
                        String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());

                        if (tempSalesForceId != null && tempSalesForceId.equals(hrEntry.getKey())) {
                            for (HrnetDetails hr : hrEntry.getValue()) {
                                updateAbsentStatusWithLeaveToPresentOrHalfDay(hr, empObj); //update emoObj using the details from hr
                                updateAttendanceUsingLeaveType(hr, empObj);
                            }
                        }
                    }
                    break;
            }//end of switch
        }//end of for loop
    }//end of function

    private static void setAbsentToUnaccountedAndHalfDayWorkTime(EmpBiometricDetails empObj) {
        for (int i = 0; i < ProjectConstants.getMONTH().maxLength(); i++) {
            // 06-03-2016 changed the Type from ABSENT to UNACCOUNTED_ABSENCE.
            if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(ABSENT)) {
                empObj.attendanceOfDate[i].setAttendanceStatusType(UNACCOUNTED_ABSENCE);
            } else if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(HALF_DAY)) {
                LocalTime time = empObj.attendanceOfDate[i].getWorkTimeForDay();
                if (time == null)
                    empObj.attendanceOfDate[i].setWorkTimeForDay(LocalTime.of(4, 0));
                else if (empObj.attendanceOfDate[i].getLeaveTypeForThisDate() == LeaveType.WORK_FROM_HOME_IND)
                    empObj.attendanceOfDate[i].setWorkTimeForDay(time.plusHours(4));
            }
        }
    }

    private static void updateAbsentStatusWithLeaveToPresentOrHalfDay(HrnetDetails hr, EmpBiometricDetails empObj) {

        double leaveTime = hr.attendanceOfLeave.getAbsenceTime();
        LocalDate tempStart = hr.attendanceOfLeave.getStartDate();
        LocalDate tempEnd = hr.attendanceOfLeave.getEndDate();

        int changeDatesRange;
        do {
            changeDatesRange = tempStart.getDayOfMonth() - 1;

            if (leaveTime == 0.5) {
                empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(HALF_DAY);        //second point of update for Half_Day

            } else if (hr.attendanceOfLeave.getLeaveType() == LeaveType.WORK_FROM_HOME_IND) {
                empObj.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of(6, 0));
                // set work from home as 6 hours
                empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(PRESENT);
            }
            if (tempStart.equals(tempEnd)) break;
            leaveTime--;
            tempStart = tempStart.plusDays(1);
        } while (leaveTime > 0 && tempStart.getMonth().equals(ProjectConstants.getMONTH()));
    }

    private static void updateAttendanceUsingLeaveType(HrnetDetails hr, EmpBiometricDetails empObj) {
        LocalDate tempStart = hr.attendanceOfLeave.getStartDate();
        LocalDate tempEnd = hr.attendanceOfLeave.getEndDate();
        int changeDatesRange;
        do {
            changeDatesRange = tempStart.getDayOfMonth() - 1;
            empObj.attendanceOfDate[changeDatesRange].setLeaveTypeForThisDate(hr.attendanceOfLeave.getLeaveType());

            //update the AttendanceStatusType to ON_LEAVE for leavs applied on salesforce
            empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(AttendanceStatusType.ON_LEAVE);

            if (tempStart.equals(tempEnd))
                break;

            tempStart = tempStart.plusDays(1);
        } while (tempStart.getMonth().equals(ProjectConstants.getMONTH()));
    }


}
