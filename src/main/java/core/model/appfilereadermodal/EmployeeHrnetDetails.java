package core.model.appfilereadermodal;

import core.model.attendencemodal.AttendanceOfLeave;
import core.model.employeemodal.BasicEmployeeDetails;

import static core.model.ProjectConstants.*;

/**
 * Created by AroraA on 09-02-2016.
 */
public class EmployeeHrnetDetails extends BasicEmployeeDetails {
	final long serialVersionUID = 1L;
	/**
	 * the object of the Attendance of leave which
	 */
	public AttendanceOfLeave attendanceOfLeave;

	public EmployeeHrnetDetails() {
	}

	/**
	 * EmployeeHrnetDetails constructor that take following arguments
	 *
	 * @param empId             The Employee Ids generated by Reval India for its employee
	 *                          [Unique for each employee in India]
	 * @param name              The name of the Employee in Reval India
	 * @param attendanceOfLeave The overall details of attendance record read
	 *                          from the Biometric File. It has all day details of that
	 *                          particular month whose biometric file has been uploaded
	 */
	public EmployeeHrnetDetails(String empId, String name, AttendanceOfLeave attendanceOfLeave) {
		setEmpId(empId);
		setName(name);
		this.attendanceOfLeave = attendanceOfLeave;
	}

	/**
	 * Method to display the contents read till reading of the Financial-Force
	 * file
	 *
	 * @implNote Remove this from the production release version
	 */
	public void displayHrNetDetail() {
		System.out.print(EMP_FINANCIAL_FORCE_ID + " : " + this.getEmpId());
		System.out.print("\t" + EMP_NAME + " : " + this.getName());
		System.out.print("\t" + EMP_LEAVE_REQUEST_TYPE + " : " + this.attendanceOfLeave.getLeaveType());
		System.out.print("\t" + EMP_LEAVE_START_DATE + " : " + this.attendanceOfLeave.getStartDate() + "\t"
				+ EMP_LEAVE_END_DATE + " : " + this.attendanceOfLeave.getEndDate());
		System.out.println("\t" + EMP_NUM_LEAVE_REQUESTED + " : " + this.attendanceOfLeave.getAbsenceTime());
	}
}