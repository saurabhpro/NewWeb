package servlets.filegen;

import servlets.filegen.excel.CreateSingleRecordExcel;
import servlets.filegen.pdf.CreateSingleRecordPDF;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "SingleRecordServlet", urlPatterns = {"/filegen"})
public class SingleRecordServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        final ServletContext servletContext = request.getSession().getServletContext();

        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        final String temporaryFilePath = tempDirectory.getAbsolutePath();

        String id = request.getParameter("id");
        String fileToUse = request.getParameter("fileToUse");
        String excelOrPdf = request.getParameter("fileType");

        String pdfFileName = "Generated_Report_" + fileToUse + "_" + System.currentTimeMillis() + ".pdf";
        String excelFileName = "Generated_Report_" + fileToUse + "_" + System.currentTimeMillis() + ".xlsx";

        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "max-age=0");
        response.setHeader("Content-disposition", "attachment; " + "filename=" + pdfFileName);

        try {
            System.out.println("Serv" + id);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream os = null;
            excelOrPdf = "PDF";
            if (excelOrPdf.equals("PDF")) {
                CreateSingleRecordPDF.createPDF(temporaryFilePath + "\\" + pdfFileName, id, fileToUse);
                baos = convertPDFToByteArrayOutputStream(temporaryFilePath + "\\" + pdfFileName);
                os = response.getOutputStream();

            } else if (excelOrPdf.equals("EXCEL")) {
                CreateSingleRecordExcel.fromJsonToExcel(id, fileToUse);
                baos = convertPDFToByteArrayOutputStream(temporaryFilePath + "\\" + excelFileName);
                os = response.getOutputStream();
            }

            assert os != null;
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}