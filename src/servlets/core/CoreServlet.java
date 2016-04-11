package servlets.core;

import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.emplmasterrecord.EmployeeMasterData;
import core.factory.SheetFactory;
import core.jxcel.BiometricFileWorker;
import core.jxcel.HrnetFileWorker;
import core.model.ListGeneratorModel;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "CoreServlet", urlPatterns = {"/core"})
public class CoreServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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
            }

            CombineFile combineFile = new CombineFile();
            combineFile.combineFiles();

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
            // od.displayOnConsole();
            od.createJSONList(DISCREPANCY_IN_WORKERS_LIST);

            ListGeneratorModel ow = new WeekendWorkerJson();
            ow.generate();
            //ow.displayOnConsole();
            ow.createJSONList(WEEKEND_WORKERS_LIST);
        } catch (Exception ignored) {
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("MainPage.jsp");
        requestDispatcher.forward(request, response);
    }

}
