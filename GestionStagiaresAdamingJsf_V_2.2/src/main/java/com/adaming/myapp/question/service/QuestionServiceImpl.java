package com.adaming.myapp.question.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.Reponses;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.question.dao.IQuestionDao;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Transactional(readOnly = true)
public class QuestionServiceImpl implements IQuestionService {

	private IQuestionDao dao;

	public void setDao(IQuestionDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<--------Dao Question Injected----->");
	}

	@Override
	@Transactional(readOnly = false)
	public Questions addQuestions(final Questions q, final Long idModule,
			final List<Reponses> reponses) throws VerificationInDataBaseException {
		Questions question = verifyExistingQuestions(q.getLabel());
		if (question != null) {
			throw new VerificationInDataBaseException("La Question "
					+ q.getLabel() + " Existe Déja dans le Module N° "
					+ idModule);
		}
		return dao.addQuestions(q, idModule, reponses);
	}

	@Override
	public Set<Questions> getQuestionsByModule(final Long idModule) {
		// TODO Auto-generated method stub
		return dao.getQuestionsByModule(idModule);
	}

	@Override
	public Questions verifyExistingQuestions(final String label) {
		// TODO Auto-generated method stub
		return dao.verifyExistingQuestions(label);
	}

	@Override
	public Set<Reponses> getAllReponsesByModule(final Long idModule) {
		// TODO Auto-generated method stub
		return dao.getAllReponsesByModule(idModule);
	}

	
}
