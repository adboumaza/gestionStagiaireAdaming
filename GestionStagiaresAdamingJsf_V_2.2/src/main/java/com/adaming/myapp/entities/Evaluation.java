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
@Table(name = "evaluation")
public class Evaluation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvaluation;
    
    private String applicationTp;
    private String comprehension;
    private String participation;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ETU_EVALUATION")
	private Etudiant etudiant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_MOD_EVALUATION")
    private Module module;

    public Evaluation(String applicationTp, String comprehension,
			String participation) {
		super();
		this.applicationTp = applicationTp;
		this.comprehension = comprehension;
		this.participation = participation;
	}
    
    public Evaluation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idEvaluation
	 */
	public Long getIdEvaluation() {
		return idEvaluation;
	}

	/**
	 * @param idEvaluation the idEvaluation to set
	 */
	public void setIdEvaluation(Long idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	/**
	 * @return the applicationTp
	 */
	public String getApplicationTp() {
		return applicationTp;
	}

	/**
	 * @param applicationTp the applicationTp to set
	 */
	public void setApplicationTp(String applicationTp) {
		this.applicationTp = applicationTp;
	}

	/**
	 * @return the comprehension
	 */
	public String getComprehension() {
		return comprehension;
	}

	/**
	 * @param comprehension the comprehension to set
	 */
	public void setComprehension(String comprehension) {
		this.comprehension = comprehension;
	}

	/**
	 * @return the participation
	 */
	public String getParticipation() {
		return participation;
	}

	/**
	 * @param participation the participation to set
	 */
	public void setParticipation(String participation) {
		this.participation = participation;
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


	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Evaluation [idEvaluation=" + idEvaluation + ", applicationTp="
				+ applicationTp + ", comprehension=" + comprehension
				+ ", participation=" + participation + "]";
	}
    
    
    
	
}
