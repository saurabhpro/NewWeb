package core.utils;

import core.appfilereader.AllEmployeesBasicData;
import core.appfilereader.InitialObjects;
import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.factory.fileimportfactory.SheetFactory;
import core.factory.objectfillerfactory.FileObjectFactory;
import core.model.Version;
import core.model.appfilereadermodal.FileOperations;
import core.model.employeemodal.BasicEmployeeDetails;
import core.model.viewmodal.ListGeneratorModel;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;
import servlets.main.BackEndLogicController;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 2/8/2016.
 */

@Version(MaxVersion = 1, MinVersion = 0)
public class ReadExcel {

    public static void main(String[] args) throws IOException, ParseException {
        FileOperations fileWorker;
        BasicEmployeeDetails fillObject;

        SheetFactory sheetFactory = new SheetFactory();
        FileObjectFactory objectFactory = new FileObjectFactory();

        setEmployeeRecordFileName(FileFolderWorker.getPathToFile(ALL_EMPLOYEE_RECORD_FILE_PATH));
        setBiometricFileName(FileFolderWorker.getPathToFile(BIOMETRIC_FILE_PATH));
        setFinancialForceFileName(FileFolderWorker.getPathToFile(FINANCIAL_FORCE_FILE_PATH));

        AllEmployeesBasicData allEmployeesBasicData = new AllEmployeesBasicData(getEmployeeRecordFileName());
        allEmployeesBasicData.readFile();
        //  allEmployeesBasicData.toJsonFile();      //Writes Emails.json
        File serialPath = new File(UPDATED_RECORD_OBJECTS);
        if (!serialPath.exists()) FileFolderWorker.makeDirectory(serialPath);
        Serialize.serialSave(UPDATED_RECORD_OBJECTS + "emailList.ser", InitialObjects.allEmployeeBasicRecordMap);

        // read Biometric Excel File
        fileWorker = sheetFactory.dispatch("Jxcel", getBiometricFileName());
        fillObject = objectFactory.dispatch("Biometric");
        fileWorker.readFile(fillObject);      //TODO readFile argument should also be a factory
        Serialize.serialSave(UPDATED_RECORD_OBJECTS + "Biometric.ser", InitialObjects.empBiometricMap);

        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", getFinancialForceFileName());
        fillObject = objectFactory.dispatch("Hrnet");
        fileWorker.readFile(fillObject);      //TODO readFile argument should also be a factory
        Serialize.serialSave(UPDATED_RECORD_OBJECTS + "salesforce.ser", InitialObjects.hrnetDetailsMap);

        CombineFile combineFile = new CombineFile();
        combineFile.combineFiles();
        //displayAllDates Combined Files
        //combineFile.displayCombineFiles();

        // remove discrepancies
        MarkDiscrepancy markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

        BackEndLogicController.readFromSerialObjects();

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
