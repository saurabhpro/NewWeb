/**
 * Created by Saurabhk on 08-02-2016.
 */

package core.model.uploadedfiles;

import core.model.ProjectConstants;
import core.model.attendence.AttendanceOfDate;
import core.model.empl.BasicEmployeeDetails;

import java.time.LocalTime;

import static core.model.ProjectConstants.*;

public class EmpBiometricDetails extends BasicEmployeeDetails {
    public final AttendanceOfDate[] attendanceOfDate;
    public int numberOfEntriesInHrNet = 0; // To check how many leaves have been applied

    public EmpBiometricDetails(String eID, String eName, AttendanceOfDate[] attendanceOfDate) {
        setName(eName);
        setEmpId(eID);
        this.attendanceOfDate = attendanceOfDate;
    }

    public int getNumberOfEntriesInHrNet() {
        return numberOfEntriesInHrNet;
    }

    public void setNumberOfEntriesInHrNet(int numberOfEntriesInHrNet) {
        this.numberOfEntriesInHrNet = numberOfEntriesInHrNet;
    }

    public void printEmpBiometricDetails() {
        System.out.println(EMP_NAME + " : " + this.getName());
        System.out.println(EMP_REVAL_IND_ID + " : " + this.getEmpId());

        LocalTime workTime;

        for (int j = 0; j < getMONTH().maxLength(); j++) {
            System.out.print(CURRENT_DATE + " : " + this.attendanceOfDate[j].getCurrentDate());
            System.out.print("\t" + EMP_CHECK_IN + " : " + this.attendanceOfDate[j].getCheckIn());
            System.out.print("\t+" + EMP_CHECK_OUT + " : " + this.attendanceOfDate[j].getCheckOut());
            System.out.print("\t" + EMP_ATTENDANCE_STATUS_TYPE + " : " + this.attendanceOfDate[j].getAttendanceStatusType() + "\n");

            workTime = this.attendanceOfDate[j].getWorkTimeForDay();
            if (workTime != null)
                System.out.println(EMP_WORK_HOURS_FOR_THIS_DAY + " : " + workTime);

        }

        System.out.println();
    }
}
