package com.adaming.myapp.job;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.module.service.IModuleService;

@Component
public class DeactivatedModuleJob {
    
	@Inject
	private IModuleService serviceModule;
	
	private List<Module> modules;
	
	public void deactivateAllModulesActivated(){
		modules = serviceModule.getAllModules();
		if(modules.size() > 0)
		{
			for(Module m:modules){
			//	serviceModule.deactivateModule(m.getIdModule());
			}
		}
		
	}
}
