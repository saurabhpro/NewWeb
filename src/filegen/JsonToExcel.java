package filegen;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Set;

/**
 * Created by kumars on 4/6/2016.
 */
public class JsonToExcel {
    public static void fromJsonToExcel(String key, String fileToUse, String toUse) {
        try {


            FileOutputStream fos = null;

            FileInputStream fis = new FileInputStream(new File("C:\\ProjectFiles\\test.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
//Create rows
            XSSFRow row0 = sheet.createRow(0);
            XSSFCell r1c0 = row0.createCell(0);
            r1c0.setCellValue("EmpID");

            XSSFRow row1 = sheet.createRow(1);
            XSSFCell r1c1 = row1.createCell(0);
            r1c1.setCellValue("SalesforceID");

            XSSFRow row2 = sheet.createRow(2);
            XSSFCell r1c2 = row2.createCell(0);
            r1c2.setCellValue("EmpName");

            XSSFRow row3 = sheet.createRow(3);
            XSSFCell r1c3 = row3.createCell(0);
            r1c3.setCellValue("EmailId");

            XSSFRow row4 = sheet.createRow(4);
            XSSFCell r1c4 = row4.createCell(0);
            r1c4.setCellValue("Average Check In Time For Month");

            XSSFRow row5 = sheet.createRow(5);
            XSSFCell r1c5 = row5.createCell(0);
            r1c5.setCellValue("Average Check In Time For Month");

            XSSFRow row6 = sheet.createRow(6);
            XSSFCell r1c6 = row6.createCell(0);
            r1c6.setCellValue("Average Work Time For Month");

            String projectname = null;

            JSONParser parser = new JSONParser();
            Object a = parser.parse(new FileReader("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\web\\json\\" + toUse + ".json"));
            JSONObject jsonObject = (JSONObject) a;
            Set s = jsonObject.keySet();

            for (Object value : s) {
                String jKey = (String) value;
                int i = 0;
                if (jKey.equals(key)) {

                    JSONObject person = (JSONObject) jsonObject.get(jKey);

                    XSSFRow row00 = sheet.getRow(0);
                    XSSFCell r1c00 = row00.createCell(i + 1);
                    r1c00.setCellValue(person.get("EmpId").toString());
                    XSSFRow row102 = sheet.getRow(1);
                    XSSFCell r1c102 = row102.createCell(i + 1);
                    r1c102.setCellValue(person.get("empSalesforceId").toString());
                    XSSFRow row202 = sheet.getRow(2);
                    XSSFCell r1c202 = row202.createCell(i + 1);
                    r1c202.setCellValue(person.get("empName").toString());
                    XSSFRow row30 = sheet.getRow(3);
                    XSSFCell r1c30 = row30.createCell(i + 1);
                    r1c30.setCellValue(person.get("empEmailId").toString());
                    XSSFRow row40 = sheet.getRow(4);
                    XSSFCell r1c40 = row40.createCell(i + 1);
                    r1c40.setCellValue(person.get("empAvgCheckInTimeForMonth").toString());
                    XSSFRow row50 = sheet.getRow(5);
                    XSSFCell r1c50 = row50.createCell(i + 1);
                    r1c50.setCellValue(person.get("empAvgCheckOutTimeForMonth").toString());
                    XSSFRow row60 = sheet.getRow(6);
                    XSSFCell r1c60 = row60.createCell(i + 1);
                    r1c60.setCellValue(person.get("empAvgWorkHoursForMonth").toString());

    /*XSSFRow row220 = sheet.getRow(22);
    XSSFCell r1c220 = row220.createCell(i+1);
    r1c220.setCellValue(obj.getString("contact_no"));*/
                }

                fis.close();
                fos = new FileOutputStream(new File("C:\\ProjectFiles\\test.xlsx"));
                workbook.write(fos);
                fos.flush();
                fos.close();

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}


