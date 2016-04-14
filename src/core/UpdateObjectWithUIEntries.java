package core;

import core.combined.MarkDiscrepancy;
import core.model.FinalObjectModel;
import core.model.ListGeneratorModel;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static core.model.ProjectConstants.*;

/**
 * Created by Saurabh on 4/14/2016.
 */
public class UpdateObjectWithUIEntries {
    private Map<String, FinalObjectModel> objectToBeUpdated = MarkDiscrepancy.EmpCombinedMap;

    public void updateObject(String empRevalId, String currentDate, String checkIn, String checkOut) {
        LocalDate date = convertToProgramStandardDate(currentDate);
        LocalTime checkInTime = convertToProgramStandardTime(checkIn);
        LocalTime checkOutTime = convertToProgramStandardTime(checkOut);
        update(empRevalId, date, checkInTime, checkOutTime);

        MarkDiscrepancy.EmpCombinedMap = objectToBeUpdated;
        generateJsons();

    }

    private void generateJsons() {

        ListGeneratorModel ob = new PublicHolidayWorkerJson();
        ob.generate();
        //ph.displayOnConsole();
        ob.createJSONList(PUBLIC_HOLIDAY_WORKER_LIST);

        ob = new AllEmployeeDetailsJson();
        ob.generate();
        //c.displayOnConsole();
        ob.createJSONList(ALL_EMP_WORKERS_LIST);

        ob = new OnlyDiscrepancyDetailsJson();
        ob.generate();
        //ob.displayOnConsole();
        ob.createJSONList(DISCREPANCY_IN_WORKERS_LIST);

        ob = new WeekendWorkerJson();
        ob.generate();
        //ow.displayOnConsole();
        ob.createJSONList(WEEKEND_WORKERS_LIST);

        File source = new File(FILE_PATH + "JsonFiles");
        //update this for amrita and home
        //TODO when deploying on actual server, use this to copy the JSon files directory
        //File dest = new File("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");
        File dest = new File("C:\\Users\\Saurabh\\Documents\\GitHub\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");
        //File dest = new File("C:\\Users\\Aroraa\\IdeaProjects\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");

        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LocalTime convertToProgramStandardTime(String time) {
        return LocalTime.parse(time);
    }

    private LocalDate convertToProgramStandardDate(String currentDate) {
        return LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
    }

    private void update(String empRevalId, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime) {
        objectToBeUpdated.values().stream().filter(obj -> obj.getEmpId().equals(empRevalId)).forEach(obj -> {
            obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckIn(checkInTime);
            obj.attendanceOfDate[date.getDayOfMonth() - 1].setCheckOut(checkOutTime);
        });
    }
}
