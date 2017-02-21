package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_EVENEMENT", discriminatorType = DiscriminatorType.STRING)
@Table(name="evenement")
public class Evenement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvenement;
	protected Date startDate;
	protected Date endDate;
	protected String signaleur;
	protected Date curentDate;


	/* assoc */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_EVE_ETUDIANT")
	private Etudiant etudiant;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_EVE_SESSION")
	private SessionEtudiant sessionEtudiant;

	public Evenement() {
		// TODO Auto-generated constructor stub
	}

	public Evenement(Date startDate, Date endDate, String signaleur,
			Date curentDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.signaleur = signaleur;
		this.curentDate = curentDate;
	}

	public Evenement(String signaleur, Date curentDate) {
		super();
		this.signaleur = signaleur;
		this.curentDate = curentDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getIdEvenement() {
		return idEvenement;
	}

	public void setIdEvenement(Long idEvenement) {
		this.idEvenement = idEvenement;
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

	public Date getCurentDate() {
		return curentDate;
	}

	public void setCurentDate(Date curentDate) {
		this.curentDate = curentDate;
	}

	public String getSignaleur() {
		return signaleur;
	}

	public void setSignaleur(String signaleur) {
		this.signaleur = signaleur;
	}

	@Override
	public String toString() {
		return "Evenement [idEvenement=" + idEvenement + ", startDate="
				+ startDate + ", endDate=" + endDate + ", signaleur="
				+ signaleur + ", curentDate=" + curentDate + "]";
	}
	
	

}
