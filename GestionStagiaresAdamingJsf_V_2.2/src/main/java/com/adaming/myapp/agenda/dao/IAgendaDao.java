package com.adaming.myapp.agenda.dao;

import java.util.Date;
import java.util.List;

import com.adaming.myapp.entities.Agenda;

public interface IAgendaDao {

	Agenda addAgenda(final Agenda agenda, final Long idUser);

	Agenda updateAgenda(final Agenda agenda, final Long idUser);

	void deleteAgenda(final Long idAgenda);

	Agenda getAgenda(final Long idAgenda);

	List<Agenda> getAllAgenda();
	
	List<Agenda> getAllAgendaByName(final String mail);
	
	Agenda getAgendaByUserAndDate(final String mail,final Date startDate,final Date endDate,final String title);
}
