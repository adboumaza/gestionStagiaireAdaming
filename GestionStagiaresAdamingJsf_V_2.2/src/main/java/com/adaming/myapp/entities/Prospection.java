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

@SuppressWarnings("serial")
@Entity
@Table(name = "prospection")
public class Prospection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProspection;
	private String risque;
	private String commentaire;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ETU_PROSPECTION")
	private Etudiant etudiant;
	
	public Prospection() {
		// TODO Auto-generated constructor stub
	}
	
	public Prospection(String risque, String commentaire) {
		this.risque = risque;
		this.commentaire = commentaire;
	}

	/**
	 * @return the idProspection
	 */
	public Long getIdProspection() {
		return idProspection;
	}

	/**
	 * @param idProspection the idProspection to set
	 */
	public void setIdProspection(Long idProspection) {
		this.idProspection = idProspection;
	}

	/**
	 * @return the risque
	 */
	public String getRisque() {
		return risque;
	}

	/**
	 * @param risque the risque to set
	 */
	public void setRisque(String risque) {
		this.risque = risque;
	}

	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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
	
	
	
	
	
}
