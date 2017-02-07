package com.adaming.myapp.evenement.dao;

import java.util.Date;
import java.util.List;

import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IEvenementDao {

	/* add evenement */

	 Evenement addEvenement(Evenement e, Long idSession, Long idEtudiant);

	 Evenement AddWarningAndTop(Evenement e, Long idSession,
			Long idEtudiant);
	 
	 List<Object[]> getEventsExiste(final Long idEtudiant);
	 
	 //List<Object[]> getEventsTopExiste(final Long idEtudiant);

	/* toutes les evenements par semaine */

	 List<Object[]> getEvenementsRetards();
			

	 List<Object[]> getEvenementsAbsences();

	 List<Object[]> getEvenementsEntretien();

	/* evenement du jour */

	 List<Object[]> getDailyCountOfRetards();

	 List<Object[]> getDailyCountOfAbsence();

	 List<Object[]> getDailyCountOfWarning();

	 List<Object[]> getDailyCountOfTop();
	 
	 /* evenement du jour */

	 long getNumberOfRetards();

	 long getNumberOfAbsence();

	 long getNumberOfWarning();

	 long getNumberOfTop();


	/* toutes les evenements */


	 List<Evenement> getAllEvenements();

	 List<Evenement> getAllEvenementsBySession(Long idSession);
	 
	 /**@verification*/
	 Evenement verifyExistingEvent(Long idEtudiant);
	 
	

}
