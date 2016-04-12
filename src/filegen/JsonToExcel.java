package filegen;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kumars on 4/6/2016.
 */
public class JsonToExcel {
    public static void fromJsonToExcel(String id, String fileToUse) {
        try {
            FileOutputStream fos = null;

            FileInputStream fis = new FileInputStream(new File("C:\\ProjectFiles\\test.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
//Create rows
            int ro, co;
            ro = co = 0;
            XSSFRow row0 = sheet.createRow(ro);
            XSSFCell r1c0 = row0.createCell(0 + co);
            r1c0.setCellValue("EmpID");

          /*  XSSFRow row1 = sheet.createRow(ro);
            XSSFCell r1c1 = row1.createCell(1+co);
            r1c1.setCellValue("SalesforceID");

            XSSFRow row2 = sheet.createRow(ro);
            XSSFCell r1c2 = row2.createCell(2+co);
            r1c2.setCellValue("EmpName");

            XSSFRow row3 = sheet.createRow(ro);
            XSSFCell r1c3 = row3.createCell(3+co);
            r1c3.setCellValue("EmailId");

            XSSFRow row4 = sheet.createRow(ro);
            XSSFCell r1c4 = row4.createCell(4+co);
            r1c4.setCellValue("Average Check In Time For Month");

            XSSFRow row5 = sheet.createRow(ro);
            XSSFCell r1c5 = row5.createCell(5+co);
            r1c5.setCellValue("Average Check In Time For Month");

            XSSFRow row6 = sheet.createRow(ro);
            XSSFCell r1c6 = row6.createCell(6+co);
            r1c6.setCellValue("Average Work Time For Month");

            String projectname = null;
*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}