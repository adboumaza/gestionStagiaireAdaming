package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Examen;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Note;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.Reponses;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.examen.service.IExamenService;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.notes.service.INotesService;
import com.adaming.myapp.question.service.IQuestionService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.MyComparator;
import com.adaming.myapp.tools.Utilitaire;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Component("examenBean")
@Scope(value = "session")
public class ExamenBean implements Serializable {
	/**
	 * LOGGER LOG4j 
	 * @see org.apache.log4j.Logger
	 */
   
	
    @Inject
	private IEtudiantService serviceEtudiant;
    
    @Inject
   	private ISessionService serviceSession;
    
	@Inject
	private IModuleService serviceModule;
	@Inject
	private IQuestionService serviceQuestion;
	@Inject
	private INotesService serviceNotes;
	@Inject
	private UserAuthentificationBean userAuthentification;
	@Inject
	private IExamenService serviceExamen;
	
    private Set<Questions> questions;
	private Long idModule;
	private List<Note> notes;
	private Double note = 0.0;
	private Long reponseSelectionnee;
	private Object scoreFinal;
	private List<Reponses> reponsesSelected;
	private Etudiant etudiant;
	private SessionEtudiant sessionEtudiant;
	private boolean activeException;
	private List<Module> modulesActived = new ArrayList<Module>();
	private  int examenTimeInSeconde ;
	private  Date serverTime;
	private Examen examen;
	private Integer numberOfReponseSelected = 0;
	private Boolean reponseSelected = false;

	
	/**@init examen*/
	public String initExamen(){
		idModule = null;
		activeException = true;
		etudiant        = createEtudiant();
		etudiant        = serviceEtudiant.getEtudiant(userAuthentification.getName());
		try {
			sessionEtudiant = serviceSession.getSessionByEtudiant(etudiant.getIdEtudiant());
			getModulesActivedBySession();
		} catch (VerificationInDataBaseException e) {
			activeException = false;
			Utilitaire.displayMessageWarning(e.getMessage());
		}
		return "examen";
	}
	
	/**
	 * @create New Etudiant
	 ** @return Object Etudiant
	 ** @factory.create.method
	 */
	private Etudiant createEtudiant() {
		etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
	    return etudiant;
	}
	
	/**@methode getModules Actived By Session*/
	
	public void getModulesActivedBySession(){
		if(sessionEtudiant != null){
			try {
				modulesActived = serviceModule.getModuleActivedBySession(sessionEtudiant.getIdSession());
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
			}
		}
		if(modulesActived.size()>0){
			getNotesByStudent();
		}
	}
	
	/**@noteByStudents */
	public void getNotesByStudent(){
		List<Module> modulesNotDisplay =  new ArrayList<Module>();
		List<Module> modulesValidee = new ArrayList<Module>();
		notes = serviceNotes.getAllNotesByStudent(etudiant.getIdEtudiant());
		if(notes.size() > 0){
			for(Note n:notes){
				if(n.getScore() != null && n.getEtudiant().getIdEtudiant() == etudiant.getIdEtudiant()){
					modulesValidee.add(n.getModule());
				}
			}
			
			for(Module modActived : modulesActived){
				for(Module modValide : modulesValidee){
					if(modActived.getIdModule().equals(modValide.getIdModule())){
						modulesNotDisplay.add(modActived);
					}
				}
			}
			modulesActived.removeAll(modulesNotDisplay);
			if(modulesActived.size() == 0){
				Utilitaire.displayMessageWarning("Vous n'avez aucun module a validé");
			}
		}

		LoggerConfig.logInfo("modules actived : "+modulesActived);
		LoggerConfig.logInfo("modules not display :"+modulesValidee);
		LoggerConfig.logInfo("modulesDisplay : "+modulesNotDisplay);
			
	}
	/**cette methode permet d'initialisée le tableau de 
	 * reponses en cas d'actualisation de la page*/
	public void initResponses(){
		reponsesSelected = new ArrayList<Reponses>();
		reponsesSelected.clear();
		note = 0.0;
		numberOfReponseSelected = 0;
		reponseSelected = false;
	}
	
	public String startExamen(){
		scoreFinal = 0.0;
		reponseSelectionnee = null;
		initResponses();
		questions = serviceQuestion.getQuestionsByModule(idModule);
		
		if(questions.isEmpty()){
			Utilitaire.displayMessageWarning("il existe aucune question dans la base de donnée");
			return null;
		}
		else{
			final Date afterAddingTwentntyFiveMins = calculateTimeOfExamen();
			Examen examen = new Examen(afterAddingTwentntyFiveMins);
			try {
				serviceExamen.addExamen(examen, etudiant.getIdEtudiant(), idModule, sessionEtudiant.getIdSession());
				getTimeOfExamenInit();
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageInfo("Examen en Cours");
				getTimeOfExamenInit();
				return "start_examen";
			}
			
			return "start_examen";
		}
	}

	/**
	 * @return
	 */
	private Date calculateTimeOfExamen() {
		serverTime = new Date();
		final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
		final Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		final Date afterAddingTwentntyFiveMins=new Date(t + (25 * ONE_MINUTE_IN_MILLIS));
		return afterAddingTwentntyFiveMins;
	}
	
