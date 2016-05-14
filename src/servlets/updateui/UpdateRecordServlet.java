/*
 * Copyright (c) 2016.
 */

package servlets.updateui;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by AroraA on 14-04-2016.
 *
 * @author Amrita & Saurabh
 * @version 1.2
 */
@WebServlet(name = "UpdateRecordServlet", urlPatterns = {"/updateRecord"})
public class UpdateRecordServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String empRevalId = request.getParameter("empRevalId");
		String[] currentDate = request.getParameterValues("currentDate");
		String[] checkIn = request.getParameterValues("checkIn");
		String[] checkOut = request.getParameterValues("checkOut");

		session.setAttribute("currentDate", currentDate);
		session.setAttribute("empRevalId", empRevalId);
		session.setAttribute("checkIn", checkIn);
		session.setAttribute("checkOut", checkOut);

		response.setContentType("text/html");
		/*System.out.println(empRevalId);
		System.out.println(Arrays.toString(currentDate));
		System.out.println(Arrays.toString(checkIn));
		System.out.println(Arrays.toString(checkOut));*/

		//Function call to update the serialized object for persistence
		new UpdateObjectWithUIEntries().updateObjects(empRevalId, currentDate, checkIn, checkOut);

		// temporary function to copy json files generated and stored in web
		// local folder to artifact out folder
		//FileFolderWorker.copyFromWebToArtifacts();

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("MainPage.jsp");
		requestDispatcher.forward(request, response);
	}
}
