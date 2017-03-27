package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.stereotype.Component;

import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.notes.service.INotesService;
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
@Component("suivieExamenBean")
@ViewScoped
public class SuivieExamenBean implements Serializable{
    
	@Inject
	private ISessionService serviceSession;
	@Inject
	private IModuleService serviceModule;
	@Inject
	private INotesService serviceNote;

	private Long idSession;
	private List<Object[]> sessionEnCours;
	private List<Object[]> modules;
	private List<Object[]> notes;
	private List<Object[]> results;
	private Double moyenne;
	private Double moyenneModule;
	private PieChartModel pieModel1;
	
	public String init(){
		idSession = null;
		setModules(null);
		sessionEnCours = serviceSession.getAllSessionsInProgressV2();
		return "suivie_examen?faces-redirect=true";
	}
	@PostConstruct
	public void initChart(){
		pieModel1 = new PieChartModel();
	}
	
	/** cette method permet de vérifier est ce que le module à été passée ou non **/
	public void getModulesBySession(){
		modules=serviceModule.getModulesBySessionV2(idSession);
		if(modules.size()>0){
			for(Object[] m:modules){
				Long idModule = (Long) m[0];
				moyenne = serviceNote.getMoyenne(idSession,idModule);
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
		
		
	}
	
	/** get all notes by session end modules**/
	public String getNotesByModulesAndSession(Long idSession,Long idModule){
		results = serviceNote.getNotesBySessionAndModule(idSession, idModule);
		moyenneModule = serviceNote.getMoyenne(idSession, idModule);
		pieModel1.set("Moyenne", moyenneModule);
		pieModel1.set("Rest", 20 - moyenneModule);
		pieModel1.setTitle("la moyenne du module numéro"+idModule);
	    pieModel1.setLegendPosition("e");

        pieModel1.setFill(false);
        pieModel1.setShowDataLabels(true);
        pieModel1.setDiameter(150);
		return "notes?faces-redirect=true";
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
	public List<Object[]> getSessionEnCours() {
		return sessionEnCours;
	}
	public void setSessionEnCours(List<Object[]> sessionEnCours) {
		this.sessionEnCours = sessionEnCours;
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
	
	

}
