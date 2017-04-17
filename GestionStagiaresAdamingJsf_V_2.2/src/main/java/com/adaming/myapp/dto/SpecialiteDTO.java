package com.adaming.myapp.dto;

import java.util.List;

public class SpecialiteDTO {

	private Long idSpecialite;
	private String designation;
	private List<SessionDTO> sessionDto;
	private List<ModuleDTO> moduleDto;
	/**
	 * @return the idSpecialite
	 */
	public Long getIdSpecialite() {
		return idSpecialite;
	}
	/**
	 * @param idSpecialite the idSpecialite to set
	 */
	public void setIdSpecialite(Long idSpecialite) {
		this.idSpecialite = idSpecialite;
	}
	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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
	/**
	 * @return the moduleDto
	 */
	public List<ModuleDTO> getModuleDto() {
		return moduleDto;
	}
	/**
	 * @param moduleDto the moduleDto to set
	 */
	public void setModuleDto(List<ModuleDTO> moduleDto) {
		this.moduleDto = moduleDto;
	}
	
	
}
