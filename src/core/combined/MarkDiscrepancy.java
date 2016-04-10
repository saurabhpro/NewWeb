package core.combined;

import core.model.FinalObjectModel;
import core.model.ProjectConstants;
import core.model.uploadedfiles.HrnetDetails;

import java.time.LocalDate;
import java.util.Map;

import static core.model.attendence.AttendanceStatusType.*;
import static core.model.attendence.LeaveType.NO_LEAVE;
import static core.model.attendence.LeaveType.WORK_FROM_HOME_IND;

/**
 * Created by AroraA on 17-02-2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 */

public class MarkDiscrepancy {
    public static Map<String, FinalObjectModel> EmpCombinedMap;

    public void findDiscrepancy() {
        EmpCombinedMap = CombineFile.EmpCombinedMap;
        for (FinalObjectModel finalObjectModel : EmpCombinedMap.values()) {
            // MarkDiscrepancy if an employee is absent and there is no entry in
            // Hrnet file.
            if (finalObjectModel.hrnetDetails == null) {
                for (int j = 0; j < ProjectConstants.getMONTH().maxLength(); j++) {
                    if ((finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE))
                            || (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY))) {
                        //  System.out.println("Null List- MarkDiscrepancy Set for " + finalObjectModel.getName() + " Date: " + (j + 1));
                        finalObjectModel.setIfClarificationNeeded(true);
                    }
                }

            } else {
                // case where there is an entry
                int flag;
                for (int j = 0; j < ProjectConstants.getMONTH().maxLength(); j++) {
                    flag = 0;
                    // his status is still absent after merging
                    if (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE)) {

                        int[] temp;
                        for (HrnetDetails hrnetDetail : finalObjectModel.hrnetDetails) {
                            temp = setClarificationStatus(j, finalObjectModel, hrnetDetail, "AbsentInOneNotInOther", 0);
                            j = temp[0];
                            flag = temp[1];
                            if (flag == 1)
                                break;
                        }
                        if (flag == 0) {
                            //  System.out.println("MarkDiscrepancy Set for " + finalObjectModel.getName() + " Date: " + (j + 1));
                            finalObjectModel.setIfClarificationNeeded(true);
                        }
                    }

                    // MarkDiscrepancy if there is an entry for an employee in both
                    // Biometric and Hrnet file.
                    else if (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT)) {
                        for (HrnetDetails hrnetDetail : finalObjectModel.hrnetDetails) {
                            setClarificationStatus(j, finalObjectModel, hrnetDetail, "PresentInBoth", 0);
                        }
                    }

                    // MarkDiscrepancy if an employee applies for half day but works
                    // for less than four hours.
                    else if (finalObjectModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY)) {

                        if (finalObjectModel.attendanceOfDate[j].getLeaveTypeForThisDate().equals(NO_LEAVE))
                            finalObjectModel.setIfClarificationNeeded(true);
                        else
                            for (HrnetDetails hrnetDetail : finalObjectModel.hrnetDetails) {
                                if (hrnetDetail.attendanceOfLeave.getStartDate().getDayOfMonth() == j + 1) {
                                    if ((finalObjectModel.attendanceOfDate[j].getWorkTimeForDay() == null)
                                            || (finalObjectModel.attendanceOfDate[j].getWorkTimeForDay().getHour() < 4)) {
                                        finalObjectModel.setIfClarificationNeeded(true);
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private int[] setClarificationStatus(int j, FinalObjectModel finalObjectModel, HrnetDetails hrnetDetail, String type, int flag) {
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
