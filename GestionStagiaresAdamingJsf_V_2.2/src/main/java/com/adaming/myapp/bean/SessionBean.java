package com.adaming.myapp.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Salle;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.Site;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.salle.service.ISalleService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.site.service.ISiteService;
import com.adaming.myapp.tools.DataUtil;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.user.service.IUserService;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */

@SuppressWarnings("serial")
@Component("sessionBean")
@ViewScoped
public class SessionBean implements Serializable{
	
	/**
	 * LOGGER log4J
	 * @see org.apache.log4j.Logger
	 * */
   
    
	@Inject
	private ISessionService serviceSession;
	@Inject
	private ISiteService serviceSite;

	
	private Long idSession;
	
	@NotNull(message="La Formation est Obligatoire")
	private Long idSpecialite;
	
	@NotNull(message="Le Site est Obligatoire")
	private Long idSite;
	
	@NotNull(message="La Salle est Obligatoire")
	private Long idSalle;
	
	@NotNull(message="Date de Début est Obligatoire")
	private Date dateDebute;
	
	@NotNull(message="Date de Fin est Obligatoire")
	private Date dateFin;
	
	private Long nombreJours;


	private List<Object[]> sessions;
	private List<SessionEtudiant> sessionsInProgress;
	private String succes;
	private String addSessionException;
	private SessionEtudiant sessionEtudiant;
	private Date currentTime;
	private List<Site> sites;
	private List<Object[]> salles;
	private String choix;
	private List<Evenement> evenements;

    /**@method add session*/
	public void addSession() throws ParseException {
		sessionEtudiant = createSession();
		try {
			serviceSession.addSessionStudent(sessionEtudiant, idSpecialite,idSite,idSalle);
			getAllSessions();
			Utilitaire.displayMessageInfo(
					"La Prochaine Session à bien été enregistrer avec succès ");
			reset();
		} catch (AddSessionException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}

	}

	/**
	 * @create New Session
	 **@return Object Session 
	 **@factory.create.method
	 */
	private SessionEtudiant createSession() {
		sessionEtudiant = FactoryBean.getSessionFactory().create("SessionEtudiant");
		sessionEtudiant.setDateDebute(dateDebute);
		sessionEtudiant.setDateFin(dateFin);
		sessionEtudiant.setNombreJours((dateFin.getTime()/(24 * 60 * 60 * 1000)) - (dateDebute.getTime()/(24 * 60 * 60 * 1000)));
		return sessionEtudiant;
	}
	
	/*vider les champs aprés l'insertion de chaque session*/
	public void reset(){
		dateDebute=null;
		dateFin=null;
		idSpecialite=null;
		idSite=null;
		idSalle=null;
		setSalles(null);
	}
	public void getAllSites(){
		sites = serviceSite.getAll();
	}
	public void getAllSallesDisponible(){
		List<Object[]> salles = serviceSession.getSallesDisponible(idSalle);
		Date today = new Date();
		for(Object[] o:salles){
			Long idsession = (Long)o[0];
			Date debut = (Date) o[1];
			Date fin   = (Date) o[2];
			String nomSalle = (String) o[3];
			Long finSession = fin.getTime()/(24 * 60 * 60 * 1000);
			Long debutSession = debut.getTime()/(24 * 60 * 60 * 1000);
			Long resteJoursSession = finSession-(today.getTime()/(24 * 60 * 60 * 1000));
			System.out.println(resteJoursSession);
			if(fin.after(today)){
				Utilitaire.displayMessageWarning("La salle "+nomSalle+" est Ocupée par la session numéro "+idsession+" il reste "+resteJoursSession+" Jours");
			}else{
	            Utilitaire.displayMessageInfo("La salle "+nomSalle+" est Disponible");
			}
		}
	}
	public void getAllSallesBySite(){
		try {
			salles = serviceSite.getSallesBySite(idSite);
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			salles = null;
		}
	}
	
	public String resetAndRedirect(){
		reset();
		getAllSites();
		return "session_etudiant?faces-redirect=true";
	}
	
