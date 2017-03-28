/*
 * Copyright (c) 2016.
 */

package servlets.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Created by Saurabh on 5/14/2016.
 * Class to provide core functionality to the servlet call for sending an email from Java code
 *
 * @author Amrita & Saurabh
 * @version 1.0
 */
class EmailHelperUtility {
	private static Session mailSession;
	private static Properties properties = System.getProperties();
	private static String to, from, pass, subject, messageBody, host;

	private EmailHelperUtility() {
	}

	/**
	 * Method to set basic fields for an email message
	 *
	 * @param request to get the message body and to for an email
	 * @param config  to get the from, password and subject
	 */
	static void setEmailFields(HttpServletRequest request, ServletConfig config) {
		to = request.getParameter("to");
		from = config.getInitParameter("from");
		pass = config.getInitParameter("password");
		subject = request.getParameter("subject");
		host = "smtp.office365.com";

		messageBody = getFinalMessage(request);
	}

	/**
	 * The Method to send email by setting the configuration, getting mimemessage and Transporting
	 *
	 * @throws MessagingException
	 */
	static void sendEmail() throws MessagingException {
		//set the properties object with basic email fields
		setConfigurationProperty();

		//get the mime message, coz on email only mime formatted data can be sent
		MimeMessage mimemessage = createMimeMessage(messageBody);

		//create a Transport object and use its sendMessage object to send the mail
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(host, from, pass);

		//verify that mimemessage dont be null
		assert mimemessage != null;
		transport.sendMessage(mimemessage, mimemessage.getAllRecipients());

		//close the connection
		transport.close();
	}


	/**
	 * Method to extract the message body content from various fields of UI
	 *
	 * @param request object that contains the parameters which contain message
	 * @return combined and reformatted message to be sent as body in the email
	 */
	private static String getFinalMessage(HttpServletRequest request) {
		String message;
		String finalMessage;
		String tempMessage2;
		String tempMessage3;
		String[] tempMessage4;
		String tempMessage5;
		String[] tempMessage6;
		String tempMessage7;
		String tempMessage8;
		String tempMessage9;
		String[] tempMessage10;
		message = request.getParameter("message");

		finalMessage = "Hi " + message + " ,<br/>";
		tempMessage4 = request.getParameterValues("message4");
		tempMessage6 = request.getParameterValues("message6");
		tempMessage2 = request.getParameter("message2");
		tempMessage3 = request.getParameter("message3");
		tempMessage5 = request.getParameter("message5");
		tempMessage7 = request.getParameter("message7");
		tempMessage8 = request.getParameter("message8");
		tempMessage9 = request.getParameter("message9");
		tempMessage10 = request.getParameterValues("message10");
		if ((tempMessage6 != null) || (tempMessage4 != null)) {
			// System.out.println(tempMessage6);
			finalMessage += "<br/>" + tempMessage2;
		}
		if (tempMessage4 != null) {
			finalMessage += "<br/>" + tempMessage3 + "<br/>";
			for (String str : tempMessage4) {
				finalMessage += str + "<br/>";
			}
		}
		if (tempMessage6 != null) {
			finalMessage += "<br/>" + tempMessage5 + "<br/>";
			for (String str : tempMessage6) {
				finalMessage += str + "<br/>";
			}
		}

		if ((tempMessage6 != null) || (tempMessage4 != null))
			finalMessage += "<br/>" + tempMessage7;
		if (tempMessage10 != null) {
			finalMessage += "<br/>" + tempMessage8 + "<br/>" + tempMessage9 + "<br/>";
			for (String str : tempMessage10) {
				finalMessage += str + "<br/>";
			}
		}
		System.out.println(finalMessage);
		/*
		 * if((message[5]!="" && message[5]!=null)||(message[7]!="" &&
		 * message[7]!=null)) finalMessage+=message[3]; if(message[5]!="" &&
		 * message[5]!=null) finalMessage+=message[4]+message[5]; if
		 * (message[7]!="" && message[7]!=null)
		 * finalMessage+=message[6]+message[7]; if((message[5]!="" &&
		 * message[5]!=null)||(message[7]!="" && message[7]!=null))
		 * finalMessage+=message[8]; if(message[10]!="" && message[10]!=null)
		 * finalMessage+=message[9]+message[10];
		 * System.out.println(finalMessage);
		 *
		 * for( String st : message) finalMessage+= st;
		 */
		return finalMessage;
	}


	/**
	 * Method to set various properties required for sending an email
	 */
	private static void setConfigurationProperty() {
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.user", from);
		properties.setProperty("mail.smtp.password", pass);
		properties.setProperty("mail.smtp.auth", "true");

		mailSession = Session.getDefaultInstance(properties);
	}

	/**
	 * Method to create mime message to be sent as email
	 *
	 * @param finalMessage the html message from UI
	 * @return a mime formatted message
	 * @throws MessagingException
	 */
	private static MimeMessage createMimeMessage(String finalMessage) throws MessagingException {

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(finalMessage, "text/html; charset=utf-8");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Create a default MimeMessage object.
		MimeMessage mimeMessage = new MimeMessage(mailSession);

		// Set From: header field of the header.
		mimeMessage.setFrom(new InternetAddress(from));

		// Set To: header field of the header.
		// Only one for now, but can also be used for multiple receivers in future
		String[] st = to.split(",");
		for (String eid : st) mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(eid));

		// Set Subject: header field
		mimeMessage.setSubject(subject);

		// Now set the actual email message
		mimeMessage.setContent(multipart);
		return mimeMessage;
	}

}
