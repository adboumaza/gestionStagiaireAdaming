package com.adaming.myapp.session.service;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.session.dao.ISessionDao;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Transactional(readOnly=true)
public class SessionServiceImpl implements ISessionService{
    
	
	private ISessionDao dao;

	public void setDao(ISessionDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<------------Dao Session Injected------>");
	}

	@Override
	@Transactional(readOnly=false)
	public SessionEtudiant addSessionStudent(final SessionEtudiant se,
			final Long idSpecialite,final Long idSite,final Long idSalle) throws AddSessionException {
		List<Object[]> sallesDisponible = getSallesDisponible(idSalle);
		final Date CURRENT_TIME = new Date();
		for(Object[] o:sallesDisponible){
			Date fin   = (Date) o[2];
			String nomSalle = (String) o[3];
			Long finSession = fin.getTime()/(24 * 60 * 60 * 1000);
			Long resteJoursSession = finSession-(CURRENT_TIME.getTime()/(24 * 60 * 60 * 1000));
			if(fin.after(CURRENT_TIME))
			{
				throw new AddSessionException("La salle "+nomSalle+" est Ocupée il reste "+resteJoursSession+" Jours");
			}
		}
	    if(se.getDateDebute().after(se.getDateFin()))
	    {
			throw new AddSessionException("Veuillez vous assurer que la date de Fin est postérieure à la date de départ.");
		}
	    else if(se.getDateFin().before(CURRENT_TIME))
	    {
	    	throw new AddSessionException("Veuillez vous assurer que la date de Fin est postérieure à la date d'aujourd'hui.");
	    }
		return dao.addSessionStudent(se, idSpecialite,idSite,idSalle);
	}

	@Transactional(readOnly=false)
	@Override
	public SessionEtudiant updateSessionEtudiant(final SessionEtudiant se,
			final Long idSpecialite, final Long idSite, final Long idSalle) throws AddSessionException {
		  final Date CURRENT_TIME = new Date();
		  if(se.getDateDebute().after(se.getDateFin()))
		  {
				throw new AddSessionException("Veuillez vous assurer que la date de Fin est postérieure à la date de départ.");
		  }
		  else if(se.getDateFin().before(CURRENT_TIME))
		  {
		    	throw new AddSessionException("Veuillez vous assurer que la date de Fin est postérieure à la date d'aujourd'hui.");
		  }
		return dao.updateSessionEtudiant(se, idSpecialite, idSite, idSalle);
	}


	@Override
	public SessionEtudiant getSessionEtudiantById(final Long idSessionEtudiant) {
		// TODO Auto-generated method stub
		return dao.getSessionEtudiantById(idSessionEtudiant);
	}

	@Override
	public List<SessionEtudiant> getAllSessions() {
		// TODO Auto-generated method stub
		return dao.getAllSessions();
	}

	@Override
	public List<SessionEtudiant> getAllSessionsInProgress() {
		// TODO Auto-generated method stub
		return dao.getAllSessionsInProgress();
	}

	@Override
	public List<Object[]> getSallesDisponible(final Long idSalle) {
		// TODO Auto-generated method stub
		return dao.getSallesDisponible(idSalle);
	}

	@Override
	public List<Object[]> getAllSessionsInProgressV2() {
		// TODO Auto-generated method stub
		return dao.getAllSessionsInProgressV2();
	}

	@Override
	public List<Object[]> getAllSessionsV2() {
		// TODO Auto-generated method stub
		return dao.getAllSessionsV2();
	}

	@Override
	public SessionEtudiant getSessionByFormateur(final Long idFormateur) throws VerificationInDataBaseException {
		SessionEtudiant session = dao.getSessionByFormateur(idFormateur);
		if(session == null)
		{
			   throw new VerificationInDataBaseException("Vous n'est pas encore affecter à une session");
		}
		else if(session.getDateFin().before(new Date()))
		{
			throw new VerificationInDataBaseException("Votre Session à été Terminé, il faut voir avec l'administration pour une nouvelle affectation");
		}
		return dao.getSessionByFormateur(idFormateur);
	}

	@Override
	public SessionEtudiant getSessionByEtudiant(final Long idEtudiant) throws VerificationInDataBaseException {
		SessionEtudiant session = dao.getSessionByEtudiant(idEtudiant);
		final Date CURRENT_TIME = new Date();
		if(session.getDateFin().before(CURRENT_TIME))
		{
			   throw new VerificationInDataBaseException("Votre session a été terminée");
		}
		return session;
	}

	@Override
	public List<Evenement> getMoreInformationBySession(final Long idSession,final String choix) throws VerificationInDataBaseException {
		List<Evenement> evenements = dao.getMoreInformationBySession(idSession,choix);
		if(choix.equals("TOP") && evenements.isEmpty()){
			throw new VerificationInDataBaseException("le team leader n'a pas encore été signalé..!");
		}
		if(choix.equals("WARNING") && evenements.isEmpty()){
			throw new VerificationInDataBaseException("la black list n'a pas encore été signalée..!");
		}
		if(choix.equals("ABSENCE") && evenements.isEmpty()){
			throw new VerificationInDataBaseException("aucune absence signalée..!");
		}
		if(choix.equals("RETARD") && evenements.isEmpty()){
			throw new VerificationInDataBaseException("aucun retard signalée..!");
		}
		return evenements;
	}




	
}
