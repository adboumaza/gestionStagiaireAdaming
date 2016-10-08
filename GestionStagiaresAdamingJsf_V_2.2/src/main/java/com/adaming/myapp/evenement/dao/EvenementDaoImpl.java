package com.adaming.myapp.evenement.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.time.DateUtils;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.EvenementNotFoundException;

public class EvenementDaoImpl implements IEvenementDao{
   
	@PersistenceContext
	private EntityManager em;
	
	Logger logger = Logger.getLogger("EvenementDaoImpl");

	@Override
	public Evenement addEvenement(Evenement e, Long idSession, Long idEtudiant) {
		SessionEtudiant se = em.find(SessionEtudiant.class,idSession);
		Etudiant e1 = em.find(Etudiant.class,idEtudiant);
	    e.setEtudiant(e1);
	    e.setSessionEtudiant(se);
		em.persist(e);
		logger.info("l'entretien a bien �t� enregistrer "+"Session id : "+idSession+"etudiant :"+idEtudiant);
		return e;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Evenement> getEvenementsRetards() throws EvenementNotFoundException {
		Date weeckAgo = DateUtils.addDays(new Date(), -6);
		Query query = em.createQuery("from Evenement e where TYPE_EVENEMENT =:x and e.curentDate >= :y");
		query.setParameter("y",weeckAgo);
		logger.info("weeck"+weeckAgo);
		query.setParameter("x","RETARD");
		logger.info("le size de retarad est"+query.getResultList().size());
		if(query.getResultList().size() == 0){
			throw new EvenementNotFoundException("Aucun retard mentionn� !");
		}
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Evenement> getEvenementsAbsences() throws EvenementNotFoundException {
		Date weeckAgo = DateUtils.addDays(new Date(), -6);
		Query query = em.createQuery("from Evenement e where TYPE_EVENEMENT =:x and e.curentDate >= :y");
		query.setParameter("y",weeckAgo);
		logger.info("weeck"+weeckAgo);
		query.setParameter("x","ABSENCE");
		logger.info("le size de Absences est"+query.getResultList().size());
		if(query.getResultList().size() == 0){
			throw new EvenementNotFoundException("Aucune absence mentionn�e !");
		}
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Evenement> getEvenementsEntretien() throws EvenementNotFoundException {
		Date weeckAgo = DateUtils.addDays(new Date(), -6);
		Query query = em.createQuery("from Evenement e where TYPE_EVENEMENT=:x and e.curentDate >= :y");
		query.setParameter("y",weeckAgo);
		logger.info("weeck"+weeckAgo);
		query.setParameter("x","ENTRETIENT");
		logger.info("le size de ENTRETIENT est"+query.getResultList().size());
		if(query.getResultList().size() == 0){
			throw new EvenementNotFoundException("Aucun entretien mentionn� !");
		}
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Evenement> getNumberOfCurrentsRetards() {
		Query query = em.createQuery("from Evenement e where TYPE_EVENEMENT=:x and e.curentDate =CURRENT_DATE");
		query.setParameter("x","RETARD");
		logger.info("les retards d'aujourdhuit sont :"+query.getResultList().size());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Evenement> getNumberOfCurrentsAbsence() {
		Query query = em.createQuery("from Evenement e where TYPE_EVENEMENT=:x and e.curentDate =CURRENT_DATE");
		query.setParameter("x","ABSENCE");
		logger.info("les absences d'aujourdhuit sont :"+query.getResultList().size());
		return query.getResultList();
	}

	
	
}
