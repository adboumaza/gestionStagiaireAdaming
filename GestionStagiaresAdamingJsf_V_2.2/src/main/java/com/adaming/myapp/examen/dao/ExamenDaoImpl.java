package com.adaming.myapp.examen.dao;
import java.util.List;
import java.util.Set;

import com.adaming.myapp.entities.Examen;


public class ExamenDaoImpl extends ExamenAbstractJpa implements IExamenDao{

	@Override
	public Examen addExamen(final Examen examen, final Long idEtudiant, final Long idModule,
			final Long idSession) {
		// TODO Auto-generated method stub
		return addExamenAbstractJpa(examen, idEtudiant, idModule, idSession);
	}

	@Override
	public Examen verifyExistingExamen(final Long idEtdudiant, final Long idModule, final Long idSession) {
		// TODO Auto-generated method stub
		return verifyExistingExamenAbstractJpa(idEtdudiant, idModule, idSession);
	}

	@Override
	public List<Object[]> sqlQuiz(String sql) throws javax.persistence.PersistenceException {
		// TODO Auto-generated method stub
		return sqlQuizAbstractJpa(sql);
	}

	@Override
	public List<Object[]> getAllQuizEntrainement() {
		// TODO Auto-generated method stub
		return getAllQuizEntrainementAbstractJpa();
	}

	@Override
	public Set<Object[]> getAllQuestionsQuizByModule(String nomModule,Integer nbrQuestions) {
		// TODO Auto-generated method stub
		return getAllQuestionsQuizByModuleAbstractJpa(nomModule, nbrQuestions);
	}

	

}
