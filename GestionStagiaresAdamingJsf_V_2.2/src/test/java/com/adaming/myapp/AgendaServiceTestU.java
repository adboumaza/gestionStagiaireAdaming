package com.adaming.myapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.agenda.service.IAgendaService;
import com.adaming.myapp.entities.Agenda;

public class AgendaServiceTestU {

	private static IAgendaService serviceAgenda;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceAgenda=(IAgendaService)context.getBean("AgendaService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}
	
	@Test
	@Ignore
	public void testAddAgenda() {
	  serviceAgenda.addAgenda(new Agenda("SQL", new Date(), new Date()), 3L);
	}
	
	@Test
	@Ignore
	public void testUpdateAgenda() {
	  Agenda a = serviceAgenda.getAgenda(4L);
	  a.setLabel("SPRING");
	  serviceAgenda.updateAgenda(a, 4L);
	}
	
	@Test
	@Ignore
	public void testDeleteAgenda() {
	    serviceAgenda.deleteAgenda(4L);
	}
    @Test
    @Ignore
	public void testgetAllAgenda() {
	   serviceAgenda.getAllAgenda();
	}
    @Test
    @Ignore
    public void testgetAllAgendaByName() {
 	  List<Agenda> a =  serviceAgenda.getAllAgendaByName("Admin");
 	  System.out.println(a);
    }
    
    @Test
    public void testgetAgendaByUser() throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date d1 = sdf.parse("2017-03-07 23:00:00");
    	Date d2 = sdf.parse("2017-03-07 23:00:00");
 	  Agenda a =  serviceAgenda.getAgendaByUserAndDate("Admin", d1, d2, "spring");
 	  System.out.println(a);
    }
}
