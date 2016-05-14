/*
 * Copyright (c) 2016.
 */

package servlets.mail;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by AmritaArora on 4/6/2016.
 *
 * @version 1.7
 */
@WebServlet(name = "SendEmailServlet", urlPatterns = {"/email"})
public class SendEmailServlet extends HttpServlet implements java.io.Serializable {


	private String successfulMessage;

	public SendEmailServlet() {
	}

	public String getSuccessfulMessage() {
		return successfulMessage;
	}

	public void setSuccessfulMessage(String successfulMessage) {
		this.successfulMessage = successfulMessage;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		ServletConfig config = getServletConfig();
		EmailHelperUtility.setEmailFields(request, config);

		try {
			EmailHelperUtility.sendEmail();
			// HttpSession session = request.getSession(false);
			// session.setAttribute("Message Sent", successfulMessage);
			response.sendRedirect("./MainPage.jsp#/GenerateDiscrepancy");

		} catch (MessagingException | IOException mex) {
			mex.printStackTrace();
			out.print("Error: unable to send message...." + mex);
			//no need of thankyou on error !!!!!
			RequestDispatcher rd = request.getRequestDispatcher("./ThankYou.html");
			rd.include(request, response);
		}
	}
}
