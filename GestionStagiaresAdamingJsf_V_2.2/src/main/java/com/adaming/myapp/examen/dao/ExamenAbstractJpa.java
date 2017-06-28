package com.adaming.myapp.examen.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



import org.hibernate.JDBCException;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Examen;
import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.SessionEtudiant;

public abstract class ExamenAbstractJpa {

	/**
	 * @see javax.persistence.EntityManager
	 **/
	@PersistenceContext
	private EntityManager entityManager;

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

}
