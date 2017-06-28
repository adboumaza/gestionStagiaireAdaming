package com.adaming.myapp.evenement.dao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;




import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;

public abstract class EvenementAbstractJpa {
	
	@PersistenceContext
	private EntityManager em;

	public Evenement addEvenementAbstractJpa(final Evenement e, final Long idSession, final Long idEtudiant)
			{
		SessionEtudiant se = em.find(SessionEtudiant.class, idSession);
		Etudiant e1 = em.find(Etudiant.class, idEtudiant);
		e.setEtudiant(e1);
		e.setSessionEtudiant(se);
		em.persist(e);
		LoggerConfig.logInfo("l'evenement a bien été enregistrer " + "Session id : "
				+ idSession + "etudiant :" + idEtudiant);
		return e;
	}

	
	public Evenement addWarningAndTopAbstractJpa(final Evenement e, final Long idSession,
			final Long idEtudiant) {
		SessionEtudiant se = em.find(SessionEtudiant.class, idSession);
		Etudiant e1 = em.find(Etudiant.class, idEtudiant);
		e.setEtudiant(e1);
		e.setSessionEtudiant(se);
		em.persist(e);
		LoggerConfig.logInfo("l'evenement a bien été enregistrer " + "Session id : "
				+ idSession + "etudiant :" + idEtudiant);
		return e;
	}

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getEvenementsRetardsAbstractJpa()
			 {
		Date weeckAgo = DateUtils.addDays(new Date(), -6);
		Query query = em
				.createQuery("Select e.idEvenement,e.startDate,e.endDate,e.signaleur,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession,e.curentDate "
						   + "from Evenement e join e.sessionEtudiant se join e.etudiant et where TYPE_EVENEMENT =:x and e.curentDate >= :y ORDER BY e.idEvenement DESC");
		query.setParameter("y", weeckAgo);
		LoggerConfig.logInfo("weeck" + weeckAgo);
		query.setParameter("x", "RETARD");
		LoggerConfig.logInfo("le size de retarad est" + query.getResultList().size());
		
		return query.getResultList();
	}


	
	@SuppressWarnings("unchecked")
	public List<Object[]> getEvenementsAbsencesAbstractJpa()
			 {
		Date weeckAgo = DateUtils.addDays(new Date(), -6);
		Query query = em
				.createQuery("Select e.idEvenement,e.startDate,e.endDate,e.signaleur,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession,e.curentDate "
						    + "from Evenement e join e.sessionEtudiant se join e.etudiant et where TYPE_EVENEMENT =:x and e.curentDate >= :y ORDER BY e.idEvenement DESC");
		query.setParameter("y", weeckAgo);
		LoggerConfig.logInfo("weeck" + weeckAgo);
		query.setParameter("x", "ABSENCE");
		LoggerConfig.logInfo("le size de Absences est" + query.getResultList().size());
		
		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> getEvenementsEntretienAbstractJpa()
			 {
		Date weeckAgo = DateUtils.addDays(new Date(), -6);
		Query query = em
				.createQuery("Select e.idEvenement,e.startDate,e.endDate,e.signaleur,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession,e.curentDate "
						+ "from Evenement e join e.sessionEtudiant se join e.etudiant et where TYPE_EVENEMENT=:x and e.curentDate >= :y ORDER BY e.idEvenement DESC");
		query.setParameter("y", weeckAgo);
		LoggerConfig.logInfo("weeck" + weeckAgo);
		query.setParameter("x", "ENTRETIENT");
		LoggerConfig.logInfo("le size de ENTRETIENT est" + query.getResultList().size());
		
		return query.getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<Object[]> getDailyCountOfRetardsAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT e.idEvenement,e.startDate,e.endDate,e.signaleur,e.curentDate,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession"
				         + " FROM Evenement e join e.etudiant et join e.sessionEtudiant se"
				         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "RETARD");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		LoggerConfig.logInfo("les retards d'aujourdhuit sont :"
				+query.getResultList().size());
		return query.getResultList();
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDailyCountOfAbsenceAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT e.idEvenement,e.startDate,e.endDate,e.signaleur,e.curentDate,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession"
		         + " FROM Evenement e join e.etudiant et join e.sessionEtudiant se"
		         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "ABSENCE");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		LoggerConfig.logInfo("les absences d'aujourdhuit sont :"
				+query.getResultList().size());
		
