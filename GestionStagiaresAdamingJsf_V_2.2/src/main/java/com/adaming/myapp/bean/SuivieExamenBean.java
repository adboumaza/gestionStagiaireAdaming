package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.factory.manager.IFactory;
import com.adaming.myapp.formateur.service.IFormateurService;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.notes.service.INotesService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
import com.sun.faces.facelets.impl.IdMapper;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Component("suivieExamenBean")
@Scope(value = "session")
public class SuivieExamenBean implements Serializable{
    
	@Inject
	private ISessionService serviceSession;
	@Inject
	private IModuleService serviceModule;
	@Inject
	private INotesService serviceNote;
	@Inject
	private UserAuthentificationBean userAuthentificationBean;
	@Inject
    private IFormateurService serviceFormateur;
	@Inject
	private INotesService serviceNotes;
	
	private Long idSession;
	private List<Object[]> allSessions;
	private List<Object[]> modules;
	private List<Object[]> notes;
	private List<Object[]> results;
	private Double moyenne;
	private Double moyenneModule;
	private PieChartModel pieModel1;
	private BarChartModel barModel;
	private PieChartModel pieModel2;
	private Double moyenneGeneral;
	private SessionEtudiant sessionFormateur;
	private String customException;
	
	public String init(){
		idSession = null;
		setModules(null);
		allSessions = serviceSession.getAllSessionsV2();
		return "suivie_examen?faces-redirect=true";
	}
	@PostConstruct
	public void initChart(){
		pieModel1 = new PieChartModel();
		pieModel2 = new PieChartModel();
		barModel = new BarChartModel();
		createBarModel();
	}
	
	/** cette method permet de vérifier est ce que le module à été passée ou non 
	 *  espace admin
	 *  si un module est passé par la session on récupère le classement
	 * **/
	public void getModulesBySession(){
		modules=serviceModule.getModulesBySessionV2(idSession);
		if(modules.size()>0){
			for(Object[] m:modules){
				Long idModule = (Long) m[0];
				moyenne = serviceNote.getMoyenne(idSession,idModule);
				if(moyenne != null){
					m[2] = true;
					/*si des modules ont déja passé par la session  on récupére le classement*/
					
					LoggerConfig.logDebug("module "+m[0]+"--"+moyenne);
				}else{
					m[2] = false;
					LoggerConfig.logDebug("module "+m[0]+"--"+moyenne);
				
				}
				
			}
		}else{
			Utilitaire.displayMessageWarning("Aucun Module Trouvé dans la session N° "+idSession);
		}
		
		
	}
	
