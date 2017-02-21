package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Entity
@Table(name="formateur")
public class Formateur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFormateur;
	private String civilite;
	private String nom;
	private String prenom;
	private String telMobile;
	private String mail;
	private String nationalite;
	private Date dateDeNaissance;
	private String specialite;
	@Embedded
	private Adresse adresse;

	/* assoc */
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "TB_SES_FORMATEUR")
	private List<SessionEtudiant> sessionsEtudiant = new ArrayList<SessionEtudiant>();

	public Formateur() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Formateur(String civilite, String nom, String prenom,
			String telMobile, String mail, String nationalite,
			Date dateDeNaissance, String specialite, Adresse adresse) {
		super();
		this.civilite = civilite;
		this.nom = nom;
		this.prenom = prenom;
		this.telMobile = telMobile;
		this.mail = mail;
		this.nationalite = nationalite;
		this.dateDeNaissance = dateDeNaissance;
		this.specialite = specialite;
		this.adresse = adresse;
	}



	public Long getIdFormateur() {
		return idFormateur;
	}

	public void setIdFormateur(Long idFormateur) {
		this.idFormateur = idFormateur;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelMobile() {
		return telMobile;
	}

	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}



	public List<SessionEtudiant> getSessionsEtudiant() {
		return sessionsEtudiant;
	}



	public void setSessionsEtudiant(List<SessionEtudiant> sessionsEtudiant) {
		this.sessionsEtudiant = sessionsEtudiant;
	}



	@Override
	public String toString() {
		return "Formateur [idFormateur=" + idFormateur + ", civilite="
				+ civilite + ", nom=" + nom + ", prenom=" + prenom
				+ ", telMobile=" + telMobile + ", mail=" + mail
				+ ", nationalite=" + nationalite + ", dateDeNaissance="
				+ dateDeNaissance + ", specialite=" + specialite + ", adresse="
				+ adresse + "]";
	}

	
}
