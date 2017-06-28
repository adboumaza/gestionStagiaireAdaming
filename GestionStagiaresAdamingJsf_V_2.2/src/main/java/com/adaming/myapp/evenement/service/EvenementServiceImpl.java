package com.adaming.myapp.evenement.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Absence;
import com.adaming.myapp.entities.Entretien;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Retard;
import com.adaming.myapp.entities.TopEtudiant;
import com.adaming.myapp.entities.WarningEtudiant;
import com.adaming.myapp.evenement.dao.IEvenementDao;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;

@Transactional(readOnly=true)
public class EvenementServiceImpl implements IEvenementService {


	private IEvenementDao dao;

	public void setDao(IEvenementDao dao) {
		LoggerConfig.logInfo("<---------dao Evenement injected------->");
		this.dao = dao;
	}

	@Override
	public List<Object[]> getEvenementsRetards()
			throws EvenementNotFoundException {
		List<Object[]> retards = null;
		retards = dao.getEvenementsRetards();
		if (retards.isEmpty()) {
			throw new EvenementNotFoundException("Aucun retard mentionné !");
		}
		return retards;
	}

	@Override
	public List<Object[]> getEvenementsAbsences()
			throws EvenementNotFoundException {
		List<Object[]> absences = null;
		absences = dao.getEvenementsAbsences();
		if (absences.isEmpty()) {
			throw new EvenementNotFoundException("Aucune absence mentionnée !");
		}
		return absences;
	}

	@Override
	public List<Object[]> getEvenementsEntretien()
			throws EvenementNotFoundException {
		List<Object[]> entretien = null;
		entretien = dao.getEvenementsEntretien();
		if (entretien.isEmpty()) {
			throw new EvenementNotFoundException("Aucun entretien mentionné !");
		}
		return entretien;
	}




	@Override
	@Transactional(readOnly=false)
	public Evenement addEvenement(final Evenement e,final Long idSession, Long idEtudiant) throws VerificationInDataBaseException {

		List<Object[]> evenements = getEventsExiste(idEtudiant);
		if(!evenements.isEmpty()){
			for (Object[] evenement : evenements) {
				Date dStart = (Date) evenement[0];
				Date dEnd = (Date) evenement[1];
				Long idEt= (Long) evenement[2];
				if (    idEt == idEtudiant
						&& dStart.compareTo(e.getStartDate()) == 0
						&& dEnd.compareTo(e.getEndDate()) == 0) {
					throw new VerificationInDataBaseException(
							" cette evènement est déja signalé");
				}
			}
		}
		return dao.addEvenement(e, idSession, idEtudiant);
	}

	@Override
	@Transactional(readOnly=false)
	public Evenement AddWarningAndTop(final Evenement e, final Long idSession,
			Long idEtudiant) throws VerificationInDataBaseException {
		
		Evenement event = verifyExistingEvent(idEtudiant);
		if(event != null)
		{
			throw new VerificationInDataBaseException("l'étudiant N °"
					+idEtudiant + " , " + " est déja signalé");
	
		}
		return dao.AddWarningAndTop(e, idSession, idEtudiant);
	}

	@Override
	public List<Object[]> getEventsExiste(final Long idEtudiant) {
		// TODO Auto-generated method stub
		return dao.getEventsExiste(idEtudiant);
	}

	@Override
	public List<Object[]> getDailyCountOfRetards() {
		// TODO Auto-generated method stub
		return dao.getDailyCountOfRetards();
	}

	@Override
	public List<Object[]> getDailyCountOfAbsence() {
		// TODO Auto-generated method stub
		return dao.getDailyCountOfAbsence();
	}

	@Override
	public List<Object[]> getDailyCountOfWarning() {
		// TODO Auto-generated method stub
		return dao.getDailyCountOfWarning();
	}

	@Override
	public List<Object[]> getDailyCountOfTop() {
		// TODO Auto-generated method stub
		return dao.getDailyCountOfTop();
	}


	@Override
	public Evenement verifyExistingEvent(final Long idEtudiant) {
		// TODO Auto-generated method stub
		return dao.verifyExistingEvent(idEtudiant);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteEvenement(final Long idEvenement) {
		dao.deleteEvenement(idEvenement);
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public Evenement updateEvenement(Evenement evenement, Long idEtudiant,
			Long idSession) throws Exception {
		if(evenement.getStartDate().after(evenement.getEndDate())){
			throw new Exception("la date de départ ne peut être antérieur ");
		}
		else if(evenement.getStartDate().equals(evenement.getEndDate()))
		{
			throw new Exception("la durré ne peut être 0 min");	
		}
		return dao.updateEvenement(evenement, idEtudiant, idSession);
	}

	@Override
	public Object [] getEventByStudentBetweenTwoDates(Long idSession,
			String nomEtudiant, DateTime date){
		// TODO Auto-generated method stub
		return dao.getEventByStudentBetweenTwoDates(idSession, nomEtudiant, date);
	}

}
