package core.view;

import core.model.FinalObjectModel;
import core.model.ListGeneratorModel;
import core.model.WebJSONModel;
import core.model.attendence.AttendanceOfDate;

import java.util.TreeMap;

import static core.model.attendence.AttendanceStatusType.WEEKEND_HOLIDAY;

/**
 * Created by Saurabh on 4/8/2016.
 */
public class WeekendWorkerJson extends ListGeneratorModel {
    @Override
    public void generate() {
        WebJSONModel webJSONModel;
        filteredEmpDetails = new TreeMap<>();
        for (FinalObjectModel f : allEmpDetails.values()) {
            for (AttendanceOfDate a : f.attendanceOfDate) {
                if (a.getAttendanceStatusType().equals(WEEKEND_HOLIDAY) && a.getWorkTimeForDay() != null) {
                    //advantage of this check is additional redundant objects are not stored in the filteredEmpDetails
                    webJSONModel = new WebJSONModel(f, "Weekend");
                    filteredEmpDetails.put(f.getEmpId(), webJSONModel);
                }
            }

        }
    }
}
