package com.adaming.myapp.dto;

public class SiteDTO {

	private Long idSite;
	private String nomSite;
	private AdresseDTO adresseDto;
	/**
	 * @return the idSite
	 */
	public Long getIdSite() {
		return idSite;
	}
	/**
	 * @param idSite the idSite to set
	 */
	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}
	/**
	 * @return the nomSite
	 */
	public String getNomSite() {
		return nomSite;
	}
	/**
	 * @param nomSite the nomSite to set
	 */
	public void setNomSite(String nomSite) {
		this.nomSite = nomSite;
	}
	/**
	 * @return the adresseDto
	 */
	public AdresseDTO getAdresseDto() {
		return adresseDto;
	}
	/**
	 * @param adresseDto the adresseDto to set
	 */
	public void setAdresseDto(AdresseDTO adresseDto) {
		this.adresseDto = adresseDto;
	}
	
	
}
