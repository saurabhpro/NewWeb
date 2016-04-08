package core.jxcel;

import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.emplmasterrecord.EmployeeMasterData;
import core.factory.SheetFactory;
import core.model.ListGeneratorModel;
import core.model.Version;
import core.view.AllEmployeeDetailsJson;
import core.view.JsonMapper;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;

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
        setBiometricFile(".\\ExcelFiles\\march 8.xls");
        setHrNetFile(".\\ExcelFiles\\March FF report Final.xlsx");

        EmployeeMasterData employeeMasterData = new EmployeeMasterData(empListID);
        employeeMasterData.readFile();
        employeeMasterData.toJsonFile();

        // read Biometric Excel File
        fileWorker = sheetFactory.dispatch("Jxcel", biometricFile);
        if (fileWorker instanceof BiometricFileWorker) {
            ((BiometricFileWorker) fileWorker).readFile();
        }

        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", hrNetFile);
        if (fileWorker instanceof HrnetFileWorker) {
            ((HrnetFileWorker) fileWorker).readFile();
         //   ((HrnetFileWorker) fileWorker).displayFile();
        }

        CombineFile combineFile = new CombineFile();
        combineFile.combineFiles();
        new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);

        //displayAllDates Combined Files
        //combineFile.displayCombineFiles();

        // remove discrepancies
        MarkDiscrepancy markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

        ListGeneratorModel ph = new PublicHolidayWorkerJson();
        ph.generate();
      //  ph.displayOnConsole();
        ph.createJSONList("PublicHoliday");

        ListGeneratorModel c = new AllEmployeeDetailsJson();
        c.generate();
        c.createJSONList("WebDetails");

        ListGeneratorModel od = new OnlyDiscrepancyDetailsJson();
        od.generate();
        od.displayOnConsole();
        od.createJSONList("Discrepancy");
    }

    public static void setBiometricFile(String biometricFile) {
        ReadExcel.biometricFile = biometricFile;
    }

    public static void setEmpListID(String empListID) {
        ReadExcel.empListID = ".\\ExcelFiles\\Emails.xlsx";
    }

    public static void setHrNetFile(String hrNetFile) {
        ReadExcel.hrNetFile = hrNetFile;
    }

}
