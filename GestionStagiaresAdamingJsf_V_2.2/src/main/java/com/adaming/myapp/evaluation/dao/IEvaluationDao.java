package com.adaming.myapp.evaluation.dao;

import java.util.List;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evaluation;

public interface IEvaluationDao {

	Evaluation addEvaluation(final Evaluation evaluation,
			final Long idEtudiant, final Long idModule);

	Evaluation verifyExistingEvaluation(final Long idEtudiant,
			final Long idModule);

	List<Etudiant> getAllEvaluationsBySessionAndModule(final Long idSession,
			final Long idModule);

	Evaluation getEvaluationByEtudiant(final Long idModule,
			final Long idEtudiant);
	
	Evaluation updateEvaluation(final Evaluation evaluation,final Long idModule,final Long idEtudiant);
	
	List<Object[]> getAllEvaluationsBySession(final Long idSession);
	
	
}
