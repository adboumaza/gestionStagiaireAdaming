package com.adaming.myapp.etudiant.dao;

import java.util.List;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.exception.AddEtudiantException;
/*
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
	
	
	
	/* Cette m�thode permet d'enregister un objet de type etudiant 
	 * donne le type " identifiant de session " afin de l'associ� � une session 
	 * @return le type sous forme d'un objet etudiant
	 * @throws AddEtudiantException se l�ve si le nom et le mail existe d�ja dans la base de donn�e **/ 
	 Etudiant addStudent(Etudiant e, Long idSession)
			throws AddEtudiantException;

	
	 
	 
	/* Cette m�thode permet de modifi� un objet de type �tudiant 
	 * donne le type " identifiant de l'objet session " afin de le garder � sa session initial 
	 * @return le type sous forme d'un objet  etudiant**/
	 Etudiant updateStudent(Etudiant e, Long idSession);

	 
	 
	 
	/* Cette m�thode permet de supprimer un objet de type �tudiant 
     * donne le type " identifiant de l'objet etudiant "
     * @return le type sous forme d'un objet etudiant**/
	 Etudiant removeStudent(Long idStudent);
	   
	  
	  
	 
	 /* Cette m�thode permet de r�cup�rer un objet de type �tudiant 
	  * donne le type " identifiant de l'objet etudiant "
	  * @return le type sous forme d'un objet etudiant**/
	  Etudiant getStudentById(Long idStudent);
			 
	  
	  
	  
	 /* Cette m�thode permet de r�cup�rer une liste d'objets de type �tudiant 
	  * donne le type " identifiant de l'objet etudiant "
	  * @return une liste sous forme d'un objet etudiant**/
	  List<Etudiant> getEtudiantBySession(Long idSession);
			 
	
	  
	  
	  
	 /* Cette m�thode permet de r�cup�rer un objet de type �tudiant 
	  * donne le type " mail de l'objet etudiant "
	  * @return le type sous forme d'un objet etudiant**/
	  Etudiant getEtudiant(String mail);

}
