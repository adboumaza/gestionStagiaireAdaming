package com.adaming.myapp.tools;

import java.util.Comparator;

import com.adaming.myapp.entities.Questions;

public class ComparatorQuestion implements Comparator<Questions> {

	@Override
	public int compare(Questions q1, Questions q2) {
		// TODO Auto-generated method stub
		return q1.getNumeroQuestion() - q2.getNumeroQuestion();
	}

}
