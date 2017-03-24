package com.adaming.myapp.bean;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.adaming.myapp.admin.service.IAdminService;
import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.role.service.IRoleService;
import com.adaming.myapp.tools.SendEmailUtil;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.user.service.IUserService;

@SuppressWarnings("serial")
@Component("adminBean")
@Scope(value = "request")
public class AdminBean extends SavedRequestAwareAuthenticationSuccessHandler implements Serializable {
    
	@Inject
	private IAdminService serviceAdmin;
    
	@Inject
	private IUserService serviceUser;
	
	@Inject
	private IRoleService serviceRole;
	
	@NotEmpty(message = "nom obligatoire")
	private String nom;
	@NotEmpty(message = "prénom obligatoire")
	private String prenom;
	private String mail;
	@NotEmpty(message = "fonction obligatoire")
	private String fonction;
	private User user;
	private Role role;
	private Admin admin;
	private String passwordRandom;
	private String passwordCrypted;
	
	
	
	/**
	 * la methode addAdmin permet d'ajouter un Admin,
	 * génerer un password random,crypter le password,
	 * créer un nouveau Admin,créer un nouveau User,
	 * créer un nouveau Role et associer le role et le user au Admin créeé
	 * @throws URISyntaxException 
	 *
	 * @see com.adaming.myapp.bean.generateRandomPassword
	 * @see com.adaming.myapp.bean.createFormateur
	 * @see com.adaming.myapp.bean.createUser
	 * @see com.adaming.myapp.tools.Utilitaire.passWordEncoderGenerator
	 * @see com.adaming.myapp.tools.Utilitaire.displayMessageInfo
	 * @see com.adaming.myapp.tools.Utilitaire.displayMessageWarning
	 * @throws @see com.adaming.myapp.exception.VerificationInDataBaseException
	 */
	public void addAdmin() throws URISyntaxException{
		
		passwordRandom  = generateRandomPassword();
		/***/
		passwordCrypted = Utilitaire.passWordEncoderGenerator(passwordRandom);
		/***/
		admin = new Admin(nom, prenom, mail, fonction);
		/***/
		user = createUser(passwordCrypted);
		/***/
		role = createRole();
		
			try {
				
				
				serviceAdmin.createAdmin(admin);
				serviceUser.saveUser(user);
				serviceRole.saveRole(role, user.getIdUser());
				Utilitaire.displayMessageInfo("l'admin "+nom+", "+prenom+" à bien été ajouté avec succès"+" Un e-mail de confirmation a été adressé à l’adresse e-mail : "+"Pseudo : "+mail+", Password : "+passwordRandom);
				SendEmailUtil.sendMail(admin.getMail(), "Confirmation de votre inscription",SendEmailUtil.CONFIRMATION_MESSAGE("M/Mme",admin.getNom(),admin.getPrenom(),admin.getMail(), passwordRandom,SendEmailUtil.getAbsoluteApplicationUrl()));
				reset();
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
			}
			
	}
	
	public String getAbsoluteApplicationUrl() throws URISyntaxException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        URI uri = new URI(request.getRequestURL().toString());
        URI newUri = new URI(uri.getScheme(), null,
                uri.getHost(),
                uri.getPort(),
                request.getContextPath().toString(),null, null);
        return newUri.toString();
  }
	
	
    
	/**
	 * la methode generateRandomPassword permet de génerer un 
	 * password aléatoirement
	 * 
	 * @return password générer
	 * @see com.adaming.myapp.tools.Utilitaire.generateRandomKey
	 * 
	 */
	private String generateRandomPassword() {
		passwordRandom = Utilitaire.generateRandomKey(8);
		return passwordRandom;
	}
	
	
	/**
	 * la methode createUser permet de créer un user
	 * 
	 * @param passwordRandom un password génere aléatoirement
	 * @return le user créeé
	 * @see com.adaming.myapp.bean.FactoryBean.USER_FACTORY
	 */
	private User createUser(String passwordRandom) {
		user=FactoryBean.getUserFactory().create("User");
		user.setName(admin.getMail());
		user.setPassword(passwordRandom);
		user.setActived(true);
		return user;
	}
	
	/**
	 * la methode createRole permet de créer un Role
	 * 
	 * @return le role créeé
	 * @see com.adaming.myapp.bean.FactoryBean.ROLE_FACTORY
	 * 
	 */
	private Role createRole() {
		role = FactoryBean.getRoleFactory().create("Role");
		role.setRoleName("ROLE_ADMIN");
		return role;
	}
	
	
	/**
	 * la methode reset permet de vider
	 * les champs aprés l'inscription de chaque formateur
	 * 
	 **/
	public void reset(){
		nom= "";
		prenom ="";
		mail ="";
		fonction ="";
		
	}
	

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}



	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}



	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}



	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}



	/**
	 * @return the fonction
	 */
	public String getFonction() {
		return fonction;
	}



	/**
	 * @param fonction the fonction to set
	 */
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}



	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}



	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}



	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}



	/**
	 * @return the passwordRandom
	 */
	public String getPasswordRandom() {
		return passwordRandom;
	}



	/**
	 * @param passwordRandom the passwordRandom to set
	 */
	public void setPasswordRandom(String passwordRandom) {
		this.passwordRandom = passwordRandom;
	}



	/**
	 * @return the passwordCrypted
	 */
	public String getPasswordCrypted() {
		return passwordCrypted;
	}



	/**
	 * @param passwordCrypted the passwordCrypted to set
	 */
	public void setPasswordCrypted(String passwordCrypted) {
		this.passwordCrypted = passwordCrypted;
	}
	
	
	
	
	
}
