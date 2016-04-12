package filegen.excel;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by kumars on 4/6/2016.
 */
public class JsonToExcel {

    public static void fromJsonToExcel(String key, String fileToUse) {
        try {
            FileOutputStream fos;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Employee Record");

            CreationHelper createHelper = workbook.getCreationHelper();

            int ro, co;
            ro = co = 0;

            /*
            First Row for Each Employee
             */
            DataParserForExcel.createHeaderRowForMonthlyData(sheet, ro, co);

            JSONParser parser = new JSONParser();
            Object a = parser.parse(new FileReader(FILE_PATH + "JsonFiles\\" + fileToUse + ".json"));
            JSONObject jsonObject = (JSONObject) a;
            Set s = jsonObject.keySet();

            for (Object value : s) {
                String jKey = (String) value;
                ro = 0;
                if (jKey.equals(key)) {
                    DataParserForExcel.createDailyData(workbook, sheet, jsonObject, createHelper, ro, jKey);
                }

                fos = new FileOutputStream(new File("C:\\ProjectFiles\\test1.xlsx"));
                workbook.write(fos);
                fos.flush();
                fos.close();
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}