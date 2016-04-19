package core.appfilereader;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.factory.fileimportfactory.XLSXSheetAndCell;
import core.model.appfilereadermodal.FileOperations;
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
public class AllEmployeesBasicData extends InitialObjects implements FileOperations {


    private int numberOfRowsInBio;
    private Sheet sheet = null;

    public AllEmployeesBasicData() {
    }

    public AllEmployeesBasicData(String allEmpDetails) {
        sheet = new XLSXSheetAndCell().ApacheXLSXSheet(allEmpDetails);
        // Get the first sheet
        numberOfRowsInBio = sheet.getPhysicalNumberOfRows();
    }

    private String getCustomCellContent(int column, int row) {
        Cell cell = sheet.getRow(row).getCell(column);
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return Objects.toString((int) cell.getNumericCellValue());
        else
            return cell.getStringCellValue();
    }

    @Override
    public void displayFile() {
        allEmployeeRecordMap.values().forEach(BasicEmployeeDetails::displayBasicInfo);
    }

    @Override
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
