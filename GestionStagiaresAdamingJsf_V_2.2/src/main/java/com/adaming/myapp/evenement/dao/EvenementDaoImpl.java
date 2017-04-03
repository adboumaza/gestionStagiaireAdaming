package com.adaming.myapp.evenement.dao;


import java.util.List;

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
	public List<Evenement> getAllEvenements() {

		return getAllEvenementsAbstractJpa();
	}

	@Override
	public List<Evenement> getAllEvenementsBySession(final Long idSession) {

		return getAllEvenementsBySessionAbstractJpa(idSession);
	}

	@Override
	public List<Object[]> getEventsExiste(final Long idEtudiant) {

		return getEventsExisteAbstractJpa(idEtudiant);
	}

	@Override
	public long getNumberOfRetards() {
		// TODO Auto-generated method stub
		return getNumberOfRetardsAbstractJpa();
	}

	@Override
	public long getNumberOfAbsence() {
		// TODO Auto-generated method stub
		return getNumberOfAbsenceAbstractJpa();
	}

	@Override
	public long getNumberOfWarning() {
		// TODO Auto-generated method stub
		return getNumberOfWarningAbstractJpa();
	}

	@Override
	public long getNumberOfTop() {
		// TODO Auto-generated method stub
		return getNumberOfOfTopAbstractJpa();
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


}
