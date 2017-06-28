package com.adaming.myapp.examen.service;

import java.sql.SQLException;
import java.util.List;

import javax.management.Query;

import org.hibernate.JDBCException;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Examen;
import com.adaming.myapp.examen.dao.IExamenDao;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
@Transactional(readOnly = true)
public class ExamenServiceImpl implements IExamenService {
    
	private IExamenDao dao;
	
	public void setDao(IExamenDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<-------Dao Examen Injecetd----->");
	}
	
	@Override
	@Transactional(readOnly = false)
	public Examen addExamen(final Examen examen, final Long idEtudiant, final Long idModule,
			Long idSession) throws VerificationInDataBaseException {
		Examen examenExisting= verifyExistingExamen(idEtudiant, idModule, idSession);
		if(examenExisting != null){
			throw new VerificationInDataBaseException("l'examen en cours...");
		}
		
		return dao.addExamen(examen, idEtudiant, idModule, idSession);
	}

	@Override
	public Examen verifyExistingExamen(final Long idEtdudiant, final Long idModule, final Long idSession) {
		
		return dao.verifyExistingExamen(idEtdudiant, idModule, idSession);
	}

	@Override
	public List<Object[]> sqlQuiz(final String sql) throws javax.persistence.PersistenceException {
		// TODO Auto-generated method stub
		return dao.sqlQuiz(sql);
	}

}
