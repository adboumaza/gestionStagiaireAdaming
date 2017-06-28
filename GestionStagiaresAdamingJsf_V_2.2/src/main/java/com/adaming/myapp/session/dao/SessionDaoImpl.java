package com.adaming.myapp.session.dao;
import java.util.Date;
import java.util.List;

import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddSessionException;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public class SessionDaoImpl extends SessionAbstractJpa implements ISessionDao{

	@Override
	public SessionEtudiant addSessionStudent(final SessionEtudiant se,
			final Long idSpecialite, final Long idSite, final Long idSalle)
			throws AddSessionException {
		// TODO Auto-generated method stub
		return addSessionStudentAbstractJpa(se, idSpecialite, idSite, idSalle);
	}

	@Override
	public SessionEtudiant updateSessionEtudiant(final SessionEtudiant se,
			final Long idSpecialite, final Long idSite,final Long idSalle) {
		return updateSessionEtudianAbstractJpa(se,idSpecialite,idSite,idSalle);
	}

	@Override
	public SessionEtudiant getSessionEtudiantById(final Long idSessionEtudiant) {
		return getSessionEtudiantByIdAbstractJpa(idSessionEtudiant);
	}


	@Override
	public List<Object[]> getSallesDisponible(final Long idSalle) {
		// TODO Auto-generated method stub
		return getSallesDisponibleAbstracJpa(idSalle);
	}

	@Override
	public List<Object[]> getAllSessionsInProgressV2() {
		return getAllSessionsInProgressAbstractJpaV2();
	}

	@Override
	public List<Object[]> getAllSessionsV2() {
		// TODO Auto-generated method stub
		return getAllSessionsAbstractJpaV2();
	}

	@Override
	public SessionEtudiant getSessionByFormateur(final Long idFormateur) {
		// TODO Auto-generated method stub
		return getSessionByFormateurAbstractJpa(idFormateur);
	}

	@Override
	public SessionEtudiant getSessionByEtudiant(final Long idEtudiant) {
		// TODO Auto-generated method stub
		return getSessionByEtudiantAbstractJpa(idEtudiant);
	}

	@Override
	public List<Evenement> getMoreInformationBySession(final Long idSession,final String choix){
		// TODO Auto-generated method stub
		return getMoreInformationBySessionAbstractJpa(idSession,choix);
	}
	
	@Override
	public List<Object[]> getAllSessionsBetwenTwoDates(
			final Date dateDebut,final Date dateFin) {
		// TODO Auto-generated method stub
		return getAllSessionsBetwenTwoDatesAbstractJpa(dateDebut,dateFin);
	}

	@Override
	public List<Object[]> getAllSessionsQuiTermineCetteSemaine() {
		// TODO Auto-generated method stub
		return getAllSessionsQuiTermineCetteSemaineAbstractJpa();
	}



	

	
	
	

	

}
