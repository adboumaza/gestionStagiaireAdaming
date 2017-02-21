package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Entity
@Table(name="module")
public class Module implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idModule;
	private String nomModule;
	private boolean actif = false;

	/* assoc */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_SP_MODULE")
	private Specialite specialite;

	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
	private List<Questions> questions;
	
	/* construct */
	public Module() {
		// TODO Auto-generated constructor stub
	}

	public Module(String nomModule) {
		super();
		this.nomModule = nomModule;
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

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	
	


	@Override
	public String toString() {
		return "Module [idModule=" + idModule + ", nomModule=" + nomModule
				+ ", actif=" + actif + "]";
	}
	

}
