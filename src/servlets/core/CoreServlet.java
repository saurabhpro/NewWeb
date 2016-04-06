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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "CoreServlet", urlPatterns = {"/src/core"})
public class CoreServlet extends HttpServlet {

    private static String biometricFile;
    private static String hrNetFile;
    private static String empListID;

    private static void setBiometricFile(String biometricFile) {
        CoreServlet.biometricFile = biometricFile;
    }

    private static void setEmpListID(String empListID) {
        CoreServlet.empListID = empListID;
    }

    private static void setHrNetFile(String hrNetFile) {
        CoreServlet.hrNetFile = hrNetFile;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Object fileWorker;
            SheetFactory sheetFactory = new SheetFactory();

            setEmpListID("C:\\ProjectFiles\\Emails.xlsx");
            setBiometricFile("C:\\ProjectFiles\\Biometric\\feb leaves.xls");
            setHrNetFile("C:\\ProjectFiles\\Salesforce\\Jan-Feb FF Report.xlsx");

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

            CombineFile combineFile = new CombineFile();
            combineFile.combineFiles();

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
            od.createJSONList("MarkDiscrepancy");
        } catch (Exception ignored) {}

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("mainPage.jsp");
        requestDispatcher.forward(request, response);
    }

}
