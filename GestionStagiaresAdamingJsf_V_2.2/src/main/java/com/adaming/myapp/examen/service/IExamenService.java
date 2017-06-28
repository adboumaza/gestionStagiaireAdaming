package com.adaming.myapp.examen.service;


import java.util.List;

import org.hibernate.JDBCException;

import com.adaming.myapp.entities.Examen;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IExamenService {

	Examen addExamen(final Examen examen,final Long idEtudiant,final Long idModule,final Long idSession) throws VerificationInDataBaseException;
	
	Examen verifyExistingExamen(final Long idEtdudiant,final Long idModule,final Long idSession);
	
	List<Object[]> sqlQuiz(String sql) throws  javax.persistence.PersistenceException;
}
