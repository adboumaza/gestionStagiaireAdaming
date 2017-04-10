package com.adaming.myapp.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.atmosphere.config.service.Post;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
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
 * la calss Bean , c'est le bean qui permet de répondre aux besoins métiers,de
 * la class Questions ajouter une question, ajouter une réponse, récupérer la
 * liste des quetion par module
 * 
 * 
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 */

@SuppressWarnings("serial")
@Component("classementBean")
@Scope(value = "session")
public class ClassementBean implements Serializable {

	/**
	 * LOGGER LOG4j
	 * 
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
    @Inject
	private ScheduleView scheduleViewBean;
	
    private Long idModule;
	private SessionEtudiant sessionEtudiant;
	private List<Object[]> notes;
	private Etudiant etudiant;
	private Set<Object[]> modules;
	private List<Note> notesByStudents;
	private Set<Questions> questions;
	private BarChartModel barModel;
	private PieChartModel pieModel3;
	private Double moyenneGeneral;
	private String customException;

	@PostConstruct
	public void initChart() {
		barModel = new BarChartModel();
		pieModel3 = new PieChartModel();
		createBarModel();
	}

	/* get all notes by session */
	public String getAllModulesValideBySession() {
		getEtudiantByName();
		modules = new HashSet<Object[]>();
		try {
			modules = serviceModule.getModulesValideBySession(sessionEtudiant
					.getIdSession());
			reset();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
		LoggerConfig.logInfo("Modules : " + modules);
		return "classement?redirect=true";
	}

	public void reset() {
		idModule = null;
		setNotes(null);
	}

	/* get All Notes By Session And Module */
	public void getAllNotesBySessionAndModule() {
		notes = new ArrayList<Object[]>();
		notes = serviceNotes.getNotesBySessionAndModule(
				sessionEtudiant.getIdSession(), idModule);
		LoggerConfig.logInfo("Notes : " + notes);
	}

	/* @method get Etudiant By Name */
	public void getEtudiantByName() {
		etudiant = createEtudiant();
		LoggerConfig.logInfo("Etudiant : " + etudiant);
		try {
			sessionEtudiant = serviceSession.getSessionByEtudiant(etudiant
					.getIdEtudiant());
			LoggerConfig.logInfo("Session : " + sessionEtudiant);
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
	}

	public Etudiant createEtudiant() {
		etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
		etudiant = serviceEtudiant.getEtudiant(userAuthentification.getName());
		return etudiant;
	}
    /*recupérer la litse des notes par etudiant*/
	public String getAllNotesByStudents() {
		etudiant = createEtudiant();
		notesByStudents = serviceNotes.getAllNotesByStudent(etudiant
				.getIdEtudiant());
		return "resultats?faces-redirect=true";
	}

	/** cette methode permet de recuperer toutes les questions avec les reponses */
	public String getCorrectionByModule(Long idModule) {
		getEtudiantByName();
		try {
			System.out.println("idModule" + idModule);
			System.out.println("idSession" + sessionEtudiant.getIdSession());
			questions = serviceQuestions.getQuestionsByModule(idModule);
			serviceNotes.getAllExamesEnCoursBySessionAndModule(
					sessionEtudiant.getIdSession(), idModule);
			return "corrige_examen?faces-redirect=true";
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			return null;
		}

	}

	/** cette méthode permet de faire un classement des etudiants 
	 * acces etudiants */
	public String getClassementGeneral() {
		getEtudiantByName();
		if (sessionEtudiant != null) {
			barModel.clear();
			barModel = new BarChartModel();
			LoggerConfig.logInfo("la session en Cours" + sessionEtudiant);
			try {
				setCustomException("");
				moyenneGeneral = new Double(0);
				List<Object[]> classement = serviceNotes
						.getClassementGeneralBySession(sessionEtudiant
								.getIdSession());
				LoggerConfig.logInfo("liste des etudiants qui ont une note"
						+ classement);

				ChartSeries chartBare = new ChartSeries();
				for (Object[] cc : classement) {
					String prenomEtudiant = (String) cc[1];
					Double moyenne = (Double) cc[2];
					chartBare.set(prenomEtudiant, new Double(moyenne));
					LoggerConfig.logInfo("moyenne" + moyenne);
				}

				barModel.addSeries(chartBare);
				moyenneGeneral = serviceNotes
						.getMoyenneGeneralBySession(sessionEtudiant
								.getIdSession());
				pieModel3.set("La Moyenne Générale", moyenneGeneral);
				pieModel3.set("Rest", 20 - moyenneGeneral);
				LoggerConfig.logInfo("Moyenne général" + moyenneGeneral);
				customChart(pieModel3,
						"la Moyenne Générale de la session numéro "
								+ sessionEtudiant.getIdSession());

			} catch (VerificationInDataBaseException e) {
				setCustomException(e.getMessage());
				setMoyenneGeneral(null);
			}

		}

		return "classement_general?faces-redirect=true";
	}
	
	
	/** cette méthode permet de faire un classement des etudiants 
	 * pour l'acces Formateur */
	public String getClassementGeneralFormateur() {
		SessionEtudiant sessionFormateur = scheduleViewBean.initReporting();
		
		if (sessionFormateur != null) {
			barModel.clear();
			barModel = new BarChartModel();
			LoggerConfig.logInfo("la session en Cours" + sessionFormateur);
			try {
				setCustomException("");
				moyenneGeneral = new Double(0);
				List<Object[]> classement = serviceNotes
						.getClassementGeneralBySession(sessionFormateur
								.getIdSession());
				LoggerConfig.logInfo("liste des etudiants qui ont une note"
						+ classement);

				ChartSeries chartBare = new ChartSeries();
				for (Object[] cc : classement) {
					String prenomEtudiant = (String) cc[1];
					Double moyenne = (Double) cc[2];
					chartBare.set(prenomEtudiant, new Double(moyenne));
					LoggerConfig.logInfo("moyenne" + moyenne);
				}

				barModel.addSeries(chartBare);
				moyenneGeneral = serviceNotes
						.getMoyenneGeneralBySession(sessionFormateur
								.getIdSession());
				pieModel3.set("La Moyenne Générale", moyenneGeneral);
				pieModel3.set("Rest", 20 - moyenneGeneral);
				LoggerConfig.logInfo("Moyenne général" + moyenneGeneral);
				customChart(pieModel3,
						"la Moyenne Générale de la session numéro "
								+ sessionFormateur.getIdSession());

			} catch (VerificationInDataBaseException e) {
				setCustomException(e.getMessage());
				setMoyenneGeneral(null);
			}

		}

		return "classement_general?faces-redirect=true";
	}

	private void createBarModel() {

		barModel.setTitle("le classement Général");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Etudiant");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Moyenne");
		yAxis.setMin(0);
		yAxis.setMax(20);
	}

	private PieChartModel customChart(PieChartModel pieModel2, String title) {
		pieModel2.setLegendPosition("e");
		pieModel2.setFill(false);
		pieModel2.setShowDataLabels(true);
		pieModel2.setDiameter(150);
		pieModel2.setTitle(title);
		return pieModel2;
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

	/**
	 * @return the barModel
	 */
	public BarChartModel getBarModel() {
		return barModel;
	}

	/**
	 * @param barModel
	 *            the barModel to set
	 */
	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	/**
	 * @return the pieModel3
	 */
	public PieChartModel getPieModel3() {
		return pieModel3;
	}

	/**
	 * @param pieModel3
	 *            the pieModel3 to set
	 */
	public void setPieModel3(PieChartModel pieModel3) {
		this.pieModel3 = pieModel3;
	}

	/**
	 * @return the moyenneGeneral
	 */
	public Double getMoyenneGeneral() {
		return moyenneGeneral;
	}

	/**
	 * @param moyenneGeneral
	 *            the moyenneGeneral to set
	 */
	public void setMoyenneGeneral(Double moyenneGeneral) {
		this.moyenneGeneral = moyenneGeneral;
	}

	/**
	 * @return the customException
	 */
	public String getCustomException() {
		return customException;
	}

	/**
	 * @param customException the customException to set
	 */
	public void setCustomException(String customException) {
		this.customException = customException;
	}

	/**
	 * @return the scheduleViewBean
	 */
	public ScheduleView getScheduleViewBean() {
		return scheduleViewBean;
	}

	/**
	 * @param scheduleViewBean the scheduleViewBean to set
	 */
	public void setScheduleViewBean(ScheduleView scheduleViewBean) {
		this.scheduleViewBean = scheduleViewBean;
	}
	
	

}
