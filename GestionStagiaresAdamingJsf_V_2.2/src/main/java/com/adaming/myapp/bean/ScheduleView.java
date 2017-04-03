package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.evenement.service.IEvenementService;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.service.IFormateurService;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.question.service.IQuestionService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Component("scheduleView")
@Scope("session")
public class ScheduleView  implements Serializable {

	/**
	 * LOGGER LOG4j 
	 * @see org.apache.log4j.Logger
	 */
    
    @Inject
    private SessionBean sessionBean; 
    
	@Inject
	private IEtudiantService serviceEtudiant;
	@Inject
	private IModuleService serviceModule;

	@Inject
	private IFormateurService serviceFormateur;
	
	@Inject
	private ISessionService serviceSession;
    
	@Inject
	private IQuestionService serviceQuestion;

	@Inject
	private IEvenementService serviceEvenement;
	/* get the name of user (formateur) for evenement */
	@Inject
	private UserAuthentificationBean userAuthentificationBean;
    
	@NotNull(message="Veuillez sélectionner un Module")
	private Long idModule;
	private Long idSpecialite;
	private List<Object[]> etudiants;
	private List<Etudiant> students;
	private List<Object[]> modules;
	private Module module;
	private Set<Questions> questions;

	private Date dateIn;
	private String[] dateString;
	private DateTime[] joursSemaine;
	private int annee;
	private int semaine;
	private boolean dispo;
	/**@evenement */
    @NotNull(message="Veuillez sélectionner un étudiant")
	private Long idEtudiant;
    @NotNull(message="Veuillez sélectionner une Date de début")
	private Date dateStart;
    @NotNull(message="Veuillez sélectionner une Date de fin")
	private Date dateEnd;
    @NotEmpty(message="Veuillez sélectionner un type d'événement")
	private String typeEvenement;
    private boolean active = false;
	private Formateur formateur;
	private Long idFormateur;
	private List<SessionEtudiant> sessionsFormateur;
	private SessionEtudiant sessionFormateur;
	private List<Evenement> events;
	private Evenement evenement;
	

