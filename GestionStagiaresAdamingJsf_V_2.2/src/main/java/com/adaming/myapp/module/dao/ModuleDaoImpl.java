package com.adaming.myapp.module.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.exception.AddModuleException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.persistence.AbstractJpaDao;
import com.adaming.myapp.tools.LoggerConfig;

public class ModuleDaoImpl extends AbstractJpaDao<Module> implements IModuleDao{
    
	
	@Override
	public List<Module> getAll() {
		List<Module> modules = getAllAbstractJpa();
		LoggerConfig.logInfo("il existe "+modules.size()+"Modules");
		return modules;
	}

	@Override
	public Module getOne(final Long id) {
		 final String SQL = "select distinct m from Module m " +
                 "left join fetch m.specialite " +
                 "where m.id = :x";

		 return (Module) em.createQuery(SQL)
		       .setParameter("x", id)
		       .getSingleResult();
	}

	@Override
	public Module addModule(final Module m, final Long idSpecialite){
		Specialite s  = em.find(Specialite.class, idSpecialite);
		m.setSpecialite(s);
		em.persist(m);
		LoggerConfig.logInfo("le module"+m.getIdModule()+" a bien �t� enregistrer");
		return m;
	}

	@Override
	public Module updateModule(final Module m, final Long idSpecialite) {
		Specialite s = em.find(Specialite.class,idSpecialite);
		m.setSpecialite(s);
		em.merge(m);
		LoggerConfig.logInfo("le module "+m.getIdModule()+" a bien �t� Modifi�");
		return m;
	}

	@Override
	public List<Module> getModulesBySpecialite(final Long idSpecialite) throws VerificationInDataBaseException {
		Specialite s  = em.find(Specialite.class,idSpecialite);
		List<Module> modules=null;
		if(s != null){
			modules = s.getModules();
			if(modules.size() ==0){
				throw new VerificationInDataBaseException("Il n'existe aucun module dans le cursus "+s.getDesignation());
			}
			LoggerConfig.logInfo("il existes"+modules.size()+" dans la specialite"+s.getDesignation());
		}
		return modules;
	}

	@Override
	public List<Module> getModulesBySession(final Long idSession) {
		SessionEtudiant session = em.find(SessionEtudiant.class,idSession);
		List<Module> modules = session.getSpecialite().getModules();
		LoggerConfig.logInfo("il existe "+modules.size()+" modules dans la sessions Num�ro "+idSession);
		return modules;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getModulesBySessionV2(final Long idSession) {
		final String SQL = "Select m.idModule,m.nomModule,m.actif,sp.idSession From Module m join m.specialite.sessionEtudiant sp where sp.idSession =:x";
		Query query = em.createQuery(SQL);
		query.setParameter("x",idSession);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> getModuleActivedBySession(final Long idSession) {
		final String SQL ="from Module m join fetch m.specialite sp join fetch sp.sessionEtudiant se where se.idSession =:x and m.actif = TRUE";
		Query query = em.createQuery(SQL).setParameter("x",idSession);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Object[]> getModulesValideBySession(final Long idSession) {
		final String SQL = "Select DISTINCT m.idModule,m.nomModule,m.actif,se.idSession FROM Note n "
				         + "join n.module m join n.sessionEtudiant se where se.idSession =:x and n.score IS NOT NULL";
		Query query = em.createQuery(SQL).setParameter("x",idSession);
		Set<Object[]> modulesValides = new HashSet<Object[]>(query.getResultList());
		return modulesValides;
	}

	@Override
	public Module verifyExistingModule(final String name) {
		 final String SQL = "select distinct m from Module m where m.nomModule =:x";     
		 Module module = null;
         Query query =  em.createQuery(SQL)
				       .setParameter("x", name);
		 if(query.getResultList() != null && !query.getResultList().isEmpty()){
			 module = (Module) query.getResultList().get(0);
		 }

		 return module;
	}

}
