package com.adaming.myapp.dto;
import java.util.Date;
import java.util.List;

public class FormateurDTO {

	private Long idFormateur;
	private String civilite;
	private String nom;
	private String prenom;
	private String telMobile;
	private String mail;
	private String nationalite;
	private Date dateDeNaissance;
	private String specialite;
	private AdresseDTO adresseDto;
	private List<SessionDTO> sessionDto;
	/**
	 * @return the idFormateur
	 */
	public Long getIdFormateur() {
		return idFormateur;
	}
	/**
	 * @param idFormateur the idFormateur to set
	 */
	public void setIdFormateur(Long idFormateur) {
		this.idFormateur = idFormateur;
	}
	/**
	 * @return the civilite
	 */
	public String getCivilite() {
		return civilite;
	}
	/**
	 * @param civilite the civilite to set
	 */
	public void setCivilite(String civilite) {
		this.civilite = civilite;
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
	 * @return the telMobile
	 */
	public String getTelMobile() {
		return telMobile;
	}
	/**
	 * @param telMobile the telMobile to set
	 */
	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
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
	 * @return the nationalite
	 */
	public String getNationalite() {
		return nationalite;
	}
	/**
	 * @param nationalite the nationalite to set
	 */
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	/**
	 * @return the dateDeNaissance
	 */
	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}
	/**
	 * @param dateDeNaissance the dateDeNaissance to set
	 */
	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}
	/**
	 * @return the specialite
	 */
	public String getSpecialite() {
		return specialite;
	}
	/**
	 * @param specialite the specialite to set
	 */
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	/**
	 * @return the adresseDto
	 */
	public AdresseDTO getAdresseDto() {
		return adresseDto;
	}
	/**
	 * @param adresseDto the adresseDto to set
	 */
	public void setAdresseDto(AdresseDTO adresseDto) {
		this.adresseDto = adresseDto;
	}
	/**
	 * @return the sessionDto
	 */
	public List<SessionDTO> getSessionDto() {
		return sessionDto;
	}
	/**
	 * @param sessionDto the sessionDto to set
	 */
	public void setSessionDto(List<SessionDTO> sessionDto) {
		this.sessionDto = sessionDto;
	}
	
	
}
