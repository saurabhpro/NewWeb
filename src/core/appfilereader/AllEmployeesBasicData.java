package core.appfilereader;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.factory.fileimportfactory.XLSXSheetAndCell;
import core.model.employeemodal.BasicEmployeeDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by kumars on 3/1/2016. this contains all employees basic records
 */
public class AllEmployeesBasicData extends InitialObjects {


    private int numberOfRowsInBio;
    private Sheet sheet = null;

    public AllEmployeesBasicData() {
    }

    /**
     * @param allEmpDetails
     */
    public AllEmployeesBasicData(String allEmpDetails) {
        sheet = new XLSXSheetAndCell().ApacheXLSXSheet(allEmpDetails);
        // Get the first sheet
        numberOfRowsInBio = sheet.getPhysicalNumberOfRows();
    }

    /**
     * @param column the column number starting from 0 from which data is to be retrieved
     * @param row    the row number stating with 0 from which data is to be retrieved
     * @return return the contents for the cell specified by column and row
     */
    private String getCustomCellContent(int column, int row) {
        Cell cell = sheet.getRow(row).getCell(column);
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return Objects.toString((int) cell.getNumericCellValue());
        else
            return cell.getStringCellValue();
    }

    /**
     * Method to display the contents read till reading of the All Employees Basic Details file
     *
     * @implNote Remove this from the production release version
     */
    public void displayFile() {
        allEmployeeRecordMap.values().forEach(BasicEmployeeDetails::displayBasicInfo);
    }

    /**
     * Reads the All Employees Basic record data like email id, name, reval id and corresponding Salesforce Id
     */
    public void readFile() {
        allEmployeeRecordMap = new TreeMap<>();

        for (int row = 0; row < numberOfRowsInBio; row++) {
            BasicEmployeeDetails b = new BasicEmployeeDetails();

            b.setName(getCustomCellContent(0, row));
            b.setEmailId(getCustomCellContent(1, row));
            b.setEmpId(getCustomCellContent(2, row));
            b.setSalesForceId(getCustomCellContent(3, row));

            allEmployeeRecordMap.put(b.getEmpId(), b);
        }
    }

    /**
     * method to convert the object map containing the all employees basic data record to Json files
     */
    public void toJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        // For testing
        Map<String, BasicEmployeeDetails> user = allEmployeeRecordMap;

        try {
            File jfile = new File(FILE_PATH + "JsonFiles\\Emails.json");
            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
