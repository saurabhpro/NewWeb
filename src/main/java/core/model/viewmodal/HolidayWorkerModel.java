package core.model.viewmodal;

import core.model.attendencemodal.HolidaysList;
import core.model.employeemodal.BasicEmployeeDetails;

/**
 * Created by Saurabh on 3/12/2016.
 */
public class HolidayWorkerModel {
	HolidaysList holiday;
	BasicEmployeeDetails basicEmployeeDetails;
	WebJSONModel.PerDayAttendanceRecord perDayAttendanceRecord;

	public HolidayWorkerModel(BasicEmployeeDetails b, WebJSONModel.PerDayAttendanceRecord s) {
		this.basicEmployeeDetails = b;
		this.perDayAttendanceRecord = s;
	}

	public BasicEmployeeDetails getBasicEmployeeDetails() {
		return basicEmployeeDetails;
	}

	public WebJSONModel.PerDayAttendanceRecord getPerDayAttendanceRecord() {
		return perDayAttendanceRecord;
	}

	public HolidaysList getHoliday() {
		return holiday;
	}

	public void setHoliday(HolidaysList holiday) {
		this.holiday = holiday;
	}

	public void display() {
		System.out.println();
		basicEmployeeDetails.displayBasicInfo();
		perDayAttendanceRecord.displaySub();
		if (holiday != null)
			System.out.println("Holiday is " + holiday.name());
	}
}
