package core.jxcel;

import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.emplmasterrecord.AllEmployeesBasicData;
import core.factory.SheetFactory;
import core.model.FileOperations;
import core.model.ListGeneratorModel;
import core.model.Version;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;
import servlets.filegen.excel.CreateMultiRecordExcel;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 2/8/2016.
 */

@Version(MaxVersion = 1, MinVersion = 0)
public class ReadExcel {

    public static void main(String[] args) throws IOException, ParseException {
        FileOperations fileWorker;
        SheetFactory sheetFactory = new SheetFactory();

        setEmployeeRecordFileName(ALL_EMPLOYEE_RECORD_FILE_PATH);
        setBiometricFileName(BIOMETRIC_FILE_PATH);
        setFinancialForceFileName(FINANCIAL_FORCE_FILE_PATH);

        AllEmployeesBasicData allEmployeesBasicData = new AllEmployeesBasicData(getEmployeeRecordFileName());
        allEmployeesBasicData.readFile();
        allEmployeesBasicData.toJsonFile();      //Writes Emails.json

        // read Biometric Excel File
        fileWorker = sheetFactory.dispatch("Jxcel", getBiometricFileName());
        fileWorker.readFile();      //TODO readFile argument should also be a factory

        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", getFinancialForceFileName());
        fileWorker.readFile();      //TODO readFile argument should also be a factory

        CombineFile combineFile = new CombineFile();
        combineFile.combineFiles();
        //displayAllDates Combined Files
        combineFile.displayCombineFiles();

        // remove discrepancies
        MarkDiscrepancy markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

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

        List<String> nme = new ArrayList<>();
        nme.add("Saurabh");
        nme.add("homr");
        nme.add("emai");

        CreateMultiRecordExcel.fromJsonToExcel(nme,"AllWorkers");
    }
}
