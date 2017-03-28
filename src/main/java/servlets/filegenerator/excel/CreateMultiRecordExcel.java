package servlets.filegenerator.excel;

import core.model.ProjectConstants;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by kumars on 4/12/2016.
 */
public class CreateMultiRecordExcel {
	private static XSSFWorkbook workbook;

	public static void fromJsonToExcel(String fileName, List<String> listOfIds, String fileToUse) {
		workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Employee Record");

		if (listOfIds.get(0) == null) {
			makeAll(fileName, fileToUse, sheet);
		} else
			makeSpecifiedExcel(fileName, listOfIds, fileToUse, sheet);
	}

	private static void makeAll(String fileName, String fileToUse, XSSFSheet sheet) {
		try {
			JSONParser parser = new JSONParser();
			Object a = parser.parse(new FileReader(FILE_PATH + "JsonFiles" + ProjectConstants.FILE_SEPARATOR + fileToUse + ".json"));
			JSONObject jsonObject = (JSONObject) a;
			Set s = jsonObject.keySet();

			int rowNumber = 0;
			for (Object value : s) {
				String jKey = (String) value;
				/*
				 * First Row for Each Employee
				 */
				// System.out.println(jKey);
				DataParserForExcel.setStyles(workbook);
				DataParserForExcel.createHeaderRowForMonthlyData(sheet, rowNumber);
				rowNumber = DataParserForExcel.createDailyData(sheet, jsonObject, rowNumber, jKey);
			}

			for (int i = 0; i < 7; i++) {
				sheet.autoSizeColumn(i);
			}

			FileOutputStream fos = new FileOutputStream(new File(fileName));
			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void makeSpecifiedExcel(String fileName, List<String> listOfIds, String fileToUse, XSSFSheet sheet) {
		try {
			JSONParser parser = new JSONParser();
			Object a = parser.parse(new FileReader(FILE_PATH + "JsonFiles" + ProjectConstants.FILE_SEPARATOR + fileToUse + ".json"));
			JSONObject jsonObject = (JSONObject) a;
			Set s = jsonObject.keySet();

			int rowNumber = 0;
			for (String webKey : listOfIds) {
				for (Object value : s) {
					String jKey = (String) value;
					// System.out.println("@Row = " + rowNumber);
					if (jKey.equals(webKey)) {
						/*
						 * First Row for Each Employee
						 */
						DataParserForExcel.setStyles(workbook);
						DataParserForExcel.createHeaderRowForMonthlyData(sheet, rowNumber);
						rowNumber = DataParserForExcel.createDailyData(sheet, jsonObject, rowNumber, jKey);
					}
				}
			}

			for (int i = 0; i < 7; i++) {
				sheet.autoSizeColumn(i);
			}

			FileOutputStream fos = new FileOutputStream(new File(fileName));
			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
}
