package com.adaming.myapp.dto;

import java.util.Collection;

public class UserDTO {

	private Long idUser;
	private String name;
	private String password;
	private boolean actived;
	private Collection<RoleDTO> rolesDto;
	/**
	 * @return the idUser
	 */
	public Long getIdUser() {
		return idUser;
	}
	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the actived
	 */
	public boolean isActived() {
		return actived;
	}
	/**
	 * @param actived the actived to set
	 */
	public void setActived(boolean actived) {
		this.actived = actived;
	}
	/**
	 * @return the rolesDto
	 */
	public Collection<RoleDTO> getRolesDto() {
		return rolesDto;
	}
	/**
	 * @param rolesDto the rolesDto to set
	 */
	public void setRolesDto(Collection<RoleDTO> rolesDto) {
		this.rolesDto = rolesDto;
	}
	
	
	
}
