package servlets.core;

import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.emplmasterrecord.AllEmployeesBasicData;
import core.factory.SheetFactory;
import core.jxcel.FileFolderWorker;
import core.model.FileOperations;
import core.model.FinalObjectModel;
import core.model.ListGeneratorModel;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;

import java.util.Map;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 4/15/2016.
 */
public class BackEndLogic {
    public static void readDataFromSources() {
        FileOperations fileWorker;
        SheetFactory sheetFactory = new SheetFactory();

        setEmployeeRecordFileName(FileFolderWorker.getPathToFile(ALL_EMPLOYEE_RECORD_FILE_PATH));
        setBiometricFileName(FileFolderWorker.getPathToFile(BIOMETRIC_FILE_PATH));
        setFinancialForceFileName(FileFolderWorker.getPathToFile(FINANCIAL_FORCE_FILE_PATH));

        AllEmployeesBasicData allEmployeesBasicData = new AllEmployeesBasicData(getEmployeeRecordFileName());
        allEmployeesBasicData.readFile();
        allEmployeesBasicData.toJsonFile();

        // read Biometric Excel File
        fileWorker = sheetFactory.dispatch("Jxcel", getBiometricFileName());
        fileWorker.readFile();      //TODO readFile argument should also be a factory

        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", getFinancialForceFileName());
        fileWorker.readFile();      //TODO readFile argument should also be a factory

    }

    public static Map<String, FinalObjectModel> getFinalObject() {
        CombineFile combineFile = new CombineFile();
        combineFile.combineFiles();

        //displayAllDates Combined Files
        //combineFile.displayCombineFiles();

        // remove discrepancies
        MarkDiscrepancy markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

        return MarkDiscrepancy.EmpCombinedMap;

    }

    public static void generateReportsJson() {
        ListGeneratorModel ob = new PublicHolidayWorkerJson();
        ob.generate();
        //ph.displayOnConsole();
        ob.createJSONList(PUBLIC_HOLIDAY_WORKER_LIST);

        ob = new AllEmployeeDetailsJson();
        ob.generate();
        //c.displayOnConsole();
        ob.createJSONList(ALL_EMP_WORKERS_LIST);

        ob = new OnlyDiscrepancyDetailsJson();
        ob.generate();
        //ob.displayOnConsole();
        ob.createJSONList(DISCREPANCY_IN_WORKERS_LIST);

        ob = new WeekendWorkerJson();
        ob.generate();
        //ow.displayOnConsole();
        ob.createJSONList(WEEKEND_WORKERS_LIST);
    }
}
