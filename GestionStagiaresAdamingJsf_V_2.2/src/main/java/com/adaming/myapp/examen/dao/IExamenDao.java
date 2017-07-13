package com.adaming.myapp.examen.dao;

import java.util.List;
import java.util.Set;

import com.adaming.myapp.entities.Examen;

public interface IExamenDao{

	Examen addExamen(final Examen examen,final Long idEtudiant,final Long idModule,final Long idSession);
	
	Examen verifyExistingExamen(final Long idEtdudiant,final Long idModule, final Long idSession);
	
	List<Object[]> sqlQuiz(final String sql) throws javax.persistence.PersistenceException;
	
	List<Object[]> getAllQuizEntrainement();
	
	Set<Object[]> getAllQuestionsQuizByModule(String nomModule,Integer nbrQuestions);
}
