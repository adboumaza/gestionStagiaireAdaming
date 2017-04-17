package com.adaming.myapp.dto;

import java.util.Date;

public class EvenementDTO {

	private Long idEvenement;
	protected Date startDate;
	protected Date endDate;
	protected String signaleur;
	protected Date curentDate;
	private EtudiantDto etudiantDto;
	private SessionDTO sessionEtudiant;
	
	/**
	 * @return the idEvenement
	 */
	public Long getIdEvenement() {
		return idEvenement;
	}
	/**
	 * @param idEvenement the idEvenement to set
	 */
	public void setIdEvenement(Long idEvenement) {
		this.idEvenement = idEvenement;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the signaleur
	 */
	public String getSignaleur() {
		return signaleur;
	}
	/**
	 * @param signaleur the signaleur to set
	 */
	public void setSignaleur(String signaleur) {
		this.signaleur = signaleur;
	}
	/**
	 * @return the curentDate
	 */
	public Date getCurentDate() {
		return curentDate;
	}
	/**
	 * @param curentDate the curentDate to set
	 */
	public void setCurentDate(Date curentDate) {
		this.curentDate = curentDate;
	}
	/**
	 * @return the etudiantDto
	 */
	public EtudiantDto getEtudiantDto() {
		return etudiantDto;
	}
	/**
	 * @param etudiantDto the etudiantDto to set
	 */
	public void setEtudiantDto(EtudiantDto etudiantDto) {
		this.etudiantDto = etudiantDto;
	}
	/**
	 * @return the sessionEtudiant
	 */
	public SessionDTO getSessionEtudiant() {
		return sessionEtudiant;
	}
	/**
	 * @param sessionEtudiant the sessionEtudiant to set
	 */
	public void setSessionEtudiant(SessionDTO sessionEtudiant) {
		this.sessionEtudiant = sessionEtudiant;
	}
	
	

}
