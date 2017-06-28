package com.adaming.myapp.examen.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.JDBCException;

import com.adaming.myapp.entities.Examen;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public interface IExamenDao{

	Examen addExamen(final Examen examen,final Long idEtudiant,final Long idModule,final Long idSession);
	
	Examen verifyExistingExamen(final Long idEtdudiant,final Long idModule, final Long idSession);
	
	List<Object[]> sqlQuiz(String sql) throws javax.persistence.PersistenceException;
}
