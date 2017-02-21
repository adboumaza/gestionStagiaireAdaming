package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Entity
@Table(name="examen")
public class Examen implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idExamen;
	private Date dateExamenStart;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ETU_EXAMEN")
	private Etudiant etudiant;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SES_EXAMEN")
	private SessionEtudiant sessionEtudiant;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MOD_EXAMEN")
	private Module module;
	
	public Examen() {
		// TODO Auto-generated constructor stub
	}
	
	public Examen(Date dateExamenStart) {
		super();
		this.dateExamenStart = dateExamenStart;
	}






	public Long getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(Long idExamen) {
		this.idExamen = idExamen;
	}

	
	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public SessionEtudiant getSessionEtudiant() {
		return sessionEtudiant;
	}

	public void setSessionEtudiant(SessionEtudiant sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Date getDateExamenStart() {
		return dateExamenStart;
	}

	public void setDateExamenStart(Date dateExamenStart) {
		this.dateExamenStart = dateExamenStart;
	}



	@Override
	public String toString() {
		return "Examen [idExamen=" + idExamen + ", dateExamenStart="
				+ dateExamenStart + ", dateExamenEnd=" + "]";
	}

	
	
	
	
}
