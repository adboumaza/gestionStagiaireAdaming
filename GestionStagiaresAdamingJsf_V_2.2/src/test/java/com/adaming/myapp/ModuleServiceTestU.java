package com.adaming.myapp;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.entities.Note;
import com.adaming.myapp.exception.AddModuleException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.module.service.IModuleService;
import com.adaming.myapp.notes.service.INotesService;

public class ModuleServiceTestU {

	private static IModuleService serviceModule;
    private static ClassPathXmlApplicationContext context;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app.xml");
		serviceModule=(IModuleService)context.getBean("ModuleService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}

	@Test
	@Ignore
	public void testAddModule() {
		Module m= new Module("AngularJs2");
		try {
			serviceModule.addModule(m,2L);
			assertNotNull(m.getIdModule());
		} catch (VerificationInDataBaseException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Test
	@Ignore
	public void testGetModuleById() {
		Module m  =serviceModule.getModuleById(1L);
		assertNotNull(m.getIdModule());
	}

	@Test
	@Ignore
	public void testUpdateModule() {
		Module m  = serviceModule.getModuleById(1L);
		m.setNomModule("Hibernate");
		serviceModule.updateModule(m, 1L);
		Module m2 = serviceModule.getModuleById(1L);
		assertThat("Hibernate", IsEqual.equalTo(m2.getNomModule()));
	}

	@Test
	@Ignore
	public void testGetModulesBySession() {
		try {
			List<Object[]> tabM= serviceModule.getModulesBySessionV2(1L);
			for(Object [] o:tabM){
				System.out.println(o[0]);
				System.out.println(o[1]);
				System.out.println(o[4]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	@Test
	@Ignore
	public void getModulesActived(){
		List<Module> modules;
		try {
			modules = serviceModule.getModuleActivedBySession(10L);
		} catch (VerificationInDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	@Ignore
	public void getAllModulePasser(){
		Set<Object[]> modules;
		try {
			modules = serviceModule.getModulesValideBySession(2L);
			for(Object [] m:modules){
				System.out.println(m[1]);
			}
		} catch (VerificationInDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	@Ignore
	public void verificationModule(){
		Module m = serviceModule.verifyExistingModule("angularJs");
		System.out.println(m);
	}
	
	@Test
	public void deactivateModule(){
		//serviceModule.deactivateModule(2L);
	}

}