	public void getTimeOfExamenInit(){
		examen = serviceExamen.verifyExistingExamen(etudiant.getIdEtudiant(), idModule,sessionEtudiant.getIdSession());
		if(examen != null){
			final long examenTimeInMiliSeconde = (examen.getDateExamenStart().getTime() - serverTime.getTime() );
			 examenTimeInSeconde = (int) (examenTimeInMiliSeconde / 1000) ;
			LoggerConfig.logInfo("difference en secondes"+examenTimeInSeconde);
		}
	}
	
	/**
	 * @create New Score
	 ** @return Object Note
	 ** @factory.create.method
	 */
	private Note createNote() {
		Note noteFinal = FactoryBean.getNoteFactory().create("Note");
        noteFinal.setScore(note);
		return noteFinal;
	}
	
	/* @method redirection à la fin de l'examen et afficher la note final */
	public String redirect() {

		// on cree la note finale
		Note noteFinal = createNote();
		try {
			serviceNotes.addNoteFinal(noteFinal, sessionEtudiant.getIdSession(), etudiant.getIdEtudiant(), idModule);
		} catch (Exception e) {
			resetVarsExam();
			return "index?faces-redirect=true";
		}

		// init reponsesFinal
		resetVarsExam();
		// on ordonne la listes des reponses par num

		Collections.sort(reponsesSelected, new MyComparator());
		LoggerConfig.logInfo("trier le tableau"+reponsesSelected);
		return "examen_success?faces-redirect=true";
	}
	public void resetVarsExam() {
		// init reponsesFinal
		reponseSelectionnee = new Long(0);
		scoreFinal = note;
		note = 0.0;
		numberOfReponseSelected = 0;
		reponseSelectionnee = null;
		reponseSelected = false;
		questions = new HashSet<Questions>();
	}
	
	/* @method copie de redirect() avec redirection a la fin de Timeout */
	public void onTimeout() {

		// on cree la note finale
		 Note noteFinal = createNote();
		try {
			serviceNotes.addNoteFinal(noteFinal, sessionEtudiant.getIdSession(), etudiant.getIdEtudiant(), idModule);
			// init reponsesFinal
			resetVarsExam();
			// on ordonne la listes des reponses par num
			Collections.sort(reponsesSelected, new MyComparator());
			LoggerConfig.logInfo("trier le tableau"+reponsesSelected);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"examen_success");
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}

	}
	
	public void incrementeNumberOfReponseSelected(){
		numberOfReponseSelected ++;
	}
	/* @method repondre */
	public void register() {
        Reponses rep = null;
        if(!reponseSelectionnee.equals(null)){
           for(Questions q:questions){
        	   for(Reponses r:q.getReponses()){
                  if(reponseSelectionnee.equals(r.getIdReponse())){
                	 rep = r;
                  }
        	   }
		   }
           if(rep.isEtat() && !reponsesSelected.contains(rep)){
        	   note  ++;
        	   LoggerConfig.logInfo("Note actuelle "+note);
           }
           if(!reponsesSelected.contains(rep)){
        	   reponsesSelected.add(rep);
           }
             
             LoggerConfig.logInfo("tab"+reponsesSelected);
		}
       
	}
	
	public void verificationClickReponse(){
		reponseSelected = true;
	}

	
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public Double getNote() {
		return note;
	}
	public void setNote(Double note) {
		this.note = note;
	}
	public Long getReponseSelectionnee() {
		return reponseSelectionnee;
	}
	public void setReponseSelectionnee(Long reponseSelectionnee) {
		this.reponseSelectionnee = reponseSelectionnee;
	}
	public Object getScoreFinal() {
		return scoreFinal;
	}
	public void setScoreFinal(Object scoreFinal) {
		this.scoreFinal = scoreFinal;
	}
	

	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public SessionEtudiant getSessionEtudiant() {
		return sessionEtudiant;
	}
	public void setSessionEtudiant(SessionEtudiant sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}
	public boolean isActiveException() {
		return activeException;
	}
	public void setActiveException(boolean activeException) {
		this.activeException = activeException;
	}
	public List<Module> getModulesActived() {
		return modulesActived;
	}
	public void setModulesActived(List<Module> modulesActived) {
		this.modulesActived = modulesActived;
	}

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	

	public List<Reponses> getReponsesSelected() {
		return reponsesSelected;
	}

	public void setReponsesSelected(List<Reponses> reponsesSelected) {
		this.reponsesSelected = reponsesSelected;
	}

	public Set<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Questions> questions) {
		this.questions = questions;
	}


	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public int getExamenTimeInSeconde() {
		return examenTimeInSeconde;
	}

	public void setExamenTimeInSeconde(int examenTimeInSeconde) {
		this.examenTimeInSeconde = examenTimeInSeconde;
	}

	public Date getServerTime() {
		return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}

	public Integer getNumberOfReponseSelected() {
		return numberOfReponseSelected;
	}

	public void setNumberOfReponseSelected(Integer numberOfReponseSelected) {
		this.numberOfReponseSelected = numberOfReponseSelected;
	}

	public Boolean getReponseSelected() {
		return reponseSelected;
	}

	public void setReponseSelected(Boolean reponseSelected) {
		this.reponseSelected = reponseSelected;
	}

	

	


}
