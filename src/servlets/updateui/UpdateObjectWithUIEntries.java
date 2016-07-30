/*
 * Copyright (c) 2016.
 */

package servlets.updateui;

import core.appfilereader.BiometricFileWorker;
import core.appfilereader.InitialObjects;
import core.model.ProjectConstants;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.attendencemodal.AttendanceStatusType;
import core.utils.Serialize;
import core.utils.TimeManager;
import servlets.main.BackEndLogicHelperUtility;

import java.time.LocalDate;
import java.time.LocalTime;

import static core.model.ProjectConstants.BIOMETRIC_SERIALIZED;
import static core.model.ProjectConstants.UNDEFINED;
import static core.utils.TimeManager.convertToProgramStandardDate;

/**
 * Created by Saurabh on 4/14/2016. Class to update the Biometric file object
 * with the updated entry from UI
 *
 * @author Saurabh
 * @version 1.0
 */
class UpdateObjectWithUIEntries {

	/**
	 * Constructor that initializes the initial objects using serialized files
	 * in case of any exception new serialized objects are created
	 */
	UpdateObjectWithUIEntries() {
		try {
			// Method that reads serialized objects and updates the initial objects
			BackEndLogicHelperUtility.readFromSerialObjects();
		} catch (Exception e) {
			//In case no serialized object was found, create initial objects from scratch and save as serialized
			BackEndLogicHelperUtility.readDataFromSourcesToInitialObjects();
		}
	}

	/**
	 * This method updates the object which is save using serialization.
	 *
	 * @param empRevalId  The employee's Reval Id which will be used to find the
	 *                    entry in map
	 * @param listOfDates the array of dates which needs to be changed
	 * @param checkIn     the array of check-in time for the respective dates
	 * @param checkOut    the array of check-out time for respective dates
	 * @see InitialObjects
	 */
	void updateObjects(String empRevalId, String[] listOfDates, String[] checkIn, String[] checkOut) {
		int i = 0;
		// loop through the list of days
		for (String date : listOfDates) {
			/**
			 * begin calling the chain method for single day from array of days
			 */
			updateObject(empRevalId, date, checkIn[i], checkOut[i]);
			i++;
		}
		/**
		 * Call the method to combine and find discrepancy and update Final
		 * Object accordingly
		 *
		 * @see core.combined.FinalObject
		 * @see core.combined.CombineFile
		 * @see core.combined.MarkDiscrepancy
		 */
		BackEndLogicHelperUtility.prepareFinalObject();
		/**
		 * Call the method to generate Json files based on the FinalObjects
		 *
		 * @see core.model.viewmodal.ListGeneratorModel
		 * @see core.combined.FinalObject
		 */
		BackEndLogicHelperUtility.generateReportsJson();
	}

	/**
	 * The second method in the chain which basically does the validation checks
	 * and performs necessary standard format conversions and then call the
	 * update method
	 *
	 * @param empRevalId  The employee's Reval Id which will be used to find the
	 *                    entry in map
	 * @param currentDate the date which needs to be changed in string
	 * @param checkIn     the check-in time for the respective date in string
	 * @param checkOut    the check-out time for respective date in string
	 */
	private void updateObject(String empRevalId, String currentDate, String checkIn, String checkOut) {
		if (!checkIn.equals(UNDEFINED) && !checkOut.equals(UNDEFINED)) {
			LocalTime checkInTime = TimeManager.convertToProgramStandardTime(checkIn);
			LocalTime checkOutTime = TimeManager.convertToProgramStandardTime(checkOut);

			LocalDate date = convertToProgramStandardDate(currentDate);
			update(empRevalId, date, checkInTime, checkOutTime);
		}
		// System.out.println(date + " " + checkIn + " " + checkOut);
	}

	/**
	 * The third method in the chain which finds the employee who needs to be
	 * updated and updates the checkin, check-out and worktime for date with
	 * attendance status
	 *
	 * @param empRevalId   The employee's Reval Id which will be used to find the
	 *                     entry in map
	 * @param date         the date which needs to be changed in LocalDate
	 * @param checkInTime  the check-out time for respective date in LocalTime
	 * @param checkOutTime the check-out time for respective date in LocalTime
	 */
	private void update(String empRevalId, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime) {
		for (EmployeeBiometricDetails obj : BiometricFileWorker.empBiometricMap.values()) {
			if (obj.getEmpId().equals(empRevalId)) {
				obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckIn(checkInTime);
				obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckOut(checkOutTime);
				obj.attendanceOfDate[date.getDayOfMonth() - 1]
						.setWorkTimeForDay(TimeManager.calculateTimeDifference(checkInTime, checkOutTime, date));
				obj.attendanceOfDate[date.getDayOfMonth() - 1].setAttendanceStatusType(AttendanceStatusType.PRESENT);
			}
		}

		// Save the updated object back using serialization
		Serialize.serialSave(ProjectConstants.UPDATED_RECORD_OBJECTS + BIOMETRIC_SERIALIZED,
				InitialObjects.empBiometricMap);

	}

}
