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
	 * ces methodes permets de faire l'autocompl�tion remplir le tableau des
	 * sp�cialit� affecter � chaque formateur
	 * 
	 **/
	public static final String [] fillingSpecialites(String query) {
		final String [] specialites = DataEnum.SPECIALITES.getData();
		return specialites;
	}

	public static final String[] fillingNation(String query) {
		final String[] nations = DataEnum.NATIONS.getData();
		return nations;
	}
	
	public static final String [] fillingVilles(String query){
		final String [] villes = DataEnum.VILLES.getData();
		return villes;
	}
	
	public static final String [] fillingPays(String query){
		final String [] pays = DataEnum.PAYS.getData();
		return pays;
	}
}
