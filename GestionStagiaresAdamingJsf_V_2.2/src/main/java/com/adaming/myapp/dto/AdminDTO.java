package com.adaming.myapp.dto;

public class AdminDTO {

	private Long idAdmin;
	private String nom;
	private String prenom;
	private String mail;
	private String fonction;
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
	
	
}
