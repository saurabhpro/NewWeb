package core.appfilereader;

import core.factory.fileimportfactory.JXLSSheetAndCell;
import core.model.ProjectConstants;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.FileOperations;
import core.model.attendencemodal.AttendanceOfDate;
import core.model.attendencemodal.AttendanceStatusType;
import jxl.Sheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.StringTokenizer;
import java.util.TreeMap;

import static core.model.attendencemodal.AttendanceStatusType.*;

/**
 * Created by Saurabh on 2/10/2016. updated on 2/13/2016
 */
public class BiometricFileWorker extends InitialObjects implements FileOperations, Serializable {

    private int numberOfRowsInBio;
    private Sheet sheet = null;
    private int ADD_ROW_STEPS = 0;

    public BiometricFileWorker() {
    }

    public BiometricFileWorker(String biometricFile) {
        sheet = new JXLSSheetAndCell().JXLSSheet(biometricFile);
        numberOfRowsInBio = (sheet.getRows() - 11) / 18;
    }

    @Override
    public void displayFile() {
        System.out.println(ProjectConstants.getMONTH());
        empBiometricMap.values().forEach(EmployeeBiometricDetails::printEmpBiometricDetails);
    }

    /**
     * method to return the contents of a given row, col
     */
    private String getCustomCellContent(int column, int row) {
        return sheet.getCell(column, row).getContents();
    }

    /**
     * method to return the attendance for an employeemodal for that month
     */
    private void getMonthlyAttendanceOfEmployee(AttendanceOfDate[] attendanceOfDate) {
        StringTokenizer st;
        AttendanceStatusType attendanceStatus;

        int noOfDaysInThatMonth = ProjectConstants.getMONTH().maxLength();

        for (int k = 0; k < noOfDaysInThatMonth; k++) {
            LocalDate tempDate = LocalDate.of(ProjectConstants.getYEAR().getValue(), ProjectConstants.getMONTH(), (k + 1));
            attendanceOfDate[k] = new AttendanceOfDate();
            attendanceOfDate[k].setCurrentDate(tempDate);
            attendanceStatus = NOT_AN_EMPLOYEE; // default
            // status
            // for
            // an
            // employeemodal

            st = new StringTokenizer(getCustomCellContent(k, 20 + (18 * ADD_ROW_STEPS)), "   ");

            lb:
            for (int j = 2; j < 6; j++) {
                String tempString;
                if (st.hasMoreElements()) {
                    tempString = (String) st.nextElement();
                    // A
                    // 11:00 12:00 00;00 P
                    switch (tempString) {
                        case "A":
                        case "A/A":
                        case "P/A":
                        case "A/P":
                            attendanceStatus = ABSENT;
                            break lb;

                        case "W":
                            // case when employeemodal checks in on weekend
                            attendanceStatus = WEEKEND_HOLIDAY;
                            break lb;

                        case "P":
                            attendanceStatus = PRESENT;
                            break lb;

                        default:
                            if (j == 2)
                                attendanceOfDate[k].setCheckIn(LocalTime.parse(tempString));
                            else if (j == 3)
                                attendanceOfDate[k].setCheckOut(LocalTime.parse(tempString));
                    }
                }
            }
            attendanceOfDate[k].setAttendanceStatusType(attendanceStatus);
        }
    }

    @Override
    public void readFile() {
        // local data
        String empId, empName;
        AttendanceOfDate[] attendanceOfDate;
        empBiometricMap = new TreeMap<>();

        String monthYear = getCustomCellContent(13, 7);
        String[] st = monthYear.split("   ");

        ProjectConstants.setMONTH(Month.valueOf(st[0].toUpperCase()));
        ProjectConstants.setYEAR(Year.parse(st[1]));

        for (int i = 0; i < numberOfRowsInBio; i++) {
            attendanceOfDate = new AttendanceOfDate[ProjectConstants.getMONTH().maxLength()];
            getMonthlyAttendanceOfEmployee(attendanceOfDate); // referenced

            empName = getCustomCellContent(3, 13 + (18 * ADD_ROW_STEPS));
            empId = getCustomCellContent(3, 15 + (18 * ADD_ROW_STEPS));

            //name change
            empBiometricMap.put(empId, new EmployeeBiometricDetails(empId, empName, attendanceOfDate));

            ADD_ROW_STEPS++;
        }
    }
}
