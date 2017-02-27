package com.adaming.myapp;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.geonames.Toponym;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.bootsfaces.render.E;

import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;





public class Test {
    
	public static String generateSessionKey(int length){
		String alphabet = 
		        new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
		int n = alphabet.length(); //10

		String result = new String(); 
		Random r = new Random(); //11

		for (int i=0; i<length; i++) //12
		    result = result + alphabet.charAt(r.nextInt(n)); //13

		return result;
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		//System.out.println(generateSessionKey(8));
		
		//String y = Utilitaire.passWordEncoderGenerator("admin");
		//System.out.println(y);
		
	/*
            String myEncryptionPassword ="key";
            String myDataBasePassword ="password";
            BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

            //  1 - Cryptage
            //création d'un Encryptor avec une clé de cryptage
            textEncryptor.setPassword(myEncryptionPassword);
            //mon mot de passe avant cryptage
            System.out.println("Monde passe en clair est : " + myDataBasePassword);                 
            String myEncryptedPassword = textEncryptor.encrypt(myDataBasePassword);
            //mon mot de passe après cryptage
            System.out.println("Monde passe crypté avec la clé : " + myEncryptionPassword+
                            " est " + myEncryptedPassword);

            //  2 - Décryptage
            String plainText = textEncryptor.decrypt(myEncryptedPassword);
            //mon mot de passe après décryptage avec la même clé
            System.out.println("Aprèsyptage avec la clé : " + myEncryptionPassword+
                            " on obtient : " + plainText);                
      
     */
        //System.out.println(Utilitaire.getIpHostname());
		//System.out.println(Utilitaire.getNumberDaysBetweenTwoDates(new Date(),new Date()));		
		//System.out.println(Utilitaire.getVilles("93300"));
		
		/*System.out.println(Utilitaire.getVilles("21000"));
		List<Toponym> get = Utilitaire.getVilles("17 Boulevard de Vaugirard");
		for(Toponym t:get){
			System.out.println(t.getCountryName());
		}*/
		
	/*	BCryptPasswordEncoder bc = new BCryptPasswordEncoder(12);
		System.out.println(bc.encode("admin2017"));
		

		StringBuilder sb = new StringBuilder();
		sb.append("B");
		sb.append("O");
		String sql = "select * from muClass where ";
		sb.append(sql).append("idE").append("").append("=:").append("?");
		
		System.out.println(sb);*/
/*		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		System.out.println(origRequest);*/
	}
	
	
}
