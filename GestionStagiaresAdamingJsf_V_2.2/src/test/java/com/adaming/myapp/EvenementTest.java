package com.adaming.myapp;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.entities.Absence;
import com.adaming.myapp.entities.Entretien;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.Retard;
import com.adaming.myapp.evenement.service.IEvenementService;
import com.adaming.myapp.exception.EvenementNotFoundException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.service.IModuleService;

public class EvenementTest {

	private static IEvenementService serviceEvenement;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceEvenement=(IEvenementService)context.getBean("EvenementService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}
	@Ignore
	@Test
	public void addEntretien() throws VerificationInDataBaseException{
		Entretien e =  new Entretien(new Date(), new Date(), null, new Date());
		serviceEvenement.addEvenement(e, 1L,1L);
	}
	@Ignore
	@Test
	public void addAbsence() throws VerificationInDataBaseException{
		Absence a =  new Absence(new Date(), new Date(), null, new Date());
		serviceEvenement.addEvenement(a,1L, 1L);
	}
	@Ignore
	@Test
	public void addRetard() throws VerificationInDataBaseException{
		Retard r =  new Retard(new Date(), new Date(), null, new Date());;
		serviceEvenement.addEvenement(r, 1L, 1L);
	}
	@Test
	@Ignore
	public void getEntretien(){
		List<Object[]> evenements;
		try {
			evenements = serviceEvenement.getEvenementsEntretien();
			assertTrue(evenements.size()>0);
		} catch (EvenementNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	@Ignore
	public void getRetard(){
		List<Object[]> evenements;
		try {
			evenements = serviceEvenement.getEvenementsRetards();
			assertTrue(evenements.size()>0);
		} catch (EvenementNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	@Ignore
	public void getAbsence(){
		List<Object[]> evenements;
		try {
			evenements = serviceEvenement.getEvenementsAbsences();
			assertTrue(evenements.size()>0);
		} catch (EvenementNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	@Ignore
	public void getCurrentAbsences(){
		serviceEvenement.getDailyCountOfAbsence();
		
	}
	@Ignore
	@Test
	public void getCurrentRetards(){
		serviceEvenement.getDailyCountOfRetards();
		
	}
	
	@Test
	@Ignore
	public void getWarning(){
		 serviceEvenement.getDailyCountOfWarning();
	
	}
	@Test
	@Ignore
	public void getTop(){
		serviceEvenement.getDailyCountOfTop();
	
	}
	@Test
	@Ignore
	public void verify(){
		Evenement e = serviceEvenement.verifyExistingEvent(1L);
		System.out.println(e.getEtudiant().getNomEtudiant() + e.getClass().getSimpleName());
	}
	@Test
	public void getEvenetBetweenTwoDates() throws ParseException{
		
		Object [] e = serviceEvenement.getEventByStudentBetweenTwoDates(1L, "boum", new DateTime());
		Date de =(Date) e[1];
		Date a = (Date) e[2];
		
		
	}

}
