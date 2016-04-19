package core.model.appfilereadermodal;

import core.model.attendencemodal.AttendanceOfLeave;
import core.model.employeemodal.BasicEmployeeDetails;

import static core.model.ProjectConstants.*;

/**
 * Created by AroraA on 09-02-2016.
 */
public class EmployeeHrnetDetails extends BasicEmployeeDetails {
    public final AttendanceOfLeave attendanceOfLeave;

    public EmployeeHrnetDetails(String empId, String name, AttendanceOfLeave attendanceOfLeave) {
        setEmpId(empId);
        setName(name);
        this.attendanceOfLeave = attendanceOfLeave;
    }

    public void printHrNetDetail() {
        System.out.print(EMP_FINANCIAL_FORCE_ID + " : " + this.getEmpId());
        System.out.print("\t" + EMP_NAME + " : " + this.getName());
        System.out.print("\t" + EMP_LEAVE_REQUEST_TYPE + " : " + this.attendanceOfLeave.getLeaveType());
        System.out.print("\t" + EMP_LEAVE_START_DATE + " : " + this.attendanceOfLeave.getStartDate() + "\t" + EMP_LEAVE_END_DATE + " : " + this.attendanceOfLeave.getEndDate());
        System.out.println("\t" + EMP_NUM_LEAVE_REQUESTED + " : " + this.attendanceOfLeave.getAbsenceTime());
    }
}
