package login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Saurabh on 4/3/2016.
 */
@WebServlet(name = "login.SignInServlet", urlPatterns = {"/signin"})
public class SignInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        HttpSession session = request.getSession();
        session.setAttribute("username", userName);

        RequestDispatcher dispatcher;

        if( password.equals("Test")){
            dispatcher = request.getRequestDispatcher("mainPage.html");
            dispatcher.forward(request,response);
        }
        else{
            out.println("<font color='red'><b>You have entered incorrect password</b></font>");
            dispatcher = request.getRequestDispatcher("signin.html");
            dispatcher.include(request, response);
        }

    }


}
