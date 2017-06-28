package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Questions;
import com.adaming.myapp.entities.Reponses;
import com.adaming.myapp.exception.AddModuleException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.question.service.IQuestionService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */

@SuppressWarnings("serial")
@Component("moduleBean")
@ViewScoped
public class ModuleBean implements Serializable {

	/**
	 * LOGGER LOG4j 
	 * @see org.apache.log4j.Logger
	 */
  
    
	@Inject
	private IModuleService serviceModule;
	@Inject
	private Bean bean;
	
	private Long idModule;
	@NotEmpty
	@NotBlank
	@Size(max=30,min=2)
	private String nomModule;
	
	@NotEmpty
	private String type;
	
	private Long idSpecialite;
	private List<Module> modulesBySpecialites;
	private Module m;
	
	/**@method add new Module*/
	public void addModule(){
		m = createModule();
		try {
			serviceModule.addModule(m, idSpecialite);
			Utilitaire.displayMessageInfo("Le Module "+m.getNomModule()+" à bien été ajouter avec succès");
			reset();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
	}

	/**
	 * @create New Object Module
	 **@return Object Module 
	 **@factory.create.method
	 */
	private Module createModule() {
		m = FactoryBean.getModuleFactory().create("Module");
		m.setNomModule(nomModule);
		m.setType(type);
		if(m.getType().equals("Quiz")){
			m.setNomModule("Quiz - ".concat(nomModule));
		}
		return m;
	}
	
	public void reset(){
		nomModule = "";
		type = "";
		idSpecialite=null;
	}
	/*vider les champs on clickant sur l'aside ajouter un module*/
	public String resetAndRedirect(){
		reset();
		setModulesBySpecialites(null);
		return "module?faces-redirect=true";
	}
	/*vider les champs on clickant sur l'aside ajouter un module*/
	public String resetQuestionAndRedirect(){
		reset();
		/** meleanger les reponses @bean c'est cette methode est déclarer dans le bean @bean*/
		bean.reset();
		bean.setQuestions(null);
		setModulesBySpecialites(null);
		return "question?faces-redirect=true";
	}
	/*@method update*/
	public String edit(){
			m = serviceModule.updateModule(m, idSpecialite);
			getModulesBySpecialite();
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info","Le Module "+m.getNomModule()+" à bien été modifié avec succès"));
			return "module?faces-redirect=true";
	}
    /*get Module By Specialities*/
	public void getModulesBySpecialite(){
			try {
				modulesBySpecialites=serviceModule.getModulesBySpecialite(idSpecialite);
				LoggerConfig.logInfo("Modules By Specialites : "+modulesBySpecialites);
			} catch (VerificationInDataBaseException e) {
				Utilitaire.displayMessageWarning(e.getMessage());
				setModulesBySpecialites(null);
			}
	}
	/** get current module*/
	public void getCurrentModule(Long idModule){
		m=serviceModule.getModuleById(idModule);
		LoggerConfig.logInfo("Module : "+m);
	}
	
	public Long getIdModule() {
		return idModule;
	}
	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}
	public String getNomModule() {
		return nomModule;
	}
	public void setNomModule(String nomModule) {
		this.nomModule = nomModule;
	}
	public Long getIdSpecialite() {
		return idSpecialite;
	}
	public void setIdSpecialite(Long idSpecialite) {
		this.idSpecialite = idSpecialite;
	}
	
	public List<Module> getModulesBySpecialites() {
		return modulesBySpecialites;
	}
	public void setModulesBySpecialites(List<Module> modulesBySpecialites) {
		this.modulesBySpecialites = modulesBySpecialites;
	}
	public Module getM() {
		return m;
	}
	public void setM(Module m) {
		this.m = m;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
