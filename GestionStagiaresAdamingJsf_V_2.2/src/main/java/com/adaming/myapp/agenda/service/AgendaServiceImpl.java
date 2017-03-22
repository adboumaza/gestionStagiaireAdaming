package com.adaming.myapp.agenda.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.agenda.dao.IAgendaDao;
import com.adaming.myapp.entities.Agenda;
import com.adaming.myapp.tools.LoggerConfig;
@Transactional(readOnly = true)
public class AgendaServiceImpl implements IAgendaService {

	private IAgendaDao dao;
	
	

	/**
	 * @param dao the dao to set
	 */
	public void setDao(IAgendaDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<----Dao Agenda Injected---->");
	}

	@Override
	@Transactional(readOnly = false)
	public Agenda addAgenda(Agenda agenda, Long idUser) {
		// TODO Auto-generated method stub
		return dao.addAgenda(agenda, idUser);
	}

	@Override
	@Transactional(readOnly = false)
	public Agenda updateAgenda(Agenda agenda, Long idUser) {
		// TODO Auto-generated method stub
		return dao.updateAgenda(agenda, idUser);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAgenda(Long idAgenda) {
		dao.deleteAgenda(idAgenda);

	}

	@Override
	public Agenda getAgenda(Long idAgenda) {
		// TODO Auto-generated method stub
		return dao.getAgenda(idAgenda);
	}

	@Override
	public List<Agenda> getAllAgenda() {
		// TODO Auto-generated method stub
		return dao.getAllAgenda();
	}

	@Override
	public List<Agenda> getAllAgendaByName(String mail) {
		// TODO Auto-generated method stub
		return dao.getAllAgendaByName(mail);
	}

	@Override
	public Agenda getAgendaByUserAndDate(String mail, Date startDate,
			Date endDate, String title) {
		// TODO Auto-generated method stub
		return dao.getAgendaByUserAndDate(mail, startDate, endDate, title);
	}

}
