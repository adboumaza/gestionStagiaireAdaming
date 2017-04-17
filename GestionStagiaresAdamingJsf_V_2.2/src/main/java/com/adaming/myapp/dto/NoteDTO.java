package com.adaming.myapp.dto;
public class NoteDTO {

	private Long idNote;
	private Double score;
	private EtudiantDto etudiantDto;
	private SessionDTO sessionDto;
	private ModuleDTO moduleDto;
	/**
	 * @return the idNote
	 */
	public Long getIdNote() {
		return idNote;
	}
	/**
	 * @param idNote the idNote to set
	 */
	public void setIdNote(Long idNote) {
		this.idNote = idNote;
	}
	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
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
