package com.adaming.myapp.evenement.dao;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;

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
	public List<Evenement> getAllEvenementsAbstractJpa() {
		Query query = em
				.createQuery("from Evenement e ORDER BY e.idEvenement DESC");
		LoggerConfig.logInfo("il existe :" + query.getResultList().size()
				+ "evenements dans la base de données");
		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Evenement> getAllEvenementsBySessionAbstractJpa(Long idSession) {
		Query query = em
				.createQuery("from Evenement e join fetch e.etudiant et join fetch e.sessionEtudiant se where se.idSession=:x ORDER BY e.idEvenement DESC");
		query.setParameter("x", idSession);
		LoggerConfig.logInfo("il existe :" + query.getResultList().size()
				+ "evenements dans la session");
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
	

	public long getNumberOfRetardsAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT count(e.idEvenement)"
				         + " FROM Evenement e"
				         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "RETARD");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		long count =(Long) query.getSingleResult();
		LoggerConfig.logInfo("les retards d'aujourdhuit sont :"
				+count);
		return count;
	}

	
	public long getNumberOfAbsenceAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT count(e.idEvenement)"
				         + " FROM Evenement e"
				         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "ABSENCE");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		long count =(Long) query.getSingleResult();
		LoggerConfig.logInfo("les absences d'aujourdhuit sont :"
				+count);
		return count;
	}

	
	public long getNumberOfWarningAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT count(e.idEvenement)"
				         + " FROM Evenement e"
				         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "WARNING");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		long count =(Long) query.getSingleResult();
		LoggerConfig.logInfo("les warning d'aujourdhuit sont :"
				+count);
		return count;
	}

	
	public long getNumberOfOfTopAbstractJpa() {
		Date tomorrow    = DateUtils.addDays(new Date(), +1);
		Date yesterday   = DateUtils.addDays(new Date(), -1);
		final String SQL = "SELECT count(e.idEvenement)"
				         + " FROM Evenement e"
				         + " where TYPE_EVENEMENT=:x and e.curentDate BETWEEN :y AND :t ORDER BY e.idEvenement DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", "TOP");
		query.setParameter("y", yesterday, TemporalType.DATE);
		query.setParameter("t", tomorrow, TemporalType.DATE);
		long count =(Long) query.getSingleResult();
		LoggerConfig.logInfo("les TOPS d'aujourdhuit sont :"
				+count);
		return count;
	}

	
	public Evenement verifyExistingEventAbstractJpa(final Long idEtudiant){
		final String SQL = "Select distinct e from Evenement e join e.etudiant et where TYPE_EVENEMENT =:x or TYPE_EVENEMENT =:y and et.idEtudiant =:z";
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
	

	
}
