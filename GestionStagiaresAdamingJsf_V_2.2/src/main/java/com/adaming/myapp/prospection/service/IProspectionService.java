package com.adaming.myapp.prospection.service;

import com.adaming.myapp.entities.Prospection;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IProspectionService {

	Prospection addProspection(final Prospection entity,final Long idEtudiant) throws VerificationInDataBaseException;

	Prospection getProspectionByEtudiant(final Long idEtudiant);
    
	Prospection updateProspection(final Prospection prospection,final Long idEtudiant);

}
