package servlets.core;

import core.combined.CombineFile;
import core.combined.MarkDiscrepancy;
import core.emplmasterrecord.AllEmployeesBasicData;
import core.factory.SheetFactory;
import core.jxcel.FileFolderWorker;
import core.model.FileOperations;
import core.model.ListGeneratorModel;
import core.view.AllEmployeeDetailsJson;
import core.view.OnlyDiscrepancyDetailsJson;
import core.view.PublicHolidayWorkerJson;
import core.view.WeekendWorkerJson;
import org.apache.commons.io.FileUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "CoreServlet", urlPatterns = {"/core"})
public class CoreServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

            CombineFile combineFile = new CombineFile();
            combineFile.combineFiles();

            //displayAllDates Combined Files
            //combineFile.displayCombineFiles();

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
        } catch (Exception ignored) {
        }


        File source = new File(FILE_PATH + "JsonFiles");
        //update this for amrita and home
        //TODO when deploying on actual server, use this to copy the JSon files directory
        //File dest = new File("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");
        //File dest = new File("C:\\Users\\Saurabh\\Documents\\GitHub\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");

        File dest = new File("C:\\Users\\Aroraa\\IdeaProjects\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("MainPage.jsp");
        requestDispatcher.forward(request, response);
    }

}
