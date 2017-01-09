package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.adaming.myapp.dto.EtudiantDto;
import com.adaming.myapp.dto.EtudiantMapper;
import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.role.service.IRoleService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.GenerateSessionKey;
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
	private final Logger LOGGER = Logger.getLogger("EtudiantBean");

	@Inject
	private IEtudiantService serviceEtudiant;

	@Inject
	private ISessionService serviceSession;

	@Inject
	private IUserService serviceUser;

	@Inject
	private IRoleService serviceRole;

	private Long idSession;
	private Long idEtudiant;
	private String nomEtudiant;
	private String prenomEtudiant;
	private Date dateDeNaissance;
	private String formationInitial;
	private String ecole;
	private Date dateObtention;
	private String adressePostal;
	private String codePostal;
	private String numTel;
	private String mail;
	private List<SessionEtudiant> sessionsEncours;
	private List<SessionEtudiant> allSessions;
	private List<Etudiant> etudiants;
	private Specialite specialite;
	private Etudiant etudiant;
	private Role role;
	private User user;
	private String passwordRandom;

	/**
	 ** @create New Etudiant
	 ** @throws VerificationInDataBaseException
	 *             , if the object exist @return exception Object already exist
	 ** @see EtudiantBean.generateRandomPassword()
	 ** @see EtudiantBean.createEtudiant()
	 ** @see EtudiantBean.createEtudiant()
	 ** @see createRole()
	 */
	public void addEtudiant(){
		passwordRandom = generateRandomPassword();
		etudiant = createEtudiant();
		user = createUser(passwordRandom);
		role = createRole();
		try {
			serviceEtudiant.addStudent(etudiant, idSession);
			serviceUser.saveUser(user);
			serviceRole.saveRole(role, user.getIdUser());
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Info","l'Etudiant "
			+ nomEtudiant+ ", "+ prenomEtudiant+ " � bien �t� ajout�e avec Success"
            + " Voici les informations du compte etudiant : "
			+ "Pseudo : " + mail+ ", Password : " + passwordRandom));
			reset();
		} catch (AddEtudiantException e1) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", e1.getMessage()));
		}

	}

	/**
	 ** @method generateRandomKey, generate random password width length 8
	 ** 
	 */
	private String generateRandomPassword() {
		passwordRandom = GenerateSessionKey.generateRandomKey(8);
		return passwordRandom;
	}

	/**
	 *  @create New Etudiant
	 ** @return Object Etudiant
	 ** @factory.create.method
	 */
	private Etudiant createEtudiant() {
		etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
		etudiant.setNomEtudiant(nomEtudiant);
		etudiant.setPrenomEtudiant(prenomEtudiant);
		etudiant.setDateDeNaissance(dateDeNaissance);
		etudiant.setFormationInitial(formationInitial);
		etudiant.setEcole(ecole);
		etudiant.setDateObtention(dateObtention);
		etudiant.setAdressePostal(adressePostal);
		etudiant.setCodePostal(codePostal);
		etudiant.setNumTel(numTel);
		etudiant.setMail(mail);
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
		numTel = "";
		mail = "";
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
		sessionsEncours = serviceSession.getAllSessionsInProgress();
		allSessions = serviceSession.getAllSessions();
		LOGGER.info("Sessions en Cours : " + sessionsEncours);
		LOGGER.info("Toutes Les Sessions : " + sessionsEncours);
		// show statut of session finish or in progress
		Date curentDate = new Date();
		for (SessionEtudiant s : allSessions) {
			if (s.getDateFin().getTime() <= curentDate.getTime()) {
				s.setEtatSession("TERMINE");
			} else {
				s.setEtatSession("EN COURS");
			}
		}
	}

	/* @method get etudiant */
	public void getCurrentEtudiant(Long idEtudiant) {
		etudiant = serviceEtudiant.getStudentById(idEtudiant);
		LOGGER.info("Etudiant : " + etudiant);
	}

	/* @methode update etudiant */
	public String edit() {
		serviceEtudiant.updateStudent(etudiant, idSession);
		return "etudiant_update_success?redirect=true";
	}

	/* get all students by session */
	public void getAllStudentsBySession() {
		try {
			etudiants = serviceEtudiant.getEtudiantBySession(idSession);
		} catch (VerificationInDataBaseException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", e
							.getMessage()));
			setEtudiants(null);
		}
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

	public List<SessionEtudiant> getSessionsEncours() {
		return sessionsEncours;
	}

	public void setSessionsEncours(List<SessionEtudiant> sessionsEncours) {
		this.sessionsEncours = sessionsEncours;
	}

	public List<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public List<SessionEtudiant> getAllSessions() {
		return allSessions;
	}

	public void setAllSessions(List<SessionEtudiant> allSessions) {
		this.allSessions = allSessions;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

}
