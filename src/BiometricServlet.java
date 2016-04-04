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
 * Created by Saurabh on 4/3/2016.
 */
@WebServlet(name = "BiometricServlet", urlPatterns = {"/upBiometric"})
public class BiometricServlet extends HttpServlet {
    MultipartRequest m;
    String message;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        File biometricFilePath = new File("C:\\ProjectFiles\\Biometric");
        if (!biometricFilePath.exists()) {
            biometricFilePath.mkdirs();
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        m = new MultipartRequest(request, biometricFilePath.toString());

        String filename = m.getFilesystemName("biometricFile");

        //request.setAttribute("message", "Upload has been done successfully!");

        response.sendRedirect("http://localhost:8080/Email_war_exploded/#/uploadBiometric");

        /*
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/../uploadBiometric.jsp");
        requestDispatcher.include(request, response);
        */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
