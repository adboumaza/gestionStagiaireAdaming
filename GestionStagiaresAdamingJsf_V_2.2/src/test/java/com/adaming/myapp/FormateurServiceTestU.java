package com.adaming.myapp;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.entities.Adresse;
import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.evenement.service.IEvenementService;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.service.IFormateurService;

public class FormateurServiceTestU {

	private static IFormateurService serviceFormateur;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceFormateur=(IFormateurService)context.getBean("FormateurService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}

	@Test
	@Ignore
	public void addFormateur(){
		Adresse adresse = new Adresse("adresse", "ville", "codePostal", "pays");
		Formateur f = new Formateur("civilite", "nom", "prenom", "telMobile", "mail", "nationalite",null, "specialite",adresse);
		try {
			serviceFormateur.addFormateur(f);
		} catch (VerificationInDataBaseException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	@Ignore
	public void addFormateurToSession(){
		
		try {
			serviceFormateur.addFormateurToSession(1L, 1L);
		} catch (VerificationInDataBaseException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	@Ignore
	public void getAll(){
		serviceFormateur.getAllFormateurs();
	}
	
	@Test
	@Ignore
	public void getOne() throws VerificationInDataBaseException{
		serviceFormateur.getFormateurById(1L);
	}
	@Test
	@Ignore
	public void getSessionByFormateur(){
		try {
			SessionEtudiant se = serviceFormateur.verifyExistingAffectation(1L,2L);
		    System.out.println(se);
		} catch (VerificationInDataBaseException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void getFormateursInSessionEnCours()
	{
		List<Formateur> formateurs = serviceFormateur.getFormateursInSessionEncours();
		System.out.println(formateurs);
	}
}