	/** cette method permet de vérifier est ce que le module à été passée ou non 
	 *  espace Formateur
	 * **/
	public String getSessionByFormateur(){
		
		try {
			Formateur formateur = createFormateur();
			formateur = serviceFormateur.getFormateur(userAuthentificationBean.getName());
			sessionFormateur = serviceSession.getSessionByFormateur(formateur.getIdFormateur());
			modules=serviceModule.getModulesBySessionV2(sessionFormateur.getIdSession());
			if(modules.size()>0){
				for(Object[] m:modules){
					Long idModule = (Long) m[0];
					moyenne = serviceNote.getMoyenne(sessionFormateur.getIdSession(),idModule);
					if(moyenne != null){
						m[2] = true;
						LoggerConfig.logDebug("module "+m[0]+"--"+moyenne);
					}else{
						m[2] = false;
						LoggerConfig.logDebug("module "+m[0]+"--"+moyenne);
					}
					
				}
			}else{
				Utilitaire.displayMessageWarning("Aucun Module Trouvé dans la session N° "+idSession);
			}
		
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
		return "suivie_examen?faces-redirect=true";
	}
	private Formateur createFormateur(){
		return FactoryBean.getFormateurFactory().create("Formateur");
	}
	
	/** get all notes by session end modules**/
	public String getNotesByModulesAndSession(Long idSession,Long idModule){
		results = serviceNote.getNotesBySessionAndModule(idSession, idModule);
		moyenneModule = serviceNote.getMoyenne(idSession, idModule);
		pieModel1.set("Moyenne", moyenneModule);
		pieModel1.set("Rest", 20 - moyenneModule);
		customChart(pieModel1, "la moyenne du module numéro"+idModule);
		return "notes?faces-redirect=true";
	}
	
	/** cette méthode permet de faire un classement des etudiants 
	 * acces admin */
	public String getClassementGeneral(Long idSession,Long idModule) {
			barModel.clear();
			barModel = new BarChartModel();
			LoggerConfig.logInfo("la session en Cours" + idSession);
			try {
				setCustomException("");
				moyenneGeneral = new Double(0);
				List<Object[]> classement = serviceNotes
						.getClassementGeneralBySession(idSession);
				LoggerConfig.logInfo("liste des etudiants qui ont une note"
						+ classement);

				ChartSeries chartBare = new ChartSeries();
				for (Object[] cc : classement) {
					String prenomEtudiant = (String) cc[1];
					Double moyenne = (Double) cc[2];
					String nomEtudiant = (String)cc[3];
					chartBare.set(prenomEtudiant.concat("-"+nomEtudiant.substring(0,2).toUpperCase()), new Double(moyenne));
					LoggerConfig.logInfo("moyenne" + moyenne);
				}

				barModel.addSeries(chartBare);
				barModel.setAnimate(true);
				moyenneGeneral = serviceNotes
						.getMoyenneGeneralBySession(idSession);
				moyenneModule = serviceNote.getMoyenne(idSession, idModule);
				pieModel2.set("La Moyenne Générale", moyenneModule);
				pieModel2.set("Rest", 20 - moyenneModule);
				LoggerConfig.logInfo("Moyenne général" + moyenneGeneral);
				customChart(pieModel2,
						"la Moyenne Générale du module N° "+idModule);

			} catch (VerificationInDataBaseException e) {
				setCustomException(e.getMessage());
				setMoyenneGeneral(null);
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

	public Long getIdSession() {
		return idSession;
	}
	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}
	public List<Object[]> getModules() {
		return modules;
	}
	public void setModules(List<Object[]> modules) {
		this.modules = modules;
	}
	

	/**
	 * @return the allSessions
	 */
	public List<Object[]> getAllSessions() {
		return allSessions;
	}
	/**
	 * @param allSessions the allSessions to set
	 */
	public void setAllSessions(List<Object[]> allSessions) {
		this.allSessions = allSessions;
	}
	public List<Object[]> getResults() {
		return results;
	}

	public void setResults(List<Object[]> results) {
		this.results = results;
	}

	public Double getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(Double moyenne) {
		this.moyenne = moyenne;
	}

	/**
	 * @return the moyenneModule
	 */
	public Double getMoyenneModule() {
		return moyenneModule;
	}

	/**
	 * @param moyenneModule the moyenneModule to set
	 */
	public void setMoyenneModule(Double moyenneModule) {
		this.moyenneModule = moyenneModule;
	}
	/**
	 * @return the notes
	 */
	public List<Object[]> getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(List<Object[]> notes) {
		this.notes = notes;
	}
	/**
	 * @return the pieModel1
	 */
	public PieChartModel getPieModel1() {
		return pieModel1;
	}
	/**
	 * @param pieModel1 the pieModel1 to set
	 */
	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
	/**
	 * @return the userAuthentificationBean
	 */
	public UserAuthentificationBean getUserAuthentificationBean() {
		return userAuthentificationBean;
	}
	/**
	 * @param userAuthentificationBean the userAuthentificationBean to set
	 */
	public void setUserAuthentificationBean(
			UserAuthentificationBean userAuthentificationBean) {
		this.userAuthentificationBean = userAuthentificationBean;
	}
	/**
	 * @return the sessionFormateur
	 */
	public SessionEtudiant getSessionFormateur() {
		return sessionFormateur;
	}
	/**
	 * @param sessionFormateur the sessionFormateur to set
	 */
	public void setSessionFormateur(SessionEtudiant sessionFormateur) {
		this.sessionFormateur = sessionFormateur;
	}
	/**
	 * @return the barModel
	 */
	public BarChartModel getBarModel() {
		return barModel;
	}
	/**
	 * @param barModel the barModel to set
	 */
	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	/**
	 * @return the pieModel2
	 */
	public PieChartModel getPieModel2() {
		return pieModel2;
	}
	/**
	 * @param pieModel2 the pieModel2 to set
	 */
	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
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
	 * @return the moyenneGeneral
	 */
	public Double getMoyenneGeneral() {
		return moyenneGeneral;
	}
	/**
	 * @param moyenneGeneral the moyenneGeneral to set
	 */
	public void setMoyenneGeneral(Double moyenneGeneral) {
		this.moyenneGeneral = moyenneGeneral;
	}
	
	

}
