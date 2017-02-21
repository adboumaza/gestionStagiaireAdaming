package com.adaming.myapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@Entity
@DiscriminatorValue("ABSENCE")

public class Absence extends Evenement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Absence() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Absence(Date startDate, Date endDate, String signaleur,
			Date curentDate) {
		super(startDate, endDate, signaleur, curentDate);
		// TODO Auto-generated constructor stub
	}

	

	

}
