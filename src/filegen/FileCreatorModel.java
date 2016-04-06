package filegen;

import core.model.FinalObjectModel;
import core.model.HolidayWorkerModel;
import core.model.attendence.AttendanceOfDate;
import core.model.attendence.AttendanceStatusType;
import core.model.empl.BasicEmployeeDetails;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kumars on 4/6/2016.
 */
public class FileCreatorModel {
    private String empRevalId;
    private String empSalesforceId;
    private String empName;
    private String empEmailId;
    private String empAvgCheckInTimeForMonth;
    private String empAvgCheckOutTimeForMonth;
    private String empAvgWorkHoursForMonth;
    private ArrayList<DayDetail> allDateDetailsList;
    private Boolean empIfClarificationNeeded;

    public FileCreatorModel(){}

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

    public void setEmpAvgCheckInTimeForMonth(LocalTime a) {
        this.empAvgCheckInTimeForMonth = a.toString();
    }

    public void setEmpAvgCheckInTimeForMonth(String a) {
        this.empAvgCheckInTimeForMonth = a;
    }

    public String getEmpAvgCheckOutTimeForMonth() {
        return empAvgCheckOutTimeForMonth;
    }

    public void setEmpAvgCheckOutTimeForMonth(LocalTime a) {
        this.empAvgCheckOutTimeForMonth = a.toString();
    }

    public void setEmpAvgCheckOutTimeForMonth(String a) {
        this.empAvgCheckOutTimeForMonth = a;
    }

    public String getEmpAvgWorkHoursForMonth() {
        return empAvgWorkHoursForMonth;
    }

    public void setEmpAvgWorkHoursForMonth(LocalTime a) {
        this.empAvgWorkHoursForMonth = a.toString();
    }

    public void setEmpAvgWorkHoursForMonth(String a) {
        this.empAvgWorkHoursForMonth = a;
    }

    public ArrayList<DayDetail> getAllDateDetailsList() {
        return allDateDetailsList;
    }

    public void setAllDateDetailsList(ArrayList< Map<String, String>> attendanceOfDates) {
        allDateDetailsList = new ArrayList<>();
        this.allDateDetailsList.addAll(attendanceOfDates.stream().map(DayDetail::new).collect(Collectors.toList()));
    }

    public Boolean getEmpIfClarificationNeeded() {
        return empIfClarificationNeeded;
    }

    public void setEmpIfClarificationNeeded(Boolean empIfClarificationNeeded) {
        this.empIfClarificationNeeded = empIfClarificationNeeded;
    }

    public static class DayDetail {
        private final String currentDate;
        private final String attendanceStatusType;
        private final String leaveTypeForThisDate;
        private String checkIn;
        private String checkOut;
        private String workTimeForDay;

        public DayDetail(Map<String, String> attendance) {
            currentDate = attendance.get("currentDate");
            checkIn = attendance.get("checkIn");
            checkOut = attendance.get("checkOut");
            workTimeForDay = attendance.get("workTimeForDay");

            attendanceStatusType = attendance.get("attendanceStatusType");
            leaveTypeForThisDate = attendance.get("leaveTypeForThisDate");
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

        public String getAttendanceStatusType() {
            return attendanceStatusType;
        }

        public String getLeaveTypeForThisDate() {
            return leaveTypeForThisDate;
        }
    }


}
