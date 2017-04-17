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
 * */
public interface IEtudiantDao {
	
	
	
	/** Cette méthode permet d'enregister un objet de type etudiant 
	 * donne le type " identifiant de session " afin de l'associé à une session 
	 * @return le type sous forme d'un objet etudiant
	 * @throws AddEtudiantException se lève si le nom et le mail existe déja dans la base de donnée **/ 
	 Etudiant addStudent(final Etudiant e, final Long idSession);
			

	
	 
	 
	/** Cette méthode permet de modifié un objet de type étudiant 
	 * donne le type " identifiant de l'objet session " afin de le garder à sa session initial 
	 * @return le type sous forme d'un objet  etudiant**/
	 Etudiant updateStudent(final Etudiant e, final Long idSession);

	 
	 
	 
	/** Cette méthode permet de supprimer un objet de type étudiant 
     * donne le type " identifiant de l'objet etudiant "
     * @return le type sous forme d'un objet etudiant**/
	 Etudiant removeStudent(final Long idStudent);
	   
	  
	  
	 
	 /** Cette méthode permet de récupérer un objet de type étudiant 
	  * donne le type " identifiant de l'objet etudiant "
	  * @return le type sous forme d'un objet etudiant**/
	  Etudiant getStudentById(final Long idStudent);
			 
	  
	  
	  
	 /** Cette méthode permet de récupérer une liste d'objets de type étudiant 
	  * donne le type " identifiant de l'objet etudiant "
	  * @return une liste sous forme d'un objet etudiant
	  *  @throws VerificationInDataBaseException **/
	  List<Object[]> getEtudiantBySession(final Long idSession) ;
			 
	
	  
	  
	  
	 /** Cette méthode permet de récupérer un objet de type étudiant 
	  * @param  le type " mail de l'objet etudiant "
	  * @return le type sous forme d'un objet etudiant**/
	  Etudiant getEtudiant(final String mail);
	  
	  
	  /** Cette méthode permet de récupérer une Lis d'objets de type étudiant 
	   * @param donne le l'objet de type session etudiant "
	   * @return une list d'objets de type etudiant
	   * @method utilisée pour la vérification le momoent d'ajouter un etudiant**/
	  List<Etudiant>getStudentsBySession(final Long idSession);
	  
	  Etudiant verifyExistingEtudiant(final String name,final Date dateDeNaissance);
	  
      EtudiantDto getStudentDto(Long idStudent);
}
