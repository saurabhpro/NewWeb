package core;

import core.combined.MarkDiscrepancy;
import core.model.FinalObjectModel;
import servlets.core.BackEndLogic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static core.model.ProjectConstants.UNDEFINED;

/**
 * Created by Saurabh on 4/14/2016.
 */
public class UpdateObjectWithUIEntries {
    private Map<String, FinalObjectModel> objectToBeUpdated;

    public UpdateObjectWithUIEntries() {
        objectToBeUpdated = BackEndLogic.getFinalObject();
    }


    public void updateObjects(String empRevalId, String[] currentDate, String[] checkIn, String[] checkOut) {
        int i = 0;
        for (String date : currentDate) {
            updateObject(empRevalId, date, checkIn[i], checkOut[i]);
            i++;
        }
        BackEndLogic.generateReportsJson();
    }

    private void updateObject(String empRevalId, String currentDate, String checkIn, String checkOut) {
        LocalDate date = convertToProgramStandardDate(currentDate);
        System.out.println(date + " " + checkIn + " " + checkOut);
        if (!checkIn.equals(UNDEFINED) || !checkOut.equals(UNDEFINED)) {
            LocalTime checkInTime = convertToProgramStandardTime(checkIn);
            LocalTime checkOutTime = convertToProgramStandardTime(checkOut);
            update(empRevalId, date, checkInTime, checkOutTime);

            MarkDiscrepancy.EmpCombinedMap = objectToBeUpdated;     //jugad wala kaam
        }
    }

    private LocalTime convertToProgramStandardTime(String time) {
        return LocalTime.parse(time);
    }

    private LocalDate convertToProgramStandardDate(String currentDate) {
        return LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
    }

    private void update(String empRevalId, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime) {
        for (FinalObjectModel obj : objectToBeUpdated.values()) {
            if (obj.getEmpId().equals(empRevalId)) {
                obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckIn(checkInTime);
                obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckOut(checkOutTime);
            }
        }
    }


}
