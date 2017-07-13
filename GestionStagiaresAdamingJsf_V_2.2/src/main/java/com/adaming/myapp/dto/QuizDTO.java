package com.adaming.myapp.dto;

import java.math.BigInteger;

public class QuizDTO {

	private String module;
	private String nbrQuestion;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getNbrQuestion() {
		return nbrQuestion;
	}
	public void setNbrQuestion(String nbrQuestion) {
		this.nbrQuestion = nbrQuestion;
	}
	@Override
	public String toString() {
		return "QuizDTO [module=" + module + ", nbrQuestion=" + nbrQuestion
				+ "]";
	}
	
}
