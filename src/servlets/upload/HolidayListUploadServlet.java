package servlets.upload;

import com.oreilly.servlet.MultipartRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by AroraA on 07-03-2016.
 */
@WebServlet(name = "servlets.upload.HolidayListUploadServlet", urlPatterns = {"/upHoliday"})
public class HolidayListUploadServlet extends HttpServlet {
    MultipartRequest m;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File holidayListFilePath = new File("C:\\ProjectFiles\\HolidayList");
        if (!holidayListFilePath.exists()) {
            holidayListFilePath.mkdirs();
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        m = new MultipartRequest(request, holidayListFilePath.toString());

        String filename = m.getFilesystemName("holidayListFile");
        out.println(filename + "Successfully Uploaded");

        response.sendRedirect("./MainPage.jsp#/UploadFiles");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
