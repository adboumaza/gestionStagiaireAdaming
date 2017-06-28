package com.adaming.myapp.session.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Salle;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.Site;
import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;

/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public abstract class SessionAbstractJpa {

	@PersistenceContext
	private EntityManager em;

	public SessionEtudiant addSessionStudentAbstractJpa(
			final SessionEtudiant se, final Long idSpecialite,
			final Long idSite, final Long idSalle) throws AddSessionException {
		Specialite sp = em.find(Specialite.class, idSpecialite);
		Site site = em.find(Site.class, idSite);
		Salle salle = em.find(Salle.class, idSalle);
		se.setSpecialite(sp);
		se.setSite(site);
		se.setSalle(salle);
		sp.getSessionEtudiant().add(se);
		em.persist(se);
		LoggerConfig.logInfo(" la session+" + se.getIdSession()
				+ "a bien été enregistrer");
		return se;
	}

	public SessionEtudiant updateSessionEtudianAbstractJpa(
			final SessionEtudiant se, final Long idSpecialite,
			final Long idSite, final Long idSalle) {
		Specialite sp = em.find(Specialite.class, idSpecialite);
		Site site = em.find(Site.class, idSite);
		Salle salle = em.find(Salle.class, idSalle);
		se.setSpecialite(sp);
		se.setSalle(salle);
		se.setSite(site);
		em.merge(se);
		LoggerConfig.logInfo(" la session+" + se.getIdSession()
				+ "a bien été Modifié");
		return se;
	}

	public SessionEtudiant getSessionEtudiantByIdAbstractJpa(
			final Long idSessionEtudiant) {
		final String SQL = "select p from SessionEtudiant p "
				+ "left join fetch p.site left join fetch p.salle left join fetch p.specialite "
				+ "where p.id = :id";

		return (SessionEtudiant) em.createQuery(SQL)
				.setParameter("id", idSessionEtudiant).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getSallesDisponibleAbstracJpa(final Long idSalle) {
		Query query = em
				.createQuery("Select se.idSession,se.dateDebute,se.dateFin,sa.numeroSalle FROM SessionEtudiant se join se.salle sa where sa.idSalle=:x order by se.idSession desc");
		query.setParameter("x", idSalle);
		query.setMaxResults(1);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSessionsInProgressAbstractJpaV2() {
		final String SQL = "Select se.idSession,sp.designation,DATE_FORMAT(se.dateDebute,'%d-%m-%Y'),DATE_FORMAT(se.dateFin,'%d-%m-%Y'),si.nomSite,sa.numeroSalle,se.nombreJours "
				+ "From sessionEtudiant se "
				+ "join Specialite sp on se.ID_SPEC_SESSION = sp.idSpecialite "
				+ "join site si on se.ID_SITE_SESSION = si.idSite "
				+ "join salle sa on se.ID_SALLE_SESSION = sa.idSalle "
				+ " where se.dateFin >= CURRENT_DATE";
		Query query = em
				.createNativeQuery(SQL);
		LoggerConfig.logInfo("il existe" + query.getResultList().size()
				+ " sessions en cours");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSessionsAbstractJpaV2() {
		final String SQL = "Select se.idSession,sp.designation,se.dateDebute,se.dateFin,site.nomSite,sa.numeroSalle From SessionEtudiant se join se.specialite sp join se.site site join se.salle sa Order By se.dateFin DESC";
		Query query = em.createQuery(SQL);
		LoggerConfig.logInfo("il existe" + query.getResultList().size());
		return query.getResultList();
	}

	public SessionEtudiant getSessionByFormateurAbstractJpa(
			final Long idFormateur) {
		final String SQL = "select distinct s from SessionEtudiant s "
				+ "left join fetch s.site left join fetch s.salle left join fetch s.specialite "
				+ "left join fetch s.formateurs f left join s.etudiants e where f.idFormateur = :id";
		SessionEtudiant session = null;
		Query query = em.createQuery(SQL).setParameter("id", idFormateur);
		if (query.getResultList() != null && !query.getResultList().isEmpty()
				&& query.getResultList().size() == 1) {
			session = (SessionEtudiant) query.getResultList().get(0);
			LoggerConfig.logInfo("size == 1" + query.getResultList() + ""
					+ (query.getResultList().get(0)));
			LoggerConfig.logInfo("mysession" + session);
		}

		if (query.getResultList() != null && !query.getResultList().isEmpty()
				&& query.getResultList().size() > 1) {
			session = (SessionEtudiant) query.getResultList().get(
					query.getResultList().size() - 1);
			LoggerConfig.logInfo("size > 1" + query.getResultList()
					+ "size -1," + (query.getResultList().size() - 1)
					+ "get 0, " + (query.getResultList().get(0)));
			LoggerConfig.logInfo("mysession" + session);
		}

		return session;
	}

	public SessionEtudiant getSessionByEtudiantAbstractJpa(final Long idEtudiant) {
		final String SQL = "select distinct s from SessionEtudiant s "
				+ "join fetch s.site join fetch s.salle join fetch s.specialite "
				+ "left join s.etudiants e where e.idEtudiant = :id ";
		SessionEtudiant session = null;
		Query query = em.createQuery(SQL).setParameter("id", idEtudiant);
		if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			session = (SessionEtudiant) query.getResultList().get(0);
		}

		return session;
	}

	@SuppressWarnings("unchecked")
	public List<Evenement> getMoreInformationBySessionAbstractJpa(
			final Long idSession, final String choix) {

		StringBuilder req = new StringBuilder();
		final String SQL = "select e from Evenement e join fetch "
				+ "e.etudiant et join fetch e.sessionEtudiant se "
				+ "where se.idSession =:id and ";
		req.append(SQL);
		if (choix.equals("TOP")) {
			final String reqTeamLeader = "TYPE_EVENEMENT =:t ";
			req.append(reqTeamLeader);
			System.out.println(req.toString());
		}
		if (choix.equals("WARNING")) {
			final String reqWarning = "TYPE_EVENEMENT =:w ";
			req.append(reqWarning);
			System.out.println(req.toString());
		}
		if (choix.equals("RETARD")) {
			final String reqRetard = "TYPE_EVENEMENT =:r ";
			req.append(reqRetard);
			System.out.println(req.toString());
		}
		if (choix.equals("ABSENCE")) {
			final String reqAbesence = "TYPE_EVENEMENT =:a ";
			req.append(reqAbesence);
			System.out.println(req.toString());
		}

		Query query = em.createQuery(req.toString()).setParameter("id",
				idSession);
		if (choix.equals("TOP")) {
			query.setParameter("t", "TOP");
		}
		if (choix.equals("WARNING")) {
			query.setParameter("w", "WARNING");
		}
		if (choix.equals("RETARD")) {
			query.setParameter("r", "RETARD");
		}
		if (choix.equals("ABSENCE")) {
			query.setParameter("a", "ABSENCE");
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSessionsBetwenTwoDatesAbstractJpa(
			final Date dateDebut, final Date dateFin) {
        
		final String SQL = "select se.idSession,DATE_FORMAT(se.dateDebute,'%d-%m-%Y'),DATE_FORMAT(se.dateFin,'%d-%m-%Y'),si.ville,sp.designation from "
				          +"sessionEtudiant se join site si on se.ID_SITE_SESSION = si.idSite "
				          +"join Specialite sp on sp.idSpecialite = se.ID_SPEC_SESSION "
                          +" where (:d >= se.dateDebute and :f <= se.dateFin )"
                          +" or (:f >= se.dateDebute and :d <= se.dateFin)";

		Query query = em.createNativeQuery(SQL).setParameter("d", dateDebut).setParameter("f", dateFin);
		return query.getResultList();

	}

	@SuppressWarnings({"unchecked"})
	public List<Object[]> getAllSessionsQuiTermineCetteSemaineAbstractJpa() {

		final String SQL = "select se.idSession,f.nom,f.prenom,se.dateDebute,se.dateFin,si.nomSite,si.ville,sa.numeroSalle "
				+"from TB_SES_FORMATEUR tbf join sessionEtudiant se on se.idSession = tbf.sessionsEtudiant_idSession "
				+"join formateur f on f.idFormateur = tbf.formateurs_idFormateur "
				+"join site si on si.idSite = se.id_site_session "
				+"join salle sa on sa.idSalle = se.id_salle_session "
				+" where DATE(now()) = DATE( DATE_SUB( se.dateFin , INTERVAL 1 WEEK ) )";

		Query query = em.createNativeQuery(SQL);
		List<Object[]> sessions = query.getResultList();

		return sessions;
	}

}