	public String colorRow(long time){
		if(time < currentTime.getTime()){
			return "red";
		}else{
			return null;
		}
	}
	
	
	/*@method get CurrentSession*/
	public SessionEtudiant getCurrentSession(Long idSession){
		System.out.println("idSession"+idSession);
		return sessionEtudiant = serviceSession.getSessionEtudiantById(idSession);
	}
	/**@method cette methode permet de recupérer les informations liées à une session et faire une redirection*/
	public String getCureentSessionAndRedirect(Long idSession){
		getCurrentSession(idSession);
		evenements = null;
		choix      = null;
		return "informationSession?faces-redirect=true";
	}
	
	public void getMoreInformation(Long idSession,String choix){
		try {
			evenements = serviceSession.getMoreInformationBySession(idSession,choix);
			LoggerConfig.logInfo("teamLeader"+choix);
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			evenements = null;
		}
	}
	
	/**@method update SessionEtudiant*/
	public String edit(SessionEtudiant sessionEtudiant,Long idSpecialite,Long idSite,Long idSalle){
	
		try {
			serviceSession.updateSessionEtudiant(sessionEtudiant,idSpecialite,idSite,idSalle);
		} catch (AddSessionException e) {
		    Utilitaire.displayMessageWarning(e.getMessage());
			return null;
		}
	    return "session_etudiant?faces-redirect=true";
	}
	
    /*init in load Page*/
	public void getAllSessions() throws ParseException {
		sessions = serviceSession.getAllSessionsV2();
		currentTime= new Date();
	}
	
	/**@filling all villes and départements*/
	public List<String> getAllVilles(String query){
		List<String> villes = Arrays.asList(DataUtil.fillingVilles(query));
		List<String> villesFiltred = Utilitaire.filterObject(query, villes);
		return villesFiltred;
	}

	public Long getIdSession() {
		return idSession;
	}

	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	public Date getDateDebute() {
		return dateDebute;
	}

	public void setDateDebute(Date dateDebute) {
		this.dateDebute = dateDebute;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public SessionEtudiant getSessionEtudiant() {
		return sessionEtudiant;
	}

	public void setSessionEtudiant(SessionEtudiant sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}

	public Long getIdSpecialite() {
		return idSpecialite;
	}

	public void setIdSpecialite(Long idSpecialite) {
		this.idSpecialite = idSpecialite;
	}

	public String getSucces() {
		return succes;
	}

	public void setSucces(String succes) {
		this.succes = succes;
	}

	public String getAddSessionException() {
		return addSessionException;
	}

	public void setAddSessionException(String addSessionException) {
		this.addSessionException = addSessionException;
	}



	/**
	 * @return the sessions
	 */
	public List<Object[]> getSessions() {
		return sessions;
	}

	/**
	 * @param sessions the sessions to set
	 */
	public void setSessions(List<Object[]> sessions) {
		this.sessions = sessions;
	}

	public List<SessionEtudiant> getSessionsInProgress() {
		return sessionsInProgress;
	}

	public void setSessionsInProgress(List<SessionEtudiant> sessionsInProgress) {
		this.sessionsInProgress = sessionsInProgress;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * @return the idSite
	 */
	public Long getIdSite() {
		return idSite;
	}

	/**
	 * @param idSite the idSite to set
	 */
	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}

	/**
	 * @return the idSalle
	 */
	public Long getIdSalle() {
		return idSalle;
	}

	/**
	 * @param idSalle the idSalle to set
	 */
	public void setIdSalle(Long idSalle) {
		this.idSalle = idSalle;
	}

	/**
	 * @return the sites
	 */
	public List<Site> getSites() {
		return sites;
	}

	/**
	 * @param sites the sites to set
	 */
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	/**
	 * @return the salles
	 */
	public List<Object[]> getSalles() {
		return salles;
	}

	/**
	 * @param salles the salles to set
	 */
	public void setSalles(List<Object[]> salles) {
		this.salles = salles;
	}

	public Long getNombreJours() {
		return nombreJours;
	}

	public void setNombreJours(Long nombreJours) {
		this.nombreJours = nombreJours;
	}


	public List<Evenement> getEvenements() {
		return evenements;
	}

	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	



}
