package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Note;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.evenement.service.IEvenementService;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.notes.service.INotesService;
import com.adaming.myapp.session.service.ISessionService;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Component("dashboardBean")
@Scope("session")
public class DashboardBean implements Serializable {

	/**
	 * LOGGER LOG4j
	 * 
	 * @see org.apache.log4j.Logger
	 */
	

	@Inject
	private IEvenementService serviceEvenement;
	@Inject
	private ISessionService serviceSession;

	@Inject
	private IEtudiantService serviceEtudiant;

	private Etudiant etudiant;
	private List<Object[]> retards;
	private List<Object[]> absences;
	private List<Object[]> entretiens;
	private List<Object[]> sessionsInProgress;

	private String retardNotFoundException;
	private String absenceNotFoundException;
	private String entretienNotFoundException;

	/* header */
	private long countRetards;
	private long countAbsence;
	private long countTop;
	private long countWarning;
	private List<Object[]> retardsJournaliere;
	private List<Object[]> absenceJournaliere;
	private List<Object[]> topJournaliere;
	private List<Object[]> warningJournaliere;
	private List<Object[]> retardsJournaliereLimited;
	private List<Object[]> absenceJournaliereLimited;
	private List<Object[]> topJournaliereLimited;
	private List<Object[]> warningJournaliereLimited;
	private Date currentTime;

	public void init() {

        retardNotFoundException = new String();
		absenceNotFoundException = new String();
		entretienNotFoundException = new String();
		/** getSessionsInProgress */
		getSessionEnCours();
		/** getWeecklyEvenements */
		try {
			retards = serviceEvenement.getEvenementsRetards();
			setRetardNotFoundException("");
		} catch (EvenementNotFoundException e) {
			setRetardNotFoundException(e.getMessage());
			setRetards(null);
		}
		try {
			absences = serviceEvenement.getEvenementsAbsences();
			setAbsenceNotFoundException("");
		} catch (EvenementNotFoundException e) {
			setAbsenceNotFoundException(e.getMessage());
			setAbsences(null);
		}
		try {
			entretiens = serviceEvenement.getEvenementsEntretien();
			setEntretienNotFoundException("");
		} catch (EvenementNotFoundException e) {
			setEntretienNotFoundException(e.getMessage());
			setEntretiens(null);
		}

	}
	
	

	/** @methode remplire la session en cours */
	public void sessionEnCours() {
		sessionsInProgress = serviceSession.getAllSessionsInProgressV2();
	}

	/** get etudiant by id */
	public String getStudentById(Long idStudent) {
		etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
		etudiant = serviceEtudiant.getStudentById(idStudent);
		return "informtaionEtudiant?redirect=true";
	}

	/** @ method pour avoir les jours d'une sessions (progress bar in css) */
	public void getSessionEnCours() {
		
		sessionEnCours();
		/* parcourir chaque session en cours */

			final Date CURRENT_DATE = new Date();
			for (Object[] s : sessionsInProgress) {
				Date dateDebutSession = (Date) s[2];
				Date dateFinSession = (Date) s[3];
				// Long nommbreDeJours = (Long)s[6];
				final long DATE_START_EN_DAY = dateDebutSession.getTime()
						/ (24 * 60 * 60 * 1000);
				final long DATE_END_EN_DAY = dateFinSession.getTime()
						/ (24 * 60 * 60 * 1000);

				// la date du jour
				final long currentDate = CURRENT_DATE.getTime()
						/ (24 * 60 * 60 * 1000);

				// nombre de jours de la formation
				final long DIFFERENCE_DATE = DATE_END_EN_DAY - DATE_START_EN_DAY;
				String dayFin = Long.toString(DIFFERENCE_DATE);
				s[5] = dayFin;

				// nombre de jours entre le début et le jour courant
				final long differenceTwo = currentDate - DATE_START_EN_DAY;
				String differenceTwoStr = Long.toString(differenceTwo);
				s[6] = differenceTwoStr;
		}
		
	}
	
	public void initHeader(){
		currentTime = new Date();
		retardsHeader();
		absenceHeader();
		topHeader();
		warningHeader();
	}

	public void retardsHeader() {
       retardsJournaliere = serviceEvenement.getDailyCountOfRetards();
       countRetards = retardsJournaliere.size();
       retardsJournaliereLimited = new ArrayList<Object[]>();
       if(countRetards >  4){
    	   retardsJournaliereLimited = retardsJournaliere.subList(0,4);
       }else{
    	   retardsJournaliereLimited.addAll(retardsJournaliere);
       }
	}

	public void absenceHeader() {
      absenceJournaliere = serviceEvenement.getDailyCountOfAbsence();
	  countAbsence = absenceJournaliere.size();
	  absenceJournaliereLimited = new ArrayList<Object[]>();
      if(countAbsence >  4){
    	  absenceJournaliereLimited = absenceJournaliere.subList(0,4);
      }else{
    	  absenceJournaliereLimited.addAll(absenceJournaliere);
      }
	}

