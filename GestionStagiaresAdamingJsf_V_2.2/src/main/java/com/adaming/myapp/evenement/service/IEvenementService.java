package com.adaming.myapp.evenement.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IEvenementService {

	/* Add warning and Top */
	Evenement AddWarningAndTop(final Evenement e, final Long idSession, Long idEtudiant)
			throws VerificationInDataBaseException;

	/* add evenement */
	Evenement addEvenement(Evenement e, final Long idSession, final Long idEtudiant)
			throws VerificationInDataBaseException;

	List<Object[]> getEventsExiste(final Long idEtudiant);



	/* toutes les evenements par semaine */
	List<Object[]> getEvenementsRetards() throws EvenementNotFoundException;

	List<Object[]> getEvenementsAbsences() throws EvenementNotFoundException;

	List<Object[]> getEvenementsEntretien() throws EvenementNotFoundException;

	/* evenement du jour */

	 List<Object[]> getDailyCountOfRetards();

	 List<Object[]> getDailyCountOfAbsence();

	 List<Object[]> getDailyCountOfWarning();

	 List<Object[]> getDailyCountOfTop();
	 


	
	/**@verification evenement */
	 Evenement verifyExistingEvent(final Long idEtudiant);

	 void deleteEvenement(final Long idEvenement);
	 
	 Evenement updateEvenement(final Evenement evenement,final Long idEtudiant,final Long idSession) throws Exception;
	 
	 Object [] getEventByStudentBetweenTwoDates(Long idSession,String nomEtudiant,DateTime date);
}
