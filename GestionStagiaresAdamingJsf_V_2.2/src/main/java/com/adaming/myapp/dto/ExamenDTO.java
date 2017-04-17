package com.adaming.myapp.dto;

import java.util.Date;

public class ExamenDTO {

	private Long idExamen;
	private Date dateExamenStart;
	private EtudiantDto etudiantDto;
	private SessionDTO sessionDto;
	private ModuleDTO moduleDto;
	/**
	 * @return the idExamen
	 */
	public Long getIdExamen() {
		return idExamen;
	}
	/**
	 * @param idExamen the idExamen to set
	 */
	public void setIdExamen(Long idExamen) {
		this.idExamen = idExamen;
	}
	/**
	 * @return the dateExamenStart
	 */
	public Date getDateExamenStart() {
		return dateExamenStart;
	}
	/**
	 * @param dateExamenStart the dateExamenStart to set
	 */
	public void setDateExamenStart(Date dateExamenStart) {
		this.dateExamenStart = dateExamenStart;
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
	 * @return the sessionDto
	 */
	public SessionDTO getSessionDto() {
		return sessionDto;
	}
	/**
	 * @param sessionDto the sessionDto to set
	 */
	public void setSessionDto(SessionDTO sessionDto) {
		this.sessionDto = sessionDto;
	}
	/**
	 * @return the moduleDto
	 */
	public ModuleDTO getModuleDto() {
		return moduleDto;
	}
	/**
	 * @param moduleDto the moduleDto to set
	 */
	public void setModuleDto(ModuleDTO moduleDto) {
		this.moduleDto = moduleDto;
	}
	
	
	
}
