package com.adaming.myapp.job;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.tools.LoggerConfig;

@Component("deactivatedModuleJob")
public class DeactivatedModuleJob {
    
	@Autowired
	private IModuleService serviceModule;
	
	private List<Module> modules;
	
	
	@Scheduled(cron = "0 01 18 ? * MON-FRI")
	public void deactivateAllModulesActivated(){
		modules = serviceModule.getAllModules();
		if(modules.size() > 0)
		{
			for(Module m:modules){
				m.setActif(false);
			    serviceModule.desactivateModule(m);
			    LoggerConfig.logInfo("Job : Désactivate Module "+m.getNomModule());
			}
		}
		
	}
}
