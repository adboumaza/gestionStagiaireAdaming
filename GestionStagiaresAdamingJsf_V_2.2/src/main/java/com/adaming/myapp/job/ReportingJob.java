package com.adaming.myapp.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.formateur.service.IFormateurService;
import com.adaming.myapp.tools.DataEnum;
import com.adaming.myapp.tools.SendEmailUtil;

@Component("myJob")
public class ReportingJob  {
 
	@Inject
	private IFormateurService formateurService;
	
	private List<Formateur> formateurs;
	private final String message   = DataEnum.MESSAGE.getMessage();
	private final String warning   = DataEnum.WARNING.getMessage();
	private final String signature = DataEnum.SIGNATURE.getMessage();
	
	/**
	 * 
	 *0 15 10 ? * THU-FRI	Fire at 10:15am every Thursday and Friday
	 * */
	
	
	
	
	@Scheduled(cron = "0 15 10 ? * THU-FRI")
	public void send(){
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		final int week = cal.get(Calendar.WEEK_OF_YEAR);
		final String weekString = Integer.toString(week);
		formateurs = getAllFormateur();
		if(formateurs.size() > 0){
			for(Formateur form:formateurs){
				SendEmailUtil.sendMail(form.getMail(), "Reporting", "Bonjour "+form.getCivilite().concat(" ").concat(form.getNom().concat(" ").concat(form.getPrenom()).concat(",")).concat(message.concat(weekString).concat(" .")).concat(warning).concat(signature));
			}
		}
		
	}
	
	private List<Formateur> getAllFormateur(){
		List<Formateur> formateurs = new ArrayList<Formateur>();
		formateurs = formateurService.getFormateursInSessionEncours();
		return formateurs;
	}

}