		return query.getResultList();
	}

	

	@SuppressWarnings("unchecked")
	public List<Object[]> getDailyCountOfWarningAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT e.idEvenement,e.startDate,e.endDate,e.signaleur,e.curentDate,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession"
		         + " FROM Evenement e join e.etudiant et join e.sessionEtudiant se"
		         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "WARNING");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		
		LoggerConfig.logInfo("les warnings d'aujourdhuit sont :"
				+ query.getResultList().size());
		return query.getResultList();
	}

	

	@SuppressWarnings("unchecked")
	public List<Object[]> getDailyCountOfTopAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT e.idEvenement,e.startDate,e.endDate,e.signaleur,e.curentDate,et.idEtudiant,et.nomEtudiant,et.prenomEtudiant,se.idSession"
		         + " FROM Evenement e join e.etudiant et join e.sessionEtudiant se"
		         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "TOP");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		LoggerConfig.logInfo("les warnings d'aujourdhuit sont :"
				+ query.getResultList().size());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getEventsExisteAbstractJpa(final Long idEtudiant) {
		Query query = em.createQuery("Select e.startDate,e.endDate,e.etudiant.idEtudiant"
				+ " from Evenement e join e.etudiant et "
				+ " where et.idEtudiant =:x order by e.idEvenement desc");
		query.setParameter("x",idEtudiant);
		//query.setMaxResults(1);
		return query.getResultList();
	}
	
	
	public Evenement verifyExistingEventAbstractJpa(final Long idEtudiant){
		final String SQL = "select e from Evenement e join e.etudiant et where (TYPE_EVENEMENT =:x or TYPE_EVENEMENT =:y) and et.idEtudiant =:z";
		Evenement event = null;
		Query query = em.createQuery(SQL).setParameter("x","WARNING").setParameter("y","TOP").setParameter("z",idEtudiant);
		if(query.getResultList().size() > 0 && query.getResultList() != null){
			event = (Evenement) query.getResultList().get(0);
		}
		return event;
	}
	
	public void deleteEvenementAbstractJpa(final Long idEvenement){
		Evenement evnement = em.find(Evenement.class, idEvenement);
		em.remove(evnement);
	}
	
	public Evenement updateEvenementAbstractJpa(final Evenement evenement,final Long idEtudiant,final Long idSession){
		Etudiant etudiant = em.find(Etudiant.class, idEtudiant);
		SessionEtudiant sessionEtudiant = em.find(SessionEtudiant.class, idSession);
		evenement.setSessionEtudiant(sessionEtudiant);
		evenement.setEtudiant(etudiant);
		em.merge(evenement);
		return evenement;
	}
	
	public Object [] getEventByStudentBetweenTwoDatesAbstractJpa(Long idSession,String nomEtudiant,DateTime date){

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		String dtStr = fmt.print(date);
		
		final String SQL = "SELECT e.nomEtudiant,ev.startDate, ev.endDate,ev.TYPE_EVENEMENT " + 
        "FROM evenement ev join etudiant e on ev.ID_EVE_ETUDIANT = e.idEtudiant " + 
		"join sessionEtudiant se on se.idSession = ev.ID_EVE_SESSION " + 
		"where se.idSession =:id and e.nomEtudiant =:nom and (:date  >= cast(ev.startDate as date) and :date <= cast(ev.endDate as date) ) "
		+"and (TYPE_EVENEMENT =:r or TYPE_EVENEMENT =:a or TYPE_EVENEMENT =:e)" ;

		Query query = em.createNativeQuery(SQL).
				setParameter("id",idSession).
				setParameter("nom", nomEtudiant).
				setParameter("date",dtStr).
				setParameter("a", "ABSENCE").
				setParameter("r", "RETARD").
				setParameter("e", "ENTRETIENT");
		Object [] event= null;
		if(query.getResultList().size() > 0 && query.getResultList() != null){
			event = (Object[]) query.getResultList().get(0);
		}
		return event;
		
	}
	

	
}
