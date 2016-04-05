package core.jxcel;

import core.combinedModel.*;
import core.emplmasterrecord.EmployeeMasterData;
import core.factory.SheetFactory;
import core.model.ListGenerator;
import core.model.Version;
import core.view.JsonMapper;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by kumars on 2/8/2016.
 */

@Version(MaxVersion = 1, MinVersion = 0)
public class ReadExcel {
    private static String biometricFile;
    private static String hrNetFile;
    private static String empListID;

    public static void main(String[] args) throws IOException, ParseException {
        Object fileWorker;
        SheetFactory sheetFactory = new SheetFactory();

        setEmpListID(".\\ExcelFiles\\Emails.xlsx");
        setBiometricFile(".\\ExcelFiles\\jan leaves.xls");
        setHrNetFile(".\\ExcelFiles\\Jan-Feb FF Report.xlsx");

        EmployeeMasterData employeeMasterData = new EmployeeMasterData(empListID);
        employeeMasterData.readFile();

        // read Biometric Excel File
        fileWorker = sheetFactory.dispatch("Jxcel", biometricFile);
        if (fileWorker instanceof BiometricFileWorker) {
            ((BiometricFileWorker) fileWorker).readFile();
        }

        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", hrNetFile);
        if (fileWorker instanceof HrnetFileWorker) {
            ((HrnetFileWorker) fileWorker).readFile();
        }

        Combined2 combined2 = new Combined2();
        combined2.combineFiles();
        new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);

        // displayAllDates Combined Files
        //	combined2.displayCombineFiles();

        // remove discrepancies
        Discrepancy discrepancy = new Discrepancy();
        discrepancy.findDiscrepancy();

        ListGenerator ph = new PublicHolidayWorkerJson();
        ph.generate();
        ph.displayOnConsole();
        ph.createJSONList("PublicHoliday");

        ListGenerator c = new AllEmployeeDetailsJson();
        c.generate();
        c.createJSONList("WebDetails");

        ListGenerator od = new OnlyDiscrepancyDetailsJson();
        od.generate();
        //od.displayOnConsole();
        od.createJSONList("Discrepancy");
    }

    private static void setBiometricFile(String biometricFile) {
        ReadExcel.biometricFile = biometricFile;
    }

    private static void setEmpListID(String empListID) {
        ReadExcel.empListID = ".\\ExcelFiles\\Emails.xlsx";
    }

    private static void setHrNetFile(String hrNetFile) {
        ReadExcel.hrNetFile = hrNetFile;
    }

}
