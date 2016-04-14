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

import static core.model.ProjectConstants.ALL_EMPLOYEE_RECORD_FILE_PATH;

/**
 * Created by AroraA on 07-03-2016.
 */
@WebServlet(name = "servlets.upload.EmailListServlet", urlPatterns = {"/upEmailList"})
public class EmailListServlet extends HttpServlet {
    MultipartRequest m;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File emailListFilePath = new File(ALL_EMPLOYEE_RECORD_FILE_PATH);
        if (!emailListFilePath.exists()) {
            emailListFilePath.mkdirs();
        } else {
            FileFolderWorker.cleanDirectory(emailListFilePath);
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        m = new MultipartRequest(request, emailListFilePath.toString());

        String filename = m.getFilesystemName("emailListFile");
        out.println(filename + "Successfully Uploaded");

        response.sendRedirect("./MainPage.jsp#/UploadFiles");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
