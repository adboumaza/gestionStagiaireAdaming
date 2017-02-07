package com.adaming.myapp.etudiant.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
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
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.addStudent
	**/
	public Etudiant addStudentAbstractJpa(Etudiant e, Long idSession) {

		SessionEtudiant s = entityManager.find(SessionEtudiant.class, idSession);
		e.setSessionEtudiant(s);
		s.getEtudiants().add(e);
		entityManager.persist(e);
		LoggerConfig.logInfo("l'etudiant " + e.getIdEtudiant() + " a bien �t� ajouter");
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
		LoggerConfig.logInfo("l'etudiant " + e.getIdEtudiant() + " a bien �t� modifie");
		return e;
	}
    
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.removeStudent
	**/
	public Etudiant removeStudentAbstractJpa(Long idStudent) {
		Etudiant e = entityManager.find(Etudiant.class, idStudent);
		entityManager.remove(e);
		LoggerConfig.logInfo("l'etudiant " + e.getIdEtudiant() + " a bien �t� supprime");
		return e;
	}
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentById
	**/
	public Etudiant getStudentByIdAbstractJpa(Long idStudent) {
		final String SQL = "select distinct e from Etudiant e " +
                "join fetch e.sessionEtudiant " +
                "where e.idEtudiant = :x";

		 return (Etudiant) entityManager.createQuery(SQL)
		       .setParameter("x", idStudent)
		       .getSingleResult();
	}
	
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @throws VerificationInDataBaseException 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiantBySession
	**/
	@SuppressWarnings("unchecked")
	public List<Object[]> getEtudiantBySessionAbstractJpa(Long idSession) {

		final String SQL = "Select e.idEtudiant,e.nomEtudiant,e.prenomEtudiant,e.dateDeNaissance,e.formationInitial,e.ecole,e.dateObtention,e.adresse.adresse,e.adresse.codePostal,e.numTel,e.mail,se.idSession,se.dateDebute,se.dateFin,e.adresse.ville,e.adresse.pays FROM Etudiant e join e.sessionEtudiant se where se.idSession=:x";
        Query query = entityManager.createQuery(SQL);
        query.setParameter("x",idSession);
        List<Object[]> objects = query.getResultList();
		return objects;
	}
	
	@SuppressWarnings("unchecked")
	public List<Etudiant> getStudentsBySessionAbstractJpa(Long idSession){
		
		final String SQL = "From Etudiant e join fetch e.sessionEtudiant se where se.idSession =:x";
		Query query = entityManager.createQuery(SQL).setParameter("x",idSession);
		return query.getResultList();
	}

	
	
	
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiant
	**/

	public Etudiant getEtudiantAbstractJpa(String mail) {
		final String SQL = "select distinct e From Etudiant e where e.mail=:x";
		Query query = entityManager
				.createQuery(SQL).setParameter("x", mail);

		Etudiant etudiant = null;
		if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 etudiant = (Etudiant) query.getResultList().get(0);
		 }
		return etudiant;
	}
    
	public Etudiant verifyExistingEtudiantAbstractJpa(String name, Date dateDeNaissance) {
		final String SQL = "select distinct e From Etudiant e where e.nomEtudiant=:x and e.dateDeNaissance =:y";
		Query query = entityManager
				.createQuery(SQL).setParameter("x", name).setParameter("y",dateDeNaissance);

		Etudiant etudiant = null;
		if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 etudiant = (Etudiant) query.getResultList().get(0);
		 }
		return etudiant;
	}
}
