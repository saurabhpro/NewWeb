package core.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.model.ProjectConstants;
import core.model.attendencemodal.HolidaysList;
import core.model.viewmodal.FinalObjectModel;
import core.model.viewmodal.HolidayWorkerModel;
import core.model.viewmodal.ListGeneratorModel;
import core.model.viewmodal.WebJSONModel;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static core.model.ProjectConstants.FILE_PATH;
import static core.model.attendencemodal.AttendanceStatusType.PUBLIC_HOLIDAY;

/**
 * Created by kumars on 3/4/2016.
 */
public class PublicHolidayWorkerJson extends ListGeneratorModel {
	List<HolidayWorkerModel> holidayWorkerList = new ArrayList<>();

	@Override
	public void generate() {
		for (FinalObjectModel finalObjectModel : allEmpDetails.values()) {
			for (HolidaysList h : HolidaysList.values()) {
				Month currentMonth = h.getDate().getMonth();
				int changeOnDate = h.getDate().getDayOfMonth() - 1;
				if (currentMonth == ProjectConstants.getMONTH()
						&& h.getDate().getDayOfMonth() < ProjectConstants.getNumberOfDaysConsideredInRespectiveMonth()
						&& finalObjectModel.attendanceOfDate[changeOnDate].getAttendanceStatusType() == PUBLIC_HOLIDAY
						&& finalObjectModel.attendanceOfDate[changeOnDate].getCheckIn() != null) {
					HolidayWorkerModel h1 = new WebJSONModel(finalObjectModel)
							.getHolidayWorkerObjForThisDate(changeOnDate);
					if (h1 != null) {
						h1.setHoliday(h);
						holidayWorkerList.add(h1);
					}
				}
			}
		}
	}

	@Override
	public void displayOnConsole() {
		System.out.println("HOLIDAY LIST HIGHLIGHTED");
		holidayWorkerList.forEach(HolidayWorkerModel::display);
	}

	@Override
	public void createJSONList(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		// For testing
		List<HolidayWorkerModel> user = holidayWorkerList;

		try {
			File jfile = new File(FILE_PATH + "JsonFiles" + ProjectConstants.FILE_SEPARATOR + fileName + ".json");
			// Convert object to JSON string and save into file directly
			System.out.println(jfile.getAbsolutePath());
			mapper.writeValue(jfile, user);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
