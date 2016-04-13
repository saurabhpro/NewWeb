package servlets.filegen.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servlets.filegen.FileCreatorModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 4/11/2016.
 */
public class DataParserForExcel {
    static Date time;
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public static int createDailyData(XSSFWorkbook workbook, XSSFSheet sheet, JSONObject jsonObject, CreationHelper createHelper, int ro, String jKey) {
        CellStyle cellStyleForDate = workbook.createCellStyle();
        cellStyleForDate.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        CellStyle cellStyleForTime = workbook.createCellStyle();
        cellStyleForTime.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm"));

        int co = 0;

        JSONObject person = (JSONObject) jsonObject.get(jKey);
        setDetailForMonthlyData(co, ro, sheet, person, cellStyleForTime);
        createHeaderRowForDailyDetails(sheet, ro, co);

        for (FileCreatorModel.DayDetail mp : getFinalCreatorModelObject(person)) {
            setDailyDetail(sheet, cellStyleForDate, cellStyleForTime, mp, ro);
            ro++;
        }

        return ro;
    }

    static void createHeaderRowForMonthlyData(XSSFSheet sheet, int ro, int co) {
        Row row1 = sheet.createRow(ro);
        Cell colVal1 = row1.createCell(co);
        colVal1.setCellValue(EMP_REVAL_IND_ID);

        Cell colVal2 = row1.createCell(1 + co);
        colVal2.setCellValue(EMP_FINANCIAL_FORCE_ID);

        Cell colVal3 = row1.createCell(2 + co);
        colVal3.setCellValue(EMP_NAME);

        Cell colVal4 = row1.createCell(3 + co);
        colVal4.setCellValue(EMP_EMAIL_ID);

        Cell colVal5 = row1.createCell(4 + co);
        colVal5.setCellValue(EMP_AVERAGE_MONTHLY_CHECK_IN);

        Cell colVal6 = row1.createCell(5 + co);
        colVal6.setCellValue(EMP_AVERAGE_MONTHLY_CHECK_OUT);

        Cell colVal7 = row1.createCell(6 + co);
        colVal7.setCellValue(EMP_AVERAGE_MONTHLY_WORK_HOURS);
    }

    private static void setDetailForMonthlyData(int co, int ro, XSSFSheet sheet, JSONObject person, CellStyle cellStyle1) {
        try {
            String tmp;
            Row row2 = sheet.createRow(ro + 2);
            Cell c00 = row2.createCell(co);
            tmp = person.get("empRevalId").toString();
            c00.setCellValue(tmp);

            Cell c01 = row2.createCell(co + 1);
            tmp = person.get("empSalesforceId").toString();
            c01.setCellType(Cell.CELL_TYPE_NUMERIC);
            c01.setCellValue(Integer.parseInt(tmp));

            Cell c02 = row2.createCell(co + 2);
            tmp = person.get("empName").toString();
            c02.setCellValue(tmp);

            Cell c03 = row2.createCell(co + 3);
            tmp = person.get("empEmailId").toString();
            c03.setCellValue(tmp);

            Cell c04 = row2.createCell(co + 4);
            tmp = person.get("empAvgCheckInTimeForMonth").toString();
            time = timeFormatter.parse(tmp);
            c04.setCellStyle(cellStyle1);
            c04.setCellValue(time);


            Cell c05 = row2.createCell(co + 5);
            tmp = person.get("empAvgCheckOutTimeForMonth").toString();
            time = timeFormatter.parse(tmp);
            c05.setCellStyle(cellStyle1);
            c05.setCellValue(tmp);


            Cell c06 = row2.createCell(co + 6);
            tmp = person.get("empAvgWorkHoursForMonth").toString();
            time = timeFormatter.parse(tmp);
            c06.setCellStyle(cellStyle1);
            c06.setCellValue(tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void createHeaderRowForDailyDetails(XSSFSheet sheet, int ro, int co) {
        Row row3 = sheet.createRow(ro + 4);
        Cell colVal11 = row3.createCell(co);
        colVal11.setCellValue(CURRENT_DATE);

        Cell colVal22 = row3.createCell(1 + co);
        colVal22.setCellValue(EMP_ATTENDANCE_STATUS_TYPE);

        Cell colVal33 = row3.createCell(2 + co);
        colVal33.setCellValue(EMP_LEAVE_REQUEST_TYPE);

        Cell colVal44 = row3.createCell(3 + co);
        colVal44.setCellValue(EMP_CHECK_IN);

        Cell colVal55 = row3.createCell(4 + co);
        colVal55.setCellValue(EMP_CHECK_OUT);

        Cell colVal66 = row3.createCell(5 + co);
        colVal66.setCellValue(EMP_WORK_HOURS_FOR_THIS_DAY);
    }

    private static void setDailyDetail(XSSFSheet sheet, CellStyle cellStyle, CellStyle cellStyle1, FileCreatorModel.DayDetail mp, int ro) {
        try {
            int co = 0;
            Row row4 = sheet.createRow(ro + 6);

            Date date = dateFormatter.parse(mp.getCurrentDate());
            Cell cell = row4.createCell(co);
            cell.setCellValue(date);
            cell.setCellStyle(cellStyle);

            row4.createCell(co + 1).setCellValue(mp.getAttendanceStatusType());
            row4.createCell(co + 2).setCellValue(mp.getLeaveTypeForThisDate());


            String strTime = mp.getCheckIn();
            if (!strTime.equals("NA")) {
                time = timeFormatter.parse(strTime);
                cell = row4.createCell(co + 3);
                cell.setCellStyle(cellStyle1);
                cell.setCellValue(time);
            }

            strTime = mp.getCheckOut();
            if (!strTime.equals("NA")) {
                time = timeFormatter.parse(strTime);
                cell = row4.createCell(co + 4);
                cell.setCellStyle(cellStyle1);
                cell.setCellValue(time);
            }


            strTime = mp.getWorkTimeForDay();
            if (!strTime.equals("NA")) {
                time = timeFormatter.parse(strTime);
                cell = row4.createCell(co + 5);
                cell.setCellStyle(cellStyle1);
                cell.setCellValue(time);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<FileCreatorModel.DayDetail> getFinalCreatorModelObject(JSONObject person) {
        FileCreatorModel ob = new FileCreatorModel();
        JSONArray allDateDetailsList = (JSONArray) person.get("allDateDetailsList");
        ArrayList<Map<String, String>> tempList = new ArrayList<>();

        for (Object c : allDateDetailsList) {
            JSONObject day = (JSONObject) c;
            Map<String, String> tempMap = new TreeMap<>();
            tempMap.put("currentDate", (String) day.get("currentDate"));
            tempMap.put("attendanceStatusType", (String) day.get("attendanceStatusType"));
            tempMap.put("leaveTypeForThisDate", (String) day.get("leaveTypeForThisDate"));
            tempMap.put("checkIn", (String) day.get("checkIn"));
            tempMap.put("checkOut", (String) day.get("checkOut"));
            tempMap.put("workTimeForDay", (String) day.get("workTimeForDay"));

            tempList.add(tempMap);
        }

        ob.setAllDateDetailsList(tempList);

        return ob.getAllDateDetailsList();
    }
}