	public SessionEtudiant initReporting(){
		formateur = FactoryBean.getFormateurFactory().create("Formateur");
		formateur = serviceFormateur.getFormateur(userAuthentificationBean
				.getName());
		try {
			sessionFormateur = serviceSession.getSessionByFormateur(formateur.getIdFormateur());
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
		
		return sessionFormateur;
		
	}

	public String initEvenement() throws VerificationInDataBaseException {
		initReporting();
		getAllStudentsBySession();
		return "evenement?redirect=true";
	}

	public String initWarning() throws VerificationInDataBaseException {
		initReporting();
		getAllStudentsBySession();
		return "warning?redirect=true";
	}

	public String initAbsences() throws VerificationInDataBaseException {
		initReporting();
		//getStudentsBySession();
		dateIn = new Date();
		active = false;
		genererDates();
		//genererSchedule();
		return "absence?redirect=true";
	}
	
	/*public String colorMoy(String m) {
		if (m != null && !m.equals("--")) {

			if (Float.parseFloat(m) >= (Float.parseFloat("10"))) {
				return "color: blue;";
			} else {
				return "color: red;";
			}
		}
		return null;

	}

	public String colorTd(String s) {
		System.out.println(s);
		if (s != null) {
			if(s.equalsIgnoreCase("R")){
				System.out.println("color");
				return "background-color:red";
			}
			else if(s.equalsIgnoreCase("A")){
				return "background-color:green;";
			}
			else if(s.equalsIgnoreCase("E")){
				return "background-color: rgba(251, 13, 13, 0.32);";
			}
			return "background-color: rgba(251, 13, 13, 0.32);";
		} else {
			return null;
		}

	}*/


	public String initProspection() throws VerificationInDataBaseException {
		initReporting();
		getStudentsBySession();
		return "prospection?redirect=true";
	}

	public String initEvaluation() throws VerificationInDataBaseException {
		SessionEtudiant se= initReporting();
		if(se != null){
		   getAllModulesBySession();
		}
		active = false;
		setIdModule(null);
		return "evaluation?faces-redirect=true";
	}

	public String initActivationModule() throws VerificationInDataBaseException {
		 SessionEtudiant se = initReporting();
		 if(se != null){
			 getAllModulesBySession();
		 }
		return "activation_module?redirect=true";
	}

	/** @method get All Students By Session for evenements */
	public void getAllStudentsBySession(){
		if(sessionFormateur != null){
			try {
				etudiants = serviceEtudiant.getEtudiantBySession(sessionFormateur.getIdSession());
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
			}
		}
		
	}
	/** get All Students for reporting*/
	public void getStudentsBySession(){
		if(sessionFormateur != null){
			try {
				students = serviceEtudiant.getStudentsBySession(sessionFormateur.getIdSession());
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
			}
		}
	}
	
	/** get All Evenement par session for reporting*/
	public String getAllEvenementsBySession(){
		initReporting();
		if(sessionFormateur != null){
			return "data_evenements?faces-redirect=true";
		}else{
			return null;
		}
		
	}
	
	
	
	/** delete evenement */
	public void deleteEvenement(){
		LoggerConfig.logDebug("delete Evenement : "+evenement);
		serviceEvenement.deleteEvenement(evenement.getIdEvenement());
		LoggerConfig.logInfo("before Evenements : "+sessionBean.getEvenements());
		sessionBean.getEvenements().remove(evenement);
	    LoggerConfig.logInfo("after Evenement : "+sessionBean.getEvenements());
	    Utilitaire.displayMessageInfo("L'évènement a bien été supprimé");
	}
	
	/**  generate Absences **/
	public void genererSchedule(){
		getStudentsBySession();
	}

	public void getAllModulesBySession() {
		modules = serviceModule.getModulesBySessionV2(sessionFormateur.getIdSession());
		if(modules.isEmpty()){
			Utilitaire.displayMessageWarning("Aucun Module Trouvé..");
		}
	}

	public void getModuleById() {
		module = FactoryBean.getModuleFactory().create("Module");
		module = serviceModule.getModuleById(idModule);
	}

	/* @method get module by id for activation Formateur */
	public Module getCurrentModule(Long idModule) {
		module = FactoryBean.getModuleFactory().create("Module");
		module = serviceModule.getModuleById(idModule);
	   return module;
	}
	
	/* @method get all Questions and responses by Module  */
	public void getQuestionsAndResponse(Long idModule) {
		LoggerConfig.logDebug("idM"+idModule);
		questions = new HashSet<Questions>();
		module = getCurrentModule(idModule);
		questions = serviceQuestion.getQuestionsByModule(idModule);
	}

	/* @method update */
	public String edit(Long idSpecialite) {
		serviceModule.updateModule(module, idSpecialite);
		return "module_update_success?redirect=true";
	}

	/** @methode generate Evaluations */
	public void genererScheduleEvaluations() throws VerificationInDataBaseException {
		getStudentsBySession();
		getModuleById();
		active=true;
	}
	
	/**@methode generate date and events**/
	public void generateEvents() throws VerificationInDataBaseException{
		try {
			events = serviceEvenement
					.getAllEvenementsBetweenTwoDate(sessionFormateur.getIdSession(), dateIn);
			genererSchedule();
			Utilitaire.displayMessageWarning("Les évènements trouvés à partir du "+dateIn+" Veuillez modifié le tableau en dessous");
			active=true;
		} catch (EvenementNotFoundException e) {
			genererSchedule();
			Utilitaire.displayMessageWarning(e.getMessage());
			events=null;
			active=true;
		}
		

	}

	/** @method generate date */
	public void genererDates() {
		DateTime date = new DateTime(dateIn);
		final int dayOfWeek = date.getDayOfWeek();
		annee   = date.getYear();
		semaine = date.getWeekOfWeekyear();

		// si jour select n'est pas un lundi
		if (dayOfWeek != 1) {
			date = date.withDayOfWeek(1);
		}

		joursSemaine = new DateTime[5];
		dateString = new String[5];
		// joursSemaine[0] = date.toDate();

		// on boucle jusqu'a vendredi
		for (int i = 0; i < 5; i++) {
			joursSemaine[i] = date.plusDays(i);
			dateString[i] = joursSemaine[i].getDayOfMonth() + "/"
					+ joursSemaine[i].getMonthOfYear();
		}
	}

	/** @reset evenement */
	public void resetEvenement() {
		dateStart = null;
		dateEnd = null;
		idEtudiant = null;
		typeEvenement = "";
	}

	/** @methode signaler un retard (refactoring)*/
	private void signalerUnRetad() {
		Evenement retard = null;
		retard = FactoryBean.getEvenementFactory().create("retard");
		retard.setStartDate(dateStart);
		retard.setEndDate(dateEnd);
		retard.setCurentDate(new Date());
		retard.setSignaleur(userAuthentificationBean.getName());
		if(retard.getStartDate().after(retard.getEndDate())){
			Utilitaire.displayMessageWarning("la date de départ ne peut être antérieur ");
		}
		else if(retard.getStartDate().equals(retard.getEndDate()))
		{
			Utilitaire.displayMessageWarning("la durré de retard ne peut être 0 min");	
		}
		else 
		{
			try {
				serviceEvenement.addEvenement(retard, sessionFormateur.getIdSession(), idEtudiant);
				Utilitaire.displayMessageInfo(
								"le Retard de " + dateStart + " A " + dateEnd
										+ " à bien été signalé");
				resetEvenement();
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
				resetEvenement();
			}
		}
		
	}

	/** @methode signaler une absence (refactoring) */
	private void signalerUneAbsence() {
		Evenement absence = null;
		absence = FactoryBean.getEvenementFactory().create("Absence");
		absence.setStartDate(dateStart);
		absence.setEndDate(dateEnd);
		absence.setCurentDate(new Date());
		absence.setSignaleur(userAuthentificationBean.getName());
		if(absence.getStartDate().after(absence.getEndDate())){
			Utilitaire.displayMessageWarning("la date de départ ne peut être antérieur ");
		}
		else 
		{
			try {
				serviceEvenement.addEvenement(absence, sessionFormateur.getIdSession(), idEtudiant);
				Utilitaire.displayMessageInfo(
								"l'absence de " + dateStart + " A " + dateEnd
										+ " à bien été signalé");
				resetEvenement();
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());;
				resetEvenement();
			}
		}
		
	}

