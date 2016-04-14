package servlets.filegen.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servlets.filegen.FileCreatorModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    private static CellStyle cellStyleForDate;
    private static CellStyle cellStyleForTime;
    private static CellStyle cellStyleFont;


    public static int createDailyData(XSSFSheet sheet, JSONObject jsonObject, int rowNumber, String jKey) {
        int columnNumber = 0;

        JSONObject person = (JSONObject) jsonObject.get(jKey);
        rowNumber += 1;
        setDetailForMonthlyData(rowNumber, columnNumber, sheet, person);
        rowNumber += 2;
        createHeaderRowForDailyDetails(sheet, rowNumber, columnNumber);

        for (FileCreatorModel.DayDetail dayDetail : getFinalCreatorModelObject(person)) {
            rowNumber++;
            setDailyDetail(sheet, dayDetail, rowNumber);
        }

        return rowNumber + 3;
    }

    static void createHeaderRowForMonthlyData(XSSFSheet sheet, int rowNumber) {
        int columnNumber = 0;
        Row row1 = sheet.createRow(rowNumber);
        Cell colVal1 = row1.createCell(columnNumber);
        colVal1.setCellStyle(cellStyleFont);
        colVal1.setCellValue(EMP_REVAL_IND_ID);


        Cell colVal2 = row1.createCell(1 + columnNumber);
        colVal2.setCellStyle(cellStyleFont);
        colVal2.setCellValue(EMP_FINANCIAL_FORCE_ID);


        Cell colVal3 = row1.createCell(2 + columnNumber);
        colVal3.setCellStyle(cellStyleFont);
        colVal3.setCellValue(EMP_NAME);


        Cell colVal4 = row1.createCell(3 + columnNumber);
        colVal4.setCellStyle(cellStyleFont);
        colVal4.setCellValue(EMP_EMAIL_ID);


        Cell colVal5 = row1.createCell(4 + columnNumber);
        colVal5.setCellStyle(cellStyleFont);
        colVal5.setCellValue(EMP_AVERAGE_MONTHLY_CHECK_IN);


        Cell colVal6 = row1.createCell(5 + columnNumber);
        colVal6.setCellStyle(cellStyleFont);
        colVal6.setCellValue(EMP_AVERAGE_MONTHLY_CHECK_OUT);


        Cell colVal7 = row1.createCell(6 + columnNumber);
        colVal7.setCellStyle(cellStyleFont);
        colVal7.setCellValue(EMP_AVERAGE_MONTHLY_WORK_HOURS);

    }

    private static void setDetailForMonthlyData(int rowNumber, int columnNumber, XSSFSheet sheet, JSONObject person) {
        try {
            String tmp;
            Row row2 = sheet.createRow(rowNumber);
            Cell c00 = row2.createCell(columnNumber);
            tmp = person.get("empRevalId").toString();
            c00.setCellValue(tmp);

            Object ob = person.get("empSalesforceId");
            if (ob != null) {
                Cell c01 = row2.createCell(columnNumber + 1);
                c01.setCellType(Cell.CELL_TYPE_NUMERIC);
                tmp = ob.toString();
                if (!tmp.equals(""))
                    c01.setCellValue(Integer.parseInt(tmp));
            }

            ob = person.get("empName");
            if (ob != null) {
                Cell c02 = row2.createCell(columnNumber + 2);
                tmp = ob.toString();
                c02.setCellValue(tmp);
            }

            Cell c03 = row2.createCell(columnNumber + 3);
            tmp = (String) person.get("empEmailId");
            /**
             * 2nd way of handling null cases
             * we can typecast null with String,
             * but we cant call any method like toString() on null
             * */
            c03.setCellValue(tmp);

            Cell c04 = row2.createCell(columnNumber + 4);
            tmp = person.get("empAvgCheckInTimeForMonth").toString();
            time = timeFormatter.parse(tmp);
            c04.setCellStyle(cellStyleForTime);
            c04.setCellValue(time);


            Cell c05 = row2.createCell(columnNumber + 5);
            tmp = person.get("empAvgCheckOutTimeForMonth").toString();
            time = timeFormatter.parse(tmp);
            c05.setCellStyle(cellStyleForTime);
            c05.setCellValue(time);


            Cell c06 = row2.createCell(columnNumber + 6);
            tmp = person.get("empAvgWorkHoursForMonth").toString();
            time = timeFormatter.parse(tmp);
            //System.out.println(time);
            c06.setCellStyle(cellStyleForTime);
            c06.setCellValue(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void createHeaderRowForDailyDetails(XSSFSheet sheet, int rowNumber, int columnNumber) {
        Row row3 = sheet.createRow(rowNumber);
        Cell colVal11 = row3.createCell(columnNumber);
        colVal11.setCellStyle(cellStyleFont);
        colVal11.setCellValue(CURRENT_DATE);

        Cell colVal22 = row3.createCell(1 + columnNumber);
        colVal22.setCellStyle(cellStyleFont);
        colVal22.setCellValue(EMP_ATTENDANCE_STATUS_TYPE);

        Cell colVal33 = row3.createCell(2 + columnNumber);
        colVal33.setCellStyle(cellStyleFont);
        colVal33.setCellValue(EMP_LEAVE_REQUEST_TYPE);

        Cell colVal44 = row3.createCell(3 + columnNumber);
        colVal44.setCellStyle(cellStyleFont);
        colVal44.setCellValue(EMP_CHECK_IN);

        Cell colVal55 = row3.createCell(4 + columnNumber);
        colVal55.setCellStyle(cellStyleFont);
        colVal55.setCellValue(EMP_CHECK_OUT);

        Cell colVal66 = row3.createCell(5 + columnNumber);
        colVal66.setCellStyle(cellStyleFont);
        colVal66.setCellValue(EMP_WORK_HOURS_FOR_THIS_DAY);
    }

    private static void setDailyDetail(XSSFSheet sheet, FileCreatorModel.DayDetail dayDetail, int rowNumber) {
        try {
            int columnNumber = 0;
            Row row4 = sheet.createRow(rowNumber);

            Date date = dateFormatter.parse(dayDetail.getCurrentDate());
            Cell cell = row4.createCell(columnNumber);
            cell.setCellValue(date);
            cell.setCellStyle(cellStyleForDate);

            row4.createCell(columnNumber + 1).setCellValue(dayDetail.getAttendanceStatusType());
            row4.createCell(columnNumber + 2).setCellValue(dayDetail.getLeaveTypeForThisDate());


            String strTime = dayDetail.getCheckIn();
            if (!strTime.equals("NA")) {
                time = timeFormatter.parse(strTime);
                cell = row4.createCell(columnNumber + 3);
                cell.setCellStyle(cellStyleForTime);
                cell.setCellValue(time);
            }

            strTime = dayDetail.getCheckOut();
            if (!strTime.equals("NA")) {
                time = timeFormatter.parse(strTime);
                cell = row4.createCell(columnNumber + 4);
                cell.setCellStyle(cellStyleForTime);
                cell.setCellValue(time);
            }


            strTime = dayDetail.getWorkTimeForDay();
            if (!strTime.equals("NA")) {
                time = timeFormatter.parse(strTime);
                cell = row4.createCell(columnNumber + 5);
                cell.setCellStyle(cellStyleForTime);
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

    public static void setStyles(XSSFWorkbook workbook) {
        CreationHelper createHelper = workbook.getCreationHelper();

        cellStyleForDate = workbook.createCellStyle();
        cellStyleForDate.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        cellStyleForTime = workbook.createCellStyle();
        cellStyleForTime.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm"));

        cellStyleFont = workbook.createCellStyle();//Create cellStyleFont
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        cellStyleFont.setFont(font);//set it to bold
    }

    private static int getMonthLength(JSONObject person) {
        JSONArray allDateDetailsList = (JSONArray) person.get("allDateDetailsList");
        String curr;

        JSONObject day = (JSONObject) allDateDetailsList.get(0);
        curr = (String) day.get("currentDate");
        LocalDate date = LocalDate.parse(curr, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.getDayOfMonth();
    }
}


/*
BASIC_ISO_DATE	Basic ISO date	'20111203'
ISO_LOCAL_DATE	ISO Local Date	'2011-12-03'
ISO_OFFSET_DATE	ISO Date with offset	'2011-12-03+01:00'
ISO_DATE	ISO Date with or without offset	'2011-12-03+01:00'; '2011-12-03'
ISO_LOCAL_TIME	Time without offset	'10:15:30'
ISO_OFFSET_TIME	Time with offset	'10:15:30+01:00'
ISO_TIME	Time with or without offset	'10:15:30+01:00'; '10:15:30'
ISO_LOCAL_DATE_TIME	ISO Local Date and Time	'2011-12-03T10:15:30'
ISO_OFFSET_DATE_TIME	Date Time with Offset	2011-12-03T10:15:30+01:00'
ISO_ZONED_DATE_TIME	Zoned Date Time	'2011-12-03T10:15:30+01:00[Europe/Paris]'
ISO_DATE_TIME	Date and time with ZoneId	'2011-12-03T10:15:30+01:00[Europe/Paris]'
ISO_ORDINAL_DATE	Year and day of year	'2012-337'
ISO_WEEK_DATE	Year and Week	2012-W48-6'
ISO_INSTANT	Date and Time of an Instant	'2011-12-03T10:15:30Z'
RFC_1123_DATE_TIME	RFC 1123 / RFC 822	'Tue, 3 Jun 2008 11:05:30 GMT'
 */