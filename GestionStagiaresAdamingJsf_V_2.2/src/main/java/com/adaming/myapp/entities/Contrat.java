package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Entity
@Table(name="contrat")
public class Contrat implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idContrat;
	private Date date;
	private boolean active;
	
    /**Assoc*/
	@OneToOne
	@JoinColumn(name="ID_CONTRAT_ETU")
	private Etudiant etudiant;
	
	/**Construct*/
	public Contrat() {
		// TODO Auto-generated constructor stub
	}

	public Contrat(Date date, boolean active) {
		super();
		this.date = new Date();
		this.active = active;
	}




	/**
	 * @return the idContrat
	 */
	public Long getIdContrat() {
		return idContrat;
	}

	/**
	 * @param idContrat the idContrat to set
	 */
	public void setIdContrat(Long idContrat) {
		this.idContrat = idContrat;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Contrat [idContrat=" + idContrat + ", date=" + date
				+ ", active=" + active + "]";
	}
	
	
	
}