	/** @methode signaler un entretien (refactoring) */
	private void signalerUnEntretien() {
		Evenement entretien = null;
		entretien = FactoryBean.getEvenementFactory().create("Entretien");
		entretien.setStartDate(dateStart);
		entretien.setEndDate(dateEnd);
		entretien.setCurentDate(new Date());
		entretien.setSignaleur(userAuthentificationBean.getName());
		if(entretien.getStartDate().after(entretien.getEndDate())){
			Utilitaire.displayMessageWarning("la date de départ ne peut être antérieur ");
		}
		else
		{
			try {
				serviceEvenement.addEvenement(entretien, sessionFormateur.getIdSession(), idEtudiant);
				Utilitaire.displayMessageInfo(
								"l'entretien de " + dateStart + " A " + dateEnd
										+ " à bien été signalé");
				resetEvenement();
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
				resetEvenement();
			}	
		}
		
	}

	/** @methode signaler un etudiantTop (refactoring)*/
	private void signalerUnEtudiantTop() {
		Evenement topEtudiant = null;
		topEtudiant = FactoryBean.getEvenementFactory().create("TopEtudiant");
		topEtudiant.setStartDate(new Date());
		topEtudiant.setEndDate(new Date());
		topEtudiant.setCurentDate(new Date());
		topEtudiant.setSignaleur(userAuthentificationBean.getName());
		try {
			serviceEvenement.AddWarningAndTop(topEtudiant, sessionFormateur.getIdSession(),
					idEtudiant);
			Utilitaire.displayMessageInfo(
							"l'evènement à bien été signalée");
			resetEvenement();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			resetEvenement();
		}
	}

