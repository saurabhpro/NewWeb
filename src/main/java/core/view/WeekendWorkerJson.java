package core.view;

import core.model.ProjectConstants;
import core.model.attendencemodal.AttendanceOfDate;
import core.model.viewmodal.FinalObjectModel;
import core.model.viewmodal.ListGeneratorModel;
import core.model.viewmodal.WebJSONModel;
import core.utils.RevalIdComparator;

import java.util.TreeMap;

import static core.model.attendencemodal.AttendanceStatusType.WEEKEND_HOLIDAY;

/**
 * Created by Saurabh on 4/8/2016.
 *
 * @version 1.1
 * @since 1.0 added revalidcomparator
 */
public class WeekendWorkerJson extends ListGeneratorModel {
	@Override
	public void generate() {
		WebJSONModel webJSONModel;
		filteredEmpDetails = new TreeMap<>(new RevalIdComparator());
		for (FinalObjectModel f : allEmpDetails.values()) {
			// TODO remove the redundant double check
			for (AttendanceOfDate a : f.attendanceOfDate) {
				if (a.getAttendanceStatusType().equals(WEEKEND_HOLIDAY) && a.getWorkTimeForDay() != null) {
					// advantage of this check is additional redundant objects
					// are not stored in the filteredEmpDetails
					webJSONModel = new WebJSONModel(f, ProjectConstants.WEEKEND_WORKERS_LIST);
					filteredEmpDetails.put(f.getEmpId(), webJSONModel);
				}
			}

		}
	}
}