	public void topHeader() {
     topJournaliere = serviceEvenement.getDailyCountOfTop();
	 countTop = topJournaliere.size();
	 topJournaliereLimited = new ArrayList<Object[]>();
     if(countTop >  4){
   	    topJournaliereLimited = topJournaliere.subList(0,4);
     }else{
   	    topJournaliereLimited.addAll(topJournaliere);
     }
	}

	public void warningHeader() {
     warningJournaliere = serviceEvenement.getDailyCountOfWarning();
	 countWarning = warningJournaliere.size();
	 warningJournaliereLimited = new ArrayList<Object[]>();
	     if(countWarning >  4){
	    	 warningJournaliereLimited = warningJournaliere.subList(0,4);
	     }else{
	    	 warningJournaliereLimited.addAll(warningJournaliere);
	     }
	}



	public List<Object[]> getRetards() {
		return retards;
	}

	public void setRetards(List<Object[]> retards) {
		this.retards = retards;
	}

	public List<Object[]> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Object[]> absences) {
		this.absences = absences;
	}

	public List<Object[]> getEntretiens() {
		return entretiens;
	}

	public void setEntretiens(List<Object[]> entretiens) {
		this.entretiens = entretiens;
	}

	public List<Object[]> getSessionsInProgress() {
		return sessionsInProgress;
	}

	public void setSessionsInProgress(List<Object[]> sessionsInProgress) {
		this.sessionsInProgress = sessionsInProgress;
	}

	public String getRetardNotFoundException() {
		return retardNotFoundException;
	}

	public void setRetardNotFoundException(String retardNotFoundException) {
		this.retardNotFoundException = retardNotFoundException;
	}

	public String getAbsenceNotFoundException() {
		return absenceNotFoundException;
	}

	public void setAbsenceNotFoundException(String absenceNotFoundException) {
		this.absenceNotFoundException = absenceNotFoundException;
	}

	public String getEntretienNotFoundException() {
		return entretienNotFoundException;
	}

	public void setEntretienNotFoundException(String entretienNotFoundException) {
		this.entretienNotFoundException = entretienNotFoundException;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public long getCountRetards() {
		return countRetards;
	}

	public void setCountRetards(long countRetards) {
		this.countRetards = countRetards;
	}

	public long getCountAbsence() {
		return countAbsence;
	}

	public void setCountAbsence(long countAbsence) {
		this.countAbsence = countAbsence;
	}

	public long getCountTop() {
		return countTop;
	}

	public void setCountTop(long countTop) {
		this.countTop = countTop;
	}

	public long getCountWarning() {
		return countWarning;
	}

	public void setCountWarning(long countWarning) {
		this.countWarning = countWarning;
	}

	public List<Object[]> getRetardsJournaliere() {
		return retardsJournaliere;
	}

	public void setRetardsJournaliere(List<Object[]> retardsJournaliere) {
		this.retardsJournaliere = retardsJournaliere;
	}

	public List<Object[]> getAbsenceJournaliere() {
		return absenceJournaliere;
	}

	public void setAbsenceJournaliere(List<Object[]> absenceJournaliere) {
		this.absenceJournaliere = absenceJournaliere;
	}

	public List<Object[]> getTopJournaliere() {
		return topJournaliere;
	}

	public void setTopJournaliere(List<Object[]> topJournaliere) {
		this.topJournaliere = topJournaliere;
	}

	public List<Object[]> getWarningJournaliere() {
		return warningJournaliere;
	}

	public void setWarningJournaliere(List<Object[]> warningJournaliere) {
		this.warningJournaliere = warningJournaliere;
	}

	public List<Object[]> getRetardsJournaliereLimited() {
		return retardsJournaliereLimited;
	}

	public void setRetardsJournaliereLimited(
			List<Object[]> retardsJournaliereLimited) {
		this.retardsJournaliereLimited = retardsJournaliereLimited;
	}

	public List<Object[]> getAbsenceJournaliereLimited() {
		return absenceJournaliereLimited;
	}

	public void setAbsenceJournaliereLimited(
			List<Object[]> absenceJournaliereLimited) {
		this.absenceJournaliereLimited = absenceJournaliereLimited;
	}

	public List<Object[]> getTopJournaliereLimited() {
		return topJournaliereLimited;
	}

	public void setTopJournaliereLimited(List<Object[]> topJournaliereLimited) {
		this.topJournaliereLimited = topJournaliereLimited;
	}

	public List<Object[]> getWarningJournaliereLimited() {
		return warningJournaliereLimited;
	}

	public void setWarningJournaliereLimited(
			List<Object[]> warningJournaliereLimited) {
		this.warningJournaliereLimited = warningJournaliereLimited;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public Date getCurrentTime() {
		return currentTime;
	}



	
	

}
