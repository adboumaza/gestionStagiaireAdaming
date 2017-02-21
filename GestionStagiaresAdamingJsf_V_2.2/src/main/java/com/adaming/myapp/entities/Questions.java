package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="questions")
public class Questions implements Serializable {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idQuestions;
	@Column(length = 1000)
	private String label;
	private Integer numeroQuestion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_QUES_MODULE")
    private Module module;

	@OneToMany(mappedBy="questions",cascade = CascadeType.ALL)
	private List<Reponses> reponses =  new ArrayList<Reponses>();

	public Questions(String label,Integer numeroQuestion) {
		this.label = label;
		this.numeroQuestion = numeroQuestion;
	}

	public Questions() {
		// TODO Auto-generated constructor stub
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getIdQuestions() {
		return idQuestions;
	}

	public void setIdQuestions(Long idQuestions) {
		this.idQuestions = idQuestions;
	}

	public List<Reponses> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponses> reponses) {
		this.reponses = reponses;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Integer getNumeroQuestion() {
		return numeroQuestion;
	}

	public void setNumeroQuestion(Integer numeroQuestion) {
		this.numeroQuestion = numeroQuestion;
	}

	@Override
	public String toString() {
		return "Questions [idQuestions=" + idQuestions + ", label=" + label
				+ "]";
	}
	
	

}
