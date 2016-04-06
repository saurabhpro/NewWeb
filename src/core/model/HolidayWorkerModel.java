package core.model;

import core.model.attendence.HolidaysList;
import core.model.empl.BasicEmployeeDetails;

/**
 * Created by Saurabh on 3/12/2016.
 */
public class HolidayWorkerModel {
    HolidaysList holiday;
    BasicEmployeeDetails basicEmployeeDetails;
    WebJSONModel.SubMenuAttendanceOfDate subMenuAttendanceOfDate;

    public HolidayWorkerModel(BasicEmployeeDetails b, WebJSONModel.SubMenuAttendanceOfDate s) {
        this.basicEmployeeDetails = b;
        this.subMenuAttendanceOfDate = s;
    }

    public BasicEmployeeDetails getBasicEmployeeDetails() {
        return basicEmployeeDetails;
    }

    public WebJSONModel.SubMenuAttendanceOfDate getSubMenuAttendanceOfDate() {
        return subMenuAttendanceOfDate;
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
        subMenuAttendanceOfDate.displaySub();
        if (holiday != null)
            System.out.println("Holiday is " + holiday.name());
    }
}
