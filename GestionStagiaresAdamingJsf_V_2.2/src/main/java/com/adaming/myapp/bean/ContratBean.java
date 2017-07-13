package com.adaming.myapp.bean;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.contrat.service.IContratService;
import com.adaming.myapp.entities.Contrat;
import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.Utilitaire;

@Component("contratBean")
@Scope(value="session")
public class ContratBean {
	
	@Inject
	private IContratService serviceContrat;
	@Inject
	private IEtudiantService serviceEtudiant;
	@Inject
	private UserAuthentificationBean authentificationBean;
	
	private Date date;
	private boolean active;
	private SessionEtudiant sessionEtudiant;
	private Etudiant etudiant;
	private String formattedDate;
	
	public String redirect(){
		date = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault());
		formattedDate = df.format(date);
		active=false;
		sessionEtudiant = createSessionEtudiant();
		etudiant = createEtudiant();
		etudiant = serviceEtudiant.getEtudiant(authentificationBean.getName());
		sessionEtudiant=etudiant.getSessionEtudiant();
		return "contrat?faces-redirect=true";
	}

	/**
	 ** @create New Etudiant
	 ** @return Object Etudiant
	 ** @factory.create.method
	 */
	private Etudiant createEtudiant() {
		etudiant = FactoryBean.getEtudiantFactory().create("Etudiant");
	    return etudiant;
	}

	/**
	 * @create New Session
	 **@return Object Session 
	 **@factory.create.method
	 */
	private SessionEtudiant createSessionEtudiant() {
		sessionEtudiant = FactoryBean.getSessionFactory().create("SessionEtudiant");
	    return sessionEtudiant;
	}
	
	public void addContrat(){
		date = new Date();
		Contrat contrat = new Contrat(date,active);
		if(active==false){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("vous devez cochez la case « J'ai pris connaissance et j'accepte les Conditions Générales »"));
		}else{
			try {
				serviceContrat.addContrat(contrat, etudiant.getIdEtudiant());
				Utilitaire.displayMessageInfo("Merci, votre signature a bien été prise en compte");
			} catch (VerificationInDataBaseException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
		}
		
	}
	
	public void addMessage() {
        String summary = active ? "« J'ai pris connaissance et j'accepte les Conditions Générales »" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

	

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	

	
	/**
	 * @return the authentificationBean
	 */
	public UserAuthentificationBean getAuthentificationBean() {
		return authentificationBean;
	}

	/**
	 * @param authentificationBean the authentificationBean to set
	 */
	public void setAuthentificationBean(
			UserAuthentificationBean authentificationBean) {
		this.authentificationBean = authentificationBean;
	}

	/**
	 * @return the sessionEtudiant
	 */
	public SessionEtudiant getSessionEtudiant() {
		return sessionEtudiant;
	}

	/**
	 * @param sessionEtudiant the sessionEtudiant to set
	 */
	public void setSessionEtudiant(SessionEtudiant sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}

	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/**
	 * @return the formattedDate
	 */
	public String getFormattedDate() {
		return formattedDate;
	}

	/**
	 * @param formattedDate the formattedDate to set
	 */
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	
	
	

}
