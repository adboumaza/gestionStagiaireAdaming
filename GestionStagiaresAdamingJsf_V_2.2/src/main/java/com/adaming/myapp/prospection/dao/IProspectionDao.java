package com.adaming.myapp.prospection.dao;

import java.util.List;

import com.adaming.myapp.entities.Prospection;

public interface IProspectionDao {

	Prospection addProspection(final Prospection entity,final Long idEtudiant);
	
	Prospection getProspectionByEtudiant(final Long idEtudiant);

    Prospection updateProspection(final Prospection prospection,final Long idEtudiant);
    
    List<Object[]> getAllProspectionBySession(final Long idSession);
    
    
}
