package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "agenda")
public class Agenda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAgenda;
	private String label;
	private Date debut;
	private Date fin;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USE_AGENDA")
	private User user;
	
	public Agenda() {
		// TODO Auto-generated constructor stub
	}

	public Agenda(String label, Date debut, Date fin) {
		this.label = label;
		this.debut = debut;
		this.fin = fin;
	}





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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Agenda [idAgenda=" + idAgenda + ", label=" + label + ", debut="
				+ debut + ", fin=" + fin + "]";
	}
	
	
	
	
}
