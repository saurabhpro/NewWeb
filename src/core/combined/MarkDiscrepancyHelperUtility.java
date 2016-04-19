package core.combined;

import core.model.ProjectConstants;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.viewmodal.FinalObjectModel;

import java.time.LocalDate;

import static core.model.attendencemodal.AttendanceStatusType.*;
import static core.model.attendencemodal.LeaveType.NO_LEAVE;
import static core.model.attendencemodal.LeaveType.WORK_FROM_HOME_IND;

/**
 * Created by Saurabh on 4/10/2016.
 */
class MarkDiscrepancyHelperUtility {
    private MarkDiscrepancyHelperUtility() {
    }

    static void setIfEntryPresentInEither(FinalObjectModel finalObjectModel) {
        int flag;
        for (int j = 0; j < ProjectConstants.getMONTH().maxLength(); j++) {
            flag = 0;
            // his status is still absent after merging
            if (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE)) {

                int[] temp;
                for (EmployeeHrnetDetails hrnetDetail : finalObjectModel.employeeHrnetDetails) {
                    temp = setClarificationStatus(j, finalObjectModel, hrnetDetail, "AbsentInOneNotInOther", 0);
                    j = temp[0];
                    flag = temp[1];
                    if (flag == 1)
                        break;
                }
                if (flag == 0) {
                    finalObjectModel.setIfClarificationNeeded(true);
                }
            }

            // MarkDiscrepancy if there is an entry for an employeemodal in both
            // Biometric and Hrnet file.
            else if (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT)) {
                for (EmployeeHrnetDetails hrnetDetail : finalObjectModel.employeeHrnetDetails)
                    setClarificationStatus(j, finalObjectModel, hrnetDetail, "PresentInBoth", 0);
            }

            // MarkDiscrepancy if an employeemodal applies for half day but works
            // for less than four hours.
            else if (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY)) {
                setIfHalfDayLessThanSixHoursWork(finalObjectModel, j);
            }
        }
    }

    static void setIfAbsentButNoLeaveApplied(FinalObjectModel finalObjectModel) {
        for (int j = 0; j < ProjectConstants.getMONTH().maxLength(); j++) {
            if ((finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE))
                    || (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY))) {
                finalObjectModel.setIfClarificationNeeded(true);
            }
        }
    }

    private static void setIfHalfDayLessThanSixHoursWork(FinalObjectModel f, int j) {
        //Half day worked but no leave applied
        if (f.attendanceOfDate[j].getLeaveTypeForThisDate().equals(NO_LEAVE))
            f.setIfClarificationNeeded(true);
        else
        /*
         for (EmployeeHrnetDetails hrnetDetail : f.employeeHrnetDetails) {
         if (hrnetDetail.attendanceOfLeave.getStartDate().getDayOfMonth() == j + 1) {
         if ((f.attendanceOfDate[j].getWorkTimeForDay() == null)
         || (f.attendanceOfDate[j].getWorkTimeForDay().getHour() < 4)) {
         f.setIfClarificationNeeded(true);
         }
         }
         }
         */
            f.employeeHrnetDetails.stream()
                    .filter(hrnet -> hrnet.attendanceOfLeave.getStartDate().getDayOfMonth() == j + 1)
                    .filter(hrnet -> (f.attendanceOfDate[j].getWorkTimeForDay() == null)
                            || (f.attendanceOfDate[j].getWorkTimeForDay().getHour() < 4))
                    .forEach(hrnetDetail -> f.setIfClarificationNeeded(true));

    }

    private static int[] setClarificationStatus(int j, FinalObjectModel finalObjectModel, EmployeeHrnetDetails hrnetDetail, String type, int flag) {
        LocalDate startDate = hrnetDetail.attendanceOfLeave.getStartDate();
        LocalDate endDate = hrnetDetail.attendanceOfLeave.getEndDate();

        int beginHoliday = startDate.getDayOfMonth();

        if (beginHoliday == (j + 1)) {
            flag = 1;

            while (startDate.compareTo(endDate) <= 0) {
                switch (type) {

                    case "AbsentInOneNotInOther":
                        j++;
                        break;

                    case "PresentInBoth":
                        if (!hrnetDetail.attendanceOfLeave.getLeaveType().equals(WORK_FROM_HOME_IND)) {
                            //      System.out.println("MarkDiscrepancy set for present: " + finalObjectModel.getName() + " Date:" + (j + 1));
                            finalObjectModel.setIfClarificationNeeded(true);
                        }
                        break;
                }
                startDate = startDate.plusDays(1);
            }
        }
        return new int[]{j, flag};
    }

}
