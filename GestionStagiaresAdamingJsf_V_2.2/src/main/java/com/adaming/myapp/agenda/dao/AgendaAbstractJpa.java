package com.adaming.myapp.agenda.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Agenda;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.tools.LoggerConfig;

public abstract class AgendaAbstractJpa{
   
	@PersistenceContext
	private EntityManager entityManager;


	public Agenda addAgendaAbstractJpa(final Agenda agenda, final Long idUser) {
		User user = entityManager.find(User.class, idUser);
		agenda.setUser(user);
		entityManager.persist(agenda);
		LoggerConfig.logInfo("l'evenement de l'agenda à bien été ajouté");
		return agenda;
	}


	public Agenda updateAgendaAbstractJpa(final Agenda agenda,final Long idUser) {
		User user = entityManager.find(User.class, idUser);
		agenda.setUser(user);
		entityManager.merge(agenda);
		return agenda;
	}


	public void deleteAgendaAbstractJpa(final Long idAgenda) {
		Agenda agenda = getAgendaAbstractJpa(idAgenda);
		System.out.println("agenda"+agenda);
		entityManager.remove(agenda);
		
	}


	public Agenda getAgendaAbstractJpa(final Long idAgenda) {
		final String SQL = "select a from Agenda a join a.user where a.idAgenda =:x";
		Query query = entityManager.createQuery(SQL).setParameter("x",idAgenda);
		Agenda agenda = null;
		if(query.getResultList().size() > 0 && query.getResultList() != null){
			agenda = (Agenda) query.getResultList().get(0);
		}
		return agenda;
	}

   @SuppressWarnings("unchecked")
	public List<Agenda> getAllAgendaAbstractJpa() {
		Query  query = entityManager.createQuery("from Agenda");
		return query.getResultList();
	}
   
   @SuppressWarnings("unchecked")
	public List<Agenda> getAllAgendaAbstractJpaByName(final String mail) {
		Query  query = entityManager.createQuery("from Agenda a join fetch a.user u where u.name =:x").setParameter("x",mail);
		return query.getResultList();
	}
   
    public Agenda getAgendaByUserAndDateAbstractJpa(final String mail,final Date startDate,final Date endDate,final String title){
        final String SQL = "from Agenda a join fetch a.user u where u.name =:n and a.debut =:d and a.fin =:f and a.label =:t ";
    	Query query = entityManager.createQuery(SQL).setParameter("n",mail).setParameter("d", startDate).setParameter("f", endDate).setParameter("t", title);
    	Agenda agenda = null;
    	if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 agenda  = (Agenda) query.getResultList().get(0);
		}

    	return agenda;
    }
   
   
	
	
	
}
