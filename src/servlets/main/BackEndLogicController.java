/*
 * Copyright (c) 2016.
 */

package servlets.main;

import core.appfilereader.AllEmployeesBasicData;
import core.appfilereader.InitialObjects;
import core.combined.CombineFile;
import core.combined.FinalObject;
import core.combined.MarkDiscrepancy;
import core.factory.fileimportfactory.SheetFactory;
import core.factory.objectfillerfactory.FileObjectFactory;
import core.model.ProjectConstants;
import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.appfilereadermodal.FileOperations;
import core.model.employeemodal.BasicEmployeeDetails;
import core.model.viewmodal.ListGeneratorModel;
import core.utils.FileFolderWorker;
import core.utils.Serialize;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;

import java.io.File;
import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 4/15/2016.
 */
public class BackEndLogicController {
    /**
     *
     */
    static void readDataFromSourcesToInitialObjects() {

        FileOperations fileWorker;
        BasicEmployeeDetails fillObject;

        SheetFactory sheetFactory = new SheetFactory();
        FileObjectFactory objectFactory = new FileObjectFactory();

        /**
         * This should run only once when any of the file is uploaded,
         * create one serial object folder if there is none
         */
        File serialPath = new File(UPDATED_RECORD_OBJECTS);
        if (!serialPath.exists()) FileFolderWorker.makeDirectory(serialPath);

        setEmployeeRecordFileName(FileFolderWorker.getPathToFile(ALL_EMPLOYEE_RECORD_FILE_PATH));
        setBiometricFileName(FileFolderWorker.getPathToFile(BIOMETRIC_FILE_PATH));
        setFinancialForceFileName(FileFolderWorker.getPathToFile(FINANCIAL_FORCE_FILE_PATH));

        AllEmployeesBasicData allEmployeesBasicData = new AllEmployeesBasicData(getEmployeeRecordFileName());
        allEmployeesBasicData.readFile();
        //  allEmployeesBasicData.toJsonFile();      //Writes Emails.json
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

    }

    /**
     *
     */
    public static void readFromSerialObjects() {
        InitialObjects.allEmployeeBasicRecordMap = (Map<String, BasicEmployeeDetails>) Serialize.serialRetrieve(UPDATED_RECORD_OBJECTS + "emailList.ser");
        //InitialObjects.allEmployeeBasicRecordMap.values().forEach(BasicEmployeeDetails::displayBasicInfo);
        InitialObjects.empBiometricMap = (Map<String, EmployeeBiometricDetails>) Serialize.serialRetrieve(UPDATED_RECORD_OBJECTS + "biometric.ser");
        //InitialObjects.empBiometricMap.values().forEach(EmployeeBiometricDetails::printEmpBiometricDetails);
        InitialObjects.hrnetDetailsMap = (Map<String, ArrayList<EmployeeHrnetDetails>>) Serialize.serialRetrieve(UPDATED_RECORD_OBJECTS + "salesforce.ser");

        ProjectConstants.setMONTH(InitialObjects.empBiometricMap.get("R2").attendanceOfDate[0].getCurrentDate().getMonth());
        ProjectConstants.setYEAR(Year.of(InitialObjects.empBiometricMap.get("R2").attendanceOfDate[0].getCurrentDate().getYear()));
    }

    /**
     *
     */
    public static void prepareFinalObject() {
        FinalObject combineFile = new CombineFile();
        combineFile.combineFiles();

        //displayAllDates Combined Files
        //combineFile.displayCombineFiles();

        // remove discrepancies
        FinalObject markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

    }

    /**
     *
     */
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
