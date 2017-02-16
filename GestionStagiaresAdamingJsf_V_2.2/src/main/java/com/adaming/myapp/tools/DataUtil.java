package com.adaming.myapp.tools;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public final class DataUtil {

	/**
	 * @Autocomplete pour faire l'autocompl�tion remplir le tableau des
	 * sp�cialit� affecter � chaque formateur
	 * 
	 **/
	public static final List<String> fillingSpecialites(String query) {
		List<String> specialites = new ArrayList<String>();
		specialites.add("Accompagnateur du changement");
		specialites.add("Administrateur de bases de donn�es");
		specialites.add("Administrateur r�seaux");
		specialites.add("Administrateur s�curit�");
		specialites.add("Analyste d'affaires");
		specialites.add("Architecte de donn�es");
		specialites.add("Architecte logiciel");
		specialites.add("Chief Data Officer");
		specialites.add("Concepteur");
		specialites.add("Concepteur d�veloppeur informatique");
		specialites.add("D�veloppeur Java");
		specialites.add("D�veloppeur .Net");
		specialites.add("D�veloppeur Php");
		specialites.add("D�veloppeur Cobol");
		specialites.add("Chief Data Officer");
		specialites.add("Exploitation informatique");
		specialites.add("Informaticien de gestion");
		specialites.add("Int�grateur");
		specialites.add("Mainframe");
		specialites.add("Ma�trise d'ouvrage");
		specialites.add("M�tiers du web");
		specialites.add("Responsable d'exploitation");
		specialites
				.add("Responsable de la s�curit� des syst�mes d'information");
		return specialites;
	}

	public static final String[] fillingNation(String query) {
		String[] nations = new String[] { "Afghan", "Albanais", "Alg�rien",
				"Allemand", "Am�ricain", "Angolais", "Angolais", "Argentin",
				"Arm�nien", "Arubais", "Australien", "Autrichien", "Baham�en",
				"Bangladais", "Belge", "Benin", "Bi�lorusse", "Bolivien",
				"Bosniaque", "Botswanais", "Bouthan", "Br�silien",
				"Britannique", "Bulgare", "Burkinab�", "Cambodgien",
				"Camerounais", "Canadien", "Chilien", "Chinois", "Chypriote",
				"Colombien", "Congolais", "Croate", "Cubain", "Danois",
				"Dominicain", "Ecossais", "Egyptien", "Equatorien", "Espagnol",
				"Estonien", "Ethiopien", "Europ�en", "Finlandais", "Fran�ais",
				"Drapeau", "Gabonais", "Georgien", "Ghan�en", "Grec",
				"Guatemala", "Guin�en", "Ha�tien", "Hollandais", "Hongrois",
				"Indien", "Indon�sien", "Irakien", "Iranien", "Irlandais",
				"Islandais", "Isra�lien", "Italien", "Ivoirien", "Jama�cain",
				"Japonais", "Jordanien", "Kazakh", "Kenyan", "Kirghiz",
				"Kosovar", "Kowe�tien", "Kurde", "Letton", "Libanais",
				"Libyen", "Liechtenstein", "Lituanien", "Luxembourgeois",
				"Mac�donien", "Madagascar", "Malaisien", "Malien", "Maltais","Malgache",
				"Marocain", "Mauritanien", "Mauritien", "Mexicain",
				"Mon�gasque", "Mongol", "Mozambique", "N�o-Z�landais",
				"N�palais", "Nig�rien", "Nord Cor�en", "Norv�gien",
				"Pakistanais", "Palestinien", "Panam�en", "Paraguayen",
				"P�ruvien", "Philippiens", "Polonais", "Portoricain",
				"Portugais", "Qatarien", "Roumain", "Russe", "Rwandais",
				"Saoudien", "S�n�galais", "Serbe", "Serbo-Croate", "Singapour",
				"Slovaque", "Sovi�tique", "Sri-Lankais", "Sud-Africain",
				"Sud-Cor�en", "Su�dois", "Suisse", "Syrien", "Tadjik",
				"Ta�wanais", "Tanzanien", "Tchadien", "Tch�que", "Tha�landais",
				"Tunisien", "Turc", "Ukranien", "Uruguayen", "V�n�zu�lien",
				"Vietnamien", "Yougoslave", "Zimbabw�en" };
		return nations;
	}
	
	public static final String [] fillingVilles(String query){
		String [] villes = new String []{
        "Bourg-en-Bresse -01","Laon -02","Moulins -03","Digne-les-Bains -04","Gap -05","Nice -06","Privas -07","Charleville-M�zi�res -08","Foix -09","Troyes -10","Carcassonne -11","Rodez -12",
        "Marseille -13","Caen -14","Aurillac -15","Angoul�me -16","La Rochelle -17","Bourges -18","19 - Tulle","20 - Ajaccio","21 - Dijon","22 - Saint-Brieuc","23 - Gu�ret",
        "P�rigueux -24","Besan�on -25","Valence -26","�vreux -27","Chartres -28","Quimper -29","N�mes -30","Toulouse -31","Auch -32","Bordeaux -33","Montpellier -34",
        "Rennes -35","Ch�teauroux -36","Tours -37","Grenoble -38","Lons-le-Saunier -39","Mont-de-Marsan -40","Blois -41","Saint-�tienne -42","Le Puy-en-Velay -43","Nantes -44","Orl�ans -45",
        "Cahors -46","Agen -47","Mende -48","Angers -49","Saint-L� -50","Ch�lons-en-Champagne -51","Chaumont -52","Laval -53","Nancy -54","Bar-le-Duc -55","Vannes -56",
        "Metz -57","Nevers -58","Lille -59","Beauvais -60","Alen�on -61","Arras -62","Clermont-Ferrand -63","Pau -64","Tarbes -65","Perpignan -66","Strasbourg -67",
        "Colmar -68","Lyon -69","Vesoul -70","M�con -71","Le Mans -72","Chamb�ry -73","Annecy -74","Paris -75","Rouen -76","Melun -77","Versailles -78",
        "Niort -79","Amiens -80","Albi -81","Montauban -82","Toulon -83","Avignon -84","La Roche-sur-Yon -85","86 - Poitiers","87 - Limoges","88 - �pinal","89 - Auxerre",
        "Belfort -90","�vry -91","Nanterre -92","Bobigny -93","Cr�teil -94","Cergy(chef-lieu � Pontoise) -95"
		};
		return villes;
	}
}
