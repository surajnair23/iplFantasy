package com.ipl.utility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.HtmlEmail;

import com.ipl.pojo.User;

public class SendEmail {
	
	public SendEmail() {}
	
	public void userApprovedEmail(User user) {
		
		HtmlEmail email = new HtmlEmail();
		try {
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("webfantasyipl@gmail.com", "webtools"));
		email.setSSLOnConnect(false);
		email.setStartTLSEnabled(true);
		//From:
		email.setFrom("webfantasyipl@gmail.com");
		//To:
		email.addTo(user.getEmail());
		//Subject:
		email.setSubject("Welcome to IPL Fantasy League!");
		// set the html message
		email.setHtmlMsg("<html><body><h2>Hello "+user.getfName()+"</h2><br><br><h3>A grand welcome to this years IPL Fantasy league, you can now login with your user as below:<br><br></h3><b>Username:</b>"+user.getUsername()+"<br><br>Best regards</body></html>");
        email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
