package servlets.core;

import org.apache.commons.io.FileUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "CoreServlet", urlPatterns = {"/core"})
public class CoreServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            BackEndLogic.getFinalObject();

            BackEndLogic.generateReportsJson();

        } catch (Exception ignored) {
        }


        File source = new File(FILE_PATH + "JsonFiles");
        //update this for amrita and home
        //TODO when deploying on actual server, use this to copy the JSon files directory
        //File dest = new File("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");
        //File dest = new File("C:\\Users\\Saurabh\\Documents\\GitHub\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");

        File dest = new File("C:\\Users\\Aroraa\\IdeaProjects\\NewWeb\\out\\artifacts\\NewWeb_war_exploded\\JsonFiles");
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("MainPage.jsp");
        requestDispatcher.forward(request, response);
    }

}
