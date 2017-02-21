package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.GetUserException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.SendEmailUtil;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.user.service.IUserService;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Component("updatePasswordUserBean")
@Scope(value="session")
public class UpdatePasswordUserBean implements Serializable{

	/**
	 * LOGGER LOG4j 
	 * @see org.apache.log4j.Logger
	 */
  
	@Inject
	private IUserService serviceUser;
	
	@Inject
	private UserAuthentificationBean autentificationBean;



	private String newPassword;
	private String passwordCrypted;
	private final String message = "votre nouveau mot de passe";
    private final String contentMail = "Votre mot de passe à bien été enregistrer avec succès";
	
    public void updatePassword() {
      
		User user= null;
        try {
			user = serviceUser.getUserByMail(autentificationBean.getName());
			passwordCrypted = Utilitaire.passWordEncoderGenerator(newPassword);
			user.setPassword(passwordCrypted);
			serviceUser.customPassword(user);
            Utilitaire.displayMessageInfo(
					"Votre mot de passe à bien été enregistrer avec succès ");

		    
        } catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
		
        SendEmailUtil.sendMail(user.getName(), message, contentMail.concat(" : ".concat(newPassword)));
        LoggerConfig.logInfo("Sent message successfully");
        reset();
		
	}
	
	public void reset(){
		newPassword = "";
		passwordCrypted = "";
	}

	



	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public UserAuthentificationBean getAutentificationBean() {
		return autentificationBean;
	}

	public void setAutentificationBean(UserAuthentificationBean autentificationBean) {
		this.autentificationBean = autentificationBean;
	}

	

	
}
