package com.adaming.myapp.formateur.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public class FormateurDaoImpl implements IFormateurDao {

	@PersistenceContext
	private EntityManager em;

	private Logger logger = Logger.getLogger("FormateurDaoImpl");

	@Override
	public Formateur addFormateur(Formateur f)
			throws VerificationInDataBaseException {
		
		em.persist(f);
		logger.info("le formateur " + f.getNom()
				+ "� bien �t� cri�e avec success");
		return f;
	}

	@Override
	public void addFormateurToSession(Long idSession, Long idFormateur) {
		SessionEtudiant session = em.find(SessionEtudiant.class, idSession);
		Formateur f = em.find(Formateur.class, idFormateur);
		session.getFormateurs().add(f);
		f.getSessionsEtudiant().add(session);
		logger.info("le formateur " + f.getNom()
				+ " a bien �t� attribu� � la session " + session.getIdSession());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Formateur> getAllFormateurs() {
		Query query = em.createQuery("from Formateur");
		logger.info("il existe " + query.getResultList().size() + " Formateurs");
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Formateur getFormateur(String mail) {

		Query query = em.createQuery("From Formateur f where f.mail=:x");
		query.setParameter("x", mail);
		List<Formateur> u = query.getResultList();

		return u.get(0);
	}

	@Override
	public Formateur getFormateurById(Long idFormateur) {
		Formateur f = em.find(Formateur.class,idFormateur);
		return f;
	}

}
