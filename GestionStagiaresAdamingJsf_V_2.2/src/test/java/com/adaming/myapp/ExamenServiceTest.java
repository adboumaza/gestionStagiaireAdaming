package com.adaming.myapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.contrat.service.IContratService;
import com.adaming.myapp.entities.Examen;
import com.adaming.myapp.examen.service.IExamenService;

public class ExamenServiceTest {

	private static IExamenService serviceExamen;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceExamen=(IExamenService)context.getBean("ExamenService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}
	
	@Test
	@Ignore
	public void addExamen(){
		try{
			Examen examen = new Examen(new Date());
			serviceExamen.addExamen(examen, 1L, 1L,1L);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getAllQuesionsQuizByModule(){
		List<Object[]> results = new ArrayList<Object[]> (serviceExamen.getAllQuestionsQuizByModule("Quiz - UML",3));
		for(Object[] o : results){
			System.out.println(o[0] + " "+o[1]+" "+o[2]);
		}
	}
}
