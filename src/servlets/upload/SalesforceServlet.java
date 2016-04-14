package servlets.upload;

import com.oreilly.servlet.MultipartRequest;
import core.jxcel.FileFolderWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static core.model.ProjectConstants.FINANCIAL_FORCE_FILE_PATH;

/**
 * Created by AroraA on 07-03-2016.
 */
@WebServlet(name = "servlets.upload.SalesforceServlet", urlPatterns = {"/upSalesforce"})
public class SalesforceServlet extends HttpServlet {
    MultipartRequest m;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File salesforceFilePath = new File(FINANCIAL_FORCE_FILE_PATH);
        if (!salesforceFilePath.exists()) {
            salesforceFilePath.mkdirs();
        } else {
            FileFolderWorker.cleanDirectory(salesforceFilePath);
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        m = new MultipartRequest(request, salesforceFilePath.toString());

        String filename = m.getFilesystemName("salesforceFile");
        out.println(filename + "Successfully Uploaded");

        response.sendRedirect("./MainPage.jsp#/UploadFiles");
    }
}
