package servlets;

import core.UpdateObjectWithUIEntries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by AroraA on 14-04-2016.
 */
@WebServlet(name = "UpdateRecordServlet", urlPatterns = {"/updateRecord"})
public class UpdateRecordServlet extends HttpServlet {
    String empRevalId;
    String[] currentDate;
    String[] checkIn;
    String[]  checkOut;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        empRevalId=request.getParameter("empRevalId");
        currentDate=request.getParameterValues("currentDate");

        checkIn=request.getParameterValues("checkIn");
        checkOut=request.getParameterValues("checkOut");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        System.out.println(empRevalId);
        System.out.println(Arrays.toString(currentDate));
        System.out.println(Arrays.toString(checkIn));
        System.out.println(Arrays.toString(checkOut));

        new UpdateObjectWithUIEntries().updateObjects(empRevalId, currentDate, checkIn, checkOut);

    }
}
