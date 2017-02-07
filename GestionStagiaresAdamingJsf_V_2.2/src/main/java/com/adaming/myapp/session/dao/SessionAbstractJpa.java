package com.adaming.myapp.session.dao;


import java.util.List;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;










import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.dto.SessionDto;
import com.adaming.myapp.entities.Salle;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.Site;
import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;

public abstract class SessionAbstractJpa {

	@PersistenceContext
	private EntityManager em;
	
	public SessionEtudiant addSessionStudentAbstractJpa(SessionEtudiant se,
			Long idSpecialite,Long idSite,Long idSalle) throws AddSessionException {
		Specialite sp = em.find(Specialite.class,idSpecialite);
		Site site = em.find(Site.class,idSite);
		Salle salle = em.find(Salle.class,idSalle);
		se.setSpecialite(sp);
		se.setSite(site);
		se.setSalle(salle);
		sp.getSessionEtudiant().add(se);
		em.persist(se);
		LoggerConfig.logInfo(" la session+"+se.getIdSession()+ "a bien �t� enregistrer");
		return se;
	}


	public SessionEtudiant updateSessionEtudianAbstractJpa(SessionEtudiant se,
			Long idSpecialite,Long idSite,Long idSalle) {
		Specialite sp = em.find(Specialite.class,idSpecialite);
		Site site = em.find(Site.class,idSite);
		Salle salle = em.find(Salle.class,idSalle);
		se.setSpecialite(sp);
		se.setSalle(salle);
		se.setSite(site);
		em.merge(se);
		LoggerConfig.logInfo(" la session+"+se.getIdSession()+ "a bien �t� Modifi�");
		return se;
	}


	public SessionEtudiant getSessionEtudiantByIdAbstractJpa(Long idSessionEtudiant) {
		 final String SQL = "select distinct p from SessionEtudiant p " +
                 "left join fetch p.site left join fetch p.salle left join fetch p.specialite " +
                 "where p.id = :id";

		 return (SessionEtudiant) em.createQuery(SQL)
		       .setParameter("id", idSessionEtudiant)
		       .getSingleResult();
		}

	@SuppressWarnings("unchecked")
	public List<SessionEtudiant> getAllSessionsAbstractJpa() {
		Query query = em.createQuery("from SessionEtudiant s Order By s.dateFin DESC");
		LoggerConfig.logInfo("il existe"+query.getResultList().size());
		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<SessionEtudiant> getAllSessionsInProgressAbstractJpa() {
		Query query = em.createQuery("from SessionEtudiant s where s.dateFin > CURRENT_DATE");
		LoggerConfig.logInfo("il existe"+query.getResultList().size()+" sessions en cours");
		return query.getResultList();
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSallesDisponibleAbstracJpa(Long idSalle) {
		Query query = em.createQuery("Select se.idSession,se.dateDebute,se.dateFin,sa.numeroSalle FROM SessionEtudiant se join se.salle sa where sa.idSalle=:x order by se.idSession desc");
		query.setParameter("x",idSalle);
		query.setMaxResults(1);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSessionsInProgressAbstractJpaV2() {
		Query query = em.createQuery("Select se.idSession,sp.designation,se.dateDebute,se.dateFin,site.nomSite,sa.numeroSalle,se.nombreJours From SessionEtudiant se join se.specialite sp join se.site site join se.salle sa where se.dateFin > CURRENT_DATE");
		LoggerConfig.logInfo("il existe"+query.getResultList().size()+" sessions en cours");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSessionsAbstractJpaV2() {
		final String SQL = "Select se.idSession,sp.designation,se.dateDebute,se.dateFin,site.nomSite,sa.numeroSalle From SessionEtudiant se join se.specialite sp join se.site site join se.salle sa Order By se.dateFin DESC";
		Query query = em.createQuery(SQL);
		LoggerConfig.logInfo("il existe"+query.getResultList().size());
		return query.getResultList();
	}
	
	
	public SessionEtudiant getSessionByFormateurAbstractJpa(Long idFormateur){
		 final String SQL = "select distinct s from SessionEtudiant s " +
                 "left join fetch s.site left join fetch s.salle left join fetch s.specialite " +
                 "left join fetch s.formateurs f left join s.etudiants e where f.idFormateur = :id ORDER BY s.dateFin desc";
		 SessionEtudiant session = null;
         Query query =  em.createQuery(SQL)
				       .setParameter("id", idFormateur);
		 if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 session = (SessionEtudiant) query.getResultList().get(0);
		 }

		 return session;
	}
	
	public SessionEtudiant getSessionByEtudiantAbstractJpa(Long idEtudiant){
		final String SQL = "select distinct s from SessionEtudiant s " +
                "join fetch s.site join fetch s.salle join fetch s.specialite " +
                "left join s.etudiants e where e.idEtudiant = :id ";
		 SessionEtudiant session = null;
         Query query =  em.createQuery(SQL)
				       .setParameter("id", idEtudiant);
		 if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 session = (SessionEtudiant) query.getResultList().get(0);
		 }

		 return session;
	}
	

}
