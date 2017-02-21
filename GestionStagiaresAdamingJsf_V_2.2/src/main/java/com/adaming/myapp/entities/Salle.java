package com.adaming.myapp.entities;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Entity
@Table(name="salle")
public class Salle implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSalle;
	private String numeroSalle;
	private Integer nbPlace;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SIT_SALLE")
	private Site site;

	
	public Salle(String numeroSalle, Integer nbPlace) {
		super();
		this.numeroSalle = numeroSalle;
		this.nbPlace = nbPlace;
	}

	public Salle() {
		// TODO Auto-generated constructor stub
	}

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
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	
}
