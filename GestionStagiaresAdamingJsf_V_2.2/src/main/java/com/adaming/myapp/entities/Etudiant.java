package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Embedded;
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
/**@Author Adel
 **@version 1.0.0
 **@date 09/10/2016
 *
 * */
@SuppressWarnings("serial")
@Entity
@Table(name="etudiant")
public class Etudiant implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idEtudiant;
	private String nomEtudiant;
	private String prenomEtudiant;
	private Date dateDeNaissance;
	private String formationInitial;
	private String ecole;
	private Date dateObtention;
	private String numTel;
	private String mail;
	@Embedded
	private Adresse adresse;

	/*assoc*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SESS_ETUDIANT")
    private SessionEtudiant sessionEtudiant;
	@OneToMany(mappedBy="etudiant",fetch=FetchType.LAZY)
	private List<Note> notes;
	@OneToMany(mappedBy="etudiant",fetch=FetchType.LAZY)
	private List<Evenement> evenements;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "etudiant")
	private List<Prospection> prospections;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "etudiant")
	private Set<Evaluation> evaluations;

	/**construct*/
	public Etudiant() {
		// TODO Auto-generated constructor stub
	}

	
	/*construct avec param */

	public Etudiant(String nomEtudiant, String prenomEtudiant,
			Date dateDeNaissance, String formationInitial, String ecole,
			Date dateObtention, String numTel, String mail, Adresse adresse) {
		super();
		this.nomEtudiant = nomEtudiant;
		this.prenomEtudiant = prenomEtudiant;
		this.dateDeNaissance = dateDeNaissance;
		this.formationInitial = formationInitial;
		this.ecole = ecole;
		this.dateObtention = dateObtention;
		this.numTel = numTel;
		this.mail = mail;
		this.adresse = adresse;
	}
	
	/*get and set */

	public Long getIdEtudiant() {
		return idEtudiant;
	}


	public void setIdEtudiant(Long idEtudiant) {
		this.idEtudiant = idEtudiant;
	}



	public String getNomEtudiant() {
		return nomEtudiant;
	}



	public void setNomEtudiant(String nomEtudiant) {
		this.nomEtudiant = nomEtudiant;
	}



	public String getPrenomEtudiant() {
		return prenomEtudiant;
	}



	public void setPrenomEtudiant(String prenomEtudiant) {
		this.prenomEtudiant = prenomEtudiant;
	}



	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}



	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}



	public String getFormationInitial() {
		return formationInitial;
	}



	public void setFormationInitial(String formationInitial) {
		this.formationInitial = formationInitial;
	}



	public String getEcole() {
		return ecole;
	}



	public void setEcole(String ecole) {
		this.ecole = ecole;
	}



	public Date getDateObtention() {
		return dateObtention;
	}



	public void setDateObtention(Date dateObtention) {
		this.dateObtention = dateObtention;
	}



	

	public String getNumTel() {
		return numTel;
	}



	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public SessionEtudiant getSessionEtudiant() {
		return sessionEtudiant;
	}



	public void setSessionEtudiant(SessionEtudiant sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}



	public List<Note> getNotes() {
		return notes;
	}



	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}



	public List<Evenement> getEvenements() {
		return evenements;
	}


	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}



	public Adresse getAdresse() {
		return adresse;
	}


	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}


	/**
	 * @return the prospections
	 */
	public List<Prospection> getProspections() {
		return prospections;
	}


	/**
	 * @param prospections the prospections to set
	 */
	public void setProspections(List<Prospection> prospections) {
		this.prospections = prospections;
	}
	
	


	/**
	 * @return the evaluations
	 */
	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}


	/**
	 * @param evaluations the evaluations to set
	 */
	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Etudiant [idEtudiant=" + idEtudiant + ", nomEtudiant="
				+ nomEtudiant + ", prenomEtudiant=" + prenomEtudiant
				+ ", dateDeNaissance=" + dateDeNaissance
				+ ", formationInitial=" + formationInitial + ", ecole=" + ecole
				+ ", dateObtention=" + dateObtention + ", numTel=" + numTel
				+ ", mail=" + mail + ", adresse=" + adresse + "]";
	}

    
	
	
}
