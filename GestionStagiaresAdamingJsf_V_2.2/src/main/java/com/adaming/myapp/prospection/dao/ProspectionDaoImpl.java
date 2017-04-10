package com.adaming.myapp.prospection.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Prospection;

public class ProspectionDaoImpl implements IProspectionDao{
    
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Prospection addProspection(final Prospection entity,final Long idEtudiant) {
		Etudiant etudiant = entityManager.find(Etudiant.class, idEtudiant);
		entity.setEtudiant(etudiant);
		entityManager.persist(entity);
		return entity;
	}


	@Override
	public Prospection getProspectionByEtudiant(final Long idEtudiant) {
		Query query = entityManager.createQuery("from Prospection p join fetch p.etudiant et where et.idEtudiant =:x").setParameter("x",idEtudiant);
		Prospection prospection = null;
		if(!query.getResultList().isEmpty() && query.getResultList() != null)
		{
			prospection = (Prospection) query.getResultList().get(0);
		}
		return prospection;
	}


	@Override
	public Prospection updateProspection(final Prospection prospection,
			final Long idEtudiant) {
		Etudiant etudiant = entityManager.find(Etudiant.class, idEtudiant);
		prospection.setEtudiant(etudiant);
		entityManager.merge(prospection);
		return prospection;
	}

}
