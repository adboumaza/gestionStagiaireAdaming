package com.adaming.myapp.question.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.Reponses;
import com.adaming.myapp.persistence.EntityManagerAbstract;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public abstract class QuestionAbstractJpa extends EntityManagerAbstract{

	
	public Questions addQuestionsAbstractJpa(final Questions question,final Long idModule,final List<Reponses> reponses){
		Module module = entityManager.find(Module.class, idModule);
		for(Reponses r:reponses){
			r.setQuestions(question);
		}
		question.setModule(module);
		question.setReponses(reponses);
		entityManager.persist(question);
		return question;
	}
	
	@SuppressWarnings("unchecked")
	public Set<Questions> getQuestionsByModuleAbstracJpa(final Long idModule){
		final String SQL = "from Questions q join fetch q.reponses r join fetch q.module m where m.idModule =:x ORDER BY q.idQuestions";
		Query query = entityManager.createQuery(SQL).setParameter("x",idModule);
		Set<Questions> questions = new HashSet<Questions>(query.getResultList());
		LoggerConfig.logInfo("la liste des Questions par Module");
		return questions;
	}
	
	public Questions verifyExistingQuestionsAbstractJpa(final String label){
		 final String SQL = "select q from Questions q where q.label =:x";     
		 Questions question = null;
         Query query =  entityManager.createQuery(SQL)
				       .setParameter("x", label);
		 if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 question = (Questions) query.getResultList().get(0);
		 }

		 return question;
	}
	
	@SuppressWarnings("unchecked")
	public Set<Reponses> getAllReponsesByModuleAbstractJpa(final Long idModule){
		 
		 final String SQL ="from Reponses r join fetch r.questions q join fetch q.module m where m.idModule =:x";
		 
		 Query query = entityManager.createQuery(SQL).setParameter("x",idModule);
		 Set<Reponses> reponses = new HashSet<Reponses>(query.getResultList());
		 return reponses;
	 }
	

}
