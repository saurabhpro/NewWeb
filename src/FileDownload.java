import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by AroraA on 06-03-2016.
 */
@WebServlet(name = "FileDownload",urlPatterns = {"/download"})
public class FileDownload extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = "New.txt";
        String filepath = "C:\\New folder\\";


        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + filename);

        PrintWriter pw = response.getWriter();
        int i;
        while ((i = fileInputStream.read()) != -1) {
            pw.write(i);
        }
        fileInputStream.close();

    }
}
