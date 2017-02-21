package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Entity
@Table(name="Specialite")
public class Specialite implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idSpecialite;
	private String designation;
	//associations
	@OneToMany(mappedBy="specialite")
	private List<SessionEtudiant> sessionEtudiant;
	
	@OneToMany(mappedBy="specialite")
	private List<Module> modules;
	/*construct*/
	public Specialite() {
		// TODO Auto-generated constructor stub
	}
	public Specialite(String designation) {
		super();
		this.designation = designation;
	}
	public Long getIdSpecialite() {
		return idSpecialite;
	}
	public void setIdSpecialite(Long idSpecialite) {
		this.idSpecialite = idSpecialite;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public List<SessionEtudiant> getSessionEtudiant() {
		return sessionEtudiant;
	}
	public void setSessionEtudiant(List<SessionEtudiant> sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	@Override
	public String toString() {
		return "Specialite [idSpecialite=" + idSpecialite + ", designation="
				+ designation + "]";
	}
	
	
}
