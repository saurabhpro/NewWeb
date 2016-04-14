package servlets;

import core.UpdateObjectWithUIEntries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by AroraA on 14-04-2016.
 */
@WebServlet(name = "UpdateRecordServlet", urlPatterns = {"/updateRecord"})
public class UpdateRecordServlet extends HttpServlet {
    String empRevalId;
    String currentDate;
    String checkIn;
    String  checkOut;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        empRevalId=request.getParameter("empRevalId");
        currentDate=request.getParameter("currentDate");
        checkIn=request.getParameter("checkIn");
        checkOut=request.getParameter("checkOut");
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        System.out.println(empRevalId);
        System.out.println(currentDate);
        System.out.println(checkIn);
        System.out.println(checkOut);

        new UpdateObjectWithUIEntries().updateObject(empRevalId, currentDate, checkIn, checkOut);
    }
}
