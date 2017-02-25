package main.com.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	   public static void sendMail(String roomName, String nodeServer) throws MessagingException, MalformedURLException {    
		    
   		    String mailTo = "admin@semicolon2017.com";
		    String subject = "Meeting Cancelation Request";
		    String host = "smtp.gmail.com";
	        String port = "587";
	        // message contains HTML markups
	        String message = "<i>Greetings!</i><br>";
	        message += "Currently you have booking to room "+roomName+". If you are not using, Please cancel it.<br>";
	        message += " <b>Cancellation Link :</b> "+new URL("http://"+nodeServer+":3001/deleteRoom?roomName="+roomName);
	        
		    final String mailFrom ="watsonatsemicolon@gmail.com";
		    final  String password ="semicolon@123";
	         Properties props = new Properties();  
		    props.setProperty("mail.transport.protocol", "smtp");     
		    props.setProperty("mail.host", "smtp.gmail.com");  
		    props.put("mail.smtp.auth", "true");  
		    props.put("mail.smtp.port", "465");  
		    props.put("mail.debug", "true");  
		    props.put("mail.smtp.socketFactory.port", "465");  
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		    props.put("mail.smtp.socketFactory.fallback", "false");
		    
			   
		    try {
	            sendHtmlEmail(host, port, mailFrom, password, mailTo,
	                    subject, message);
	            System.out.println("Email sent.");
	        } catch (Exception ex) {
	            System.out.println("Failed to sent email.");
	            ex.printStackTrace();
	        }  
		   
	  } 
	   
		   
		    public static void sendHtmlEmail(String host, String port,
		            final String userName, final String password, String toAddress,
		            String subject, String message) throws AddressException,
		            MessagingException {
		 
		        // sets SMTP server properties
		        Properties properties = new Properties();
		        properties.put("mail.smtp.host", host);
		        properties.put("mail.smtp.port", port);
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.smtp.starttls.enable", "true");
		 
		        // creates a new session with an authenticator
		        Authenticator auth = new Authenticator() {
		            public PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(userName, password);
		            }
		        };
		 
		        Session session = Session.getInstance(properties, auth);
		 
		        // creates a new e-mail message
		        Message msg = new MimeMessage(session);
		 
		        msg.setFrom(new InternetAddress(userName));
		        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		        msg.setRecipients(Message.RecipientType.TO, toAddresses);
		        msg.setSubject(subject);
		        msg.setSentDate(new Date());
		        // set plain text message
		        msg.setContent(message, "text/html");
		 
		        // sends the e-mail
		        Transport.send(msg);
		 
		    }
	   }

