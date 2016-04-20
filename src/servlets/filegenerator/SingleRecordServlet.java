package servlets.filegenerator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "SingleRecordServlet", urlPatterns = {"/filegenerator"})
public class SingleRecordServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        final ServletContext servletContext = request.getSession().getServletContext();

        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        final String temporaryFilePath = tempDirectory.getAbsolutePath();

        String id = request.getParameter("id");
        String fileToUse = request.getParameter("fileToUse");
        String excelOrPdf;

        try {
            excelOrPdf = "PDF";
            RecordBuilder.buildSingleRecord(id, fileToUse, excelOrPdf, temporaryFilePath, response);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}