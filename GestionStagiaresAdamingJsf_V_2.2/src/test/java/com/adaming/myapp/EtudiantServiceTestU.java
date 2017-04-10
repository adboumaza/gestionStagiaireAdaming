package com.adaming.myapp;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.exception.AddEtudiantException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.service.IModuleService;

public class EtudiantServiceTestU {


	private static IEtudiantService serviceEtudiant;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceEtudiant=(IEtudiantService)context.getBean("EtudiantService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}
	@Test
	@Ignore
	public void testAddStudent() throws VerificationInDataBaseException {
		Etudiant e = new Etudiant("nomEtudiant2", "prenomEtudiant2", new Date(), "formationInitial", "ecole", new Date(), "numTel", "mail2",null);
		try {
			serviceEtudiant.addStudent(e, 1L, new User(), new Role());
			assertNotNull(e.getIdEtudiant());
		} catch (VerificationInDataBaseException e1) {
			System.out.println(e1.getMessage());
		}
		
	}

	@Test
	@Ignore
	public void testUpdateStudent() {
		Etudiant e = serviceEtudiant.getStudentById(1L);
		e.setNomEtudiant("nomModifier");
		serviceEtudiant.updateStudent(e,1L);
		Etudiant e2 = serviceEtudiant.getStudentById(1L);
		assertThat("nomModifier",IsEqual.equalTo(e2.getNomEtudiant()));
	}

	@Test
	@Ignore
	public void testRemoveStudent() {
		Etudiant e = serviceEtudiant.getStudentById(14L);
		serviceEtudiant.removeStudent(14L);
		assertNull(e!=null);
	}

	@Test
	public void testGetStudentById() {
		Etudiant e = serviceEtudiant.getStudentById(1L);
		System.out.println(e);
		assertNotNull(e.getIdEtudiant());
	}

	@Test
	@Ignore
	public void testGetEtudiantBySession() throws VerificationInDataBaseException {
		List<Object[]> tabE = serviceEtudiant.getEtudiantBySession(1L);
		System.out.println(tabE.get(0));
		assertTrue(tabE.size()>=0);
	}
	
	@Test
	@Ignore
	public void getStudentsBySession(){
		try {
			List<Etudiant> students = serviceEtudiant.getStudentsBySession(1L);
		    for(Etudiant e:students){
		    	
		    	System.out.println(e.getNomEtudiant());
		    }
		} catch (VerificationInDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
	}


}
