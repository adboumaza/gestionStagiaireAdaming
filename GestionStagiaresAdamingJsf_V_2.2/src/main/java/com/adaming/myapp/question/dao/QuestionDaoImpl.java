package com.adaming.myapp.question.dao;

import java.util.List;
import java.util.Set;

import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.Reponses;
import com.adaming.myapp.exception.AddQuestionException;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public class QuestionDaoImpl extends QuestionAbstractJpa implements
		IQuestionDao {

	@Override
	public Questions addQuestions(final Questions q, final Long idModule,
			final List<Reponses> reponses) {
		// TODO Auto-generated method stub
		return addQuestionsAbstractJpa(q, idModule, reponses);
	}

	@Override
	public Set<Questions> getQuestionsByModule(final Long idModule) {
		// TODO Auto-generated method stub
		return getQuestionsByModuleAbstracJpa(idModule);
	}

	@Override
	public Questions verifyExistingQuestions(final String label) {
		// TODO Auto-generated method stub
		return verifyExistingQuestionsAbstractJpa(label);
	}

	@Override
	public Set<Reponses> getAllReponsesByModule(final Long idModule) {
		// TODO Auto-generated method stub
		return getAllReponsesByModuleAbstractJpa(idModule);
	}

	

}
