package servlets.upload;

import com.oreilly.servlet.MultipartRequest;
import core.utils.FileFolderWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    String filename;

    public String getBiometricName() {
        return biometricName;
    }

    public void setBiometricName(String biometricName) {
        this.biometricName = biometricName;
    }

    String biometricName;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* Part filePart = request.getPart("biometricFile"); // Retrieves <input type="file" name="biometricFile">
        String fileName = filePart.getSubmittedFileName();
        System.out.println(fileName);
        */
        File biometricFilePath = new File(BIOMETRIC_FILE_PATH);
        if (!biometricFilePath.exists()) {
            FileFolderWorker.makeDirectory(biometricFilePath);
        } else {
            FileFolderWorker.cleanDirectory(biometricFilePath);
        }
        HttpSession session = request.getSession();

        /**
         * mkdirs() will create the specified directory path in its entirety where
         * mkdir() will only create the bottom most directory,
         * failing if it can't find the parent directory of the directory it is trying to create.
         * In other words mkdir() is like mkdir and mkdirs() is like mkdir -p.
         */

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        m = new MultipartRequest(request, biometricFilePath.toString());

        filename = m.getFilesystemName("biometricFile");
        session.setAttribute("biometricName", filename);
        //System.out.println(nameForUI);
        System.out.println(filename);

        response.sendRedirect("./MainPage.jsp#/UploadFiles");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
