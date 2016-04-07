package core.jxcel;

import core.factory.XLSXSheetAndCell;
import core.model.FileOperations;
import core.model.attendence.AttendanceOfLeave;
import core.model.attendence.LeaveType;
import core.model.uploadedfiles.HrnetDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class HrnetFileWorker implements FileOperations {

    public static Map<String, ArrayList<HrnetDetails>> hrnetDetails;
    private final Sheet sheet;

    public HrnetFileWorker(String hrNetFile) {
        sheet = new XLSXSheetAndCell().ApacheXLSXSheet(hrNetFile);
    }

    @Override
    public void displayFile() {
        Set<Map.Entry<String, ArrayList<HrnetDetails>>> s = hrnetDetails.entrySet();

        for (Map.Entry<String, ArrayList<HrnetDetails>> entry : s) {
            entry.getValue().forEach(HrnetDetails::printHrNetDetail);
        }
    }

    private String getID(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return cell.getStringCellValue();
        else
            return Objects.toString((int) cell.getNumericCellValue());
    }

    private LocalDate getLocalDate(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return TimeManager.convertToLocalDate(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));
        else
            return TimeManager.convertToLocalDate(cell.getStringCellValue());
    }

    // hi
    @Override
    public void readFile() {
        Cell cell;

        AttendanceOfLeave attendanceOfLeave;

        String empName = null;
        String salesForceID = null;
        String empRequest = null;
        LocalDate tempDate;

        int numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        hrnetDetails = new TreeMap<>();

        for (int i = 1; i < numberOfRowsInHr; i++) {
            ArrayList<HrnetDetails> tempArrLst;
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

            leaveStartEndDayRangeFixer(attendanceOfLeave);
            /**
             * only consider the salesforce data for those months which is on
             * biometric excel
             */
            if (attendanceOfLeave.getStartDate() != null && attendanceOfLeave.getLeaveType() != null) {
                if (hrnetDetails.containsKey(salesForceID)) {
                    tempArrLst = hrnetDetails.get(salesForceID);
                    tempArrLst.add(new HrnetDetails(salesForceID, empName, attendanceOfLeave));
                    hrnetDetails.put(salesForceID, tempArrLst);

                } else {
                    tempArrLst = new ArrayList<>();
                    tempArrLst.add(new HrnetDetails(salesForceID, empName, attendanceOfLeave));
                    hrnetDetails.put(salesForceID, tempArrLst);
                }
            }
        }

    }

    private void leaveStartEndDayRangeFixer(AttendanceOfLeave attendanceOfLeave) {
        /**
         * case where leave applied from 29th jan to 5th feb, and we are
         * calculating for Feb
         */
        if (attendanceOfLeave.getEndDate().getMonth().equals(TimeManager.getMonth())
                && !attendanceOfLeave.getStartDate().getMonth().equals(TimeManager.getMonth()))
            attendanceOfLeave
                    .setStartDate(LocalDate.of(TimeManager.getYear().getValue(), TimeManager.getMonth(), 1));

        /**
         * case where leave applied from 29th jan to 5th feb, and we are
         * calculating for Jan
         */
        else if (attendanceOfLeave.getStartDate().getMonth().equals(TimeManager.getMonth())
                && !attendanceOfLeave.getEndDate().getMonth().equals(TimeManager.getMonth()))
            attendanceOfLeave.setEndDate(LocalDate.of(TimeManager.getYear().getValue(), TimeManager.getMonth(),
                    TimeManager.getMonth().maxLength()));

        /**
         * case where leave applied from 8th jan to 14th jan, and we are
         * calculating for feb
         */
        else if (!attendanceOfLeave.getStartDate().getMonth().equals(TimeManager.getMonth())
                && !attendanceOfLeave.getEndDate().getMonth().equals(TimeManager.getMonth()))
            attendanceOfLeave.setStartDate(null);

    }
}
