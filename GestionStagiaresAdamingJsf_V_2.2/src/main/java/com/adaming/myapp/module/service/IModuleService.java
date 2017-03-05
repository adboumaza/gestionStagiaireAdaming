package com.adaming.myapp.module.service;

import java.util.List;
import java.util.Set;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.exception.AddModuleException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public interface IModuleService {
	
	 List<Module> getAllModules();

	 Module addModule(final Module m, final Long idSpecialite) throws VerificationInDataBaseException;

	 Module getModuleById(final Long idModule);

	 Module updateModule(final Module m,  final Long idSpecialite);

	 List<Module> getModulesBySpecialite(final Long idSpecialite) throws VerificationInDataBaseException;
	
	 List<Module> getModulesBySession(final Long idSession);
	 
	 List<Object[]> getModulesBySessionV2(final Long idSession);
	 
	 List<Module> getModuleActivedBySession(final Long idSession) throws VerificationInDataBaseException;
	 
	 Set<Object[]> getModulesValideBySession(final Long idSession) throws VerificationInDataBaseException;
	 
	 Module verifyExistingModule(final String name);
	 
	// void deactivateModule(Long idModule);
}
