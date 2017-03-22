package com.adaming.myapp.agenda.dao;

import java.util.Date;
import java.util.List;

import com.adaming.myapp.entities.Agenda;

public class AgendaDaoImpl extends AgendaAbstractJpa implements IAgendaDao{

	@Override
	public Agenda addAgenda(Agenda agenda, Long idUser) {
		// TODO Auto-generated method stub
		return addAgendaAbstractJpa(agenda, idUser);
	}

	@Override
	public Agenda updateAgenda(Agenda agenda, Long idUser) {
		// TODO Auto-generated method stub
		return updateAgendaAbstractJpa(agenda, idUser);
	}

	@Override
	public void deleteAgenda(Long idAgenda) {
		deleteAgendaAbstractJpa(idAgenda);
		
	}

	@Override
	public Agenda getAgenda(Long idAgenda) {
		// TODO Auto-generated method stub
		return getAgendaAbstractJpa(idAgenda);
	}

	@Override
	public List<Agenda> getAllAgenda() {
		// TODO Auto-generated method stub
		return getAllAgendaAbstractJpa();
	}

	@Override
	public List<Agenda> getAllAgendaByName(final String mail) {
		// TODO Auto-generated method stub
		return getAllAgendaAbstractJpaByName(mail);
	}

	@Override
	public Agenda getAgendaByUserAndDate(String mail, Date startDate,
			Date endDate, String title) {
		// TODO Auto-generated method stub
		return getAgendaByUserAndDateAbstractJpa(mail, startDate, endDate, title);
	}

}
