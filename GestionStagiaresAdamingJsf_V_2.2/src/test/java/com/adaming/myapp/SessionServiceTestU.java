package com.adaming.myapp;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hamcrest.core.IsEqual;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.dto.SessionDto;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.specialite.service.ISpecialiteService;

public class SessionServiceTestU {

	private static ISessionService serviceSession;
	private static ClassPathXmlApplicationContext context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceSession = (ISessionService) context.getBean("SessionService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}

	@Test
	@Ignore
	public void testAddSessionStudent() throws AddSessionException {
		SessionEtudiant s = new SessionEtudiant(new Date(), new Date(), 1L);
		serviceSession.addSessionStudent(s, 1L, 1L, 1L);
		SessionEtudiant s2 = new SessionEtudiant(new Date(), new Date(), 1L);
		serviceSession.addSessionStudent(s2, 2L, 2L, 1L);
		assertNotNull(s.getIdSession());
		assertNotNull(s2.getIdSession());
	}

	@Test
	@Ignore
	public void testUpdateSessionEtudian() throws AddSessionException {
		SessionEtudiant se = serviceSession.getSessionEtudiantById(1L);
		// se.setLieu("Nantes");
		serviceSession.updateSessionEtudiant(se, 1L, 1L, 1L);
		SessionEtudiant se2 = serviceSession.getSessionEtudiantById(1L);
		// assertThat("Nantes", IsEqual.equalTo(se2.getLieu()));
	}

	@Test
	@Ignore
	public void testGetSessionEtudiantById() {
		try {
			SessionEtudiant se = serviceSession.getSessionEtudiantById(2L);
			System.out.println(se);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testGetSessionByFormateur() {
		SessionEtudiant s;
		try {
			s = serviceSession.getSessionByFormateur(1L);
			System.out.println(s);
		} catch (VerificationInDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void getSessionByEtudiant() {
		try {
			SessionEtudiant se = serviceSession.getSessionByEtudiant(1L);
			System.out.println(se);
		} catch (VerificationInDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTeamLeaderBySession(){
		List<Evenement> e;
		try {
			e = serviceSession.getMoreInformationBySession(1L,"ABSENCE");
			System.out.println(e.size());
			for(Evenement ee:e){
				System.out.println(ee.getEtudiant().getNomEtudiant());
			}
		} catch (VerificationInDataBaseException e1) {
			System.out.println(e1);
		}
		
	}

}
