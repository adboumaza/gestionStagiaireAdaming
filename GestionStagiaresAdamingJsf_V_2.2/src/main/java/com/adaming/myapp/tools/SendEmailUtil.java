package com.adaming.myapp.tools;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public final class SendEmailUtil {

	private static final String MAIL_TRANSPORT_PROTOCOL = "smtp";

	private static final String MAIL_HOST = "smtp.gmail.com";

	private static final String MAIL_SMTP_STARTTLS_ENABLE = "true";

	private static final String MAIL_SMTP_AUTH = "true";

	private static final String MAIL_DEBUG_TRUE = "true";

	private static final String MAIL_DEBUG_FALSE = "false";
	
	// attribut pour tester l'envoi
	private final static String USERNAME = "nymraif.stark8623@gmail.com"; // adresse de
																		// l'administrateur
	private final static String PASSWORD = "krzmngkeebnkudvh"; // password pour

	public final static String CONFIRMATION_MESSAGE (String civilite,String nom,String prenom,String mail,String password){
		return  " Bonjour "+civilite+" "+nom+" "+prenom
				+ ",\n\nVotre compte a �t� cr�� avec succ�s."
				+ "\nVoici vos identifiants de connexion : \nPseudo : "+mail
				+ "\nMot de passe :"+password
				+ "\nVous pouvez imm�diatement acc�der � l'application INTI "
				+ "\nA tr�s bient�t,\n\n\nL�equipe intiformation.com";
	}
	
	
	
	
	
	
	
	public static final void sendMail(String destinataire,String subject,String contentMail){
		
		Properties props = System.getProperties();
		props.setProperty("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
		props.setProperty("mail.host", MAIL_HOST);
		props.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
		props.put("mail.debug", MAIL_DEBUG_FALSE);
		
		Session mailSession = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(mailSession);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(USERNAME));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					destinataire));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(contentMail);
            
			// Send message
			Transport transport = mailSession.getTransport();
			transport.connect();
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();
			LoggerConfig.logInfo("Send message successfully");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
