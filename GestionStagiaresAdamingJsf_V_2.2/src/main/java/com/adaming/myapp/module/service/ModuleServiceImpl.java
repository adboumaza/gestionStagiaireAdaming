package com.adaming.myapp.module.service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.atmosphere.config.service.Ready;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.exception.AddModuleException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.dao.IModuleDao;
import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Transactional(readOnly=true)
public class ModuleServiceImpl implements IModuleService{
    
	private IModuleDao dao;
    
	public void setDao(IModuleDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<------Dao Module a bien été injecté------>");
	}

	@Override
	@Transactional(readOnly=false)
	public Module addModule(final Module m, final Long idSpecialite) throws VerificationInDataBaseException {
		    Module module = verifyExistingModule(m.getNomModule());
          
			if(module != null)
			{
				throw new VerificationInDataBaseException("le Module "+m.getNomModule()+" existe Déja dans la spécialitée N° "+idSpecialite+" Merçi de bien vouloir changer la description du Module,  Ex:"+m.getNomModule()+" 2");
			}
	
		return dao.addModule(m, idSpecialite);
	}

	@Override
	public Module getModuleById(final Long idModule) {
		// TODO Auto-generated method stub
		return dao.getOne(idModule);
	}

	@Override
	@Transactional(readOnly=false)
	public Module updateModule(final Module m, final Long idSpecialite) {
		// TODO Auto-generated method stub
		return dao.updateModule(m, idSpecialite);
	}

	@Override
	public List<Module> getModulesBySpecialite(final Long idSpecialite) throws VerificationInDataBaseException {
		// TODO Auto-generated method stub
		return dao.getModulesBySpecialite(idSpecialite);
	}

	@Override
	public List<Module> getAllModules() {
		// TODO Auto-generated method stub
		return dao.getAll();
	}


	@Override
	public List<Object[]> getModulesBySessionV2(final Long idSession) {
		// TODO Auto-generated method stub
		return dao.getModulesBySessionV2(idSession);
	}

	@Override
	public List<Module> getModuleActivedBySession(final Long idSession) throws VerificationInDataBaseException {
		List<Module> modules = dao.getModuleActivedBySession(idSession); 
		if(modules.isEmpty()){
			throw new VerificationInDataBaseException("il n'y a aucun module activé ...");
		}
		return modules;
	}

	@Override
	public Set<Object[]> getModulesValideBySession(final Long idSession) throws VerificationInDataBaseException {
		Set<Object[]> modulesValide = dao.getModulesValideBySession(idSession);
		if(modulesValide.isEmpty()){
			throw new VerificationInDataBaseException("il n'y a aucun module Validé par la session N °"+ idSession);
		}
		return modulesValide;
	}

	@Override
	public Module verifyExistingModule(final String name) {
		// TODO Auto-generated method stub
		return dao.verifyExistingModule(name);
	}

	@Override
	@Transactional(readOnly = false)
	public Module desactivateModule(Module module) {
		// TODO Auto-generated method stub
		return dao.desactivateModule(module);
	}

	/*@Override
	@Transactional(readOnly=false)
	public void deactivateModule(final Long idModule) {
		dao.deactivateModule(idModule);
	}*/

}
