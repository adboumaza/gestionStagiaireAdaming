package com.adaming.myapp.examen.dao;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import com.adaming.myapp.entities.Examen;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class ExamenDaoImpl extends ExamenAbstractJpa implements IExamenDao{

	@Override
	public Examen addExamen(final Examen examen, final Long idEtudiant, final Long idModule,
			final Long idSession) {
		// TODO Auto-generated method stub
		return addExamenAbstractJpa(examen, idEtudiant, idModule, idSession);
	}

	@Override
	public Examen verifyExistingExamen(final Long idEtdudiant, final Long idModule, final Long idSession) {
		// TODO Auto-generated method stub
		return verifyExistingExamenAbstractJpa(idEtdudiant, idModule, idSession);
	}

	@Override
	public List<Object[]> sqlQuiz(String sql) throws javax.persistence.PersistenceException {
		// TODO Auto-generated method stub
		return sqlQuizAbstractJpa(sql);
	}

}
