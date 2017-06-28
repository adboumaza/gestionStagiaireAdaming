package com.adaming.myapp.evenement.dao;


import java.text.ParseException;
import java.util.List;

import org.joda.time.DateTime;

import com.adaming.myapp.entities.Evenement;

public class EvenementDaoImpl extends EvenementAbstractJpa implements
		IEvenementDao {

	@Override
	public Evenement addEvenement(Evenement e, Long idSession, Long idEtudiant) {

		return addEvenementAbstractJpa(e, idSession, idEtudiant);
	}

	@Override
	public Evenement AddWarningAndTop(final Evenement e, final Long idSession,
			final Long idEtudiant) {

		return addWarningAndTopAbstractJpa(e, idSession, idEtudiant);
	}

	@Override
	public List<Object[]> getEvenementsRetards() {
		return getEvenementsRetardsAbstractJpa();
	}

	@Override
	public List<Object[]> getEvenementsAbsences() {
		return getEvenementsAbsencesAbstractJpa();
	}

	@Override
	public List<Object[]> getEvenementsEntretien() {
		return getEvenementsEntretienAbstractJpa();
	}

	@Override
	public List<Object[]> getDailyCountOfRetards() {
		return getDailyCountOfRetardsAbstractJpa();
	}

	@Override
	public List<Object[]> getDailyCountOfAbsence() {
		return getDailyCountOfAbsenceAbstractJpa();
	}

	@Override
	public List<Object[]> getDailyCountOfWarning() {
		return getDailyCountOfWarningAbstractJpa();
	}

	@Override
	public List<Object[]> getDailyCountOfTop() {
		return getDailyCountOfTopAbstractJpa();
	}




	@Override
	public List<Object[]> getEventsExiste(final Long idEtudiant) {

		return getEventsExisteAbstractJpa(idEtudiant);
	}

	

	@Override
	public Evenement verifyExistingEvent(final Long idEtudiant) {
		// TODO Auto-generated method stub
		return verifyExistingEventAbstractJpa(idEtudiant);
	}

	@Override
	public void deleteEvenement(final Long idEvenement) {
		
		deleteEvenementAbstractJpa(idEvenement);
	}

	@Override
	public Evenement updateEvenement(Evenement evenement, Long idEtudiant,
			Long idSession) {
		// TODO Auto-generated method stub
		return updateEvenementAbstractJpa(evenement, idEtudiant, idSession);
	}

	@Override
	public Object [] getEventByStudentBetweenTwoDates(Long idSession,
			String nomEtudiant, DateTime date) {
		// TODO Auto-generated method stub
		return getEventByStudentBetweenTwoDatesAbstractJpa(idSession, nomEtudiant, date);
	}


}
