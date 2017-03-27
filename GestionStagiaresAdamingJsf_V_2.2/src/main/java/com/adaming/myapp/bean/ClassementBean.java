package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Note;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.notes.service.INotesService;
import com.adaming.myapp.question.service.IQuestionService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.ComparatorQuestion;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;


/**
 * la calss Bean , c'est le bean qui permet 
 * de répondre aux besoins métiers,de la class Questions
 * ajouter une question, ajouter une réponse,
 * récupérer la liste des quetion par module  
 * 
 * 
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 */


@SuppressWarnings("serial")
@Component("classementBean")
@Scope(value="session")
public class ClassementBean implements Serializable {
    
	/**
	 * LOGGER LOG4j 
	 * @see org.apache.log4j.Logger
	 */
  
	
	@Inject
	private INotesService serviceNotes;
	@Inject
	private IEtudiantService serviceEtudiant;
	@Inject
	private IModuleService serviceModule;
	@Inject
	private ISessionService serviceSession;
	@Inject
	private IQuestionService serviceQuestions;
	@Inject
	private UserAuthentificationBean userAuthentification;
	

	private Long idModule;
	private SessionEtudiant sessionEtudiant;
	private List<Object[]> notes;
	private Etudiant etudiant;
	private Set<Object[]> modules;
	private List<Note> notesByStudents;
	private Set<Questions> questions;
	
	
	/*get all notes by session*/
	public String getAllModulesValideBySession(){
		getEtudiantByName();
		modules= new HashSet<Object[]>();
		try {
			modules=serviceModule.getModulesValideBySession(sessionEtudiant.getIdSession());
			reset();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
		LoggerConfig.logInfo("Modules : "+modules);
		return "classement?redirect=true";
	}
	public void reset(){
		idModule = null;
		setNotes(null);
	}
	
	/*get All Notes By Session And Module*/
    public void getAllNotesBySessionAndModule(){
		notes=new ArrayList<Object[]>();
		notes=serviceNotes.getNotesBySessionAndModule(sessionEtudiant.getIdSession(), idModule);
		LoggerConfig.logInfo("Notes : "+notes);
    }
    
    /*@method get Etudiant By Name */
    public void getEtudiantByName(){
    	etudiant = createEtudiant();
    	LoggerConfig.logInfo("Etudiant : "+etudiant);
		try {
			sessionEtudiant = serviceSession.getSessionByEtudiant(etudiant.getIdEtudiant());
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
    }
    
    public Etudiant createEtudiant(){
    	etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
		etudiant = serviceEtudiant.getEtudiant(userAuthentification.getName());
        return etudiant;
    }
    
    public String getAllNotesByStudents(){
    	etudiant = createEtudiant();
    	notesByStudents = serviceNotes.getAllNotesByStudent(etudiant.getIdEtudiant());
    	return "resultats?faces-redirect=true";
    }
    /** cette methode permet de recuperer toutes les questions avec les reponses*/
    public String getCorrectionByModule(Long idModule){
    	getEtudiantByName();
    	try {
    		 System.out.println("idModule"+idModule);
    		 System.out.println("idSession"+sessionEtudiant.getIdSession());
			questions = serviceQuestions.getQuestionsByModule(idModule);
			serviceNotes.getAllExamesEnCoursBySessionAndModule(sessionEtudiant.getIdSession(), idModule);
			return "corrige_examen?faces-redirect=true";
    	} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			return null;
		}
    	
    	
    }
    
  


	public List<Object[]> getNotes() {
		return notes;
	}

	public void setNotes(List<Object[]> notes) {
		this.notes = notes;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}


	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}


	public Long getIdModule() {
		return idModule;
	}


	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	public Set<Object[]> getModules() {
		return modules;
	}

	public void setModules(Set<Object[]> modules) {
		this.modules = modules;
	}

	public SessionEtudiant getSessionEtudiant() {
		return sessionEtudiant;
	}

	public void setSessionEtudiant(SessionEtudiant sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}
	public List<Note> getNotesByStudents() {
		return notesByStudents;
	}
	public void setNotesByStudents(List<Note> notesByStudents) {
		this.notesByStudents = notesByStudents;
	}
	public Set<Questions> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<Questions> questions) {
		this.questions = questions;
	}

	
}
