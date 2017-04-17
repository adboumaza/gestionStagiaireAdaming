package com.adaming.myapp;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.entities.Adresse;
import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.Prospection;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.service.IFormateurService;
import com.adaming.myapp.prospection.service.IProspectionService;

public class ProspectionServiceTestU {

	private static IProspectionService serviceProspection;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceProspection=(IProspectionService)context.getBean("ProspectionService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}

	@Test
	public void addProspection() throws VerificationInDataBaseException{
	  serviceProspection.addProspection(new Prospection("elevé", "autre"), 1L);
	}

}
