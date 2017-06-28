package com.adaming.myapp.prospection.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Prospection;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.prospection.dao.IProspectionDao;
import com.adaming.myapp.tools.LoggerConfig;
@Transactional(readOnly = true)
public class ProspectionServiceImpl implements IProspectionService {
    
	private IProspectionDao dao;

	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IProspectionDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<-------Dao prospection injecetd----->");
	}



	@Override
	@Transactional(readOnly = false)
	public Prospection addProspection(final Prospection entity,final Long idEtudiant) throws VerificationInDataBaseException {
		Prospection prospection = dao.getProspectionByEtudiant(idEtudiant);
		if(prospection != null){
			throw new VerificationInDataBaseException("Déja signalé..!");
		}
		return dao.addProspection(entity, idEtudiant);
	}



	@Override
	public Prospection getProspectionByEtudiant(Long idEtudiant) {
		
		return dao.getProspectionByEtudiant(idEtudiant);
	}



	@Override
	public Prospection updateProspection(final Prospection prospection,
			final Long idEtudiant) {
		// TODO Auto-generated method stub
		return dao.updateProspection(prospection, idEtudiant);
	}



	@Override
	public List<Object[]> getAllProspectionBySession(final Long idSession) throws VerificationInDataBaseException {
		List<Object[]> results = dao.getAllProspectionBySession(idSession);
		if(results.isEmpty()){
			throw new VerificationInDataBaseException("la prospection n'est pas encore signalée");
		}
		return results;
	}

}
