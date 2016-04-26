package servlets;

import core.UpdateObjectWithUIEntries;
import org.apache.commons.io.FileUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by AroraA on 14-04-2016.
 */
@WebServlet(name = "UpdateRecordServlet", urlPatterns = {"/updateRecord"})
public class UpdateRecordServlet extends HttpServlet {
    String empRevalId;
    String[] currentDate;
    String[] checkIn;
    String[] checkOut;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        empRevalId = request.getParameter("empRevalId");
        currentDate = request.getParameterValues("currentDate");

        checkIn = request.getParameterValues("checkIn");
        checkOut = request.getParameterValues("checkOut");
        System.out.println(checkOut);
        System.out.println(checkIn);
        session.setAttribute("currentDate", currentDate);
        session.setAttribute("empRevalId", empRevalId);
        session.setAttribute("checkIn", checkIn);
        session.setAttribute("checkOut", checkOut);
        System.out.println(checkOut);
        System.out.println(checkIn);

        response.setContentType("text/html");
        System.out.println(empRevalId);
        System.out.println(Arrays.toString(currentDate));
        System.out.println(Arrays.toString(checkIn));
        System.out.println(Arrays.toString(checkOut));

        new UpdateObjectWithUIEntries().updateObjects(empRevalId, currentDate, checkIn, checkOut);

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
