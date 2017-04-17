package com.adaming.myapp;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.agenda.service.IAgendaService;
import com.adaming.myapp.evaluation.service.IEvaluationService;

public class EvaluationServiceTestU {

	private static IEvaluationService serviceEvaluation;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceEvaluation=(IEvaluationService)context.getBean("EvaluationService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}
	@Test
	public void testAddEvaluation() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerifyExistingEvaluation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllEvaluationsBySessionAndModule() {
		System.out.println(serviceEvaluation.getEvaluationByEtudiant(1L, 8L));
	}

	@Test
	public void testGetEvaluationByEtudiant() {
		fail("Not yet implemented");
	}

}
