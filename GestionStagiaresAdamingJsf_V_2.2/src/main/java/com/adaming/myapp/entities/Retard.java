package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("RETARD")
public class Retard extends Evenement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Retard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Retard(Date startDate, Date endDate, String signaleur,
			Date curentDate) {
		super(startDate, endDate, signaleur, curentDate);
		// TODO Auto-generated constructor stub
	}

	
	

	
}
