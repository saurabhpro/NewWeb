package servlets;

import core.UpdateObjectWithUIEntries;
import core.utils.FileFolderWorker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

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

        //temporary function to copy json files generated and stored in web local folder to artifact out folder
        FileFolderWorker.copyFromWebToArtifacts();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("MainPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
