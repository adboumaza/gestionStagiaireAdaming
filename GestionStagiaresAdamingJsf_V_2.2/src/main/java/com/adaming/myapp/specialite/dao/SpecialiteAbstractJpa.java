package com.adaming.myapp.specialite.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.exception.AddSpecialiteException;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public abstract class SpecialiteAbstractJpa {

	@PersistenceContext
	private EntityManager em;

	public Specialite addSpecialiteAbstractJpa(final Specialite sp)
    {
		em.persist(sp);
		LoggerConfig.logInfo("la specialite " + sp.getIdSpecialite()
				+ "a bien été enregister");
		return sp;
	}


	public Specialite updateSpecialiteAbstractJpa(final Specialite sp) {
		em.merge(sp);
		LoggerConfig.logInfo("la specialite " + sp.getIdSpecialite()
				+ "a bien été Modifier");
		return sp;
	}


	public Specialite getSpecialiteByIdAbstractJpa(final Long idSpecialite) {
		Specialite sp = em.find(Specialite.class, idSpecialite);
		LoggerConfig.logInfo("la specialite " + sp.getIdSpecialite()
				+ "a bien été recupérer");
		return sp;
	}

	@SuppressWarnings("unchecked")
	public List<Specialite> getAllSpecV2AbstractJpa() {
		final String SQL = "SELECT s.idSpecialite,s.designation FROM Specialite s";
		Query query = em.createNativeQuery(SQL,Specialite.class);
		LoggerConfig.logInfo("il existe " + query.getResultList().size()
				+ " specialites dans l'applications");
		return query.getResultList();
	}
	
	public Specialite verifyExistingSpecialiteAbstractJpa(final String name){
		final String SQL = "select s from Specialite s where s.designation =:x";
        
		Specialite specialite = null;
        Query query =  em.createQuery(SQL)
				       .setParameter("x", name);
		 if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 specialite = (Specialite) query.getResultList().get(0);
		 }

		 return specialite;
	}
}
