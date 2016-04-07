import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Created by AmritaArora on 4/6/2016.
 */
@WebServlet(name = "SendEmailServlet", urlPatterns = {"/email"})
public class SendEmailServlet extends HttpServlet  {

    String subject;
    String to;
    String message;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletConfig config = getServletConfig();

        subject=request.getParameter("subject");
        System.out.println(subject);
        message=request.getParameter("message");
        System.out.println(message);
        //to = "saurabh.kumar@reval.com";
        to = request.getParameter("to");
        //String to = "amrita.arora.1192@gmail.com";

        String from = config.getInitParameter("from");
        String host = "smtp.gmail.com";
        String pass = config.getInitParameter("password");


        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.password", pass);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.user", from);


        Session mailSession = Session.getDefaultInstance(properties);

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Create a default MimeMessage object.
            MimeMessage message1 = new MimeMessage(mailSession);
            // Set From: header field of the header.
            message1.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            StringTokenizer st = new StringTokenizer(to, ",");
            while (st.hasMoreElements()) {
                message1.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(st.nextToken()));
            }
            // Set Subject: header field
            message1.setSubject(subject);
            // Now set the actual message
            message1.setText(message);
            // Send message

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message1, message1.getAllRecipients());
            transport.close();
            out.print("Successful");
            RequestDispatcher rd=request.getRequestDispatcher("./ThankYou.html");
            rd.include(request,response);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            out.print( "Error: unable to send message....");
            RequestDispatcher rd=request.getRequestDispatcher("./ThankYou.html");
            rd.include(request,response);
            out.print(mex);
        }
    }
}
