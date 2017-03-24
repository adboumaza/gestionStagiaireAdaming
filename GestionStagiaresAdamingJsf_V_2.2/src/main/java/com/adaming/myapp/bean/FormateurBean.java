package com.adaming.myapp.bean;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;




import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.geonames.Toponym;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Adresse;
import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.service.IFormateurService;
import com.adaming.myapp.role.service.IRoleService;
import com.adaming.myapp.tools.DataUtil;
import com.adaming.myapp.tools.SendEmailUtil;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.user.service.IUserService;

/**
 * la calss formateurBean, c'est le bean qui permet 
 * de répondre aux besoins métiers,
 * pour la création,suppression,modification 
 * de la class Formateur
 * 
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 */

@SuppressWarnings("serial")
@Component("formateurBean")
@ViewScoped
public class FormateurBean implements Serializable{
    
	
	@Inject
	private IFormateurService serviceFormateur;

	@Inject
	private IUserService serviceUser;
	@Inject
	private IRoleService serviceRole;
	
	private Adresse adresseObject;
	
	@NotEmpty(message="Veuillez sélectionnez une civilité")
	private String civilite;
	@NotEmpty
	@NotBlank
	private String nom;
	@NotBlank
	@NotEmpty
	private String prenom;
	@NotBlank
	@NotEmpty
	private String adresse;
	@NotEmpty
	private String codePostal;
	@NotEmpty
	private String telMobile;
	private String mail;
	@NotEmpty
	private String nationalite;
	@NotNull
	private Date dateDeNaissance;
	@NotEmpty
	private String ville;
	@NotEmpty
	@NotBlank
	private String pays;
	@NotEmpty
	private String specialite;
	
	private Formateur formateur;
	private User user;
	private Role role;
	private String passwordRandom;
	private String passwordCrypted;
	private List<Toponym> villes;
	
	 // Query Operations
	
	/** 
	 * la methode init appel la methode reset
	 * pour vider les champs
	 * 
	 * @return page formateur
	 */
	public String init(){
		reset();
		return "formateur?faces-redirect=true";
	}
	
	
	/**
	 * la methode addFormateur permet d'ajouter un formateur,
	 * génerer un password random,crypter le password,
	 * créer un nouveau Formateur,créer un nouveau User,
	 * créer un nouveau Role et associer le role et le user au formateur créeé
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
	public void addFormateur() throws URISyntaxException{
		
		passwordRandom  = generateRandomPassword();
		/***/
		passwordCrypted = Utilitaire.passWordEncoderGenerator(passwordRandom);
		/***/
		formateur = createFormateur();
		/***/
		user = createUser(passwordCrypted);
		/***/
		role = createRole();
		try 
		{
			serviceFormateur.addFormateur(formateur);
			serviceUser.saveUser(user);
			serviceRole.saveRole(role, user.getIdUser());
			Utilitaire.displayMessageInfo("le Formateur "+nom+", "+prenom+" à bien été ajouté avec succès"+" Un e-mail de confirmation a été adressé à l’adresse e-mail : "+"Pseudo : "+mail+", Password : "+passwordRandom);
			SendEmailUtil.sendMail(formateur.getMail(), "Confirmation de votre inscription",SendEmailUtil.CONFIRMATION_MESSAGE(formateur.getCivilite(),formateur.getNom(),formateur.getPrenom(),formateur.getMail(), passwordRandom,SendEmailUtil.getAbsoluteApplicationUrl()));
			reset();
		} 
		catch (VerificationInDataBaseException e1) 
		{
			Utilitaire.displayMessageWarning(e1.getMessage());
		}
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
		role.setRoleName("ROLE_FORMATEUR");
		return role;
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
		user.setName(formateur.getMail());
		user.setPassword(passwordRandom);
		user.setActived(true);
		return user;
	}
	

	/**
	 * la methode createFormateur permet de créer un formateur
	 * 
	 * @return le formateur créeé
	 * @see com.adaming.myapp.bean.FactoryBean.FORMATEUR_FACTORY
	 */
	private Formateur createFormateur() {
		adresseObject = new Adresse(adresse, ville, codePostal, pays);
		formateur = FactoryBean.getFormateurFactory().create("Formateur");
		formateur.setCivilite(civilite);
		formateur.setNom(nom);
		formateur.setPrenom(prenom);
		formateur.setTelMobile(telMobile);
		formateur.setMail(mail);
		formateur.setNationalite(nationalite);
		formateur.setDateDeNaissance(dateDeNaissance);
		formateur.setSpecialite(specialite);
		formateur.setAdresse(adresseObject);
		return formateur;
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
	 * la methode reset permet de vider
	 * les champs aprés l'inscription de chaque formateur
	 * 
	 **/
	public void reset(){
		civilite ="";
		nom= "";
		prenom ="";
		adresse ="";
		codePostal="";
		telMobile ="";
		mail ="";
		nationalite ="";
		dateDeNaissance =null;
		ville ="";
		specialite ="";
		pays ="";
		villes = null;
	}
	
	
	/**
	 * la methode specialitesInfo permet de faire l'autocomplétion,
	 * remplir le tableau des spécialités affectées à chaque formateur.
	 * 
	 * @param query le mot cle tapé dans le formulaire
	 * @return la liste des spécilaitées trouvées
	 * @see com.adaming.myapp.tools.Utilitaire.filterObject
	 * @see com.adaming.myapp.tools.DataUtil.fillingSpecialites
	 **/
	public List<String> specialitesInfo(String query){
		List<String> specilites = DataUtil.fillingSpecialites(query);
		List<String> filtred = Utilitaire.filterObject(query, specilites);
		return filtred;
	}
	
	
	
	/**
	 * la methode getNations permet de faire l'autocomplétion,
	 * remplir le tableau de nationnalité affecter à chaque formateur
	 * 
	 * @param  query le mot cle tapé dans le formulaire
	 * @return la liste des nationnalitées trouvées
	 * @see com.adaming.myapp.tools.Utilitaire.filterObject
	 **/
	public List<String> getNations(String query){
		List<String> nations = Arrays.asList(DataUtil.fillingNation(query));
		List<String> filtred = Utilitaire.filterObject(query, nations);
		return filtred;
	}
	
	/**
	 * la methode getVillesByCp permet de faire une recherche d'une ville, pays, depuis un code postal
	 * elle permet également de remplir un tableau de villes, afin d'associé le résultat obtenu  à chaque formateur
	 * 
	 * @param  query le code postal tapé dans le formulaire
	 * @return la liste des villes trouvées
	 * @see com.adaming.myapp.tools.Utilitaire.getVilles
	 **/
	public List<Toponym> getVillesByCp(String codePostal){
		if(codePostal != null)
		{
			villes = new ArrayList<Toponym>();
			for(Toponym toponym:Utilitaire.getVilles(codePostal))
			{
				System.out.println(toponym.getName());
				villes.add(toponym);
			}
		}
		else
		{
			villes.clear();
		}
		return villes;
	}
	

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getTelMobile() {
		return telMobile;
	}
	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}
	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	


	public String getCivilite() {
		return civilite;
	}


	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public Adresse getAdresseObject() {
		return adresseObject;
	}
	public void setAdresseObject(Adresse adresseObject) {
		this.adresseObject = adresseObject;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public List<Toponym> getVilles() {
		return villes;
	}
	public void setVilles(List<Toponym> villes) {
		this.villes = villes;
	}

}
