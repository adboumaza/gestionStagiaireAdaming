package com.adaming.myapp.dto;

public class SalleDTO {

	private Long idSalle;
	private String numeroSalle;
	private Integer nbPlace;
	private SiteDTO siteDto;
	/**
	 * @return the idSalle
	 */
	public Long getIdSalle() {
		return idSalle;
	}
	/**
	 * @param idSalle the idSalle to set
	 */
	public void setIdSalle(Long idSalle) {
		this.idSalle = idSalle;
	}
	/**
	 * @return the numeroSalle
	 */
	public String getNumeroSalle() {
		return numeroSalle;
	}
	/**
	 * @param numeroSalle the numeroSalle to set
	 */
	public void setNumeroSalle(String numeroSalle) {
		this.numeroSalle = numeroSalle;
	}
	/**
	 * @return the nbPlace
	 */
	public Integer getNbPlace() {
		return nbPlace;
	}
	/**
	 * @param nbPlace the nbPlace to set
	 */
	public void setNbPlace(Integer nbPlace) {
		this.nbPlace = nbPlace;
	}
	/**
	 * @return the siteDto
	 */
	public SiteDTO getSiteDto() {
		return siteDto;
	}
	/**
	 * @param siteDto the siteDto to set
	 */
	public void setSiteDto(SiteDTO siteDto) {
		this.siteDto = siteDto;
	}
	
	

}
