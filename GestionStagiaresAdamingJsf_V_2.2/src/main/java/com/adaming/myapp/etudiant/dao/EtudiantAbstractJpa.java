package com.adaming.myapp.etudiant.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
/**
 *  @author Adel 
 *  @version 1.0.0
 *  @date 11/10/2016
 *  @param e l'entit� etudiant 
 *  @param idSession le type de la relation � associ�e
 *  @param idStudent l'identifiant de l'entit� etudiant
 *  @param mail le mail de l'etudiant
 *  @exception AddEtudiantException v�rification dans la base de donn�e
 ***/
public abstract class EtudiantAbstractJpa {
    
	
	/**
	 * @see javax.persistence.EntityManager
	 **/
	@PersistenceContext
	private EntityManager entityManager;
    
	
	
	/**
     * Logger @see java.util.logging.Logger
     **/
	final Logger LOGGER = Logger.getLogger("EtudiantAbstractJpa");
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.addStudent
	**/
	public Etudiant addStudentAbstractJpa(Etudiant e, Long idSession) {

		SessionEtudiant s = entityManager.find(SessionEtudiant.class, idSession);
		e.setSessionEtudiant(s);
		s.getEtudiants().add(e);
		entityManager.persist(e);
		LOGGER.info("l'etudiant " + e.getIdEtudiant() + " a bien �t� ajouter");
		return e;
	}
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.updateStudent
	**/
	public Etudiant updateStudentAbstractJpa(Etudiant e, Long idSession) {
		SessionEtudiant s = entityManager.find(SessionEtudiant.class, idSession);
		e.setSessionEtudiant(s);
		entityManager.merge(e);
		LOGGER.info("l'etudiant " + e.getIdEtudiant() + " a bien �t� modifie");
		return e;
	}
    
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.removeStudent
	**/
	public Etudiant removeStudentAbstractJpa(Long idStudent) {
		Etudiant e = entityManager.find(Etudiant.class, idStudent);
		entityManager.remove(e);
		LOGGER.info("l'etudiant " + e.getIdEtudiant() + " a bien �t� supprime");
		return e;
	}
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentById
	**/
	public Etudiant getStudentByIdAbstractJpa(Long idStudent) {
		Etudiant e = entityManager.find(Etudiant.class, idStudent);
		LOGGER.info("l'etudiant " + e.getIdEtudiant() + " a bien �t� recuperer");
		return e;
	}
	
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @throws VerificationInDataBaseException 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiantBySession
	**/
	public List<Etudiant> getEtudiantBySessionAbstractJpa(Long idSession) throws VerificationInDataBaseException {
		List<Etudiant> etudiants = null;
		SessionEtudiant s = entityManager.find(SessionEtudiant.class, idSession);
		if(s!=null){
			etudiants = s.getEtudiants();
			if(etudiants.size() ==0){
				throw new VerificationInDataBaseException("Il n'existe aucun �tudiant dans la session Num�ro "+idSession);
			}
			LOGGER.info("le nombre des etudiants dans la session N "
					+ s.getIdSession() + "est " + etudiants.size());
		}
		return etudiants;
	}
	
	public List<Etudiant> getStudentsBySessionAbstractJpa(Long idSession){
		List<Etudiant> etudiants = null;
		SessionEtudiant s = entityManager.find(SessionEtudiant.class, idSession);
		if(s!=null){
			etudiants = s.getEtudiants();
			LOGGER.info("le nombre des etudiants dans la session N "
					+ s.getIdSession() + "est " + etudiants.size());
		}
		return etudiants;
	}

	
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiant
	**/
    @SuppressWarnings("unchecked")
	public Etudiant getEtudiantAbstractJpa(String mail) {
		Query query = entityManager
				.createQuery("From Etudiant e where e.mail=:x");
		query.setParameter("x", mail);
		List<Etudiant> u = query.getResultList();
		return u.get(0);
	}
}
