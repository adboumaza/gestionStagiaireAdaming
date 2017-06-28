package com.adaming.myapp.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.examen.service.IExamenService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

@SuppressWarnings("serial")
@Component("quizBean")
@Scope(value = "session")
public class QuizBean implements Serializable {
	
	@Inject
	private IExamenService examenService;

	private String sql;
	private List<Object[]> results;
	
	public String initEntrainementQuiz(){
		return "quiz_entrainement?faces-redirect=true";
	}
	
	public String init(){
		sql = "";
		return "quiz_sql?faces-redirect=true";
	}
	
	public void executeSql(){
		try {
			results = examenService.sqlQuiz(sql);
			System.out.println(results);
		} catch (javax.persistence.PersistenceException e) {
		   LoggerConfig.logInfo("e"+e);
		   LoggerConfig.logInfo("mssage"+e.getMessage());
		   LoggerConfig.logInfo("toString"+e.toString());
		   LoggerConfig.logInfo("toString"+e.getCause().fillInStackTrace());
		   LoggerConfig.logInfo("cause"+e.getCause());
		   LoggerConfig.logInfo("e.sqlException"+e.getCause().getLocalizedMessage());
		   Utilitaire.displayMessageError(e.getMessage());
		   LoggerConfig.logInfo("e.errorCode"+e.getStackTrace());

		}
		
		
	}
	
	
	

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the results
	 */
	public List<Object[]> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Object[]> results) {
		this.results = results;
	}
	
	
	
	
	

}
