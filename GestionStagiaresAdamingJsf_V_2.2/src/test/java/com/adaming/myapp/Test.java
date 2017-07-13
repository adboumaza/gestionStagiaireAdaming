package com.adaming.myapp;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.geonames.Toponym;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.bootsfaces.render.E;

import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
import com.sun.el.parser.Node;





public class Test {
	
	
	
	public static void main(String[] args) throws ParseException, ParserConfigurationException, MalformedURLException, SAXException, IOException, TransformerException {
		 

		String pswEnc = Utilitaire.passWordEncoderGenerator("admin2017");
		System.out.println(pswEnc);
		
		/*$2a$12$MhU5MRDeg2Y7ojmjIUvy1eIk12clnI/AiLqKJRCIVgsvVDh.bTDL6*/
		/*$2a$12$TJvAUc4P8aBICLGYFkMTmuapuWihKXZkaJT7o.BrJrYL19JmDrJN.*/
		
		
		
		/* String url="https://www.treasury.gov/ofac/downloads/sdn.xml";
	        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc = db.parse(new URL(url).openStream());
	        Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        StringWriter writer = new StringWriter();
	        transformer.transform(new DOMSource(doc), new StreamResult(writer));
	        String output = writer.getBuffer().toString();
            System.out.println(output);
	        NodeList nodeList = doc.getElementsByTagName("ENTITY");
	        for(int x=0,size= nodeList.getLength(); x<size; x++) {
	           // System.out.println(nodeList.item(x).getAttributes().getNamedItem("pdf_link").getNodeValue());
	          //System.out.println(nodeList.item(x).getAttributes().getNamedItem("Id").getNodeValue());
	        } 
	       */

          /*

            NodeList nodes = doc.getElementsByTagName("NAME");
            for (int i = 0; i < nodes.getLength(); i++) {
            	System.out.println(nodes.item(i).getTextContent());
            }*/
		
		
		
		
		
		
		
		
		/*String x = generateSessionKey(1);
		System.out.println(x);*/
		// Format for input
		/*DateTime dt = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		String dtStr = fmt.print(dt);
		System.out.println(dtStr);*/
		
		/*DateTime [] holydays = new DateTime[12];
		DateTime date = null;
		for(int i =0,length = holydays.length;i<length;i++){
			date = new DateTime();
			holydays[i] = date;
		}*/
		/*holydays[0] = date.monthOfYear().addToCopy(6);
		holydays[0] = date.dayOfMonth().setCopy(1);
		holydays[1] = date.monthOfYear().setCopy(4);
		holydays[1] = date.dayOfMonth().setCopy(16);
		holydays[2] = date.monthOfYear().setCopy(4);
		holydays[2] = date.dayOfMonth().setCopy(17);
		holydays[3] = date.monthOfYear().setCopy(5);
		holydays[3] = date.dayOfMonth().setCopy(1);
		holydays[4] = date.monthOfYear().setCopy(5);
		holydays[4] = date.dayOfMonth().setCopy(8);
		holydays[5] = date.monthOfYear().setCopy(5);
		holydays[5] = date.dayOfMonth().setCopy(25);
		holydays[6] = date.dayOfMonth().setCopy(4);
		holydays[6] = date.monthOfYear().setCopy(6);
		holydays[7] = date.dayOfMonth().setCopy(14);
		holydays[7] = date.monthOfYear().setCopy(7);
		holydays[8] = date.dayOfMonth().setCopy(15);
		holydays[8] = date.monthOfYear().setCopy(8);
		holydays[9] = date.dayOfMonth().setCopy(1);
		holydays[9] = date.monthOfYear().setCopy(11);
		holydays[10] = date.dayOfMonth().setCopy(11);
		holydays[10] = date.monthOfYear().setCopy(11);
		holydays[11] = date.dayOfMonth().setCopy(25);
		holydays[11] = date.monthOfYear().setCopy(12);

		*/
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		
		Date d1 = sdf.parse("01-05-2017 08:00:00");
		Date d2 = sdf.parse("01-05-2017 19:00:00");
		
		
        System.out.println(((d2.getTime() - d1.getTime())/1000)/3600);*/
	
		
		
		/*if((c2.get(Calendar.HOUR_OF_DAY) - c1.get(Calendar.HOUR_OF_DAY)) > 10){
			System.out.println("ko");
			System.out.println((c2.get(Calendar.HOUR_OF_DAY)) - (c1.get(Calendar.HOUR_OF_DAY)));
		}else{
			System.out.println("OK");
			System.out.println((c2.get(Calendar.HOUR_OF_DAY)) - (c1.get(Calendar.HOUR_OF_DAY)));
		}*/
		
		
		
		
/*		
		
	
	}
    
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
	
	
	
	//public static void main(String[] args) throws InstantiationException, IllegalAccessException {
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
		
		/*BCryptPasswordEncoder bc = new BCryptPasswordEncoder(11);
		System.out.println(bc.encode("admin2017"));*/
		//$2a$12$XTHhjSbnjJTn93M/t2btSO8opTYTPF.V/JsHr.86//PA1V7vrSkem
		//$2a$12$93C/Kx1SAls.ZJMlskyJrOBkitcvxjvY/CahhgGVkqC55IifRs4ku
	//}
	
	
	}
	
	
}
