package com.adaming.myapp.dto;
public class ProspectionDTO {

	private Long idProspection;
	private String risque;
	private String commentaire;
	private EtudiantDto etudiantDto;
	/**
	 * @return the idProspection
	 */
	public Long getIdProspection() {
		return idProspection;
	}
	/**
	 * @param idProspection the idProspection to set
	 */
	public void setIdProspection(Long idProspection) {
		this.idProspection = idProspection;
	}
	/**
	 * @return the risque
	 */
	public String getRisque() {
		return risque;
	}
	/**
	 * @param risque the risque to set
	 */
	public void setRisque(String risque) {
		this.risque = risque;
	}
	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}
	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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
	
	
}
