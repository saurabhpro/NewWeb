package filegen;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import core.model.WebJSONModel;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kumars on 4/5/2016.
 */
public class CreateSingleRecordPDF {
    private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    @Nullable
    public static FileCreatorModel setDataInObject(String key, FileCreatorModel ob, String fileToUse) {
        try {
            JSONParser parser = new JSONParser();
            Object a = parser.parse(new FileReader("C:\\Users\\Saurabh\\Documents\\GitHub\\NewWeb\\web\\json\\" + fileToUse+ ".json"));
            JSONObject jsonObject = (JSONObject) a;
            Set s = jsonObject.keySet();

            for (Object value : s) {
                String jKey = (String) value;

                if (jKey.equals(key)) {

                    JSONObject person = (JSONObject) jsonObject.get(jKey);

                    String id = (String) person.get("empRevalId");
                    ob.setEmpRevalId(id);

                    String salesforceId = (String) person.get("empSalesforceId");
                    ob.setEmpSalesforceId(salesforceId);

                    String empName = (String) person.get("empName");
                    ob.setEmpName(empName);

                    String empEmailId = (String) person.get("empEmailId");
                    ob.setEmpEmailId(empEmailId);

                    String empAvgCheckInTimeForMonth = (String) person.get("empAvgCheckInTimeForMonth");
                    ob.setEmpAvgCheckInTimeForMonth(empAvgCheckInTimeForMonth);

                    String empAvgCheckOutTimeForMonth = (String) person.get("empAvgCheckOutTimeForMonth");
                    ob.setEmpAvgCheckOutTimeForMonth(empAvgCheckOutTimeForMonth);

                    String empAvgWorkHoursForMonth = (String) person.get("empAvgWorkHoursForMonth");
                    ob.setEmpAvgWorkHoursForMonth(empAvgWorkHoursForMonth);

                    JSONArray allDateDetailsList = (JSONArray) person.get("allDateDetailsList");
                    ArrayList<Map<String, String>> tempList = new ArrayList<>();

                    for (Object c : allDateDetailsList) {
                        JSONObject day = (JSONObject) c;
                        Map<String, String> tempMap = new TreeMap<>();
                        String currentDate = (String) day.get("currentDate");
                        String attendanceStatusType = (String) day.get("attendanceStatusType");
                        String leaveTypeForThisDate = (String) day.get("leaveTypeForThisDate");
                        String checkIn = (String) day.get("checkIn");
                        String checkOut = (String) day.get("checkOut");
                        String workTimeForDay = (String) day.get("workTimeForDay");

                        tempMap.put("currentDate", currentDate);
                        tempMap.put("attendanceStatusType", attendanceStatusType);
                        tempMap.put("leaveTypeForThisDate", leaveTypeForThisDate);
                        tempMap.put("checkIn", checkIn);
                        tempMap.put("checkOut", checkOut);
                        tempMap.put("workTimeForDay", workTimeForDay);

                        tempList.add(tempMap);
                    }

                    ob.setAllDateDetailsList(tempList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Document createPDF(String fileName, String id, String fileToUse) {
        Document document = null;
        FileCreatorModel ob = new FileCreatorModel();
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addMetaData(document);

            setDataInObject(id, ob, fileToUse);

            addTitlePage(document, ob);

            createTable(document, ob);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static void addMetaData(Document document) {
        document.addTitle("Single Person Report");
        document.addSubject("Single Person Report");
        document.addAuthor("Saurabh");
        document.addCreator("Amrita");
    }

    private static void addTitlePage(Document document, FileCreatorModel ob)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        creteEmptyLine(preface, 1);
        preface.add(new Paragraph(ob.getEmpName(), TIME_ROMAN));

        creteEmptyLine(preface, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        preface.add(new Paragraph("Report created on "
                + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
        document.add(preface);

    }

    private static void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static void createTable(Document document, FileCreatorModel ob) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(6);

        PdfPCell c1 = new PdfPCell(new Phrase("Emp Id"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Salesforce Id"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("EmpEmail"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Avg Check In"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Avg Check Out"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Avg work hours"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        //for (int i = 0; i < 5; i++) {
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(ob.getEmpRevalId());
        table.addCell(ob.getEmpSalesforceId());
        table.addCell(ob.getEmpEmailId());
        table.addCell(ob.getEmpAvgCheckInTimeForMonth());
        table.addCell(ob.getEmpAvgCheckOutTimeForMonth());
        table.addCell(ob.getEmpAvgWorkHoursForMonth());
        // }
        document.add(table);

        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table2 = new PdfPTable(6);

        PdfPCell c2 = new PdfPCell(new Phrase("Current Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Attendence Status Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Leave Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Check In Time"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Check Out Time"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Work Time for day"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        table2.setHeaderRows(1);

        ArrayList<FileCreatorModel.DayDetail> a = ob.getAllDateDetailsList();
        for(FileCreatorModel.DayDetail mp : a){
            table2.setWidthPercentage(100);
            table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table2.addCell(mp.getCurrentDate());
            table2.addCell(mp.getAttendanceStatusType());
            table2.addCell(mp.getLeaveTypeForThisDate());
            table2.addCell(mp.getCheckIn());
            table2.addCell(mp.getCheckOut());
            table2.addCell(mp.getWorkTimeForDay());
        }

        document.add(table2);
    }

}