	/** @methode signaler un etudiantTop (refactoring) */
	private void signalerUnEtudiantWarning() {
		Evenement warningEtudiant = null;
		warningEtudiant = FactoryBean.getEvenementFactory().create("WarningEtudiant");
		warningEtudiant.setStartDate(new Date());
		warningEtudiant.setEndDate(new Date());
		warningEtudiant.setCurentDate(new Date());
		warningEtudiant.setSignaleur(userAuthentificationBean.getName());
		try {
			serviceEvenement.AddWarningAndTop(warningEtudiant, sessionFormateur.getIdSession(),
					idEtudiant);
			Utilitaire.displayMessageInfo(
							"l'evènement à bien été signalée");
			resetEvenement();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			resetEvenement();
		}
	}

	/* @method signaler un evenement */
	public void signalerEvenement() {
		if (!typeEvenement.equals(null)) {
			if (typeEvenement.equals("retard")) {
				signalerUnRetad();
			} else if (typeEvenement.equals("absence")) {
				signalerUneAbsence();
			} else if (typeEvenement.equals("entretient")) {
				signalerUnEntretien();
			} else if (typeEvenement.equals("top")) {
				signalerUnEtudiantTop();
			} else if (typeEvenement.equals("warning")) {
				signalerUnEtudiantWarning();
			}
		}

	}

	/** :::::::::::::::::::::: **/

	
	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public String[] getDateString() {
		return dateString;
	}

	public void setDateString(String[] dateString) {
		this.dateString = dateString;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getSemaine() {
		return semaine;
	}

	public void setSemaine(int semaine) {
		this.semaine = semaine;
	}

	public boolean isDispo() {
		return dispo;
	}

	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}

	

	public List<Object[]> getModules() {
		return modules;
	}

	public void setModules(List<Object[]> modules) {
		this.modules = modules;
	}

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Long getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(Long idEtudiant) {
		this.idEtudiant = idEtudiant;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getTypeEvenement() {
		return typeEvenement;
	}

	public void setTypeEvenement(String typeEvenement) {
		this.typeEvenement = typeEvenement;
	}

	public void setServiceEtudiant(IEtudiantService serviceEtudiant) {
		this.serviceEtudiant = serviceEtudiant;
	}

	public void setUserAuthentificationBean(
			UserAuthentificationBean userAuthentificationBean) {
		this.userAuthentificationBean = userAuthentificationBean;
	}



	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public void setServiceFormateur(IFormateurService serviceFormateur) {
		this.serviceFormateur = serviceFormateur;
	}

	public List<SessionEtudiant> getSessionsFormateur() {
		return sessionsFormateur;
	}

	public void setSessionsFormateur(List<SessionEtudiant> sessionsFormateur) {
		this.sessionsFormateur = sessionsFormateur;
	}

	public Long getIdFormateur() {
		return idFormateur;
	}

	public void setIdFormateur(Long idFormateur) {
		this.idFormateur = idFormateur;
	}

	public SessionEtudiant getSessionFormateur() {
		return sessionFormateur;
	}

	public void setSessionFormateur(SessionEtudiant sessionFormateur) {
		this.sessionFormateur = sessionFormateur;
	}

	public Long getIdSpecialite() {
		return idSpecialite;
	}

	public void setIdSpecialite(Long idSpecialite) {
		this.idSpecialite = idSpecialite;
	}

	public List<Evenement> getEvents() {
		return events;
	}

	public void setEvents(List<Evenement> events) {
		this.events = events;
	}

	public DateTime[] getJoursSemaine() {
		return joursSemaine;
	}

	public void setJoursSemaine(DateTime[] joursSemaine) {
		this.joursSemaine = joursSemaine;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Object[]> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Object[]> etudiants) {
		this.etudiants = etudiants;
	}



	public List<Etudiant> getStudents() {
		return students;
	}

	public void setStudents(List<Etudiant> students) {
		this.students = students;
	}

	/**
	 * @return the questions
	 */
	public Set<Questions> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(Set<Questions> questions) {
		this.questions = questions;
	}

	/**
	 * @return the evenement
	 */
	public Evenement getEvenement() {
		return evenement;
	}

	/**
	 * @param evenement the evenement to set
	 */
	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	/**
	 * @return the sessionBean
	 */
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	/**
	 * @param sessionBean the sessionBean to set
	 */
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	
	

}