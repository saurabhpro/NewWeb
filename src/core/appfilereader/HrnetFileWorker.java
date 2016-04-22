package core.appfilereader;

import core.factory.fileimportfactory.XLSXSheetAndCell;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.appfilereadermodal.FileOperations;
import core.model.attendencemodal.AttendanceOfLeave;
import core.model.attendencemodal.LeaveType;
import core.model.employeemodal.BasicEmployeeDetails;
import core.utils.TimeManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static core.model.ProjectConstants.getMONTH;
import static core.model.ProjectConstants.getYEAR;

/**
 * Created by Saurabh on 2/10/2016.
 * @author Saurabh
 * @version 1.4
 */
public class HrnetFileWorker extends InitialObjects implements FileOperations {

    //Variable to store the reference of sheet from workbook of financial force file
    private final Sheet sheet;

    /**
     * @param hrNetFile This is the filename which is read to
     *                  return the reference of its sheet from the workbook
     */
    public HrnetFileWorker(String hrNetFile) {
        // Get the first sheet
        sheet = new XLSXSheetAndCell().ApacheXLSXSheet(hrNetFile);
    }

    /**
     * Reads the financial force xlsx file and stores in object form
     */
    @Override
    public void readFile(BasicEmployeeDetails obj) {

        hrnetDetailsMap = new TreeMap<>();
        int numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        Cell cell;
        String empName = null;
        String salesForceID = null;
        LocalDate tempDate;

        AttendanceOfLeave attendanceOfLeave;
        ArrayList<EmployeeHrnetDetails> tempArrLst;

        for (int i = 1; i < numberOfRowsInHr; i++) {
            attendanceOfLeave = new AttendanceOfLeave();

            for (int j = 0; j < 7; j++) {
                // Update the value of cell
                cell = sheet.getRow(i).getCell(j);

                switch (j) {
                    case 0:
                        salesForceID = getID(cell);
                        break;

                    case 1:
                        empName = cell.getStringCellValue();
                        break;

                    case 2:
                        String tmp = cell.getStringCellValue().replace(" ", "_").toUpperCase();
                        for (LeaveType leaveType : LeaveType.values()) {
                            if (Objects.equals(leaveType.toString(), tmp)) {
                                attendanceOfLeave.setLeaveType(LeaveType.valueOf(tmp));
                                break;
                            }
                        }
                        break;
                    case 3:// startdate column
                        tempDate = getLocalDate(cell);
                        attendanceOfLeave.setStartDate(tempDate);
                        break;
                    case 4:// end date column
                        tempDate = getLocalDate(cell);
                        attendanceOfLeave.setEndDate(tempDate);
                        break;

                    case 5:
                        attendanceOfLeave.setAbsenceTime(cell.getNumericCellValue());
                        break;

                }
            }

            //method to only consider leaves for month specified by biometric file
            leaveStartEndDayRangeFixer(attendanceOfLeave);

            obj = new EmployeeHrnetDetails(salesForceID, empName, attendanceOfLeave);

            //only consider the salesforce data for those months which is on biometric excel
            if (attendanceOfLeave.getStartDate() != null && attendanceOfLeave.getLeaveType() != null) {
                //case where employee entry present in map
                //i.e. employee id is already read once form Financial Force file
                if (hrnetDetailsMap.containsKey(salesForceID)) {
                    tempArrLst = hrnetDetailsMap.get(salesForceID);
                    tempArrLst.add((EmployeeHrnetDetails) obj);
                    hrnetDetailsMap.put(salesForceID, tempArrLst);

                }
                //case where new employee id is found
                //we prepare a new arraylist for that employee to hold all his requests on Financial Force file
                else {
                    tempArrLst = new ArrayList<>();
                    tempArrLst.add((EmployeeHrnetDetails) obj);
                    hrnetDetailsMap.put(salesForceID, tempArrLst);
                }
            }
        }

    }

    /**
     * Method to display the contents read till reading of the Financial Force file
     *
     * @implNote Remove this from the production release version
     */
    @Override
    public void displayFile() {
        for (Map.Entry<String, ArrayList<EmployeeHrnetDetails>> entry : hrnetDetailsMap.entrySet())
            entry.getValue().forEach(EmployeeHrnetDetails::printHrNetDetail);
    }

    private String getID(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return cell.getStringCellValue();
        else
            return Objects.toString((int) cell.getNumericCellValue());
    }

    @NotNull
    private LocalDate getLocalDate(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return TimeManager.convertToLocalDate(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));
        else
            return TimeManager.convertToLocalDate(cell.getStringCellValue());
    }

    private void leaveStartEndDayRangeFixer(AttendanceOfLeave attendanceOfLeave) {
        /**
         * case where leave applied from 29th jan to 5th feb, and we are
         * calculating for Feb
         */
        if (attendanceOfLeave.getEndDate().getMonth().equals(getMONTH())
                && !attendanceOfLeave.getStartDate().getMonth().equals(getMONTH()))
            attendanceOfLeave
                    .setStartDate(LocalDate.of(getYEAR().getValue(), getMONTH(), 1));

        /**
         * case where leave applied from 29th jan to 5th feb, and we are
         * calculating for Jan
         */
        else if (attendanceOfLeave.getStartDate().getMonth().equals(getMONTH())
                && !attendanceOfLeave.getEndDate().getMonth().equals(getMONTH()))
            attendanceOfLeave.setEndDate(LocalDate.of(getYEAR().getValue(), getMONTH(),
                    getMONTH().maxLength()));

        /**
         * case where leave applied from 8th jan to 14th jan, and we are
         * calculating for feb
         */
        else if (!attendanceOfLeave.getStartDate().getMonth().equals(getMONTH())
                && !attendanceOfLeave.getEndDate().getMonth().equals(getMONTH()))
            attendanceOfLeave.setStartDate(null);

    }
}
