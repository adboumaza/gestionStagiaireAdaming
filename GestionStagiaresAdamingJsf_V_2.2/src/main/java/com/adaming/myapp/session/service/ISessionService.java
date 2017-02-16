package com.adaming.myapp.session.service;

import java.util.List;

import com.adaming.myapp.dto.SessionDto;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public interface ISessionService {

	 SessionEtudiant addSessionStudent(final SessionEtudiant se,final Long idSpecialite,final Long idSite,final Long idSalle) throws AddSessionException;
	
	 SessionEtudiant updateSessionEtudiant(final SessionEtudiant se,
			 final Long idSpecialite,final Long idSite,final Long idSalle) throws AddSessionException;
	
	
	 SessionEtudiant getSessionEtudiantById(final Long idSessionEtudiant);
	
	 List<SessionEtudiant> getAllSessions();
	
	 List<SessionEtudiant> getAllSessionsInProgress();
	 
	 List<Object[]> getSallesDisponible(final Long idSalle);
	 
	 List<Object[]> getAllSessionsInProgressV2();
	 
	 List<Object[]> getAllSessionsV2();
	 
	 SessionEtudiant getSessionByFormateur(final Long idFormateur) throws VerificationInDataBaseException;
	 
	 SessionEtudiant getSessionByEtudiant(final Long idEtudiant) throws VerificationInDataBaseException;
}
