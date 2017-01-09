package com.adaming.myapp.etudiant.service;

import java.util.List;

import com.adaming.myapp.entities.Etudiant;
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
 * */
public interface IEtudiantService {
     
	 
	 /**
	  * {@inheritDoc} 
	 * @throws VerificationInDataBaseException 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.addStudent
	  **/
	 Etudiant addStudent(Etudiant e, Long idSession)
			throws AddEtudiantException;
     
	 
	 
	 /**
	  * {@inheritDoc} 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.updateStudent
	  **/
	 Etudiant updateStudent(Etudiant e, Long idSession);
	 
	 
	 
	 /**
	  * {@inheritDoc} 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.removeStudent
	  **/
	 Etudiant removeStudent(Long idStudent);
	 
	 
	 
	 /**
	  * {@inheritDoc} 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentById
	  **/
	 Etudiant getStudentById(Long idStudent);

	 
	 
	 /**
	  * {@inheritDoc} 
	 * @throws VerificationInDataBaseException 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiantBySession
	  **/
	 List<Etudiant> getEtudiantBySession(Long idSession) throws VerificationInDataBaseException;
     
	 
	 
	 /**
	  * {@inheritDoc} 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiant
	  **/
	 Etudiant getEtudiant(String mail);
     
	 /**
	  * {@inheritDoc} 
	  * @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentsBySession
	  **/
	 List<Etudiant> getStudentsBySession(Long idSession);
}
