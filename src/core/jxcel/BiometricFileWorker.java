package core.jxcel;

import core.factory.JXLSSheetAndCell;
import core.model.FileOperations;
import core.model.attendence.AttendanceOfDate;
import core.model.attendence.AttendanceStatusType;
import core.model.uploadedfiles.EmpBiometricDetails;
import jxl.Sheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import static core.model.ProjectConstants.*;
import static core.model.attendence.AttendanceStatusType.*;

/**
 * Created by Saurabh on 2/10/2016. updated on 2/13/2016
 */
public class BiometricFileWorker implements FileOperations {

    public static Map<String, EmpBiometricDetails> empList = null;
    private final int numberOfRowsInBio;
    private Sheet sheet = null;
    private int ADD_ROW_STEPS = 0;

    public BiometricFileWorker(String biometricFile) {
        sheet = new JXLSSheetAndCell().JXLSSheet(biometricFile);
        numberOfRowsInBio = (sheet.getRows() - 11) / 18;
    }

    @Override
    public void displayFile() {
        System.out.println(getMONTH());
        empList.values().forEach(EmpBiometricDetails::printEmpBiometricDetails);
    }

    /**
     * method to return the contents of a given row, col
     */
    private String getCustomCellContent(int column, int row) {
        return sheet.getCell(column, row).getContents();
    }

    /**
     * method to return the attendance for an employee for that month
     */
    private void getMonthlyAttendanceOfEmployee(AttendanceOfDate[] attendanceOfDate) {
        StringTokenizer st;
        AttendanceStatusType attendanceStatus;

        int noOfDaysInThatMonth = getMONTH().maxLength();

        for (int k = 0; k < noOfDaysInThatMonth; k++) {
            LocalDate tempDate = LocalDate.of(getYEAR().getValue(), getMONTH(), (k + 1));
            attendanceOfDate[k] = new AttendanceOfDate();
            attendanceOfDate[k].setCurrentDate(tempDate);
            attendanceStatus = NOT_AN_EMPLOYEE; // default
            // status
            // for
            // an
            // employee

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
                            // case when employee checks in on weekend
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
        empList = new TreeMap<>();

        String monthYear = getCustomCellContent(13, 7);
        String[] st = monthYear.split("   ");

        setMONTH(Month.valueOf(st[0].toUpperCase()));
        setYEAR(Year.parse(st[1]));

        for (int i = 0; i < numberOfRowsInBio; i++) {
            attendanceOfDate = new AttendanceOfDate[getMONTH().maxLength()];
            getMonthlyAttendanceOfEmployee(attendanceOfDate); // referenced

            empName = getCustomCellContent(3, 13 + (18 * ADD_ROW_STEPS));
            empId = getCustomCellContent(3, 15 + (18 * ADD_ROW_STEPS));

            //name change
            empList.put(empId, new EmpBiometricDetails(empId, empName, attendanceOfDate));

            ADD_ROW_STEPS++;
        }
    }
}
