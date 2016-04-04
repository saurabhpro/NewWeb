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
@WebServlet(name = "EmailListServlet", urlPatterns = {"/upEmailList"})
public class EmailListServlet extends HttpServlet {
    MultipartRequest m;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File emailListFilePath = new File("C:\\ProjectFiles\\EmailList");
        if (!emailListFilePath.exists()) {
            emailListFilePath.mkdirs();
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        m = new MultipartRequest(request, emailListFilePath.toString());

        String filename = m.getFilesystemName("emailListFile");
        out.println(filename + "Successfully Uploaded");

        response.sendRedirect("http://localhost:8088/Email_war_exploded/#/uploadEmailList");
        /*
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        requestDispatcher.include(request, response);
        */


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
