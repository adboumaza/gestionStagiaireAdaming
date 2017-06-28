package com.adaming.myapp.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.extensions.component.exporter.Exporter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.EnterStateVetoException;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evaluation;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Prospection;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.evaluation.service.IEvaluationService;
import com.adaming.myapp.evenement.service.IEvenementService;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.service.IFormateurService;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.prospection.service.IProspectionService;
import com.adaming.myapp.question.service.IQuestionService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.DataEnum;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.util.ExcelCustomExporter;
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
	private IProspectionService serviceProspection;
	
	@Inject
	private IEvaluationService serviceEvaluation;
	
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
    
	/*prospection*/
	 @NotEmpty(message="Veuillez s�lectionner un niveau de risque")
	private String risque;
	 @NotEmpty(message="Veuillez s�lectionner un commentaire")
	private String commentaire;
	private final  String [] comportement  = DataEnum.COMPORTEMENT.getData();
    private final  String [] risques       = DataEnum.RISQUES.getData();
	private Prospection prospection = null;
    private Etudiant etudiant;
    
	/*evaluation*/
	@NotNull(message="Veuillez s�lectionner un Module")
	private Long idModule;
	private Long idSpecialite;
	private List<Object[]> etudiants;
	private List<Etudiant> students;
	private List<Etudiant> etudiantsEvaluations;
	private List<Object[]> modules;
	private Module module;
	private Set<Questions> questions;
	private final String [] comprehensionData = DataEnum.EVALUATIONS.getData();
	private final String [] applicationTpData = DataEnum.EVALUATIONS.getData();
	private final String [] participationData = DataEnum.EVALUATIONS.getData();
    private String comprehension;
    private String applicationTp;
    private String participation;
    private Evaluation evaluation;
	
   
	
	/** evenement */
    @NotNull(message="Veuillez s�lectionner un �tudiant")
	private Long idEtudiant;
    @NotNull(message="Veuillez s�lectionner une Date de d�but")
	private Date dateStart;
    @NotNull(message="Veuillez s�lectionner une Date de fin")
	private Date dateEnd;
    @NotEmpty(message="Veuillez s�lectionner un type d'�v�nement")
	private String typeEvenement;
    private boolean active = false;
	private Formateur formateur;
	private Long idFormateur;
	private List<SessionEtudiant> sessionsFormateur;
	private SessionEtudiant sessionFormateur;
	private List<Evenement> events;
	private Evenement evenement;
	private final String [] evenements = DataEnum.EVENEMENTS.getData();
	
	

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
    /*cette methode permet d'ajouter l'objet prospection */
	public void addProspection(Long idEtudiant,String risque,String commentaire){
		try {
			serviceProspection.addProspection(new Prospection(risque, commentaire), idEtudiant);
			Utilitaire.displayMessageInfo("Succ�s");
		    getStudentsBySession();
			resetProspection();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
	   
	}
	
	private void resetProspection(){
		commentaire = null;
		risque = null;
		idEtudiant = null;
	}
	/*cette methode permet de r�cup�rer les infos prospection par son idEtudiant*/
	public void getProspectionByEtudiant(Long idEtudiant){
		LoggerConfig.logInfo("idEtudiant"+idEtudiant);
		prospection = new Prospection();
		prospection = serviceProspection.getProspectionByEtudiant(idEtudiant);
	    LoggerConfig.logInfo("Prospection En Cours!"+prospection);
	   
	}
	/*cette methode permet de modifier la prospection*/
	public void update(Long idEtudiant){
		serviceProspection.updateProspection(prospection, idEtudiant);
		getStudentsBySession();
	}
    
	public String initProspection() throws VerificationInDataBaseException {
		initReporting();
		getStudentsBySession();
		resetProspection();
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
	
	public void addEvaluation(Long idEtudiant,Long idModule){
		Evaluation evaluation = null;
		try {
			evaluation = new Evaluation(applicationTp, comprehension, participation);
			serviceEvaluation.addEvaluation(evaluation, idEtudiant, idModule);
			 getAllEvaluationBySessionAndModule();
			Utilitaire.displayMessageInfo("Bien �t� Signal�");
			resetEvaluation();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			resetEvaluation();
			LoggerConfig.logInfo(e.getMessage());
		}
	}
	
	/*cette methode permet de r�cup�rer les infos evaluation par son idEtudiant et le module*/
	public void getEvaluationByEtudiant(Long idModule,Long idEtudiant){
		evaluation = new Evaluation();
		evaluation = serviceEvaluation.getEvaluationByEtudiant(idModule, idEtudiant);
	    LoggerConfig.logInfo("Evaluation En Cours!"+evaluation);
	   
	}
	/*cette methode permet de modifier les infos evaluation*/
	public void updateEvaluation(Long idEtudiant){
	   serviceEvaluation.updateEvaluation(evaluation, module.getIdModule(), idEtudiant);
	   getAllEvaluationBySessionAndModule();
	}
	private void resetEvaluation(){
		applicationTp = "";
		comprehension = "";
		participation = "";
	}
	
	/** @methode generate Evaluations */
	public String genererScheduleEvaluations(){
		getStudentsBySession();
		getModuleById();
		active=true;
		if(students.size() == 0){
			return null;
		}
		if (module != null) {
			getAllEvaluationBySessionAndModule();
		}
		return "evaluation_module?faces-redirect=true";
	}
	
	private void getAllEvaluationBySessionAndModule(){
		etudiantsEvaluations = serviceEvaluation.getAllEvaluationsBySessionAndModule(sessionFormateur.getIdSession(), module.getIdModule());
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
		sessionBean.resetInformationSession();
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
	    Utilitaire.displayMessageInfo("L'�v�nement a bien �t� supprim�");
	}
	
	

	public void getAllModulesBySession() {
		modules = serviceModule.getModulesBySessionV2(sessionFormateur.getIdSession());
		if(modules.isEmpty()){
			Utilitaire.displayMessageWarning("Aucun Module Trouv�..");
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


	

	/** @reset evenement */
	private void resetEvenement() {
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
			Utilitaire.displayMessageWarning("la date de d�part ne peut �tre ant�rieur ");
		}
		else if(Utilitaire.getHoursBetweenTwoDates(retard.getStartDate(), retard.getEndDate()) >= 10){
			Utilitaire.displayMessageWarning("la dur�e de retard ne peut pas d�passer 10 heures");
		}
		else if(retard.getStartDate().equals(retard.getEndDate()))
		{
			Utilitaire.displayMessageWarning("la dur�e de retard ne peut �tre 0 min");	
		}
		else 
		{
			try {
				serviceEvenement.addEvenement(retard, sessionFormateur.getIdSession(), idEtudiant);
				Utilitaire.displayMessageInfo(
								"le Retard de " + dateStart + " A " + dateEnd
										+ " � bien �t� signal�");
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
			Utilitaire.displayMessageWarning("la date de d�part ne peut �tre ant�rieur ");
		}
		else if(Utilitaire.getHoursBetweenTwoDates(absence.getStartDate(), absence.getEndDate()) < 9){
			Utilitaire.displayMessageWarning("la dur�e d'absence doit d�passer une journ�e soit 9h ou plus, Veuillez changer le type <--RETARD--> ");
		}
		else if(absence.getStartDate().equals(absence.getEndDate()))
		{
			Utilitaire.displayMessageWarning("la dur�e d'absence ne peut �tre 0 min");	
		}
		else 
		{
			try {
				serviceEvenement.addEvenement(absence, sessionFormateur.getIdSession(), idEtudiant);
				Utilitaire.displayMessageInfo(
								"l'absence de " + dateStart + " A " + dateEnd
										+ " � bien �t� signal�");
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
			Utilitaire.displayMessageWarning("la date de d�part ne peut �tre ant�rieur ");
		}
		else if(Utilitaire.getHoursBetweenTwoDates(entretien.getStartDate(), entretien.getEndDate()) > 11){
			Utilitaire.displayMessageWarning("la dur�e d'entretien ne doit pas d�passer une journ�e");
		}
		else if(entretien.getStartDate().equals(entretien.getEndDate()))
		{
			Utilitaire.displayMessageWarning("la dur�e d'entretien ne peut �tre 0 min");	
		}
		else
		{
			try {
				serviceEvenement.addEvenement(entretien, sessionFormateur.getIdSession(), idEtudiant);
				Utilitaire.displayMessageInfo(
								"l'entretien de " + dateStart + " A " + dateEnd
										+ " � bien �t� signal�");
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
							"l'ev�nement � bien �t� signal�e");
			resetEvenement();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			resetEvenement();
		}
	}

	/**  signaler un etudiantTop (refactoring) */
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
							"l'ev�nement � bien �t� signal�e");
			resetEvenement();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			resetEvenement();
		}
	}

	/* @method signaler un evenement */
	public void signalerEvenement() {
		if (!typeEvenement.equals(null)) {
			if (typeEvenement.equals("Retard")) {
				signalerUnRetad();
			} else if (typeEvenement.equals("Absence")) {
				signalerUneAbsence();
			} else if (typeEvenement.equals("Entretien")) {
				signalerUnEntretien();
			} else if (typeEvenement.equals("Top")) {
				signalerUnEtudiantTop();
			} else if (typeEvenement.equals("Warning")) {
				signalerUnEtudiantWarning();
			}
		}

	}

	/** :::::::::::::::::::::: **/



	

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

	/**
	 * @return the risque
	 */
	public String getRisque() {
		return risque;
	}

	/**
	 * @param risque the risque to set
	 */
	public void setRisque(String risque) {
		this.risque = risque;
	}

	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * @return the comportement
	 */
	public String[] getComportement() {
		return comportement;
	}

	/**
	 * @return the prospection
	 */
	public Prospection getProspection() {
		return prospection;
	}

	/**
	 * @param prospection the prospection to set
	 */
	public void setProspection(Prospection prospection) {
		this.prospection = prospection;
	}

	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/**
	 * @return the risques
	 */
	public  String[] getRisques() {
		return risques;
	}

	/**
	 * @return the evenements
	 */
	public String[] getEvenements() {
		return evenements;
	}

	/**
	 * @return the comprehensionData
	 */
	public String[] getComprehensionData() {
		return comprehensionData;
	}

	/**
	 * @return the applicationTpData
	 */
	public String[] getApplicationTpData() {
		return applicationTpData;
	}

	/**
	 * @return the participationData
	 */
	public String[] getParticipationData() {
		return participationData;
	}

	/**
	 * @return the comprehension
	 */
	public String getComprehension() {
		return comprehension;
	}

	/**
	 * @param comprehension the comprehension to set
	 */
	public void setComprehension(String comprehension) {
		this.comprehension = comprehension;
	}

	/**
	 * @return the applicationTp
	 */
	public String getApplicationTp() {
		return applicationTp;
	}

	/**
	 * @param applicationTp the applicationTp to set
	 */
	public void setApplicationTp(String applicationTp) {
		this.applicationTp = applicationTp;
	}

	/**
	 * @return the participation
	 */
	public String getParticipation() {
		return participation;
	}

	/**
	 * @param participation the participation to set
	 */
	public void setParticipation(String participation) {
		this.participation = participation;
	}

	/**
	 * @return the etudiantsEvaluations
	 */
	public List<Etudiant> getEtudiantsEvaluations() {
		return etudiantsEvaluations;
	}

	/**
	 * @param etudiantsEvaluations the etudiantsEvaluations to set
	 */
	public void setEtudiantsEvaluations(List<Etudiant> etudiantsEvaluations) {
		this.etudiantsEvaluations = etudiantsEvaluations;
	}

	/**
	 * @return the evaluation
	 */
	public Evaluation getEvaluation() {
		return evaluation;
	}

	/**
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	

}