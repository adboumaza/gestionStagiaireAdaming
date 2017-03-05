package com.adaming.myapp.session.dao;

import java.util.List;

import com.adaming.myapp.dto.SessionDto;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddSessionException;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public interface ISessionDao {

	 SessionEtudiant addSessionStudent(final SessionEtudiant se,final Long idSpecialite,final Long idSite,final Long idSalle) throws AddSessionException;
	
	 SessionEtudiant updateSessionEtudiant(final SessionEtudiant se,
			 final Long idSpecialite,final Long idSite,final Long idSalle);
	
	 SessionEtudiant getSessionEtudiantById(final Long idSessionEtudiant);
	
	 List<SessionEtudiant> getAllSessions();
	
	 List<SessionEtudiant> getAllSessionsInProgress();
	 
     List<Object[]> getSallesDisponible(final Long idSalle);
     
     List<Object[]> getAllSessionsInProgressV2();
     
     List<Object[]> getAllSessionsV2();
     
     SessionEtudiant getSessionByFormateur(final Long idFormateur);
     
     SessionEtudiant getSessionByEtudiant(final Long idEtudiant);
	
     List<Evenement> getMoreInformationBySession(final Long idSession,final String choix);
}
