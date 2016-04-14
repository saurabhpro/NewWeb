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

import static core.model.ProjectConstants.BIOMETRIC_FILE_PATH;

/**
 * Created by Saurabh on 4/3/2016.
 */
@WebServlet(name = "servlets.upload.BiometricServlet", urlPatterns = {"/upBiometric"})
public class BiometricServlet extends HttpServlet {
    MultipartRequest m;
    String message;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File biometricFilePath = new File(BIOMETRIC_FILE_PATH);
        if (!biometricFilePath.exists()) {
            biometricFilePath.mkdirs();
        } else {
            FileFolderWorker.cleanDirectory(biometricFilePath);
        }
        /**
         * mkdirs() will create the specified directory path in its entirety where
         * mkdir() will only create the bottom most directory,
         * failing if it can't find the parent directory of the directory it is trying to create.
         * In other words mkdir() is like mkdir and mkdirs() is like mkdir -p.
         */

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        m = new MultipartRequest(request, biometricFilePath.toString());

        String biometricDate = request.getParameter("biometricDate");

        System.out.println(biometricDate);
        String filename = m.getFilesystemName("biometricFile");

        response.sendRedirect("./MainPage.jsp#/UploadFiles");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
