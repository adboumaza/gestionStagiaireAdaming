package com.adaming.myapp.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.adaming.myapp.entities.User;

public class AgendaDTO {

	private Long idAgenda;
	private String label;
	private Date debut;
	private Date fin;
	private UserDTO userDto;
	/**
	 * @return the idAgenda
	 */
	public Long getIdAgenda() {
		return idAgenda;
	}
	/**
	 * @param idAgenda the idAgenda to set
	 */
	public void setIdAgenda(Long idAgenda) {
		this.idAgenda = idAgenda;
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
	 * @return the debut
	 */
	public Date getDebut() {
		return debut;
	}
	/**
	 * @param debut the debut to set
	 */
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	/**
	 * @return the fin
	 */
	public Date getFin() {
		return fin;
	}
	/**
	 * @param fin the fin to set
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}
	/**
	 * @return the userDto
	 */
	public UserDTO getUserDto() {
		return userDto;
	}
	/**
	 * @param userDto the userDto to set
	 */
	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
	
	
	
	
}
