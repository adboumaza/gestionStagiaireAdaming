package com.adaming.myapp.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.Weeks;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public class Utilitaire {

	private static ResourceBundle bundle = null;
	private static String userName;
	private static String userPassWord;
	
	
	
	public static long getHoursBetweenTwoDates(Date debut,Date fin){
		return ((fin.getTime() - debut.getTime())/1000)/3600;
	}
	
	
	public static DateTime [] holydays(DateTime dateTime){
		DateTime [] holydays = new DateTime[8];
		DateTime dt  = new DateTime(dateTime);
		DateTime dt1 = new DateTime(dt.getYear(), 1, 1, 0, 0, 0, 0);
		DateTime dt2 = new DateTime(dt.getYear(), 5, 1, 0, 0, 0, 0);
		DateTime dt3 = new DateTime(dt.getYear(), 5, 8, 0, 0, 0, 0);
		DateTime dt4 = new DateTime(dt.getYear(), 7, 14, 0, 0, 0, 0);
		DateTime dt5 = new DateTime(dt.getYear(), 8, 15, 0, 0, 0, 0);
		DateTime dt6 = new DateTime(dt.getYear(), 9, 1, 0, 0, 0, 0);
		DateTime dt7 = new DateTime(dt.getYear(), 9, 11, 0, 0, 0, 0);
		DateTime dt8 = new DateTime(dt.getYear(), 12, 25, 0, 0, 0, 0);
		holydays[0] = dt1;
		holydays[1] = dt2;
		holydays[2] = dt3;
		holydays[3] = dt4;
		holydays[4] = dt5;
		holydays[5] = dt6;
		holydays[6] = dt7;
		holydays[7] = dt8;
		
		return holydays;
	}
	
	
	public static int getWeeksOfYears(Date date1)
	{
		DateTime dtFr = new DateTime(date1, DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris")));
	    int numberOfWeeck = dtFr.getWeekOfWeekyear();
	    return numberOfWeeck;
	}
	
	
	/**
	 *@message  jsf warning
	 *@param String le message à afficher 
	 ***/
	public static void displayMessageWarning(final String message){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new
		FacesMessage(FacesMessage.SEVERITY_WARN,"Warning",message));
	}
	
	/**
	 *@message  jsf error
	 *@param String le message à afficher 
	 ***/
	public static void displayMessageError(final String message){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new
		FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",message));
	}
	
	/**
	 *@message  jsf info
	 *@param String le message à afficher 
	 ***/
	public static void displayMessageInfo(final String message){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new
		FacesMessage(FacesMessage.SEVERITY_INFO,"succès",message));
	}
	
	
	
	/**@webSetvice 
	 * @see http://www.geonames.org/source-code/
	 * @method getVilles 
	 * @param villeSearched the key for searchig
	 * @return objectFound list of cities
	 */
	public static List<Toponym> getVilles(String villeSearched){
		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		searchCriteria.setQ(villeSearched);//Exemple : Pour le code postal 7321 en Belgique
		ToponymSearchResult searchResult;
		List<Toponym> objectFound = null;
		objectFound = new ArrayList<Toponym>();
		try 
		{
			WebService.setUserName("boumazaadel");//Un nombre limité de requêtes par jour (30.000 par username)
			searchResult = WebService.search(searchCriteria);
			for (Toponym toponym : searchResult.getToponyms())
			{
			  // System.out.println(toponym.getName());//Affiche le nom des entités ayant le code postal 7321 (ici Harchies et Blaton)
			   objectFound.add(toponym);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return objectFound;
	}
	/**
	 * @method filterObject for l'autocomplition
	 * @param query teh key for searchig
	 * @param searchedObject the list of object searched
	 * @return filtred object founded
	 */
	public static  List<String> filterObject(String query, List<String> searchedObject) {
		List<String> filtred = new ArrayList<String>();
		for(int i=0;i<searchedObject.size();i++){
			String searche = searchedObject.get(i);
			if(searche.toLowerCase().contains(query)){
				filtred.add(searche);
			}
		}
		return filtred;
	}
	
	/**
     * @methode convert Set to list
     * 
     * */
	
	public static List<?> convertSetToList(Set<?> mySet){
		List<?> myList = new ArrayList<>(mySet);
		return myList;
		
	}
	
	
	/**
     * @methode generateRandomKey, generate a random password 
     * @param length, the size of password 
     * @return Random password generated
     * 
     * */
	
	public  static String generateRandomKey(final int length) {
		String alphabet = new String(
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // 9
		int n = alphabet.length(); // 10

		String result = new String();
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++)
			// 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;

	}
	
	
	
    /**
     * @methode passWordEncoderGenerator for encode the password of every user 
     * @param input password for crypting
     * @return password crypted
     * 
     * */
	public static String passWordEncoderGenerator(String input) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
		String hashedPassword = passwordEncoder.encode(input);
		return hashedPassword;
	}

	public static Date getLastDayOfMonth(Integer annee, Integer mois) {
		if (annee != null && mois != null) {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(Calendar.MONTH, mois - 1);
			calendar.set(Calendar.YEAR, annee);
			int lastDayOfMonth = calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			Date date = calendar.getTime();
			return date;
		} else {
			return null;
		}
	}

	public static String dateToString(Date dateForFormat) {
		String date = "";
		if (dateForFormat != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateForFormat);
			int annee = calendar.get(Calendar.YEAR);
			int mois = calendar.get(Calendar.MONTH);
			mois = mois + 1;
			int jour = calendar.get(Calendar.DAY_OF_MONTH);

			if (mois < 10) {
				if (jour < 10) {
					date = "0" + jour + "-0" + mois + "-" + annee;
				} else {
					date = jour + "-0" + mois + "-" + annee;
				}
			} else {
				if (jour < 10) {
					date = "0" + jour + "-" + mois + "-" + annee;
				} else {
					date = jour + "-" + mois + "-" + annee;
				}
			}

		}
		return date;
	}

	public static String convertDateToString(Date date, String format) {
		String dateFormatee;
		if (date != null) {
			SimpleDateFormat formatDateJour = new SimpleDateFormat(format);
			dateFormatee = formatDateJour.format(date);
		} else {
			dateFormatee = "";
		}
		return dateFormatee;
	}

	public static String convertLongToString(Long val) {
		String valConvert;
		if (val != null) {
			valConvert = Long.toString(val);
		} else {
			valConvert = "";
		}
		return valConvert;
	}

	/**
	 * cette méthode consiste à crypter en MD5 le champ entré en paramètre
	 * 
	 * @param input
	 *            le champ à crypter
	 * @return retourner le champ crypté
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashMD5Crypt(String input)
			throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(input.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;

	}

	public static boolean isEmpty(List<?> liste) {
		if (liste == null || liste.isEmpty()) {
			return true;
		}
		return false;

	}

	public static String formatDeciaml(String input) {
		if (!Utilitaire.isEmpty(input) && isNumeric(input) && isMnt(input)) {
			if (input.indexOf('.') != -1) {
				int cmp = input.length() - 1;
				Character c = input.charAt(cmp);
				while (cmp > 0 && !c.equals('.') && c.equals('0')) {
					input = input.substring(0, cmp);
					cmp--;

					c = input.charAt(cmp);
				}
				if (c.equals('.')) {
					input = input.substring(0, cmp);
				}
			}
			String dec = "";
			String vir = "";
			if (input.indexOf('.') != -1) {
				vir = "." + input.substring(input.indexOf('.') + 1);
				dec = input.substring(0, input.indexOf('.'));
				input = format(dec) + vir;
			} else {
				dec = input;
				input = format(dec);
			}

		}
		return input;
	}

	public static String preparePlaceHolders(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length;) {
			builder.append("?");
			if (++i < length) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	public static boolean isNumeric(String input) {
		boolean result = false;
		if (!Utilitaire.isEmpty(input)) {
			try {
				Double.parseDouble(input);
				result = true;
			} catch (NumberFormatException nme) {
				nme.getMessage();
			}
		}
		return result;
	}

	public static String format(String s) {
		int rest = s.length() % 3;
		List<Character> l = new ArrayList<Character>();
		int cmps = 0;
		for (int cmp = 0; cmp < rest; cmp++) {
			l.add(s.charAt(cmp));
			cmps++;
		}
		l.add(' ');
		while (cmps < s.length() - 1) {
			for (int cmp = 0; cmp < 3; cmp++) {
				l.add(s.charAt(cmps));
				cmps++;
			}
			l.add(' ');
		}
		int cmp = 0;
		String res = "";
		while (cmp < l.size()) {
			res += l.get(cmp);
			cmp++;
		}
		return res.trim();
	}

	public static boolean isMnt(String input) {
		boolean mnt = false;
		if (!isEmpty(input)) {
			Pattern p = Pattern.compile("\\d+\\.\\d+");
			Matcher m = p.matcher(input);
			Pattern p2 = Pattern.compile("\\d+");
			Matcher m2 = p2.matcher(input);
			boolean matchFound = m.matches();
			boolean matchFound2 = m2.matches();
			if (matchFound || matchFound2) {
				mnt = true;
			} else {
				mnt = false;
			}
		}
		return mnt;
	}

	public static String getMessages(String key) {
		String message = null;
		try {
			message = bundle.getString(key);
		} catch (MissingResourceException e) {
			e.getMessage();
			return "unkown message " + key;
		}
		return message;
	}

	public static Date convertStringToDate(String dateInString, String format) {

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.getMessage();
		}
		return date;
	}

	public static Date convertStringToDate(String dateInString) {

		return convertStringToDate(dateInString, "dd/MM/yyyy");
	}

	public static Date convertStringToHeure(String heureInString) {

		return convertStringToDate(heureInString, "HH:mm:ss");
	}

	public static java.sql.Date convertUtilDateToSqlDate(java.util.Date dateUtil) {
		java.sql.Date sqlDate = null;
		if (dateUtil != null) {
			sqlDate = new java.sql.Date(dateUtil.getTime());

		}
		return sqlDate;
	}

	public static java.util.Date convertSqlDateToUtillDate(java.sql.Date sqlDate) {
		java.util.Date utilDate = null;
		if (sqlDate != null) {
			utilDate = new java.util.Date(sqlDate.getTime());
		}
		return utilDate;
	}

	public static Boolean isDateFromFileValid(String dateYYYMMDD) {
		String year = dateYYYMMDD.substring(0, 4);
		String month = dateYYYMMDD.substring(4, 6);
		String day = dateYYYMMDD.substring(6, 8);
		if (!isNumeric(year)) {
			return false;
		}

		if (isNumeric(month)) {
			if (Integer.valueOf(month) > 12) {
				return false;
			}
		} else {
			return false;
		}
		if (isNumeric(day)) {
			if (Integer.valueOf(day) > 31) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static Boolean isStringContainsExactlyValue(String chaine,
			String value) {
		return chaine.matches(".*\\b" + value + "\\b.*") ? true : false;
	}

	public static Boolean isNullOrNot(String attribut, String regleValue) {
		String chaineVide = "[ ]*";
		if (regleValue.contains("{}")) {
			if (regleValue.trim().startsWith("<>")) {
				if (attribut == null) {
					return false;
				} else {
					Boolean matches = attribut.matches(chaineVide);
					if (matches) {
						return false;
					}
				}

			} else if (regleValue.trim().startsWith("=")) {
				if (attribut != null) {
					Boolean matches = attribut.matches(chaineVide);
					if (!matches) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static List<String> getListNameFields(Field[] tabField) {
		List<String> listNames = new ArrayList<String>();
		for (int i = 0; i < tabField.length; i++) {
			listNames.add(tabField[i].getName());
		}
		return listNames;
	}

	public static Object constructeObjectFromView(Object obj, Object view) {
		Field[] tabFieldObj = obj.getClass().getDeclaredFields();
		Field[] tabFieldView = view.getClass().getDeclaredFields();
		List<String> listNameFieldsView = getListNameFields(tabFieldView);
		List<Field> listFieldObj = new ArrayList<Field>(
				Arrays.asList(tabFieldObj));
		List<String> listTypes = new ArrayList<String>();
		listTypes.add("Boolean");
		listTypes.add("Integer");
		listTypes.add("Long");
		listTypes.add("Character");
		listTypes.add("String");
		listTypes.add("Double");
		listTypes.add("Float");
		listTypes.add("Date");
		listTypes.add("BigDecimal");

		for (Field fieldObj : listFieldObj) {
			if (listTypes.contains(fieldObj.getType().getSimpleName())
					&& listNameFieldsView.contains(fieldObj.getName())) {

				try {
					fieldObj.setAccessible(true);
					Field fieldView = view.getClass().getDeclaredField(
							fieldObj.getName());

					fieldView.setAccessible(true);
					fieldObj.set(obj, fieldView.get(view));
				} catch (IllegalArgumentException | IllegalAccessException
						| NoSuchFieldException | SecurityException e) {

					LoggerConfig.logDebug(e.getMessage());
				}
			}
		}

		return obj;
	}

	public static File convertInputStreamIntFile(InputStream inputStream,
			String pathFile) {
		OutputStream outputStream = null;
		File file = new File(pathFile);
		try {

			outputStream = new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024000];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.getMessage();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.getMessage();
				}

			}
		}
		return file;

	}

	public static Boolean isZipFile(File file) throws IOException {
		ZipInputStream zipInputStream = null;
		ZipEntry zipEntry = null;
		zipInputStream = new ZipInputStream(new FileInputStream(file));
		zipEntry = zipInputStream.getNextEntry();
		zipInputStream.close();

		return zipEntry != null;
	}

	public static Long formaterMontantBigDecimalToLong(BigDecimal montant,
			Integer nbreDecimal) {
		Integer coeff = 1;
		if (nbreDecimal != null) {
			for (int i = 0; i < nbreDecimal; i++) {
				coeff = coeff * 10;

			}
		}

		return montant.multiply(new BigDecimal(coeff)).longValue();
	}

	public static String formaterMontantBigDecimalToString(BigDecimal montant,
			Integer nbreDecimal) {
		Integer coeff = 1;
		if (nbreDecimal != null) {
			for (int i = 0; i < nbreDecimal; i++) {
				coeff = coeff * 10;

			}
		}

		return montant.multiply(new BigDecimal(coeff)).toString();
	}

	@SuppressWarnings("deprecation")
	public static List<Date> getListDatesBetweenTwoDate(Date dateBeguin,
			Date dateEnd) {
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateBeguin);

		while (calendar.getTime().before(dateEnd)) {
			Date date = calendar.getTime();
			if (date.getDay() != 0 && date.getDay() != 6) {
				dates.add(date);
			}
			calendar.add(Calendar.DATE, 1);
		}
		return dates;
	}

	public static Date getDateByIndice(Date dateBeguin, Integer indice) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateBeguin);
		calendar.add(Calendar.DATE, -indice);

		return calendar.getTime();

	}

	public static Integer getNumberDaysBetweenTwoDates(Date dateBeguin,
			Date dateEnd) {
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateBeguin);

		while (calendar.getTime().before(dateEnd)) {
			Date date = calendar.getTime();
			dates.add(date);
			calendar.add(Calendar.DATE, 1);
		}
		return dates.size();
	}

	public static String formatMontantLongToString(Long montant,
			int nbreDecimal, String abr) {

		String montantStr = montant.toString();
		String montantSFormate = new StringBuilder(montantStr).insert(
				montantStr.length() - nbreDecimal, ".").toString();
		return montantSFormate + " " + abr;
	}

	public static byte[] extraireImageFromPak(InputStream stream,
			Integer posDeb, Integer longueur, boolean isRecto) {

		Integer nbreChar = longueur;
		byte[] tabByte = new byte[nbreChar];
		try {
			if (isRecto) {
				stream.skip(posDeb);
			}
			stream.read(tabByte, 0, nbreChar);
		} catch (IOException e) {
			e.getMessage();
		}
		return tabByte;
	}

	public static byte[] extraireImageFromPak(byte[] stream, Integer posDeb,
			Integer longueur, boolean isRecto) throws IOException {

		Integer nbreChar = longueur;

		byte[] tabByte = new byte[nbreChar];
		System.arraycopy(stream, posDeb, tabByte, 0, nbreChar);
		return tabByte;
	}

	public static byte[] readFully(InputStream input) throws IOException {
		byte[] buffer = new byte[Integer.MAX_VALUE];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}

	public static Long convertHeureToLong(Date date) {
		Long heure = 0L;
		String dateStr = convertDateToString(date, "HHmmss");
		try {
			heure = Long.valueOf(dateStr);
		} catch (NumberFormatException e) {
			e.getMessage();
		}
		return heure;
	}

	public static BigDecimal convertStringToBigDecimal(String montantStr,
			Integer nbreDecimal) {

		BigDecimal montantValeur = null;
		if (nbreDecimal == null) {
			nbreDecimal = 0;
		}

		montantStr = new StringBuilder(montantStr).insert(
				montantStr.length() - nbreDecimal, ".").toString();
		montantValeur = new BigDecimal(montantStr);
		return montantValeur;
	}

	public static Integer incrementIndexJourney(Date dateJour,
			List<Date> joursFeries, int indexJourney) {

		Calendar cal = Calendar.getInstance();
		boolean jourFerie = false;
		cal.setTime(dateJour);
		if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY
				&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			if (joursFeries != null && !joursFeries.isEmpty()) {
				int index = 0;
				while (index < joursFeries.size() && jourFerie == false) {
					if (joursFeries.get(index).compareTo(cal.getTime()) == 0) {
						jourFerie = true;
					}
					index++;
				}
				if (!jourFerie) {
					indexJourney++;
				}
			} else {
				indexJourney++;
			}
		}

		return indexJourney;
	}

	/**
	 * cette méthode consiste à récupérer l'image au format byte[]
	 * 
	 * @param stream
	 *            l'image à convertir
	 * @param longueur
	 *            la longueur de l'image
	 * @param isRecto
	 *            préciser si c'est le recto de l'image
	 * @return retourner l'image en format byte[]
	 * @exception IOException
	 *                si l'image est introuvable
	 */
	public static byte[] extraireImageFromBlob(InputStream stream,
			Integer longueur, boolean isRecto) {

		Integer nbreChar = longueur;
		byte[] tabByte = new byte[nbreChar];

		try {
			stream.read(tabByte);

		} catch (IOException e) {
			e.getMessage();
		}

		return tabByte;
	}

	public static BufferedImage toBufferedImage(Image image, int type) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage result = new BufferedImage(w, h, type);
		Graphics2D g = result.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return result;
	}

	public static String generateRandomNumbers(int length) {
		String chars = "1234567890";
		StringBuilder pass = new StringBuilder();
		for (int x = 0; x < length; x++) {
			int i = (int) Math.floor(Math.random() * (chars.length() - 1));
			pass.append(chars.charAt(i));
		}
		return pass.toString();
	}

	public void unZipRcpFile(String zipFile, String outputFolder) {

		byte[] buffer = new byte[1024];

		try {

			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator
						+ fileName);

				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			ex.getMessage();
		}
	}

	/**
	 * cette méthode consiste à fermer le resultSet passé en paramètre
	 * 
	 * @param rs
	 *            le resultset à fermer
	 */
	public static void closeConnexion(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.getMessage();
			}
	}

	/**
	 * cette méthode consiste à fermer le statement passé en paramètre
	 * 
	 * @param stm
	 *            le statement à fermer
	 */
	public static void closeConnexion(Statement stm) {
		if (stm != null)
			try {
				stm.close();
			} catch (SQLException e) {
				e.getMessage();
			}
	}

	/**
	 * cette méthode consiste à fermer la connexion passée en paramètre
	 * 
	 * @param con
	 *            la connexion à fermer
	 */
	public static void closeConnexion(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.getMessage();
			}
	}


	public static boolean deplacer(File source, File destination) {
		if (!destination.exists()) {
			// On essaye avec renameTo
			boolean result = source.renameTo(destination);
			if (!result) {
				// On essaye de copier
				result = true;
				result &= copyFile(source, destination);
				if (result)
					result &= source.delete();

			}
			return (result);
		} else {
			// Si le fichier destination existe,
			source.renameTo(destination);
			return (false);
		}
	}

	public static boolean copyFile(File source, File dest) {
		if (source != null && dest != null) {
			try {
				// Declaration et ouverture des flux
				FileInputStream sourceFile = new FileInputStream(source);

				try {
					FileOutputStream destinationFile = null;

					try {
						destinationFile = new FileOutputStream(dest);

						// Lecture par segment de 0.5Mo
						byte buffer[] = new byte[512 * 1024];
						int nbLecture;

						while ((nbLecture = sourceFile.read(buffer)) != -1) {
							destinationFile.write(buffer, 0, nbLecture);
						}
					} finally {
						destinationFile.close();
					}
				} finally {
					sourceFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false; // Erreur
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Cette Méthode retourne une chaine contenant l'heure et les minute
	 * 
	 * @param initStringDate
	 *            : Date parsé en chaine.
	 * @return sRetour : chaine de Heure:Minute
	 * 
	 */
	public static String getOnlyHourMinuteFromDateWithStringFormat(
			String initStringDate) {
		String sRetour = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		Date parsedDate;
		try {
			parsedDate = sdf.parse(initStringDate);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(parsedDate);
			Integer min = calendar.get(Calendar.MINUTE);
			Integer hour = calendar.get(Calendar.HOUR);

			StringBuilder sb = new StringBuilder();
			sb.append(hour).append(":").append(min);
			sRetour = sb.toString();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return sRetour;

	}

	/* end */

	/**
	 * @return the userName
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public static void setUserName(String userName) {
		Utilitaire.userName = userName;
	}

	/**
	 * @return the userPassWord
	 */
	public static String getUserPassWord() {
		return userPassWord;
	}

	/**
	 * @param userPassWord
	 *            the userPassWord to set
	 */
	public static void setUserPassWord(String userPassWord) {
		Utilitaire.userPassWord = userPassWord;
	}

	public static boolean isHourInInterval(Date target, Date start, Date end) {
		return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
	}

	/**
	 * Date overleap : vérification si marges de date s'interceptent
	 * 
	 * 
	 * @param s1
	 *            the s1
	 * @param e1
	 *            the e1
	 * @param s2
	 *            the s2
	 * @param e2
	 *            the e2
	 * @return true, if successful
	 */
	public static boolean dateOverleap(Date s1, Date e1, Date s2, Date e2) {
		boolean verif = false;
		DateTime start1 = new DateTime(s1);
		DateTime end1 = new DateTime(e1);

		DateTime start2 = new DateTime(s2);
		DateTime end2 = new DateTime(e2);

		Interval interval = new Interval(start1, end1);
		Interval interval2 = new Interval(start2, end2);

		System.out.println(interval.overlaps(interval2));

		verif = interval.overlaps(interval2);
		return verif;
	}

	public static void deleteFileFromPath(String path) {
		try {
			File file = new File(path);

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getIpHostname() {
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
