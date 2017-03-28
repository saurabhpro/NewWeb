package core.model.attendencemodal;

import core.utils.TimeManager;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static core.model.ProjectConstants.MIN_WORK_HOURS_FOR_FULL_DAY;
import static core.model.ProjectConstants.WORK_HOURS_FOR_HALF_DAY;
import static core.model.attendencemodal.AttendanceStatusType.*;

/**
 * Created by kumars on 2/12/2016.
 *
 * @author Saurabh
 * @version 1.3
 */
public class AttendanceOfDate implements Serializable {
	private LocalDate currentDate = null;
	private LocalTime checkIn = null;
	private LocalTime checkOut = null;
	private LocalTime overTime = null;
	private LocalTime workTimeForDay = null;
	private AttendanceStatusType attendanceStatusType = null;
	private LeaveType leaveTypeForThisDate = LeaveType.NO_LEAVE;

	public LeaveType getLeaveTypeForThisDate() {
		return leaveTypeForThisDate;
	}

	public void setLeaveTypeForThisDate(LeaveType leaveTypeForThisDate) {
		this.leaveTypeForThisDate = leaveTypeForThisDate;
	}

	public AttendanceStatusType getAttendanceStatusType() {
		return attendanceStatusType;
	}

	/**
	 * @param statusType Enum fields
	 */
	public void setAttendanceStatusType(AttendanceStatusType statusType) {
		//by default set sat and sunday to weekend
		if (getCurrentDate().getDayOfWeek() == DayOfWeek.SATURDAY
				|| getCurrentDate().getDayOfWeek() == DayOfWeek.SUNDAY)
			statusType = WEEKEND_HOLIDAY;

		if (statusType.compareTo(ABSENT) == 0) {
			if (getWorkTimeForDay() != null && !getCheckOut().equals(LocalTime.MIDNIGHT)) {
				statusType = PRESENT;
			}
		}

		if (statusType.compareTo(PRESENT) == 0) {
			if (getWorkTimeForDay() == null || getWorkTimeForDay().compareTo(WORK_HOURS_FOR_HALF_DAY) < 0)
				statusType = ABSENT;
			else if (getWorkTimeForDay().compareTo(MIN_WORK_HOURS_FOR_FULL_DAY) < 0)
				statusType = HALF_DAY; // first point where
			// we set HALF_DAY
		}

		this.attendanceStatusType = statusType;
	}

	public LocalTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalTime checkOut) {
		this.checkOut = checkOut;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDate date) {
		this.currentDate = date;
	}

	public LocalTime getOverTime() {
		return overTime;
	}

	public void setOverTime(LocalTime overTime) {
		this.overTime = overTime;
	}

	public LocalTime getWorkTimeForDay() {
		return workTimeForDay;
	}

	public void setWorkTimeForDay(LocalTime workTimeForDay) {
		if (workTimeForDay == null && getCheckIn() != null && getCheckOut() != null && getCurrentDate() != null)
			workTimeForDay = TimeManager.calculateTimeDifference(getCheckIn(), getCheckOut(), getCurrentDate());

		this.workTimeForDay = workTimeForDay;
	}
}
