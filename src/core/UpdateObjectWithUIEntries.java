package core;

import core.appfilereader.BiometricFileWorker;
import core.appfilereader.InitialObjects;
import core.model.ProjectConstants;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.attendencemodal.AttendanceStatusType;
import core.utils.Serialize;
import core.utils.TimeManager;
import servlets.main.BackEndLogic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static core.model.ProjectConstants.UNDEFINED;

/**
 * Created by Saurabh on 4/14/2016.
 */
public class UpdateObjectWithUIEntries {

    public UpdateObjectWithUIEntries() {
        BackEndLogic.readDataFromSources();
    }


    public void updateObjects(String empRevalId, String[] currentDate, String[] checkIn, String[] checkOut) {
        int i = 0;
        for (String date : currentDate) {
            updateObject(empRevalId, date, checkIn[i], checkOut[i]);
            i++;
        }
        BackEndLogic.getFinalObject();
        BackEndLogic.generateReportsJson();
    }

    private void updateObject(String empRevalId, String currentDate, String checkIn, String checkOut) {
        LocalDate date = convertToProgramStandardDate(currentDate);
        System.out.println(date + " " + checkIn + " " + checkOut);
        if (!checkIn.equals(UNDEFINED) || !checkOut.equals(UNDEFINED)) {
            LocalTime checkInTime = convertToProgramStandardTime(checkIn);
            LocalTime checkOutTime = convertToProgramStandardTime(checkOut);
            update(empRevalId, date, checkInTime, checkOutTime);
        }
    }


    private void update(String empRevalId, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime) {
        for (EmployeeBiometricDetails obj : BiometricFileWorker.empBiometricMap.values()) {
            if (obj.getEmpId().equals(empRevalId)) {
                obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckIn(checkInTime);
                obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckOut(checkOutTime);
                obj.attendanceOfDate[date.getDayOfMonth() - 1].setAttendanceStatusType(AttendanceStatusType.PRESENT);
                obj.attendanceOfDate[date.getDayOfMonth() - 1].setWorkTimeForDay(TimeManager.calculateTimeDifference(checkOutTime, checkInTime, date));
            }
        }

        Serialize.serialSave(ProjectConstants.UPDATED_RECORD_OBJECTS + "biometric.ser", InitialObjects.empBiometricMap);

    }

    private LocalTime convertToProgramStandardTime(String time) {
        if (time.length() < 6)
            return LocalTime.parse(time);

        TimeZone tzone = TimeZone.getTimeZone("Asia/Calcutta");
        ZonedDateTime zdt = ZonedDateTime.parse(time.substring(1, time.length() - 1));
        LocalDateTime ldt = zdt.toLocalDateTime().plusMinutes(330);

        return ldt.toLocalTime();
    }

    private LocalDate convertToProgramStandardDate(String currentDate) {
        return LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
    }



}
