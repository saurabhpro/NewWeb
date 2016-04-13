package core.model;

import core.model.attendence.AttendanceOfDate;
import core.model.attendence.AttendanceStatusType;
import core.model.attendence.LeaveType;
import core.model.empl.BasicEmployeeDetails;

import java.time.LocalTime;
import java.util.ArrayList;

import static core.model.ProjectConstants.*;
import static core.model.attendence.AttendanceStatusType.WEEKEND_HOLIDAY;

/**
 * Created by kumars on 3/11/2016.
 */
public class WebJSONModel {
    private String empRevalId;
    private String empSalesforceId;
    private String empName;
    private String empEmailId;
    private String empAvgCheckInTimeForMonth;
    private String empAvgCheckOutTimeForMonth;
    private String empAvgWorkHoursForMonth;
    private AttendanceOfDate[] attendanceOfDates;
    private ArrayList<PerDayAttendanceRecord> allDateDetailsList;
    private Boolean empIfClarificationNeeded;

    public WebJSONModel(FinalObjectModel f) {
        setEmpRevalId(f.getEmpId());
        setEmpSalesforceId(f.getSalesForceId());
        setEmpName(f.getName());
        setEmpEmailId(f.getEmailId());

        setEmpAvgCheckInTimeForMonth(f.getAvgInTime());
        setEmpAvgCheckOutTimeForMonth(f.getAvgOutTime());
        setEmpAvgWorkHoursForMonth(f.getAverageNumberOfHoursMonthly());
        setEmpIfClarificationNeeded(f.getIfClarificationNeeded());

        setAttendanceOfDates(f.attendanceOfDate);
        setAllDateDetailsList();
    }

    public WebJSONModel(FinalObjectModel f, String type) {
        this(f);
        setAllDateDetailsList(type);
    }

    public void setAttendanceOfDates(AttendanceOfDate[] attendanceOfDates) {
        this.attendanceOfDates = attendanceOfDates;
    }

    public void setAllDateDetailsList() {
        allDateDetailsList = new ArrayList<>();
        for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {
            this.allDateDetailsList.add(new PerDayAttendanceRecord(attendanceOfDate));
        }
    }

    public String getEmpRevalId() {
        return empRevalId;
    }

    public void setEmpRevalId(String empRevalId) {
        this.empRevalId = empRevalId;
    }

    public String getEmpSalesforceId() {
        return empSalesforceId;
    }

    public void setEmpSalesforceId(String empSalesforceId) {
        this.empSalesforceId = empSalesforceId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }

    public String getEmpAvgCheckInTimeForMonth() {
        return empAvgCheckInTimeForMonth;
    }

    public void setEmpAvgCheckInTimeForMonth(String a) {
        this.empAvgCheckInTimeForMonth = a;
    }

    public void setEmpAvgCheckInTimeForMonth(LocalTime a) {
        this.empAvgCheckInTimeForMonth = a.toString();
    }

    public String getEmpAvgCheckOutTimeForMonth() {
        return empAvgCheckOutTimeForMonth;
    }

    public void setEmpAvgCheckOutTimeForMonth(String a) {
        this.empAvgCheckOutTimeForMonth = a;
    }

    public void setEmpAvgCheckOutTimeForMonth(LocalTime a) {
        this.empAvgCheckOutTimeForMonth = a.toString();
    }

    public String getEmpAvgWorkHoursForMonth() {
        return empAvgWorkHoursForMonth;
    }

    public void setEmpAvgWorkHoursForMonth(String a) {
        this.empAvgWorkHoursForMonth = a;
    }

    public void setEmpAvgWorkHoursForMonth(LocalTime a) {
        this.empAvgWorkHoursForMonth = a.toString();
    }

    public ArrayList<PerDayAttendanceRecord> getAllDateDetailsList() {
        return allDateDetailsList;
    }

