package com.adaming.myapp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 17/03/2017
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Entity
@Table(name = "admin")
public class Admin implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAdmin;
	private String nom;
	private String prenom;
	private String mail;
	private String fonction;
	
	public Admin() {
		// TODO Auto-generated constructor stub
	}
    

	public Admin(String nom, String prenom, String mail, String fonction) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.fonction = fonction;
	}






	/**
	 * @return the idAdmin
	 */
	public Long getIdAdmin() {
		return idAdmin;
	}



	/**
	 * @param idAdmin the idAdmin to set
	 */
	public void setIdAdmin(Long idAdmin) {
		this.idAdmin = idAdmin;
	}



	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the fonction
	 */
	public String getFonction() {
		return fonction;
	}

	/**
	 * @param fonction the fonction to set
	 */
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}



	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}



	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Admin [idAdmin=" + idAdmin + ", nom=" + nom + ", prenom="
				+ prenom + ", mail=" + mail + ", fonction=" + fonction + "]";
	}
	
	
	
	
	
	
}
