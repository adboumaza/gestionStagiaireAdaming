package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Entity
@Table(name="sessionEtudiant")
public class SessionEtudiant implements Serializable {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idSession;
    @Temporal(TemporalType.DATE)
	private Date dateDebute;
    @Temporal(TemporalType.DATE)
	private Date dateFin;
    private Long nombreJours;


	/*association*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SPEC_SESSION")
	private Specialite specialite;

	@OneToMany(mappedBy="sessionEtudiant",fetch=FetchType.LAZY)
	private List<Etudiant> etudiants;
	
	@ManyToMany(mappedBy="sessionsEtudiant",fetch=FetchType.LAZY)
	private List<Formateur> formateurs;
	@OneToMany(mappedBy="sessionEtudiant",fetch=FetchType.LAZY)
	private List<Note> notes;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SITE_SESSION")
	private Site site;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SALLE_SESSION")
	private Salle salle;
	
	/*construct*/
    public SessionEtudiant() {
		// TODO Auto-generated constructor stub
	}

	public SessionEtudiant(Date dateDebute, Date dateFin,Long nombreJours) {
		super();
		this.dateDebute = dateDebute;
		this.dateFin = dateFin;
		this.nombreJours=nombreJours;
	}

	public Long getIdSession() {
		return idSession;
	}

	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	public Date getDateDebute() {
		return dateDebute;
	}

	public void setDateDebute(Date dateDebute) {
		this.dateDebute = dateDebute;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	
	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	

	public List<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	

	public List<Formateur> getFormateurs() {
		return formateurs;
	}

	public void setFormateurs(List<Formateur> formateurs) {
		this.formateurs = formateurs;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
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

	/**
	 * @return the salle
	 */
	public Salle getSalle() {
		return salle;
	}

	/**
	 * @param salle the salle to set
	 */
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	
	

	public Long getNombreJours() {
		return nombreJours;
	}

	public void setNombreJours(Long nombreJours) {
		this.nombreJours = nombreJours;
	}

	@Override
	public String toString() {
		return "SessionEtudiant [idSession=" + idSession + ", dateDebute="
				+ dateDebute + ", dateFin=" + dateFin + ", etatSession="
				+ "]";
	}


	
	
}
