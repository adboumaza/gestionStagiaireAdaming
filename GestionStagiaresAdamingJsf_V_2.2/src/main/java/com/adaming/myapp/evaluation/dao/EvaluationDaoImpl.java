package com.adaming.myapp.evaluation.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evaluation;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Prospection;
import com.adaming.myapp.etudiant.dao.EtudiantAbstractJpa;

public class EvaluationDaoImpl extends EtudiantAbstractJpa implements IEvaluationDao {
    
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Evaluation addEvaluation(final Evaluation evaluation,final Long idEtudiant,
			final Long idModule) {
		Module    module   = entityManager.find(Module.class, idModule);
		Etudiant  etudiant = entityManager.find(Etudiant.class, idEtudiant);
		evaluation.setEtudiant(etudiant);
		evaluation.setModule(module);
		entityManager.persist(evaluation);
		return evaluation;
	}

	@Override
	public Evaluation verifyExistingEvaluation(final Long idEtudiant,
			final Long idModule) {
		 final String SQL = "from Evaluation ev join fetch ev.etudiant et join fetch ev.module mo "
				+ "where et.idEtudiant =:x and mo.idModule=:y";
		 Evaluation evaluation = null;
		 Query query = entityManager.createQuery(SQL).setParameter("x",idEtudiant).setParameter("y",idModule);
		 if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 evaluation = (Evaluation) query.getResultList().get(0);
		 }
		return evaluation;
	}

	@Override
	public List<Etudiant> getAllEvaluationsBySessionAndModule(final Long idSession,
			final Long idModule) {
		return getEvaluationBySessionAndModuleAbstractJpa(idSession, idModule);
	}

	@Override
	public Evaluation getEvaluationByEtudiant(final Long idModule,final Long idEtudiant) {
		Query query = entityManager.createQuery("from Evaluation ev join fetch ev.etudiant et join fetch ev.module m where m.idModule=:x and et.idEtudiant =:y").setParameter("x",idModule).setParameter("y", idEtudiant);
		Evaluation evaluation = null;
		if(!query.getResultList().isEmpty() && query.getResultList() != null)
		{
			evaluation = (Evaluation) query.getResultList().get(0);
		}
		return evaluation;
	}

	@Override
	public Evaluation updateEvaluation(final Evaluation evaluation,final Long idModule,final Long idEtudiant) {
		Etudiant etudiant = entityManager.find(Etudiant.class, idEtudiant);
		Module module     = entityManager.find(Module.class, idModule);
		evaluation.setModule(module);
		evaluation.setEtudiant(etudiant);
        entityManager.merge(evaluation);
		return evaluation;
	}

}
