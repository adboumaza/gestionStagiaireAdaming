package com.adaming.myapp.etudiant.dao;
import java.util.Date;
import java.util.List;

import com.adaming.myapp.dto.EtudiantDto;
import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
/**
 *  @author Adel 
 *  @version 1.0.0
 *  @date 11/10/2016
 *  @param e l'entité etudiant 
 *  @param idSession le type de la relation à associée
 *  @param idStudent l'identifiant de l'entité etudiant
 *  @param mail le mail de l'etudiant
 *  @throws AddEtudiantException vérification dans la base de donnée
 *  <p>cette class hérite de la class EtudiantAbstractJpa
	 *  @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa 
	 *  et implémente l'interface IEtudiantDao
 *  </p>
 * */

public class EtudiantDaoImpl extends EtudiantAbstractJpa implements IEtudiantDao {

   /**
	* {@inheritDoc} 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.addStudent
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.addStudentAbstractJpa
    **/
	@Override
	public Etudiant addStudent(final Etudiant e, final Long idSession) {
		return addStudentAbstractJpa(e, idSession);
	}
	
	
	
	
    /**
	* {@inheritDoc} 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.updateStudent
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.updateStudentAbstractJpa
    **/
	@Override
	public Etudiant updateStudent(final Etudiant e,  final Long idSession) {
		return updateStudentAbstractJpa(e, idSession);
	}
	
	
	
	
	
    /**
	* {@inheritDoc} 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.removeStudent
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.removeStudentAbstractJpa
    **/
	@Override
	public Etudiant removeStudent(final Long idStudent) {
		return removeStudentAbstractJpa(idStudent);
	}
    
	
	
	
   /**
	* {@inheritDoc} 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentById
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.getStudentByIdAbstractJpa
    **/
	@Override
	public Etudiant getStudentById(final Long idStudent) {
		return getStudentByIdAbstractJpa(idStudent);
	}
    
	
	
	
	
	/**
	* {@inheritDoc} 
	 * @throws VerificationInDataBaseException 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiantBySession
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.getEtudiantBySessionAbstractJpa
    **/
	@Override
	public List<Object[]> getEtudiantBySession(final Long idSession) {
		return getEtudiantBySessionAbstractJpa(idSession);
	}
    
	
	
	
	
	/**
	* {@inheritDoc} 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getEtudiant
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.getEtudiantAbstractJpa
    **/
	@Override
	public Etudiant getEtudiant(final String mail) {
		return getEtudiantAbstractJpa(mail);
	}


	/**
	* {@inheritDoc} 
	* @see com.adaming.myapp.etudiant.dao.IEtudiantDao.getStudentsBySession
	* @see com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa.getStudentsBySessionAbstractJpa
    **/

	@Override
	public List<Etudiant> getStudentsBySession(final Long idSession) {
		// TODO Auto-generated method stub
		return getStudentsBySessionAbstractJpa(idSession);
	}




	@Override
	public Etudiant verifyExistingEtudiant(final String name, final Date dateDeNaissance) {
		// TODO Auto-generated method stub
		return verifyExistingEtudiantAbstractJpa(name, dateDeNaissance);
	}




	@Override
	public EtudiantDto getStudentDto(Long idStudent) {
		// TODO Auto-generated method stub
		return getStudentDtoAbstractJpa(idStudent);
	}

}
