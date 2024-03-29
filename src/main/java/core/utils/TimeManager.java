package core.utils;

import core.model.attendencemodal.AttendanceOfDate;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static core.model.ProjectConstants.*;

/**
 * Created by SaurabhK on 09-02-2016.
 *
 * @author Amrita & Saurabh
 * @version 1.6
 */
public class TimeManager {

	private TimeManager() {
	}

	/**
	 * @param type             Specifies the type of average [monthly check in/check out]
	 * @param attendanceOfDate Object that contains all details for each day of
	 *                         month specified in Project Constants
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
	 * @param attendanceOfDate Object that contains all details for each day of
	 *                         month specified in Project Constants
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
	 * This method enables the Night Shift Operations Fix, since we dont have a
	 * related day checkin and checkout time so this method check if check out
	 * time is lesser than check in time, if yes it adds a whole day to check
	 * out time and returns the proper difference
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
	 * Method to convert the time format returned from the browser Json update
	 *
	 * @param time the argument specifies the input that needs to be converted
	 *             in string
	 * @return the time in LocalTime format
	 * @since 1.5
	 */
	public static LocalTime convertToProgramStandardTime(String time) {
		if (time.length() < 6)
			return LocalTime.parse(time);

		// TimeZone tzone = TimeZone.getTimeZone("Asia/Calcutta");
		ZonedDateTime zdt = ZonedDateTime.parse(time.substring(1, time.length() - 1));
		LocalDateTime ldt = zdt.toLocalDateTime().plusMinutes(330);

		return ldt.toLocalTime();
	}

	/**
	 * Method to convert the date format returned from the browser Json update
	 *
	 * @param currentDate the argument specifies the input that needs to be
	 *                    converted in string
	 * @return the date in LocalDate format (ISO_DATE = yyyy-mm-dd)
	 * @since 1.5
	 */
	public static LocalDate convertToProgramStandardDate(String currentDate) {
		return LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
	}

	/**
	 * This method transforms the string date to a proper LocalDate
	 *
	 * @param date      date in String format mm/dd/yyyy
	 * @param formatter generalising this method to handle different formats
	 * @return the date in LocalDate ISO yyyyMMdd format
	 * @since 1.6
	 */
	@NotNull
	public static LocalDate convertToLocalDate(String date, String formatter) {
		AtomicInteger year = new AtomicInteger();
		AtomicInteger month = new AtomicInteger();
		AtomicInteger day = new AtomicInteger();

		String[] st = date.split("/");

		int md = Integer.parseInt(st[0]);
		int dm = Integer.parseInt(st[1]);
		int y = Integer.parseInt(st[2]);

		if (formatter.equals("m/d/y")) {
			if (md > 12) {
				System.out.println("wrong input in xls file [should be d/m/y]");
				month.set(dm);
				day.set(md);
			} else {
				month.set(md);
				day.set(dm);
			}
		} else if (formatter.equals("d/m/y")) {
			if (dm > 12) {
				System.out.println("wrong input in xls file [should be m/d/y]");
				month.set(md);
				day.set(dm);
			} else {//"d/m/y"
				month.set(dm);
				day.set(md);
			}
		}
		year.set(y);
		return LocalDate.of(year.get(), month.get(), day.get());
	}

	/**
	 * This method takes in hour and minute in long and returns a LocalTime from
	 * it
	 *
	 * @param hr  the Hour specifed as long value
	 * @param min the Minutes specified as minute value
	 * @return the time in LocalTime
	 */
	public static LocalTime convertToTime(long hr, long min) {
		return LocalTime.of((int) hr, (int) min);
	}

	private static LocalTime getTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		Duration duration = Duration.between(fromDateTime, toDateTime);

		AtomicLong seconds = new AtomicLong(duration.getSeconds());

		AtomicLong hours = new AtomicLong(seconds.get() / SECONDS_PER_HOUR);
		AtomicLong minutes = new AtomicLong(((seconds.get() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE));
		// long secs = (seconds % SECONDS_PER_MINUTE);

		return convertToTime(hours.get(), minutes.get());
	}
}
