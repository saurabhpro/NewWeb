package core.jxcel;

import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.emplmasterrecord.EmployeeMasterData;
import core.factory.SheetFactory;
import core.model.ListGeneratorModel;
import core.model.Version;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;

import java.io.IOException;
import java.text.ParseException;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 2/8/2016.
 */

@Version(MaxVersion = 1, MinVersion = 0)
public class ReadExcel {

    public static void main(String[] args) throws IOException, ParseException {
        Object fileWorker;
        SheetFactory sheetFactory = new SheetFactory();

        setEmployeeRecordFileName(ALL_EMPLOYEE_RECORD_FILE_PATH);
        setBiometricFileName(BIOMETRIC_FILE_PATH);
        setFinancialForceFileName(FINANCIAL_FORCE_FILE_PATH);

        EmployeeMasterData employeeMasterData = new EmployeeMasterData(getEmployeeRecordFileName());
        employeeMasterData.readFile();
        employeeMasterData.toJsonFile();

        // read Biometric Excel File
        fileWorker = sheetFactory.dispatch("Jxcel", getBiometricFileName());
        if (fileWorker instanceof BiometricFileWorker) {
            ((BiometricFileWorker) fileWorker).readFile();
        }

        // read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", getFinancialForceFileName());
        if (fileWorker instanceof HrnetFileWorker) {
            ((HrnetFileWorker) fileWorker).readFile();
            //   ((HrnetFileWorker) fileWorker).displayFile();
        }

        CombineFile combineFile = new CombineFile();
        combineFile.combineFiles();
        //new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);

        //displayAllDates Combined Files
        //combineFile.displayCombineFiles();

        // remove discrepancies
        MarkDiscrepancy markDiscrepancy = new MarkDiscrepancy();
        markDiscrepancy.findDiscrepancy();

        ListGeneratorModel ph = new PublicHolidayWorkerJson();
        ph.generate();
        //ph.displayOnConsole();
        ph.createJSONList(PUBLIC_HOLIDAY_WORKER_LIST);

        ListGeneratorModel c = new AllEmployeeDetailsJson();
        c.generate();
        //c.displayOnConsole();
        c.createJSONList(ALL_EMP_WORKERS_LIST);

        ListGeneratorModel od = new OnlyDiscrepancyDetailsJson();
        od.generate();
        od.displayOnConsole();
        od.createJSONList(DISCREPANCY_IN_WORKERS_LIST);

        ListGeneratorModel ow = new WeekendWorkerJson();
        ow.generate();
        //ow.displayOnConsole();
        ow.createJSONList(WEEKEND_WORKERS_LIST);
    }
}
