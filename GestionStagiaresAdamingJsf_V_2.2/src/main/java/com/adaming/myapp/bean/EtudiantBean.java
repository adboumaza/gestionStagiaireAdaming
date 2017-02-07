package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.geonames.Toponym;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.adaming.myapp.dto.EtudiantDto;
import com.adaming.myapp.dto.EtudiantMapper;
import com.adaming.myapp.entities.Adresse;
import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.role.service.IRoleService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.user.service.IUserService;

@SuppressWarnings("serial")
@Component("etudiantBean")
@ViewScoped
public class EtudiantBean implements Serializable {

	/**
	 * LOGGER LOG4j
	 * 
	 * @see org.apache.log4j.Logger
	 */
	

	@Inject
	private IEtudiantService serviceEtudiant;

	@Inject
	private ISessionService serviceSession;

	@Inject
	private IUserService serviceUser;

	@Inject
	private IRoleService serviceRole;
	@Inject
	private FormateurBean formateurBean;
	
	private Adresse adresseObject;
	private Long idSession;
	private Long idEtudiant;
	
	@NotEmpty
	@NotBlank
	@Size(max=30,min=2)
	private String nomEtudiant;
	@NotEmpty
	@NotBlank
	@Size(max=30,min=2)
	private String prenomEtudiant;
	@NotNull(message="Date de Naissance est obligatoire")
	private Date dateDeNaissance;
	@NotEmpty
	@NotBlank
	@Size(max=50,min=2)
	private String formationInitial;
	@Size(max=50,min=2)
	@NotBlank
	@NotEmpty
	private String ecole;
	@NotNull(message="Date d'obtention est obligatoire")
	private Date dateObtention;
	@Size(max=100,min=10)
	@NotEmpty
	@NotBlank
	private String adressePostal;
	@NotEmpty
	@NotBlank
	private String codePostal;
	@NotEmpty
	private String numTel;
	@NotEmpty
	private String mail;
	@NotEmpty
	@NotBlank
	private String pays;
	@NotEmpty
	@NotBlank
	private String ville;
	
	private List<Object[]> sessionsEncours;
	private List<Object[]> allSessions;
	private List<Object[]> etudiants;
	private Specialite specialite;
	private Etudiant etudiant;
	private Role role;
	private User user;
	private String passwordRandom;
	private String passwordCrypted;
	private Date currentTime;
	private List<Toponym> villes;

