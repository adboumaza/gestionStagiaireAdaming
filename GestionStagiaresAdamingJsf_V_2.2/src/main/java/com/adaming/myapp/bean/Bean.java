package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.Reponses;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.question.service.IQuestionService;
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
@Component("bean")
@ViewScoped
public class Bean implements Serializable {

	/**
	 * LOGGER LOG4j
	 * 
	 * @see org.apache.log4j.Logger
	 */
	
/***/
	
	
	@Inject
	private IQuestionService serviceQuestion;
	
	private List<Questions> m_lFields;
	private Long idQuestion;
	private Long idModule;
	@Size(max = 1000)
	private String label;
	@Size(max = 500)
	private String reponseOne;
	@Size(max = 500)
	private String reponseTwo;
	@Size(max = 500)
	private String reponseThree;
	@Size(max = 500)
	private String reponseFour;
	private boolean etatOne;
	private boolean etatTwo;
	private boolean etatThree;
	private boolean etatFour;
	private List<Reponses> reponses;
	private Set<Questions> questions;
	private Questions question;
	private Reponses repOne;
	private Reponses repTwo;
	private Reponses repThree;
	private Reponses repFour;
	
	
	
	// Query Operations
	
	
	
	/** 
	* la methode addQuestion permet d'ajouter une question
	* ajouter des réonses à chaque question
	* mélanger le choix de réponse 
	* 
	* @trows VerificationInDataBaseException cette exception
	* assure de ne pas enregistrer une question 2 fois
	* @see com.adaming.myapp.tools.Utilitaire.displayMessageInfo
	* @see com.adaming.myapp.tools.Utilitaire.displayMessageWarning
	* 
	*/
	public void addQuestion(){
		
		if( notRepeat() )
		{
			warning();
		}

		else
		{
			
			try {
				repOne    = createReponsesOne();
				repTwo    = createReponsesTwo();
				repThree  = createReponsesThree();
				repFour   = createReponsesFour();
				Questions question = createQuestions();
				addReponse(repOne, repTwo, repThree, repFour);
				serviceQuestion.addQuestions(question, idModule, reponses);
				getQuestionByModule();
				success();
				reset();
				mixChoiseOfReponses();
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
			}
			
		}
	}
	
	
	
	/**
	 * la methode createQuestions permet de créer une question
	 * 
	 * @return la question créeé
	 * @see com.adaming.myapp.bean.FactoryBean.QUESTION_FACTORY
	 * 
	 */
	private Questions createQuestions() {
		question = FactoryBean.getQuestionFactory().create("Questions");
		question.setLabel(label);
		question.setNumeroQuestion(questions.size()+1);
		return question;
	}
	
	/**
	 * la methode createReponsesOne permet de créer une 1ere réponse
	 * 
	 * @return la réponse créeé
	 * @see com.adaming.myapp.bean.FactoryBean.REPONSE_FACTORY 
	 */
	private Reponses createReponsesOne(){
		repOne = FactoryBean.getReponsesFactory().create("Reponses");
	    repOne.setEtat(etatOne);
	    repOne.setLabelReponse(reponseOne);
		return repOne;
	}
	
	
	/**
	 * la methode createReponsesTwo permet de créer une 2ème réponse
	 * 
	 * @return la réponse créeé
	 * @see com.adaming.myapp.bean.FactoryBean.REPONSE_FACTORY 
	 */
	private Reponses createReponsesTwo(){
		repTwo = FactoryBean.getReponsesFactory().create("Reponses");
		repTwo.setEtat(etatTwo);
		repTwo.setLabelReponse(reponseTwo);
		return repTwo;
	}
	
	
	
	
	
	/**
	 * la methode createReponsesThree permet de créer une 3ème réponse
	 * 
	 * @return la réponse créeé
	 * @see com.adaming.myapp.bean.FactoryBean.REPONSE_FACTORY 
	 */
	private Reponses createReponsesThree(){
		repThree = FactoryBean.getReponsesFactory().create("Reponses");
		repThree.setEtat(etatThree);
		repThree.setLabelReponse(reponseThree);
		return repThree;
	}
	
	
	
	
	
	
	/**
	 * la methode createReponsesFour permet de créer une 4ème réponse
	 * 
	 * @return la réponse créeé
	 * @see com.adaming.myapp.bean.FactoryBean.REPONSE_FACTORY 
	 */
	private Reponses createReponsesFour(){
		repFour = FactoryBean.getReponsesFactory().create("Reponses");
		repFour.setEtat(etatFour);
		repFour.setLabelReponse(reponseFour);
		return repFour;
	}

	
	
	
	
	/**
	 * la methode notRepeat assure que les 4 propositions de réponse
	 * ne sont pas identiques
	 * 
	 * @return true si les 4 réponses sont différents
	 */
	private boolean notRepeat() {
		return reponseOne.equals(reponseTwo)  || 
		   reponseOne.equals(reponseThree)||
		   reponseOne.equals(reponseFour) ||
		   reponseTwo.equals(reponseThree)||
		   reponseTwo.equals(reponseFour) ||
		   reponseThree.equals(reponseFour);
	}
	
	
	
	

