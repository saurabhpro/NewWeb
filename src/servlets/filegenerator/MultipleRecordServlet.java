package servlets.filegenerator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumars on 4/11/2016.
 */
@WebServlet(name = "MultipleRecordServlet", urlPatterns = {"/multigen"})
public class MultipleRecordServlet extends HttpServlet {

    private static ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(fileName);

            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletContext servletContext = request.getSession().getServletContext();

        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        final String temporaryFilePath = tempDirectory.getAbsolutePath();
        List<String> listOfIds = new ArrayList<>();
        String tmp = request.getParameter("listOfIds");
        String[] temp2 = tmp.substring(1, tmp.length() - 1).split(",");

        for (String t : temp2) {
            if (!t.equals(""))
                listOfIds.add(t.substring(1, t.length() - 1));
            else
                listOfIds.add(null);
        }


/*
Explanation:

.*   - anything
\\\" - quote (escaped)
(.*) - anything (captured)
\\\" - another quote
.*   - anything
However, it's a lot easier to not use regex:
 */
        String fileToUse = request.getParameter("fileToUse");
        String excelOrPdf = request.getParameter("DownloadButtonType");

        try {

            //   for (String s : listOfIds)
            //      System.out.println(s);
            RecordBuilder.buildMultipleRecords(listOfIds, fileToUse, excelOrPdf, temporaryFilePath, response);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}


/**
 * Calling document.newPage() tells iText to place subsequent objects on a new page.
 * The new page will only actually get created when you place the next object.
 * Also, newPage() only creates a new page if the current page is not blank;
 * otherwise, it's ignored; you can use setPageBlank(false) to overcome that.
 * <p>
 * Refer below link for an example: http://itextpdf.com/examples/iia.php?id=99
 */