	/**
	 ** @create New Etudiant
	 ** @throws VerificationInDataBaseException
	 *             , if the object exist @return exception Object already exist
	 ** @see EtudiantBean.generateRandomPassword()
	 ** @see Utilitaire.generateRandomPassword()
	 ** @see EtudiantBean.createEtudiant()
	 ** @see EtudiantBean.createEtudiant()
	 ** @see createRole()
	 */
	public void addEtudiant(){
		passwordRandom = generateRandomPassword();
		passwordCrypted = Utilitaire.passWordEncoderGenerator(passwordRandom);
		etudiant = createEtudiant();
		user = createUser(passwordCrypted);
		role = createRole();
		try {
			serviceEtudiant.addStudent(etudiant, idSession);
			serviceUser.saveUser(user);
			serviceRole.saveRole(role, user.getIdUser());
			Utilitaire.displayMessageInfo("l'Etudiant "
			+ nomEtudiant+ ", "+ prenomEtudiant+ " � bien �t� ajout�e avec succ�s"
            + " Voici les informations du compte etudiant : "
			+ "Pseudo : " + mail+ ", Password : " + passwordRandom);
			reset();
		} catch (VerificationInDataBaseException e1) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", e1.getMessage()));
		}

	}

	/**
	 ** @method generateRandomKey, generate random password width length 8
	 ** 
	 */
	private String generateRandomPassword() {
		passwordRandom = Utilitaire.generateRandomKey(8);
		return passwordRandom;
	}

	/**
	 *  @create New Etudiant
	 ** @return Object Etudiant
	 ** @factory.create.method
	 */
	private Etudiant createEtudiant() {
		adresseObject = new Adresse(adressePostal, ville, codePostal, pays);
		etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
		etudiant.setNomEtudiant(nomEtudiant);
		etudiant.setPrenomEtudiant(prenomEtudiant);
		etudiant.setDateDeNaissance(dateDeNaissance);
		etudiant.setFormationInitial(formationInitial);
		etudiant.setEcole(ecole);
		etudiant.setDateObtention(dateObtention);
		etudiant.setNumTel(numTel);
		etudiant.setMail(mail);
		etudiant.setAdresse(adresseObject);
		return etudiant;
	}

	/**
	 * @create New User
	 * @param passwordRandom length 8
	 * @return Object User
	 * @factory.create.method
	 */
	private User createUser(String passwordRandom) {
		user = FactoryBean.getUserFactory().create("User");
		user.setName(etudiant.getMail());
		user.setPassword(passwordRandom);
		user.setActived(true);
		return user;
	}

	/**
	 ** @create New Role
	 ** @return Object Role
	 ** @factory.create.method
	 */
	private Role createRole() {
		role = FactoryBean.getRoleFactory().create("Role");
		role.setRoleName("ROLE_ETUDIANT");
		return role;
	}

	/**
	 ** @method reset the all fildes
	 ** 
	 */
	private void reset() {
		idSession = null;
		nomEtudiant = "";
		prenomEtudiant = "";
		dateDeNaissance = null;
		formationInitial = "";
		ecole = "";
		dateObtention = null;
		adressePostal = "";
		codePostal = "";
		ville ="";
		pays ="";
		numTel = "";
		mail = "";
		villes = null;
	}

	
	/**
	 ** @method reset the all fildes and 
	 ** @redirect to etudiant.xhtml
	 ** 
	 */
	public String resetAndRedirect() {
		reset();
		setEtudiants(null);
		return "etudiant?faces-redirect=true";
	}

	/* exmple via dto */
	public EtudiantDto rechercherEtudiant() {
		return EtudiantMapper.INSTANCE.etudiantToEtudiantDto(serviceEtudiant
				.getStudentById(1l));
	}

	/*
	 * get all sessions in progress and getAllSessions evry load page
	 */
	public void init() {
		sessionsEncours = serviceSession.getAllSessionsInProgressV2();
		allSessions = serviceSession.getAllSessionsV2();
		LoggerConfig.logInfo("Sessions en Cours : " + sessionsEncours);
		LoggerConfig.logInfo("Toutes Les Sessions : " + sessionsEncours);
		
	}

	/**@throws VerificationInDataBaseException 
	 * @method getCurrentEtudiant
	 * r�cup�ration de l'�tudiant en Cours
	 * @param idEtudiant, l'identifiant de l'�tudiant en Cours
	 * @method getUserByMail
	 * @return l'objet user afin de 
	 * v�rifier son adresse mail, histoire d'anticiper les changements sur 
	 * ce dernier */
	public void getCurrentEtudiant(Long idEtudiant) throws VerificationInDataBaseException {
		etudiant = serviceEtudiant.getStudentById(idEtudiant);
		user = serviceUser.getUserByMail(etudiant.getMail());
		LoggerConfig.logInfo("Etudiant : " + etudiant);
	}

	/**@throws VerificationInDataBaseException 
	 * @methode edit() on v�rifier d'abord  que la modification
	 * de l'�tudiant n'a pas �t� faite sur l'adresse mail
	 * au cas contraire on modifie aussi le name de 
	 * l'utilisateur (le mail de connection)*/
	public String edit() throws VerificationInDataBaseException {
	   
		if(!user.getName().equals(etudiant.getMail()))
		{
			user.setName(etudiant.getMail());
			serviceUser.customPassword(user);
		}
		serviceEtudiant.updateStudent(etudiant, idSession);
	
		return "etudiant?faces-redirect=true";
	}

	/* get all students by session */
	public void getAllStudentsBySession() {
		try {
			etudiants = serviceEtudiant.getEtudiantBySession(idSession);
			currentTime = new Date();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			setEtudiants(null);
		}
	}
	public List<Toponym> getVillesByCp(String codePostal){
		villes = new ArrayList<Toponym>();
		villes = formateurBean.getVillesByCp(codePostal);
		return villes;
	}

	public Long getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(Long idEtudiant) {
		this.idEtudiant = idEtudiant;
	}

	public String getNomEtudiant() {
		return nomEtudiant;
	}

	public void setNomEtudiant(String nomEtudiant) {
		this.nomEtudiant = nomEtudiant;
	}

	public String getPrenomEtudiant() {
		return prenomEtudiant;
	}

	public void setPrenomEtudiant(String prenomEtudiant) {
		this.prenomEtudiant = prenomEtudiant;
	}

	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getFormationInitial() {
		return formationInitial;
	}

	public void setFormationInitial(String formationInitial) {
		this.formationInitial = formationInitial;
	}

	public String getEcole() {
		return ecole;
	}

	public void setEcole(String ecole) {
		this.ecole = ecole;
	}

	public Date getDateObtention() {
		return dateObtention;
	}

	public void setDateObtention(Date dateObtention) {
		this.dateObtention = dateObtention;
	}

	public String getAdressePostal() {
		return adressePostal;
	}

	public void setAdressePostal(String adressePostal) {
		this.adressePostal = adressePostal;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Long getIdSession() {
		return idSession;
	}

	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	public List<Object[]> getSessionsEncours() {
		return sessionsEncours;
	}

	public void setSessionsEncours(List<Object[]> sessionsEncours) {
		this.sessionsEncours = sessionsEncours;
	}



	public List<Object[]> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Object[]> etudiants) {
		this.etudiants = etudiants;
	}

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public List<Object[]> getAllSessions() {
		return allSessions;
	}

	public void setAllSessions(List<Object[]> allSessions) {
		this.allSessions = allSessions;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
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

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public FormateurBean getFormateurBean() {
		return formateurBean;
	}

	public void setFormateurBean(FormateurBean formateurBean) {
		this.formateurBean = formateurBean;
	}

	public List<Toponym> getVilles() {
		return villes;
	}

	public void setVilles(List<Toponym> villes) {
		this.villes = villes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
