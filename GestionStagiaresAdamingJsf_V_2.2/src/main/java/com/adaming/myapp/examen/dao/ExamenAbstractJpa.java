package com.adaming.myapp.examen.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Examen;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.persistence.EntityManagerAbstract;

public abstract class ExamenAbstractJpa extends EntityManagerAbstract{


	/**
	 * Logger @see java.util.logging.Logger
	 **/

	public Examen addExamenAbstractJpa(final Examen examen, final Long idEtudiant,
			final Long idModule, final Long idSession) {

		Etudiant etudiant = entityManager.find(Etudiant.class, idEtudiant);
		Module module = entityManager.find(Module.class, idModule);
		SessionEtudiant session = entityManager.find(SessionEtudiant.class,
				idSession);
		examen.setEtudiant(etudiant);
		examen.setModule(module);
		examen.setSessionEtudiant(session);
		entityManager.persist(examen);
		return examen;
	}

	public Examen verifyExistingExamenAbstractJpa(final Long idEtdudiant, final Long idModule,
			final Long idSession) {
		final String SQL = "select e from Examen e join e.etudiant et join e.module m join e.sessionEtudiant se "
				+ "where et.idEtudiant =:x and m.idModule =:y and se.idSession =:z";
		Query query = entityManager.createQuery(SQL)
				.setParameter("x", idEtdudiant).setParameter("y", idModule)
				.setParameter("z", idSession);
		Examen examen = null;
		if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 examen = (Examen) query.getResultList().get(0);
		 }
		return examen;
	}
	
	public List<Object[]> sqlQuizAbstractJpa(final String sql) throws javax.persistence.PersistenceException{
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> result = null;
		
		
		if(sql != null){
			
           if(query.getResultList().size() == 0){
				return result;
			}
			else if(query.getResultList().size() > 1){
				result = query.getResultList();
				return result;
			}else if(query.getResultList().size() == 1){
				result = new ArrayList<Object[]>();
				result.add((Object[]) query.getResultList().get(0));
			}
		}
		
		return result;
	
	}
	
	
	/*@SuppressWarnings("unchecked")
	public List<Object[]> getAllQuizEntrainementAbstractJpa(){
		
		final String SQL =  "SELECT idModule,nomModule,sp.designation from module m "
						   +"JOIN specialite sp ON m.ID_SP_MODULE = sp.idSpecialite "
						   +"WHERE m.type ='Quiz' group by idModule";
		Query query = entityManager.createNativeQuery(SQL);
		return query.getResultList();
	}*/
	

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllQuizEntrainementAbstractJpa(){
		final String SQL =  "SELECT count(*) as nbrQuestions,idModule,nomModule,sp.designation from questions "
				           +"JOIN module m on m.idModule = ID_QUES_MODULE "
						   +"JOIN specialite sp on m.ID_SP_MODULE = sp.idSpecialite "
                           +"WHERE m.type ='Quiz' GROUP BY idModule ";
		Query query = entityManager.createNativeQuery(SQL);
		return query.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Set<Object[]> getAllQuestionsQuizByModuleAbstractJpa(String nomModule,Integer nbrQuestions)
	{
		final String SQL =   "SELECT m.nomModule,q.label,r.labelReponse FROM questions q "
							+"JOIN module m ON m.idModule = ID_QUES_MODULE "
						    +"JOIN reponses r ON r.ID_REP_QUES = q.idQuestions "
						    +"WHERE m.type ='Quiz' AND m.nomModule =:module ORDER BY q.idQuestions";
		Set<Object[]> results = null;
		if(nbrQuestions != null)
		{
			Query query = entityManager.createNativeQuery(SQL).setParameter("module", nomModule).setFirstResult(0).setMaxResults(nbrQuestions * 4);
			results =  new LinkedHashSet<Object[]>(query.getResultList());
		}
		
	    return results;
	}

}