    public void setAllDateDetailsList(String Type) {
        this.allDateDetailsList = new ArrayList<>();
        if (Type.equals(DISCREPANCY_IN_WORKERS_LIST)) {
            for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {

                if (attendanceOfDate.getAttendanceStatusType().equals(AttendanceStatusType.UNACCOUNTED_ABSENCE))
                    this.allDateDetailsList.add(new PerDayAttendanceRecord(attendanceOfDate));
                else if (attendanceOfDate.getAttendanceStatusType().equals(AttendanceStatusType.HALF_DAY)
                        && attendanceOfDate.getLeaveTypeForThisDate().equals(LeaveType.NO_LEAVE))
                    this.allDateDetailsList.add(new PerDayAttendanceRecord(attendanceOfDate));

            }
        } else if (Type.equals(WEEKEND_WORKERS_LIST)) {
            for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {
                if (attendanceOfDate.getAttendanceStatusType().equals(WEEKEND_HOLIDAY) && attendanceOfDate.getWorkTimeForDay() != null) {
                    this.allDateDetailsList.add(new PerDayAttendanceRecord(attendanceOfDate));
                }
            }
        }
    }

    public Boolean getEmpIfClarificationNeeded() {
        return empIfClarificationNeeded;
    }

    public void setEmpIfClarificationNeeded(Boolean empIfClarificationNeeded) {
        this.empIfClarificationNeeded = empIfClarificationNeeded;
    }

    private void displayBasicDetails() {
        System.out.println("\n" + EMP_REVAL_IND_ID + " : " + getEmpRevalId());
        System.out.println(EMP_FINANCIAL_FORCE_ID + " : " + getEmpSalesforceId());
        System.out.println(EMP_NAME + " : " + getEmpName());
        System.out.println(EMP_EMAIL_ID + " : " + getEmpEmailId());
        System.out.println(EMP_AVERAGE_MONTHLY_CHECK_IN + " : " + getEmpAvgCheckInTimeForMonth());
        System.out.println(EMP_AVERAGE_MONTHLY_CHECK_OUT + " : " + getEmpAvgCheckOutTimeForMonth());
        System.out.println(EMP_AVERAGE_MONTHLY_WORK_HOURS + " : " + getEmpAvgWorkHoursForMonth());
        System.out.println(CLARIFICATION_NEEDED + " : " + getEmpIfClarificationNeeded());

    }

    public void displayAllDates() {
        displayBasicDetails();
        getAllDateDetailsList().forEach(PerDayAttendanceRecord::displaySub);

    }

    public HolidayWorkerModel getHolidayWorkerObjForThisDate(int date) {
        PerDayAttendanceRecord s = allDateDetailsList.get(date);
        if (s.getCheckIn() != null)
            return new HolidayWorkerModel(new BasicEmployeeDetails(empName, empRevalId, empSalesforceId, empEmailId), s);

        return null;
    }

    public static class PerDayAttendanceRecord {
        final String currentDate;
        final AttendanceStatusType attendanceStatusType;
        final LeaveType leaveTypeForThisDate;
        String checkIn;
        String checkOut;
        String workTimeForDay;

        public PerDayAttendanceRecord(AttendanceOfDate attendance) {
            currentDate = attendance.getCurrentDate().toString();
            if (attendance.getCheckIn() != null) checkIn = attendance.getCheckIn().toString();
            if (attendance.getCheckOut() != null) checkOut = attendance.getCheckOut().toString();
            if (attendance.getWorkTimeForDay() != null) workTimeForDay = attendance.getWorkTimeForDay().toString();
            attendanceStatusType = attendance.getAttendanceStatusType();
            leaveTypeForThisDate = attendance.getLeaveTypeForThisDate();

            if (checkIn == null)
                checkIn = UNDEFINED;
            if (checkOut == null)
                checkOut = UNDEFINED;
            if (workTimeForDay == null)
                workTimeForDay = UNDEFINED;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public String getCheckIn() {
            return checkIn;
        }

        public String getCheckOut() {
            return checkOut;
        }

        public String getWorkTimeForDay() {
            return workTimeForDay;
        }

        public AttendanceStatusType getAttendanceStatusType() {
            return attendanceStatusType;
        }

        public LeaveType getLeaveTypeForThisDate() {
            return leaveTypeForThisDate;
        }

        public void displaySub() {
            System.out.println(CURRENT_DATE + " : " + getCurrentDate());
            System.out.println(EMP_CHECK_IN + " : " + getCheckIn());
            System.out.println(EMP_CHECK_OUT + " : " + getCheckOut());
            System.out.println(EMP_WORK_HOURS_FOR_THIS_DAY + " : " + getWorkTimeForDay());
            System.out.println(EMP_ATTENDANCE_STATUS_TYPE + " : " + getAttendanceStatusType());
            System.out.println(EMP_LEAVE_REQUEST_TYPE + " : " + getLeaveTypeForThisDate());
        }
    }
}