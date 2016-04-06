package filegen;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import core.model.WebJSONModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kumars on 4/5/2016.
 */
public class CreateSingleRecordPDF {
    private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static WebJSONModel ob;

    public static WebJSONModel getData(String key) {
        try {
            JSONParser parser = new JSONParser();
            Object a = parser.parse(new FileReader("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\web\\json\\" + "MarkDiscrepancy.json"));
            JSONObject jsonObject = (JSONObject) a;

            JSONArray slideContent = (JSONArray) jsonObject.get(0);
            for (Object o : slideContent) {

                JSONObject person = (JSONObject) o;

                if (person.get(0).equals(key)) {
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

             /*       for (Object c : allDateDetailsList) {
                        System.out.println(c + "");
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Document createPDF(String fileName, String id) {
        Document document = null;

        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addMetaData(document);

            ob = getData(id);

            addTitlePage(document);

            createTable(document);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static void addMetaData(Document document) {
        document.addTitle("Single Person Report");
        document.addSubject("Single Perosn Report");
        document.addAuthor("Saurabh");
        document.addCreator("Amrita");
    }

    private static void addTitlePage(Document document)
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

    private static void createTable(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(3);

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
    }

}
