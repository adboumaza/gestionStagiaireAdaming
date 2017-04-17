package com.adaming.myapp.dto;

public class RoleDTO {

	private Long idRole;
	private String roleName;
	private UserDTO userDto;
	/**
	 * @return the idRole
	 */
	public Long getIdRole() {
		return idRole;
	}
	/**
	 * @param idRole the idRole to set
	 */
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
