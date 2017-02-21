package com.adaming.myapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name="reponses")
public class Reponses implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idReponse;
    @Column(length = 500)
	private String labelReponse;
	private boolean etat;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REP_QUES")
	private Questions questions;
	
	public Reponses() {
		// TODO Auto-generated constructor stub
	}

	public Reponses(String labelReponse, boolean etat) {
		super();
		this.labelReponse = labelReponse;
		this.etat = etat;
	}



	public String getLabelReponse() {
		return labelReponse;
	}
	public void setLabelReponse(String labelReponse) {
		this.labelReponse = labelReponse;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public Long getIdReponse() {
		return idReponse;
	}

	public void setIdReponse(Long idReponse) {
		this.idReponse = idReponse;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Reponses [idReponse=" + idReponse + ", labelReponse="
				+ labelReponse + ", etat=" + etat + "]";
	}
	
	
	
}
