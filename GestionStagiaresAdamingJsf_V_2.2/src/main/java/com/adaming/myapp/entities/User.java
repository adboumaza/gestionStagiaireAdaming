package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */

@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User implements Serializable {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUser;
	private String name;
	private String password;
	private boolean actived;
	/*asso*/
	@OneToMany(mappedBy="user")
	private Collection<Role> roles;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name, String password, boolean actived) {
		super();
		this.name = name;
		this.password = password;
		this.actived = actived;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", password="
				+ password + ", actived=" + actived + "]";
	}

	
	
}
