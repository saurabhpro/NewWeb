/*
 * Copyright (c) 2016.
 */

package core.appfilereader;

import core.factory.FileImportFactory.JXLSSheetAndCell;
import core.model.ProjectConstants;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.FileOperations;
import core.model.attendencemodal.AttendanceOfDate;
import core.model.attendencemodal.AttendanceStatusType;
import core.model.employeemodal.BasicEmployeeDetails;
import jxl.Sheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.StringTokenizer;
import java.util.TreeMap;

import static core.model.attendencemodal.AttendanceStatusType.*;

/**
 * Created by Saurabh on 2/10/2016. updated on 4/22/2016
 */
public class BiometricFileWorker extends InitialObjects implements FileOperations {

    //Variable to store the number of rows in biometric file
    private transient int numberOfRowsInBio;
    //Variable to store the reference of sheet from workbook of biometric file
    private transient Sheet sheet = null;
    //Variable to add rows after which biometric xls file starts the new users entries
    private transient int ADD_ROW_STEPS = 0;

    /**
     * @param biometricFile This is the filename which is read to
     *                      return the reference of its sheet from the workbook
     */
    public BiometricFileWorker(String biometricFile) {
        // Get the first sheet
        sheet = new JXLSSheetAndCell().JXLSSheet(biometricFile);
        numberOfRowsInBio = (sheet.getRows() - 11) / 18;
    }

    /**
     * Reads the biometric xls file and stores in object form
     */
    @Override
    public void readFile(BasicEmployeeDetails obj) {

        empBiometricMap = new TreeMap<>();

        // local data
        String empId, empName;
        AttendanceOfDate[] attendanceOfDate;

        //Set Global Month and Year
        String monthYear = getCustomCellContent(13, 7);
        String[] temp = monthYear.split("   ");
        ProjectConstants.setMONTH(Month.valueOf(temp[0].toUpperCase()));
        ProjectConstants.setYEAR(Year.parse(temp[1]));

        for (int i = 0; i < numberOfRowsInBio; i++) {
            attendanceOfDate = new AttendanceOfDate[ProjectConstants.getMONTH().maxLength()];
            getMonthlyAttendanceOfEmployee(attendanceOfDate); // referenced

            empName = getCustomCellContent(3, 13 + (18 * ADD_ROW_STEPS));
            empId = getCustomCellContent(3, 15 + (18 * ADD_ROW_STEPS));

            obj = new EmployeeBiometricDetails(empId, empName, attendanceOfDate);
            empBiometricMap.put(empId, (EmployeeBiometricDetails) obj);

            ADD_ROW_STEPS++;
        }
    }


    /**
     * Method to display the contents read till reading of the Biometric file
     *
     * @implNote Remove this from the production release version
     */
    @Override
    public void displayFile() {
        System.out.println(ProjectConstants.getMONTH());
        empBiometricMap.values().forEach(EmployeeBiometricDetails::printEmpBiometricDetails);
    }


    /**
     * @param column the column number starting from 0 from which data is to be retrieved
     * @param row    the row number stating with 0 from which data is to be retrieved
     * @return return the contents for the cell specified by column and row
     */
    private String getCustomCellContent(int column, int row) {
        return sheet.getCell(column, row).getContents();
    }


    /**
     * method to retrieve the attendance for an employee for that month
     *
     * @param attendanceOfDate Empty object that will hold all dates information as array, since it is pass by reference
     *                         expected behavior is that after the method ends, we will have a filled object without
     *                         need to return anything from method
     */
    private void getMonthlyAttendanceOfEmployee(AttendanceOfDate[] attendanceOfDate) {
        StringTokenizer st;
        AttendanceStatusType attendanceStatus;

        int noOfDaysInThatMonth = ProjectConstants.getMONTH().maxLength();

        for (int k = 0; k < noOfDaysInThatMonth; k++) {
            LocalDate tempDate = LocalDate.of(ProjectConstants.getYEAR().getValue(), ProjectConstants.getMONTH(), (k + 1));
            attendanceOfDate[k] = new AttendanceOfDate();
            attendanceOfDate[k].setCurrentDate(tempDate);
            attendanceStatus = NOT_AN_EMPLOYEE; // default attendance status for an employee

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


}
