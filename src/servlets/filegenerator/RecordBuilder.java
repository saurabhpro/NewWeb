package servlets.filegenerator;

import servlets.filegenerator.excel.CreateMultiRecordExcel;
import servlets.filegenerator.excel.CreateSingleRecordExcel;
import servlets.filegenerator.pdf.CreateMultipleRecordPDF;
import servlets.filegenerator.pdf.CreateSingleRecordPDF;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by kumars on 4/19/2016.
 */
public class RecordBuilder {
    static ByteArrayOutputStream baos = new ByteArrayOutputStream();
    static OutputStream os;

    private static ByteArrayOutputStream convertFileToByteArrayOutputStream(String fileName) {
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

    private static void updatePdfResponseObject(HttpServletResponse response, String pdfFileName) {
        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "max-age=0");
        response.setHeader("Content-disposition", "attachment; " + "filename=" + pdfFileName);
    }

    private static void updateExcelResponseObject(HttpServletResponse response, String excelFileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
    }

    public static void buildSingleRecord(String id, String fileToUse, String excelOrPdf, String temporaryFilePath, HttpServletResponse response) throws IOException {
        if (excelOrPdf.equals("PDF")) {

            String pdfFileName = "Generated_Report_" + fileToUse + " " + id + "_" + System.currentTimeMillis() + ".pdf";
            updatePdfResponseObject(response, pdfFileName);

            CreateSingleRecordPDF.createPDFFromJson(temporaryFilePath + "\\" + pdfFileName, id, fileToUse);
            baos = convertFileToByteArrayOutputStream(temporaryFilePath + "\\" + pdfFileName);

        } else if (excelOrPdf.equals("EXCEL")) {

            String excelFileName = "Generated_Report_" + fileToUse + id + "_" + System.currentTimeMillis() + ".xlsx";
            updateExcelResponseObject(response, excelFileName);

            CreateSingleRecordExcel.createExcelFromJson(temporaryFilePath + "\\" + excelFileName, id, fileToUse);
            baos = convertFileToByteArrayOutputStream(temporaryFilePath + "\\" + excelFileName);
        }

        os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
    }

    public static void buildMultipleRecords(List<String> listOfIds, String fileToUse, String excelOrPdf, String temporaryFilePath, HttpServletResponse response) throws IOException {
        if (excelOrPdf.equals("DOWNLOAD_PDF")) {

            String pdfFileName = "Generated_Report_" + fileToUse + "_All_" + System.currentTimeMillis() + ".pdf";
            updatePdfResponseObject(response, pdfFileName);

            CreateMultipleRecordPDF.createPDF(temporaryFilePath + "\\" + pdfFileName, listOfIds, fileToUse);
            baos = convertFileToByteArrayOutputStream(temporaryFilePath + "\\" + pdfFileName);

        } else if (excelOrPdf.equals("DOWNLOAD_EXCEL")) {

            String excelFileName = "Generated_Report_" + fileToUse + "_All_" + System.currentTimeMillis() + ".xlsx";
            updateExcelResponseObject(response, excelFileName);

            CreateMultiRecordExcel.fromJsonToExcel(temporaryFilePath + "\\" + excelFileName, listOfIds, fileToUse);
            baos = convertFileToByteArrayOutputStream(temporaryFilePath + "\\" + excelFileName);
        }

        os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
    }
}
