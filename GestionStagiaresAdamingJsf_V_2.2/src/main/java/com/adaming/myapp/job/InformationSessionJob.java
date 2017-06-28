package com.adaming.myapp.job;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.DebugBeanDefinitionParser;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Module;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.DataEnum;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.SendEmailUtil;

@Component("InformationSessionJob")
public class InformationSessionJob {
    
	@Autowired
	private ISessionService sessionService;
	
	private final String destinataire = "ad.boumaza@yahoo.fr";
	
	private List<Object[]> sessions;
	
	@Scheduled(cron = "0 0 17 * * *")
	public void sessionsQuiTermineCetteSemaine()
	{
		sessions = sessionService.getAllSessionsQuiTermineCetteSemaine();
		if(sessions.size() > 0)
		{
			for(Object[] s:sessions)
			{
				BigInteger numeroSession = (BigInteger) s[0];
				String nomFormateur = (String) s[1];
				String prenomFormateur = (String) s[2];
				Date debut = (Date) s[3];
				Date fin = (Date) s[4];
				String ville = (String) s[5];
				String salle = (String) s[6];
				SendEmailUtil.sendMail(destinataire, "Important", "Bonjour "+"".concat("Hamadi".concat(",")).concat(information(numeroSession, ville, salle, nomFormateur, prenomFormateur, debut, fin)).concat(DataEnum.WARNING.getMessage()).concat(DataEnum.SIGNATURE.getMessage()));
			    LoggerConfig.logInfo("Lancement du job journalière infomation sessions");
			}
		}
		
	}
	
	private String information(BigInteger numeroSession,String ville,String salle,String nomFormateur,String prenomFormateur,Date debut,Date fin){
		final String message = "\n La session numéro"+": "+numeroSession+"termine cette semaine voici les informations nécessaires : "
				+ "\n ville : "+ville
				+ "\n salle : "+salle
				+ "\n formateur : "+nomFormateur.concat(" ").concat(prenomFormateur)
				+ "\n date début : "+debut
				+ "\n date Fin : "+fin;
		return message;
	}
	
	
	
	
	
}
