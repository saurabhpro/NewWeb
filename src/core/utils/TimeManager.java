package core.utils;

import core.model.attendencemodal.AttendanceOfDate;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static core.model.ProjectConstants.*;

/**
 * Created by SaurabhK on 09-02-2016.
 *
 * @author Amrita & Saurabh
 * @version 1.4
 */
public class TimeManager {

    private TimeManager() {
    }

    /**
     * @param type             Specifies the type of average [monthly check in/check out]
     * @param attendanceOfDate Object that contains all details for each day of month specified in Project Constants
     * @return the average calculated in LocalTime format
     * @see core.model.ProjectConstants
     */
    public static LocalTime calculateAverageOfTime(String type, AttendanceOfDate[] attendanceOfDate) {
        List<AttendanceOfDate> ofDates = Arrays.asList(attendanceOfDate);
        int hoursTotal, minsTotal = 0, presentDays = 0;

        for (AttendanceOfDate date : ofDates) {
            if (date.getCheckIn() != null && !date.getCheckOut().equals(LocalTime.MIDNIGHT)) {
                switch (type) {
                    case EMP_AVERAGE_MONTHLY_CHECK_IN:
                        if (date.getCheckIn() != null) {
                            minsTotal += date.getCheckIn().getHour() * 60;
                            minsTotal += date.getCheckIn().getMinute();
                            presentDays++;
                        }
                        break;

                    case EMP_AVERAGE_MONTHLY_CHECK_OUT:
                        if (date.getCheckOut() != null) {
                            minsTotal += date.getCheckOut().getHour() * 60;
                            minsTotal += date.getCheckOut().getMinute();
                            presentDays++;
                        }
                        break;
                }

            }
        }
        int t = presentDays > 0 ? presentDays : 1;
        minsTotal = minsTotal / t;

        hoursTotal = minsTotal / 60;
        minsTotal = minsTotal % 60;
        return LocalTime.of(hoursTotal, minsTotal);
    }

    /**
     * Method to find average work hours
     *
     * @param attendanceOfDate Object that contains all details for each day of month specified in Project Constants
     * @return the average calculated in LocalTime format
     * @see core.model.ProjectConstants
     */
    public static LocalTime calculateAverageTimeOfMonth(AttendanceOfDate[] attendanceOfDate) {
        List<AttendanceOfDate> ofDates = Arrays.asList(attendanceOfDate);
        int hoursTotal, minsTotal = 0, presentDays = 0;

        for (AttendanceOfDate date : ofDates) {
            if (date.getCheckIn() != null && !date.getCheckOut().equals(LocalTime.MIDNIGHT)) {

                minsTotal += date.getWorkTimeForDay().getHour() * 60;
                minsTotal += date.getWorkTimeForDay().getMinute();
                presentDays++;
            }
        }
        int t = presentDays > 0 ? presentDays : 1;
        minsTotal = minsTotal / t;

        hoursTotal = minsTotal / 60;
        minsTotal = minsTotal % 60;
        return LocalTime.of(hoursTotal, minsTotal);
    }

    /**
     * This method enables the Night Shift Operations Fix, since we dont have a related day checkin and checkout time
     * so this method check if check out time is lesser than check in time, if yes it adds a whole day to check out time
     * and returns the proper difference
     *
     * @param checkInTime  The Check in time specified by Biometric File
     * @param checkOutTime The Check out time specified by Financial Force File
     * @param date         The date from which we extracted the check in and check out
     * @return The difference after considering the day change
     * @see core.appfilereader.BiometricFileWorker
     */
    public static LocalTime calculateTimeDifference(LocalTime checkInTime, LocalTime checkOutTime, LocalDate date) {
        LocalDate froDate, toDate;
        froDate = toDate = date;
        LocalTime time;

        if (checkOutTime.compareTo(checkInTime) < 0)
            toDate = froDate.plusDays(1);

        LocalDateTime fromDateTime = LocalDateTime.of(froDate, checkInTime);
        LocalDateTime toDateTime = LocalDateTime.of(toDate, checkOutTime);

        time = getTime(fromDateTime, toDateTime);

        return time;
    }

    /**
     * This method transforms the string date to a proper LocalDate
     *
     * @param date date in String format mm/dd/yyyy
     * @return the date in LocalDate format
     */
    @NotNull
    public static LocalDate convertToLocalDate(String date) {
        AtomicInteger year = new AtomicInteger();
        AtomicInteger month = new AtomicInteger();
        AtomicInteger day = new AtomicInteger();

        String[] st = date.split("/");

        month.set(Integer.parseInt(st[0]));
        day.set(Integer.parseInt(st[1]));
        year.set(Integer.parseInt(st[2]));

        return LocalDate.of(year.get(), month.get(), day.get());
    }

    private static LocalTime getTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Duration duration = Duration.between(fromDateTime, toDateTime);

        AtomicLong seconds = new AtomicLong(duration.getSeconds());

        AtomicLong hours = new AtomicLong(seconds.get() / SECONDS_PER_HOUR);
        AtomicLong minutes = new AtomicLong(((seconds.get() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE));
        // long secs = (seconds % SECONDS_PER_MINUTE);

        return convertToTime(hours.get(), minutes.get());

    }

    /**
     * This method takes in hour and minute in long and returns a LocalTime from it
     *
     * @param hr  the Hour specifed as long value
     * @param min the Minutes specified as minute value
     * @return the time in LocalTime
     */
    public static LocalTime convertToTime(long hr, long min) {
        return LocalTime.of((int) hr, (int) min);
    }

}
