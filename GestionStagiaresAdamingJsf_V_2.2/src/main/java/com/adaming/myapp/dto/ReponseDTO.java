package com.adaming.myapp.dto;

public class ReponseDTO {

	private Long idReponse;
	private String labelReponse;
	private boolean etat;
	private QuestionDTO questionDto;
	/**
	 * @return the idReponse
	 */
	public Long getIdReponse() {
		return idReponse;
	}
	/**
	 * @param idReponse the idReponse to set
	 */
	public void setIdReponse(Long idReponse) {
		this.idReponse = idReponse;
	}
	/**
	 * @return the labelReponse
	 */
	public String getLabelReponse() {
		return labelReponse;
	}
	/**
	 * @param labelReponse the labelReponse to set
	 */
	public void setLabelReponse(String labelReponse) {
		this.labelReponse = labelReponse;
	}
	/**
	 * @return the etat
	 */
	public boolean isEtat() {
		return etat;
	}
	/**
	 * @param etat the etat to set
	 */
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	/**
	 * @return the questionDto
	 */
	public QuestionDTO getQuestionDto() {
		return questionDto;
	}
	/**
	 * @param questionDto the questionDto to set
	 */
	public void setQuestionDto(QuestionDTO questionDto) {
		this.questionDto = questionDto;
	}
	
	
	
}
