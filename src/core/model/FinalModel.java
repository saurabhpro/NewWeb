package core.model;

import core.emplmasterrecord.EmployeeMasterData;
import core.jxcel.TimeManager;
import core.model.attendence.AttendanceOfDate;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by kumars on 2/19/2016.
 */
public class FinalModel extends BasicEmployeeDetails {
    public final AttendanceOfDate[] attendanceOfDate;
    public final ArrayList<HrnetDetails> hrnetDetails;
    private final LocalTime avgInTime;
    private final LocalTime avgOutTime;
    private final int numberOfEntriesinHrNet;
    private final LocalTime averageNumberOfHoursMonthly;
    // AMRITA
    private final int[] count = new int[5];// Absent, Present, Public_Holiday,
    // Weekend_Holiday, Half_Day
    private boolean ifClarificationNeeded = false;

    public FinalModel(String EmployeeID, int numberOfEntriesinHrNet, AttendanceOfDate[] a, ArrayList<HrnetDetails> hr1) {
        this.setEmpId(EmployeeID);

        BasicEmployeeDetails b = EmployeeMasterData.allEmployeeRecordMap.get(EmployeeID);

        if (b != null) {
            this.setName(b.getName());
            this.setSalesForceId(b.getSalesForceId());
            this.setEmailId(b.getEmailId());
        }
        this.attendanceOfDate = a;
        this.numberOfEntriesinHrNet = numberOfEntriesinHrNet;
        // this.needClarificationFromEmployee = needClarificationFromEmployee;
        this.hrnetDetails = hr1;

        avgInTime = setAvgInTime();
        avgOutTime = setAvgOutTime();
        averageNumberOfHoursMonthly = setAverageNumberOfHoursMonthly();
    }

    private void displayArrayList() {
        hrnetDetails.forEach(HrnetDetails::printHrNetDetail);
    }

    public void displayFinalList() {
        System.out.println("Name: " + this.getName());
        System.out.println("Employee ID: " + this.getEmpId());
        System.out.println("Avg In Time " + this.getAvgInTime());
        System.out.println("Avg Out Time " + this.getAvgOutTime());
        System.out.println("Work Hours for day: " + this.getAverageNumberOfHoursMonthly());
        System.out.println();

        System.out.println("Number Of Leaves Applied: " + this.numberOfEntriesinHrNet);
        if (this.hrnetDetails != null) {
            this.displayArrayList();
        }

        // to be removed today
        // AMRITA
        System.out.println("\nNumber of Unaccounted Absence Days " + this.getCount(0));
        System.out.println("Number of Present Days " + this.getCount(1));
        System.out.println("Number of Public Holidays " + this.getCount(2));
        System.out.println("Number of Weekend Holidays " + this.getCount(3));
        System.out.println("Number of Half Days " + this.getCount(4));

        System.out.println("\nBiometric Data for Each Day: ");
        for (int j = 0; j < TimeManager.getMonth().maxLength(); j++) {
            System.out.print(this.attendanceOfDate[j].getCurrentDate());
            System.out.print("\tIn Time: " + this.attendanceOfDate[j].getCheckIn());
            System.out.print("\tOut Time: " + this.attendanceOfDate[j].getCheckOut());
            System.out.print("\tStatus: " + this.attendanceOfDate[j].getAttendanceStatusType() + "\n");
            System.out.print("\tWorkhours: " + this.attendanceOfDate[j].getWorkTimeForDay() + "\n");
        }

        System.out.println();
    }

    public LocalTime getAvgInTime() {
        return avgInTime;
    }

    public LocalTime getAvgOutTime() {
        return avgOutTime;
    }

    // only for debugging
    private int getCount(int i) {
        return count[i];
    }

    public boolean getIfClarificationNeeded() {
        return ifClarificationNeeded;
    }

    public void setIfClarificationNeeded(boolean needClarificationFromEmployee) {
        this.ifClarificationNeeded = needClarificationFromEmployee;
    }

    private LocalTime setAvgInTime() {
        return TimeManager.calculateAverageOfTime("AverageCheckInTime", attendanceOfDate);
    }

    private LocalTime setAvgOutTime() {
        return TimeManager.calculateAverageOfTime("AverageCheckOutTime", attendanceOfDate);
    }

    public LocalTime setAverageNumberOfHoursMonthly() {
        return TimeManager.calculateAverageTimeOfMonth(this.attendanceOfDate);
    }

    public LocalTime getAverageNumberOfHoursMonthly() {
        return averageNumberOfHoursMonthly;
    }

    public void setCount(int i) {
        count[i] = count[i] + 1;
    }
}
