package com.adaming.myapp.dto;

import java.util.Date;

public class ContratDTO {

	private Long idContrat;
	private Date date;
	private boolean active;
	private EtudiantDto etudiantDto;
	/**
	 * @return the idContrat
	 */
	public Long getIdContrat() {
		return idContrat;
	}
	/**
	 * @param idContrat the idContrat to set
	 */
	public void setIdContrat(Long idContrat) {
		this.idContrat = idContrat;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
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
