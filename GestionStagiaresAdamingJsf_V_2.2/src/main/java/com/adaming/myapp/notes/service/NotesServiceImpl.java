package com.adaming.myapp.notes.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Note;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.notes.dao.INotesDao;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Transactional(readOnly=true)
public class NotesServiceImpl implements INotesService {

	private INotesDao dao;

	public void setDao(INotesDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<----------- Dao Notes Injected----------->");
	}

	@Override
	public List<Note> getAllNotes() {
		// TODO Auto-generated method stub
		return dao.getAllNotes();
	}

	@Override
	@Transactional(readOnly=false)
	public Note addNoteFinal(final Note note, final Long idSession, final Long idEtudiant,
			Long idModule) throws VerificationInDataBaseException {
		if (testNoteByEtuAndByModule(idSession, idModule, idEtudiant)) {

			throw new VerificationInDataBaseException("Examen déjà Réalisé !");
		}
		return dao.addNoteFinal(note, idSession, idEtudiant, idModule);
	}

	@Override
	public List<Object[]> getNotesBySessionAndModule(final Long idSession, final Long idMoule) {
		// TODO Auto-generated method stub
		return dao.getNotesBySessionAndModule(idSession, idMoule);
	}

	@Override
	public boolean testNoteByEtuAndByModule(final Long idSession, final Long idModule,
			final Long idEtudiant) {
		// TODO Auto-generated method stub
		return dao.testNoteByEtuAndByModule(idSession, idModule, idEtudiant);
	}

	@Override
	public List<Note> getAllNotesByStudent(final Long idEtudiant) {

		return dao.getAllNotesByStudent(idEtudiant);
	}

	@Override
	public  List<Object[]> getAllNotesBySession(final Long idSession) {
		// TODO Auto-generated method stub
		return dao.getAllNotesBySession(idSession);
	}

	@Override
	public Double getMoyenne(final Long idSession, final Long idModule) {
		// TODO Auto-generated method stub
		return dao.getMoyenne(idSession, idModule);
	}

	@Override
	public List<Note> getAllExamesEnCoursBySessionAndModule(final Long idSession,
			final Long idModule) throws VerificationInDataBaseException {
		List<Note> notes = dao.getAllExamesEnCoursBySessionAndModule(idSession, idModule);
		
		if(notes.size() > 0)
		{
			throw new VerificationInDataBaseException("Veuillez patienter l'examen en cours!");
		}
		
		return notes;
	}

	
	@Override
	public Double getMoyenneGeneralBySession(final Long idSession) {
		// TODO Auto-generated method stub
		return dao.getMoyenneGeneralBySession(idSession);
	}

	@Override
	public List<Object[]> getClassementGeneralBySession(final Long idSession) throws VerificationInDataBaseException {
		List<Object[]> classement = dao.getClassementGeneralBySession(idSession);
		if(classement.isEmpty()){
			throw new VerificationInDataBaseException("Le classement n'est pas disponible,Veuillez passer les examens..! ");
		}
		return classement;
	}

}
