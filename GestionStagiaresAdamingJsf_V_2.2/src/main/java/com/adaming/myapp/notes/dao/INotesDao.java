package com.adaming.myapp.notes.dao;

import java.util.List;

import com.adaming.myapp.entities.Note;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public interface INotesDao {

	 Note addNoteFinal(final Note note, final Long idSession, final Long idEtudiant,
			Long idModule);


	 List<Object[]> getNotesBySessionAndModule(final Long idSession, final Long idMoule);

	 boolean testNoteByEtuAndByModule(final Long idSession, final Long idModule,
			Long idEtudiant);
	
	 List<Note> getAllNotesByStudent(final Long idEtudiant);
	
	 List<Object[]> getAllNotesBySession(final Long idSession);
	 
	 Double getMoyenne(final Long idSession,final Long idModule);
	 
	 List<Note> getAllExamesEnCoursBySessionAndModule(final Long idSession,final Long idModule);
	
	 Double getMoyenneGeneralBySession(final Long idSession);
     
	 List<Object[]> getClassementGeneralBySession(final Long idSession);
}