	/** 
	* la methode warning permet d'afficher un message warning
	* 
	* @see com.adaming.myapp.tools.Utilitaire.displayMessageWarning
	* 
	*/
	private void warning() {
		Utilitaire.displayMessageWarning("Veuillez s'assurer que vous avez écrit des réponses différentes");
	  
	}
	
	
	
	
	

	/** 
	* la methode warning permet d'afficher un message succèss
	* 
	* @see com.adaming.myapp.tools.Utilitaire.displayMessageInfo
	* 
	*/
	private void success() {
		Utilitaire.displayMessageInfo("La Question :" +
		label +" Ajouter Avec succès dans le Module N° " + idModule);
	}
	
	
	


	/**
	 * la methode addReponse permet d'ajouter les 4 rèponse
	 * dans un tableau 
	 * 
	 * @param repOne la première réponse
	 * @param repTwo la douxième réponse
	 * @param repThree la troisième réponse
	 * @param repFour la quatrième réponse
	 */
	private void addReponse(Reponses repOne, Reponses repTwo,
			Reponses repThree, Reponses repFour) {
		reponses = new ArrayList<Reponses>();
		reponses.add(repOne);
		reponses.add(repTwo);
		reponses.add(repThree);
		reponses.add(repFour);
	}
	
	
	
	
	
	/**
	 * la methode rest permet de vider les champs 
	 * et mélanger les choix de réponses
	 */
	public void reset(){
		label = "";
		reponseOne= "";
		reponseTwo= "";
		reponseThree= "";
		reponseFour= "";
		etatOne=Boolean.FALSE.booleanValue();
		etatTwo=Boolean.FALSE.booleanValue();
		etatThree=Boolean.FALSE.booleanValue();
		etatFour=Boolean.TRUE.booleanValue();
		mixChoiseOfReponses();
	}
	
	
	
	
	
	
	
	/**
	 * la methode getQuestionByModule permet de
	 * récupérer la liste des question par module
	 */
	public void getQuestionByModule(){
		questions = serviceQuestion.getQuestionsByModule(idModule);
	}
	
	
	
	
	public Bean() {
		m_lFields = new ArrayList<Questions>();
		m_lFields.add(new Questions());
	}
	
	
	/**
	 * mixChoiseOfReponses cette methode permet de mélanger les choix de
	 * rèponses
	 * 
	 */
	public void mixChoiseOfReponses(){
		// String Array
	    Boolean[] booleanArray = 
	        new Boolean[] { false, true, false, false};
	  
	    List<Boolean> tabListe = new ArrayList<Boolean>(Arrays.asList(booleanArray)) ;

	    Collections.shuffle(tabListe);
		
		for(int i=0;i<tabListe.size();i++){
			setEtatOne(tabListe.get(0));
			setEtatTwo( tabListe.get(1));
			setEtatThree(tabListe.get(2));
			setEtatFour(tabListe.get(3));
		}
		
	}
	

	public void setFields(List<Questions> p_lFields) {
		m_lFields = p_lFields;
	}

	public List<Questions> getFields() {
		return m_lFields;
	}

	public List<Questions> getM_lFields() {
		return m_lFields;
	}

	public void setM_lFields(List<Questions> m_lFields) {
		this.m_lFields = m_lFields;
	}
	public Long getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(Long idQuestion) {
		this.idQuestion = idQuestion;
	}
	public Long getIdModule() {
		return idModule;
	}
	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getReponseOne() {
		return reponseOne;
	}
	public void setReponseOne(String reponseOne) {
		this.reponseOne = reponseOne;
	}
	public String getReponseTwo() {
		return reponseTwo;
	}
	public void setReponseTwo(String reponseTwo) {
		this.reponseTwo = reponseTwo;
	}
	public String getReponseThree() {
		return reponseThree;
	}
	public void setReponseThree(String reponseThree) {
		this.reponseThree = reponseThree;
	}
	public String getReponseFour() {
		return reponseFour;
	}
	public void setReponseFour(String reponseFour) {
		this.reponseFour = reponseFour;
	}
	public boolean isEtatOne() {
		return etatOne;
	}
	public void setEtatOne(boolean etatOne) {
		this.etatOne = etatOne;
	}
	public boolean isEtatTwo() {
		return etatTwo;
	}
	public void setEtatTwo(boolean etatTwo) {
		this.etatTwo = etatTwo;
	}
	public boolean isEtatThree() {
		return etatThree;
	}
	public void setEtatThree(boolean etatThree) {
		this.etatThree = etatThree;
	}
	public boolean isEtatFour() {
		return etatFour;
	}
	public void setEtatFour(boolean etatFour) {
		this.etatFour = etatFour;
	}
	public List<Reponses> getReponses() {
		return reponses;
	}
	public void setReponses(List<Reponses> reponses) {
		this.reponses = reponses;
	}
	public Set<Questions> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<Questions> questions) {
		this.questions = questions;
	}

}
