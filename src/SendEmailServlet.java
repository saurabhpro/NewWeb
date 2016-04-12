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
    String finalMessage;

    String tempMessage2;
    String tempMessage3;
    String tempMessage4;
    String tempMessage5;
    String tempMessage6;
    String tempMessage7;
    String tempMessage8;
    String tempMessage9;
    String tempMessage10;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        response.setContentType("text/html");
        subject=request.getParameter("subject");
        System.out.println(subject);
        message=request.getParameter("message");

        finalMessage= "Hi "+message+ " ,<br/>";
        tempMessage4= request.getParameter("message4");
        tempMessage6= request.getParameter("message6");
        tempMessage2= request.getParameter("message2");
        tempMessage3= request.getParameter("message3");
        tempMessage5= request.getParameter("message5");
        tempMessage7= request.getParameter("message7");
        tempMessage8= request.getParameter("message8");
        tempMessage9= request.getParameter("message9");
        tempMessage10= request.getParameter("message10");
        if((tempMessage6!="" && tempMessage6!=null)||(tempMessage4!="" && tempMessage4!=null))
            finalMessage+="<br/>"+tempMessage2;
        if(tempMessage4!="" && tempMessage4!=null)
            finalMessage+="<br/>"+tempMessage3+"<br/>"+tempMessage4;
        if(tempMessage6!="" && tempMessage6!=null)
            finalMessage+="<br/>"+tempMessage5+"<br/>"+tempMessage6;
        if((tempMessage6!="" && tempMessage6!=null)||(tempMessage4!="" && tempMessage4!=null))
            finalMessage+="<br/>" +tempMessage7;
        if(tempMessage10!="" && tempMessage10!=null)
            finalMessage+="<br/>"+tempMessage8+"<br/>"+tempMessage9+"<br/>"+tempMessage10;
        System.out.println(finalMessage);
        /*
        if((message[5]!="" && message[5]!=null)||(message[7]!="" && message[7]!=null))
            finalMessage+=message[3];
        if(message[5]!="" && message[5]!=null)
            finalMessage+=message[4]+message[5];
        if (message[7]!="" && message[7]!=null)
            finalMessage+=message[6]+message[7];
        if((message[5]!="" && message[5]!=null)||(message[7]!="" && message[7]!=null))
            finalMessage+=message[8];
        if(message[10]!="" && message[10]!=null)
            finalMessage+=message[9]+message[10];
        System.out.println(finalMessage);

        for( String st : message)
        finalMessage+= st;
       */
        to = "saurabh.kumar@reval.com";
        //to = request.getParameter("to");
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

        PrintWriter out = response.getWriter();

        try {
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(finalMessage, "text/html; charset=utf-8");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
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
            //message1.setText(message);
            // Send message
            message1.setContent(multipart);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message1, message1.getAllRecipients());
            transport.close();
            out.print(ANSI_GREEN + "Message Sent");
            RequestDispatcher rd=request.getRequestDispatcher("./MainPage.jsp#/GenerateDiscrepancy");
            rd.include(request,response);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            out.print( ANSI_RED +"Error: unable to send message...." +mex);
            RequestDispatcher rd=request.getRequestDispatcher("./ThankYou.html");
            rd.include(request,response);
        }
    }
}
