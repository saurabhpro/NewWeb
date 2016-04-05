package servlets.core;

import core.combinedModel.*;
import core.emplmasterrecord.EmployeeMasterData;
import core.factory.SheetFactory;
import core.jxcel.BiometricFileWorker;
import core.jxcel.HrnetFileWorker;
import core.jxcel.ReadExcel;
import core.model.ListGenerator;
import core.view.JsonMapper;

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
@WebServlet(name = "CoreServlet", urlPatterns = {"/core"})
public class CoreServlet extends HttpServlet {

    private static String biometricFile;
    private static String hrNetFile;
    private static String empListID =".\\ExcelFiles\\Emails.xlsx";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Object fileWorker;
            SheetFactory sheetFactory = new SheetFactory();

            setEmpListID("C:\\ProjectFiles\\Emails.xlsx");
            setBiometricFile("C:\\ProjectFiles\\Biometric\\jan leaves.xls");
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

            Combined2 combined2 = new Combined2();
            combined2.combineFiles();

            Discrepancy discrepancy = new Discrepancy();
            discrepancy.findDiscrepancy();

            ListGenerator ph = new PublicHolidayWorkerJson();
            ph.generate();
          //  ph.displayOnConsole();
            ph.createJSONList("PublicHoliday");

            ListGenerator c = new AllEmployeeDetailsJson();
            c.generate();
            c.createJSONList("WebDetails");

            ListGenerator od = new OnlyDiscrepancyDetailsJson();
            od.generate();
            //od.displayOnConsole();
            od.createJSONList("Discrepancy");
        }catch (Exception ignored){}

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("mainPage.jsp");
        requestDispatcher.forward(request,response);
    }

    private static void setBiometricFile(String biometricFile) {
       CoreServlet.biometricFile = biometricFile;
    }

    private static void setEmpListID(String empListID) {
       CoreServlet.empListID="C:\\ProjectFiles\\Biometric\\Emails.xlsx";
    }

    private static void setHrNetFile(String hrNetFile) {
        CoreServlet.hrNetFile=hrNetFile;
    }


}
