package com.adaming.myapp.dto;


import java.util.List;


public class QuestionDTO {

	private Long idQuestions;
	private String label;
	private Integer numeroQuestion;
    private ModuleDTO moduleDto;
	private List<ReponseDTO> reponseDto;
	/**
	 * @return the idQuestions
	 */
	public Long getIdQuestions() {
		return idQuestions;
	}
	/**
	 * @param idQuestions the idQuestions to set
	 */
	public void setIdQuestions(Long idQuestions) {
		this.idQuestions = idQuestions;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the numeroQuestion
	 */
	public Integer getNumeroQuestion() {
		return numeroQuestion;
	}
	/**
	 * @param numeroQuestion the numeroQuestion to set
	 */
	public void setNumeroQuestion(Integer numeroQuestion) {
		this.numeroQuestion = numeroQuestion;
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
	/**
	 * @return the reponseDto
	 */
	public List<ReponseDTO> getReponseDto() {
		return reponseDto;
	}
	/**
	 * @param reponseDto the reponseDto to set
	 */
	public void setReponseDto(List<ReponseDTO> reponseDto) {
		this.reponseDto = reponseDto;
	}
	
	
}
