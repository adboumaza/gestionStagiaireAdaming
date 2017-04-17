package com.adaming.myapp.dto;
public class EvaluationDTO {
	
	private Long idEvaluation;
    private String applicationTp;
    private String comprehension;
    private String participation;
	private EtudiantDto etudiantDto;
    private ModuleDTO moduleDto;
	/**
	 * @return the idEvaluation
	 */
	public Long getIdEvaluation() {
		return idEvaluation;
	}
	/**
	 * @param idEvaluation the idEvaluation to set
	 */
	public void setIdEvaluation(Long idEvaluation) {
		this.idEvaluation = idEvaluation;
	}
	/**
	 * @return the applicationTp
	 */
	public String getApplicationTp() {
		return applicationTp;
	}
	/**
	 * @param applicationTp the applicationTp to set
	 */
	public void setApplicationTp(String applicationTp) {
		this.applicationTp = applicationTp;
	}
	/**
	 * @return the comprehension
	 */
	public String getComprehension() {
		return comprehension;
	}
	/**
	 * @param comprehension the comprehension to set
	 */
	public void setComprehension(String comprehension) {
		this.comprehension = comprehension;
	}
	/**
	 * @return the participation
	 */
	public String getParticipation() {
		return participation;
	}
	/**
	 * @param participation the participation to set
	 */
	public void setParticipation(String participation) {
		this.participation = participation;
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
