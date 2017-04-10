package com.adaming.myapp.etudiant.service;

import java.util.Date;
import java.util.List;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;

/**
 * @author Adel
 * @version 1.0.0
 * @date 11/10/2016
 * @param e
 *            l'entité etudiant
 * @param idSession
 *            le type de la relation à associée
 * @param idStudent
 *            l'identifiant de l'entité etudiant
 * @param mail
 *            le mail de l'etudiant
 * @exception AddEtudiantException
 *                vérification dans la base de donnée
 * */
public interface IEtudiantService {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws VerificationInDataBaseException
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.addStudent
	 **/
	Etudiant addStudent(final Etudiant e, final Long idSession,final User user,final Role role) throws VerificationInDataBaseException;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.updateStudent
	 **/
	Etudiant updateStudent( final Etudiant e, final Long idSession);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.removeStudent
	 **/
	Etudiant removeStudent(final Long idStudent);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentById
	 **/
	Etudiant getStudentById(final Long idStudent);

	/**
	 * {@inheritDoc}
	 * 
	 * @throws VerificationInDataBaseException
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiantBySession
	 **/
	List<Object[]> getEtudiantBySession(final Long idSession)
			throws VerificationInDataBaseException;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiant
	 **/
	Etudiant getEtudiant(final String mail);

	/**
	 * {@inheritDoc}
	 * 
	 * @throws VerificationInDataBaseException
	 * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentsBySession
	 **/
	List<Etudiant> getStudentsBySession(final Long idSession)
			throws VerificationInDataBaseException;

	Etudiant verifyExistingEtudiant(final String name, final Date dateDeNaissance);

	
}
