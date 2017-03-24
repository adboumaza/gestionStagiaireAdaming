package com.adaming.myapp.notes.dao;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hamcrest.core.IsSame;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Note;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public class NotesDaoImpl implements INotesDao {

	@PersistenceContext
	private EntityManager em;


	@Override
	@SuppressWarnings("unchecked")
	public List<Note> getAllNotes() {
		Query query = em.createQuery("from Note n");
		LoggerConfig.logInfo("Liste des Notes : " + query.getResultList().size());
		return query.getResultList();
	}

	@Override
	public Note addNoteFinal(final Note note, final Long idSession, final Long idEtudiant,
			Long idModule) {
		SessionEtudiant s = em.find(SessionEtudiant.class, idSession);
		note.setSessionEtudiant(s);
		Etudiant e = em.find(Etudiant.class, idEtudiant);
		note.setEtudiant(e);
		Module m = em.find(Module.class, idModule);
		note.setModule(m);
		em.persist(note);
		LoggerConfig.logInfo("la note final de l'etudiant " + idEtudiant
				+ " est ajoutée avec success");

		return note;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getNotesBySessionAndModule(final Long idSession, final Long idMoule) {
		final String SQL = "Select n.idNote,n.score,e.nomEtudiant,e.prenomEtudiant,m.nomModule,m.idModule,se.idSession FROM Note n "
						 + "join n.etudiant e join n.module m join n.sessionEtudiant se where "+
				           "se.idSession=:x and m.idModule =:y ORDER BY n.score DESC";
		Query query = em
				.createQuery(SQL);
		query.setParameter("x", idSession);
		query.setParameter("y", idMoule);
		LoggerConfig.logInfo("la liste des notes de la session Numero  " + idSession
				+ " est : " + query.getResultList().size());
		return query.getResultList();
	}

	public boolean testNoteByEtuAndByModule(final Long idSession, final Long idModule,
			Long idEtudiant) {
		Query query = em
				.createQuery("SELECT count(*) from Note n where n.sessionEtudiant.idSession=:x and n.module.idModule=:y and n.etudiant.idEtudiant=:z");
		query.setParameter("x", idSession);
		query.setParameter("y", idModule);
		query.setParameter("z", idEtudiant);
		Long count = (Long) query.getSingleResult();
		LoggerConfig.logError("::::::::: Resultat count: " + count);
		if (count == 1) {
			return true;
		}

		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> getAllNotesByStudent(final Long idEtudiant) {
		final String SQL = "from Note n join fetch n.module m join fetch n.sessionEtudiant se join fetch n.etudiant et where et.idEtudiant=:x";
		Query query = em.createQuery(SQL).setParameter("x",idEtudiant);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public  List<Object[]> getAllNotesBySession(final Long idSession) {
		final String SQL = "Select n.idNote,n.score,m.idModule,se.idSession From Note n join n.module m join n.etudiant e join n.sessionEtudiant se where se.idSession=:x";
		Query query = em.createQuery(SQL).setParameter("x",idSession);
		return query.getResultList();
	}

	@Override
	public Double getMoyenne(final Long idSession, final Long idModule) {
		//final String SQL = "Select AVG(n.score) FROM Note n join n.sessionEtudiant se join n.module m where se.idSession=:x and m.idModule=:y";
		final String SQL = "Select AVG(n.score) FROM Note n join sessionEtudiant se on se.idSession = n.id_ses_note  join module m on m.idModule = n.id_mod_note where se.idSession=:x and m.idModule=:y";
		Query query = em.createNativeQuery(SQL).setParameter("x",idSession).setParameter("y",idModule);
		Double moyenne =(Double) query.getSingleResult();
		return moyenne;
	}

	@Override
	public List<Note> getAllExamesEnCoursBySessionAndModule(final Long idSession,
			final Long idModule) {
		//final String SQL = "Select n FROM Note n right join n.etudiant e join n.sessionEtudiant se join n.module m where se.idSession=:x and m.idModule=:y and n.idNote IS NULL";
		
		//final String SQL = "select n.idNote from note n right join etudiant e on n.id_etu_note = e.idEtudiant join module m on n.id_mod_Note = m.idModule join sessionEtudiant se on se.idSession = n.id_ses_note where n.idNote IS NULL and m.idModule =? and se.idSession = ?";
		//final String SQL = "SELECT * FROM etudiant e WHERE not EXISTS (SELECT * FROM note n join module m on m.idModule = n.id_mod_note join sessionEtudiant se on se.idSession = n.id_ses_note WHERE n.id_etu_note = e.idEtudiant and se.idSession  =:x and m.idModule =:y)";
		final String SQL = "select * from etudiant e join sessionEtudiant se on e.id_sess_etudiant = se.idSession where se.idSession =:x and  not exists (select * from note n join module m on m.idModule = n.id_mod_note where n.id_etu_note = e.idEtudiant and m.idModule =:y)";
		Query query = em.createNativeQuery(SQL).setParameter("x",idSession).setParameter("y",idModule);
		LoggerConfig.logInfo("query "+query.getResultList().size());
		return query.getResultList();
	}

}
