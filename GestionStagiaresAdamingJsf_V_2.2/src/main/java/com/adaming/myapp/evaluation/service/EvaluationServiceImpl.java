package com.adaming.myapp.evaluation.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evaluation;
import com.adaming.myapp.evaluation.dao.IEvaluationDao;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
@Transactional(readOnly = true)
public class EvaluationServiceImpl implements IEvaluationService {
   
	
	private IEvaluationDao dao;

	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IEvaluationDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<------------Dao Evaluation Injected-------->");
	}

	@Override
	@Transactional(readOnly = false)
	public Evaluation addEvaluation(final Evaluation evaluation,final Long idEtudiant,
			Long idModule) throws VerificationInDataBaseException {
		Evaluation entity = verifyExistingEvaluation(idEtudiant, idModule);
		if(entity != null){
			throw new VerificationInDataBaseException("Déja signalé...!");
		}
		return dao.addEvaluation(evaluation, idEtudiant, idModule);
	}

	@Override
	public Evaluation verifyExistingEvaluation(final Long idEtudiant, final Long idModule) {
		// TODO Auto-generated method stub
		return dao.verifyExistingEvaluation(idEtudiant, idModule);
	}

	@Override
	public List<Etudiant> getAllEvaluationsBySessionAndModule(final Long idSession,
			final Long idModule) {
		// TODO Auto-generated method stub
		return dao.getAllEvaluationsBySessionAndModule(idSession, idModule);
	}

	@Override
	public Evaluation getEvaluationByEtudiant(final Long idModule, final Long idEtudiant) {
		// TODO Auto-generated method stub
		return dao.getEvaluationByEtudiant(idModule, idEtudiant);
	}

	@Override
	public Evaluation updateEvaluation(final Evaluation evaluation,final Long idModule,final Long idEtudiant){
		// TODO Auto-generated method stub
		return dao.updateEvaluation(evaluation,idModule, idEtudiant);
	}

}
