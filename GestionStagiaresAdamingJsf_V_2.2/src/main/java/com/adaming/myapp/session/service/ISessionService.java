package com.adaming.myapp.session.service;

import java.util.List;

import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddSessionException;

public interface ISessionService {

	public SessionEtudiant addSessionStudent(SessionEtudiant se,Long idSpecialite) throws AddSessionException;
	
	public SessionEtudiant updateSessionEtudian(SessionEtudiant se,Long idSpecialite);
	
	public SessionEtudiant getSessionEtudiantById(Long idSessionEtudiant);
	
	public List<SessionEtudiant> getAllSessions();
	
	public List<SessionEtudiant> getAllSessionsInProgress();
}
