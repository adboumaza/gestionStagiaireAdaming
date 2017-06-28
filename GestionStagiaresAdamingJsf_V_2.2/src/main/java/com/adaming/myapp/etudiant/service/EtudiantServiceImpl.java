package com.adaming.myapp.etudiant.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.dto.EtudiantDto;
import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.etudiant.dao.IEtudiantDao;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.role.dao.IRoleDao;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.user.dao.IUserDao;
/**
 *  @author Adel 
 *  @version 1.0.0
 *  @date 11/10/2016
 *  @Interface dao injection de dépendance dans l'interface IEtudiantService @see app.xml
 *  @param e l'entité etudiant 
 *  @param idSession le type de la relation à associée
 *  @param idStudent l'identifiant de l'entité etudiant
 *  @param mail le mail de l'etudiant
 *  @throws AddEtudiantException vérification dans la base de donnée
 * */
@Transactional(readOnly=true)
public class EtudiantServiceImpl implements IEtudiantService {
    
	
	/**
     * Logger @see java.util.logging.Logger
     */
	
    
	
   /**
    * @Interface IEtudiantDao @see com.adaming.myapp.etudiant.dao.IEtudiantDao
    **/
	private IEtudiantDao dao;
	
	@Inject
	private IUserDao daoUser;
	
	@Inject 
	private IRoleDao daoRole;

	public void setDao(IEtudiantDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<-----------Dao Student Injected---------->");
	}
    
	
   /**
	* {@inheritDoc} 
    * @throws VerificationInDataBaseException 
	* @see com.adaming.myapp.etudiant.service.IEtudiantService.addStudent
	**/
	@Override
	@Transactional(readOnly=false,rollbackFor = VerificationInDataBaseException.class)
	public Etudiant addStudent(final Etudiant e, final Long idSession,final User user,final Role role)
			throws VerificationInDataBaseException {
	            Etudiant etudiant = verifyExistingEtudiant(e.getNomEtudiant(),e.getDateDeNaissance());
	            if (etudiant != null)
				{
					throw new VerificationInDataBaseException("L'étudiant "
							+ e.getNomEtudiant()
							+ " Existe déja dans la Session N°" + idSession);
				}
				else 
					if(!daoUser.getUsersByMail(e.getMail()).isEmpty())
				{
					throw new VerificationInDataBaseException("l'adresse mail "+e.getMail()+" existe déjà dans la session N° "+idSession+" Veuillez renseigner une autre adresse mail");
				}
				else{
					dao.addStudent(e, idSession);
					daoUser.saveUser(user);
					daoRole.saveRole(role, user.getIdUser());
				}

		        return null;
	}
   
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.service.IEtudiantService.updateStudent
	 **/
	@Override
	@Transactional(readOnly=false)
	public Etudiant updateStudent(final Etudiant e, final Long idSession) {
		// TODO Auto-generated method stub
		return dao.updateStudent(e, idSession);
	}
   
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.service.IEtudiantService.removeStudent
	 **/
	@Override
	@Transactional(readOnly=false)
	public Etudiant removeStudent(final Long idStudent) {
		// TODO Auto-generated method stub
		return dao.removeStudent(idStudent);
	}
    
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.service.IEtudiantService.getStudentById
	 **/
	@Override
	public Etudiant getStudentById(final Long idStudent) {
		// TODO Auto-generated method stub
		return dao.getStudentById(idStudent);
	}
    
	
	
	/**
	 * {@inheritDoc} 
	 * @throws VerificationInDataBaseException 
	 * @see com.adaming.myapp.etudiant.service.IEtudiantService.getEtudiantBySession
	 **/
	@Override
	public List<Object[]> getEtudiantBySession(final Long idSession) throws VerificationInDataBaseException {
		 List<Object[]> objects = dao.getEtudiantBySession(idSession);
		 if(objects != null){
        	if(objects.size() == 0){
        		throw new VerificationInDataBaseException("Il n'existe aucun étudiant dans la session Numéro "+idSession);
        	}
        	LoggerConfig.logInfo("le nombre des etudiants dans la session N "
					+ idSession + "est " + objects.size());
        }
		return objects;
	}
    
	
	
	/**
	 * {@inheritDoc} 
	 * @see com.adaming.myapp.etudiant.service.IEtudiantService.getEtudiant
	 **/
	@Override
	public Etudiant getEtudiant(final String mail) {
		// TODO Auto-generated method stub
		return dao.getEtudiant(mail);
	}


	@Override
	public List<Etudiant> getStudentsBySession(final Long idSession) throws VerificationInDataBaseException {
		List<Etudiant> etudiants = dao.getStudentsBySession(idSession);
        if(etudiants == null || etudiants.isEmpty()){
        		throw new VerificationInDataBaseException("Il n'existe aucun étudiant dans la session Numéro "+idSession);
        }
		return dao.getStudentsBySession(idSession);
	}


	@Override
	public Etudiant verifyExistingEtudiant(final String name, final Date dateDeNaissance) {
		// TODO Auto-generated method stub
		return dao.verifyExistingEtudiant(name, dateDeNaissance);
	}


	@Override
	public EtudiantDto getStudentDto(Long idStudent) {
		// TODO Auto-generated method stub
		return dao.getStudentDto(idStudent);
	}



}
