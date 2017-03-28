package servlets.filegenerator.excel;

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
public class CreateSingleRecordExcel {

	public static void createExcelFromJson(String fileName, String key, String fileToUse) {
		try {
			FileOutputStream fos;

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Employee Record");

			JSONParser parser = new JSONParser();
			Object a = parser.parse(new FileReader(FILE_PATH + "JsonFiles\\" + fileToUse + ".json"));
			JSONObject jsonObject = (JSONObject) a;
			Set s = jsonObject.keySet();

			for (Object value : s) {
				String jKey = (String) value;

				if (jKey.equals(key)) {
					int ro = 0;
					/*
					 * First Row for Each Employee
					 */
					DataParserForExcel.setStyles(workbook);
					DataParserForExcel.createHeaderRowForMonthlyData(sheet, ro);
					DataParserForExcel.createDailyData(sheet, jsonObject, ro, jKey);
				}

				fos = new FileOutputStream(new File(fileName));
				workbook.write(fos);
				fos.flush();
				fos.close();
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}