package com.adaming.myapp.etudiant.dao;

import java.util.Date;
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
 *  @throws AddEtudiantException v�rification dans la base de donn�e
 * */
public interface IEtudiantDao {
	
	
	
	/** Cette m�thode permet d'enregister un objet de type etudiant 
	 * donne le type " identifiant de session " afin de l'associ� � une session 
	 * @return le type sous forme d'un objet etudiant
	 * @throws AddEtudiantException se l�ve si le nom et le mail existe d�ja dans la base de donn�e **/ 
	 Etudiant addStudent(final Etudiant e, final Long idSession);
			

	
	 
	 
	/** Cette m�thode permet de modifi� un objet de type �tudiant 
	 * donne le type " identifiant de l'objet session " afin de le garder � sa session initial 
	 * @return le type sous forme d'un objet  etudiant**/
	 Etudiant updateStudent(final Etudiant e, final Long idSession);

	 
	 
	 
	/** Cette m�thode permet de supprimer un objet de type �tudiant 
     * donne le type " identifiant de l'objet etudiant "
     * @return le type sous forme d'un objet etudiant**/
	 Etudiant removeStudent(final Long idStudent);
	   
	  
	  
	 
	 /** Cette m�thode permet de r�cup�rer un objet de type �tudiant 
	  * donne le type " identifiant de l'objet etudiant "
	  * @return le type sous forme d'un objet etudiant**/
	  Etudiant getStudentById(final Long idStudent);
			 
	  
	  
	  
	 /** Cette m�thode permet de r�cup�rer une liste d'objets de type �tudiant 
	  * donne le type " identifiant de l'objet etudiant "
	  * @return une liste sous forme d'un objet etudiant
	  *  @throws VerificationInDataBaseException **/
	  List<Object[]> getEtudiantBySession(final Long idSession) ;
			 
	
	  
	  
	  
	 /** Cette m�thode permet de r�cup�rer un objet de type �tudiant 
	  * @param  le type " mail de l'objet etudiant "
	  * @return le type sous forme d'un objet etudiant**/
	  Etudiant getEtudiant(final String mail);
	  
	  
	  /** Cette m�thode permet de r�cup�rer une Lis d'objets de type �tudiant 
	   * @param donne le l'objet de type session etudiant "
	   * @return une list d'objets de type etudiant
	   * @method utilis�e pour la v�rification le momoent d'ajouter un etudiant**/
	  List<Etudiant>getStudentsBySession(final Long idSession);
	  
	  Etudiant verifyExistingEtudiant(final String name,final Date dateDeNaissance);
	  

}
