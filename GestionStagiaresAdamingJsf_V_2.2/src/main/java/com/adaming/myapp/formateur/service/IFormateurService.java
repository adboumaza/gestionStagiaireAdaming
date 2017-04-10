package com.adaming.myapp.formateur.service;

import java.util.Date;
import java.util.List;

import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IFormateurService {

	 Formateur addFormateur(final Formateur f,final User user,final Role role)
			throws VerificationInDataBaseException;

	 SessionEtudiant verifyExistingAffectation(final Long idFormateur,final Long idSession) throws VerificationInDataBaseException;
	 
	 void addFormateurToSession(final Long idSession,final Long idFormateur) throws VerificationInDataBaseException;

	 List<Formateur> getAllFormateurs();

	 Formateur getFormateur(final String mail);
	
	 Formateur getFormateurById(final Long idFormateur) throws VerificationInDataBaseException;
	 
	 List<Object[]> getFormateuByName(final String nom,final Date dateDeNaissance,final String mail);
	 
	 List<Formateur> getFormateursInSessionEncours();
}
