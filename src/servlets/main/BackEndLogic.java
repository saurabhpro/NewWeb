package servlets.main;

import core.appfilereader.AllEmployeesBasicData;
import core.appfilereader.InitialObjects;
import core.combined.CombineFile;
import core.combined.FinalObject;
import core.combined.MarkDiscrepancy;
import core.factory.fileimportfactory.SheetFactory;
import core.factory.objectfillerfactory.FileObjectFactory;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.FileOperations;
import core.model.employeemodal.BasicEmployeeDetails;
import core.model.viewmodal.FinalObjectModel;
import core.model.viewmodal.ListGeneratorModel;
import core.utils.FileFolderWorker;
import core.utils.Serialize;
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
        BasicEmployeeDetails fillObject;

        SheetFactory sheetFactory = new SheetFactory();
        FileObjectFactory objectFactory = new FileObjectFactory();

        setEmployeeRecordFileName(FileFolderWorker.getPathToFile(ALL_EMPLOYEE_RECORD_FILE_PATH));
        setBiometricFileName(FileFolderWorker.getPathToFile(BIOMETRIC_FILE_PATH));
        setFinancialForceFileName(FileFolderWorker.getPathToFile(FINANCIAL_FORCE_FILE_PATH));

        AllEmployeesBasicData allEmployeesBasicData = new AllEmployeesBasicData(getEmployeeRecordFileName());
        allEmployeesBasicData.readFile();
        allEmployeesBasicData.toJsonFile();      //Writes Emails.json

        String callerClassName = new Exception().getStackTrace()[1].getClassName();
        if (!callerClassName.equals("core.UpdateObjectWithUIEntries")) {
            // read Biometric Excel File
            fileWorker = sheetFactory.dispatch("Jxcel", getBiometricFileName());
            fillObject = objectFactory.dispatch("Biometric");
            fileWorker.readFile(fillObject);      //TODO readFile argument should also be a factory

            Serialize.serialSave(UPDATED_RECORD_OBJECTS + "Biometric.ser", InitialObjects.empBiometricMap);
        } else {
            InitialObjects.empBiometricMap = (Map<String, EmployeeBiometricDetails>) Serialize.serialRetrieve(UPDATED_RECORD_OBJECTS + "biometric.ser");
        }
        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", getFinancialForceFileName());
        fillObject = objectFactory.dispatch("Hrnet");
        fileWorker.readFile(fillObject);      //TODO readFile argument should also be a factory

    }

    public static Map<String, FinalObjectModel> getFinalObject() {
        FinalObject combineFile = new CombineFile();
        combineFile.combineFiles();

        //displayAllDates Combined Files
        //combineFile.displayCombineFiles();

        // remove discrepancies
        FinalObject markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

        return FinalObject.EmpCombinedMap;

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
