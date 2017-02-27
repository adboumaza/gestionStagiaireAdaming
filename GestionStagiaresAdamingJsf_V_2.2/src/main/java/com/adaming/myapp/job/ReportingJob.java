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
import com.adaming.myapp.tools.SendEmailUtil;

@Component("myJob")
public class ReportingJob  {
 
	@Inject
	private IFormateurService formateurService;
	private List<Formateur> formateurs;
	private final String message   = " \n Merci de procéder à la préparation et l’envoi  de vos «  weekly reports » de la semaine N° ";
	private final String warning   = " \n\n Ce message a été generé automatiquement, veuillez ne pas y répondre.";
	private final String signature = " \n\n L'equipe intiformation.com";
	
	@Scheduled(cron = "0 0/3 * * * ?")
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
		formateurs = formateurService.getAllFormateurs();
		return formateurs;
	}

	
	
	

}
