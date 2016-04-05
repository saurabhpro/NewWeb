package servlets.login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
@WebServlet(name = "SignInServlet", urlPatterns = {"/signin"})
public class SignInServlet extends HttpServlet implements java.io.Serializable {

    public SignInServlet(){

    }
    private String userName;

    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userName = request.getParameter("userName");
        password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        session.setAttribute("userName", userName);
        session.setAttribute("password",password);
        RequestDispatcher dispatcher;

        ServletConfig config = getServletConfig();
        if( password.equals(config.getInitParameter("password"))&&userName.equals(config.getInitParameter("userName"))){
            dispatcher = request.getRequestDispatcher("./mainPage.jsp");
            dispatcher.include(request,response);
        }
        else{
            out.println("<font color='red'><b>You have entered incorrect password or username</b></font>");
            dispatcher = request.getRequestDispatcher("./signin.html");
            dispatcher.include(request, response);
        }

    }
}
