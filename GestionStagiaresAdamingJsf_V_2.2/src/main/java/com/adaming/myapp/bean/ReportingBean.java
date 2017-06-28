package com.adaming.myapp.bean;


import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import net.sf.jasperreports.engine.JRException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.entities.Evenement;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.etudiant.service.IEtudiantService;
import com.adaming.myapp.evaluation.service.IEvaluationService;
import com.adaming.myapp.evenement.service.IEvenementService;
import com.adaming.myapp.exception.AddSessionException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.notes.service.INotesService;
import com.adaming.myapp.prospection.service.IProspectionService;
import com.adaming.myapp.session.service.ISessionService;
import com.adaming.myapp.tools.DataEnum;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;

@SuppressWarnings("serial")
@Component("reportingBean")
public class ReportingBean implements Serializable {

	@Inject
	private ISessionService sessionService;

	@Inject
	private IEtudiantService etudiantService;

	@Inject
	private IEvenementService evenementService;
	
	@Inject
	private INotesService noteService;
	
	@Inject
	private IProspectionService prospectionService;
	
	@Inject
	private IEvaluationService evaluationService;

	@NotNull
	private Date dateDebut;
	@NotNull
	private Date dateFin;
	@NotNull
	private Long idSession;
	private List<Object[]> sessions;
	private SessionEtudiant session;

	private List<Evenement> events;
	private int numeroDeSemaine;
	private final String TOP = DataEnum.TOP.getMessage();
	private final String WARNING = DataEnum.BLACK_LIST.getMessage();
	private final String ABSENCE = DataEnum.ABSENCE.getMessage();
	private final String RETARD = DataEnum.RETARD.getMessage();
	private String choix;

	/* absence */
	private Date dateIn;
	private String[] dateString;
	private DateTime[] joursSemaine;
	private DateTime [] holydays;
	private int annee;
	private int semaine;

	public String init() {
		setDateDebut(new Date());
		setDateFin(null);
		setSessions(null);
		setIdSession(null);
		return "reporting?faces-redirect=true";
	}

	public String getAllSessionsBetweenTwoDates() {
		try {
			setIdSession(null);
			setSessions(null);
			setNumeroDeSemaine(0);
			sessions = sessionService.getAllSessionsBetwenTwoDates(dateDebut,
					dateFin);
			numeroDeSemaine = Utilitaire.getWeeksOfYears(dateDebut);

		} catch (AddSessionException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			return null;
		}
		return "liste_reporting?faces-redirect=true";
	}

	public String getSessionChoisie() {
		session = FactoryBean.getSessionFactory().create("SessionEtudiant");
		session = sessionService.getSessionEtudiantById(idSession);
		setDateIn(dateDebut);
		return "generate_reporting.xhtml";
	}
	
	@PostConstruct
	public void initJoursFerie(){
		DateTime dateTime = new DateTime();
		holydays = Utilitaire.holydays(dateTime);
		LoggerConfig.logInfo("Jours férie depuis"+dateTime);
	}

	public String redirectChoiseNewSession() {
		return "liste_reporting?faces-redirect=true";
	}

	public String redirectChoiseNewDates() {
		setDateDebut(new Date());
		setDateFin(null);
		return "reporting?faces-redirect=true";
	}

	


	public void generateExcelBlackListeOrTeamLeader(String choix)
			throws JRException, IOException {

		int rowIndex = 1;

		try {
			events = sessionService.getMoreInformationBySession(idSession,
					choix);
			Workbook workbook = new XSSFWorkbook();
			
			Sheet etudiantSheet = createSheet(workbook, choix);

			etudiantSheet.setColumnWidth((short) 0, (short) (50 * 100));
			etudiantSheet.setColumnWidth((short) 1, (short) (50 * 100));
			etudiantSheet.setColumnWidth((short) 2, (short) (50 * 300));
			etudiantSheet.setColumnWidth((short) 3, (short) (50 * 300));

			CellStyle csHeader = workbook.createCellStyle();
			
			org.apache.poi.ss.usermodel.Font f = workbook.createFont();
			f.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			f.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
			csHeader.setFont(f);
			//border header
			customBorderHeader(csHeader);
			csHeader.setFont(f);
            //creation du header
			//creation des celleules avec le style 
			Row header = createRowHeader(etudiantSheet,csHeader,new String []{"Nom","Prénom","Tél","Mail"});
			
			CellStyle csData = customBorderBody(workbook);
			
			for (Evenement abs : events) {
				header = etudiantSheet.createRow(rowIndex++);
				int cellIndex = 0;
				if (abs.getEtudiant().getNomEtudiant() != null) {
					header.createCell(cellIndex++).setCellValue(
							abs.getEtudiant().getNomEtudiant());
					header.getCell(0).setCellStyle(csData);
					header.setHeight(( short ) 0x249);
				}
				if (abs.getEtudiant().getPrenomEtudiant() != null) {
					header.createCell(cellIndex++).setCellValue(
							abs.getEtudiant().getPrenomEtudiant());
					header.getCell(1).setCellStyle(csData);
					header.setHeight(( short ) 0x249);
				}
				if (abs.getEtudiant().getNumTel() != null) {
					header.createCell(cellIndex++).setCellValue(
							abs.getEtudiant().getNumTel());
					header.getCell(2).setCellStyle(csData);
					header.setHeight(( short ) 0x249);
				}
				if (abs.getEtudiant().getMail() != null) {
					header.createCell(cellIndex++).setCellValue(
							abs.getEtudiant().getMail());
					header.getCell(3).setCellStyle(csData);
					header.setHeight(( short ) 0x249);
				}

			}
			
			String fileName = createFileNameExcel(choix);
			exportExcel(workbook, fileName);
			
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}

	}

	public void generateExcelNotesSession() throws IOException{
		int rowIndex = 1;

		try {
			List<Object[]> notes = new ArrayList<Object[]>();
			notes = noteService.getAllNotesBySession(idSession);
			Workbook workbook = new XSSFWorkbook();
			
			Sheet etudiantSheet = createSheet(workbook, "Notes");

			etudiantSheet.setColumnWidth((short) 0, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 1, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 2, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 3, (short) (50 * 120));
            
			//color custom
			
			CellStyle csHeader = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font f = workbook.createFont();
			f.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			f.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
			csHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
			customBorderHeader(csHeader);
			//appliquer la font sur le style
			csHeader.setFont(f);
			//creation du header
			//creation de cellules avec le style
			Row header = createRowHeader(etudiantSheet,csHeader,new String []{"Nom","Prénom","Score","Module"});
			//custom bordures de chaque cellules dans le body
			CellStyle csData = customBorderBody(workbook);
			
			// des bordures en couleur, ainsi le background red pour les etudiants qui n'ont pas eu la moyenne
			CellStyle csDataWarning = customBorderAlert(workbook, f);

			//csDataWarning.setVerticalAlignment(VerticalAlign.);
			
			Set<String> tab = new HashSet<String>();
			for (Object [] note : notes) {
				
				int cellIndex = 0;
				String moduleExsit = null;
				moduleExsit =(String) note [3];//java
				tab.add(moduleExsit);//1
				if(tab.size() > 1){
					
					etudiantSheet.createRow(rowIndex++);
					etudiantSheet.createRow(rowIndex++);
					etudiantSheet.createRow(rowIndex++);
					etudiantSheet.createRow(rowIndex++);
					
					Row headerSpaceTwo = etudiantSheet.createRow(rowIndex++);
					headerSpaceTwo.setHeight(( short ) 0x249);
					csHeader.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

					
					Row headerSpaceThree = etudiantSheet.createRow(rowIndex++);
					headerSpaceThree.setHeight(( short ) 0x249);
					headerSpaceThree.createCell(0).setCellValue("Nom");
					headerSpaceThree.createCell(1).setCellValue("Prénom");
					headerSpaceThree.createCell(2).setCellValue("Score");
					headerSpaceThree.createCell(3).setCellValue("Module");
					headerSpaceThree.getCell(0).setCellStyle(csHeader);
					headerSpaceThree.getCell(1).setCellStyle(csHeader);
					headerSpaceThree.getCell(2).setCellStyle(csHeader);
					headerSpaceThree.getCell(3).setCellStyle(csHeader);
					
					tab.clear();
					tab = null;
					tab = new HashSet<String>();
				}
				header = etudiantSheet.createRow(rowIndex++);
				if (note[0] != null) {
					header.setHeight(( short ) 0x249);
					String nomEtudiant = (String) note[0];
					header.createCell(cellIndex++).setCellValue(
							nomEtudiant);
					header.getCell(0).setCellStyle(csData);
					
					
				}
				if (note[1] != null) {
					header.setHeight(( short ) 0x249);
					String prenomEtudiant = (String) note[1];
					header.createCell(cellIndex++).setCellValue(
							prenomEtudiant);
					header.getCell(1).setCellStyle(csData);
					
				}
				if (note[2] != null) {
					header.setHeight(( short ) 0x249);
					Double score = (Double) note[2];
					header.createCell(cellIndex++).setCellValue(
							score);
					if(score >= 10){
                        header.getCell(2).setCellStyle(csData);
					}else{
						header.getCell(2).setCellStyle(csDataWarning);
					}
					
		
				}
				
				if (note[3] != null) {
					header.setHeight(( short ) 0x249);
					String module = (String) note[3];
					header.createCell(cellIndex++).setCellValue(
							module);
					header.getCell(3).setCellStyle(csData);
				}

				


			}
			
			String fileName = createFileNameExcel("Notes");
			
			exportExcel(workbook, fileName);
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}

	}

	public void generateExcelAbsence() throws JRException, IOException,
			VerificationInDataBaseException {

		
		int rowIndex = 1;
		genererDates();
		// si le jours l'annee generer n'est pas l'annee en cours en genere un tableau de jours ferier avec l'annee souhaiter
		if(joursSemaine[0].getYear()!= holydays[0].getYear()){
			holydays = Utilitaire.holydays(joursSemaine[0]);
			LoggerConfig.logInfo("new Instance dateTime For holydays");
		}
		List<Etudiant> etudiants = new ArrayList<Etudiant>();
		etudiants = etudiantService.getStudentsBySession(idSession);
		Workbook workbook = new XSSFWorkbook();
		
		Sheet etudiantSheet = createSheet(workbook, "Absences");

		etudiantSheet.setColumnWidth((short) 0, (short) (50 * 150));
		etudiantSheet.setColumnWidth((short) 1, (short) (50 * 150));
		etudiantSheet.setColumnWidth((short) 2, (short) (50 * 100));
		etudiantSheet.setColumnWidth((short) 3, (short) (50 * 100));
		etudiantSheet.setColumnWidth((short) 4, (short) (50 * 100));
		etudiantSheet.setColumnWidth((short) 5, (short) (50 * 100));
		etudiantSheet.setColumnWidth((short) 6, (short) (50 * 100));
		
		

		// Create the comment and set the text+author
		Row row = etudiantSheet.createRow(20);
		CreationHelper factory = workbook.getCreationHelper();
		ClientAnchor anchor = factory.createClientAnchor();
		Drawing drawing = etudiantSheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString("session N° "
				+ idSession + ", Semaine N° " + numeroDeSemaine + ", Site : "
				+ session.getSite().getNomSite() + "\n Salle :"
				+ session.getSalle().getNumeroSalle() + ", Lieu : "
				+ session.getSite().getAdresse().getVille() + "\n Spécialité "
				+ session.getSpecialite().getDesignation());
		comment.setString(str);
		comment.setAuthor("Inti Formation");
		Cell cell = row.createCell(3);
		cell.setCellValue("Détails Absence session numéro" + idSession);
		// Assign the comment to the cell
		cell.setCellComment(comment);
		cell.setAsActiveCell();

		CellStyle csHeader = workbook.createCellStyle();
		org.apache.poi.ss.usermodel.Font f = workbook.createFont();
		f.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
		f.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
		customBorderHeader(csHeader);
		csHeader.setFont(f);
		//creation du header
		//creation des cellules avec le style
		Row header = createRowHeader(etudiantSheet,csHeader,new String []{"Nom","Prénom","Lundi "+dateString[0],"Mardi  " + dateString[1],"Mercredi " + dateString[2],"Jeudi  " + dateString[3],"Vendredi " + dateString[4]});
		
		CellStyle csData = customBorderBody(workbook);
		
		CellStyle csDataWarning = customBorderAlert(workbook, f);
		
		for (Etudiant abs : etudiants) {

			header = etudiantSheet.createRow(rowIndex++);
			int cellIndex = 0;
			if (abs.getNomEtudiant() != null) {
				header.createCell(cellIndex++).setCellValue(
						abs.getNomEtudiant());
				header.setHeight(( short ) 0x249);
				header.getCell(0).setCellStyle(csData);

			}
			if (abs.getPrenomEtudiant() != null) {
				header.createCell(cellIndex++).setCellValue(
						abs.getPrenomEtudiant());
				header.setHeight(( short ) 0x249);
				header.getCell(1).setCellStyle(csData);
			}

			if (joursSemaine[0].isAfter(session.getDateFin().getTime())) {
				header.createCell(cellIndex++).setCellValue("TERMINE");
				header.setHeight(( short ) 0x249);
				header.getCell(2).setCellStyle(csData);
				
			}else if(verificationJoursFerie(joursSemaine[0])){
				header.createCell(cellIndex++).setCellValue("FÉRIE");
				header.setHeight(( short ) 0x249);
				header.getCell(2).setCellStyle(csData);
			}

			else {
				Object[] eventsEtudiant = evenementService
						.getEventByStudentBetweenTwoDates(idSession,
								abs.getNomEtudiant(), joursSemaine[0]);
				if (eventsEtudiant != null) {
					if(eventsEtudiant[3].equals("RETARD")){
						header.createCell(cellIndex++).setCellValue("RETARD"+calculateDuree(eventsEtudiant));
						header.setHeight(( short ) 0x249);
						header.getCell(2).setCellStyle(csDataWarning);
						
					}else if(eventsEtudiant[3].equals("ABSENCE")){
						header.createCell(cellIndex++).setCellValue("ABSENCE");
						header.setHeight(( short ) 0x249);
						header.getCell(2).setCellStyle(csDataWarning);
						
					}
					else{
						header.createCell(cellIndex++).setCellValue("ENTRETIEN");
						header.setHeight(( short ) 0x249);
						header.getCell(2).setCellStyle(csDataWarning);
					}
				} else {
					header.createCell(cellIndex++).setCellValue("OK");
					header.setHeight(( short ) 0x249);
					header.getCell(2).setCellStyle(csData);
				}

			}

			if (joursSemaine[1].isAfter(session.getDateFin().getTime())) {
				header.createCell(cellIndex++).setCellValue("TERMINE");
				header.setHeight(( short ) 0x249);
				header.getCell(3).setCellStyle(csData);
			}
			else if(verificationJoursFerie(joursSemaine[1])){
				header.createCell(cellIndex++).setCellValue("FÉRIE");
				header.setHeight(( short ) 0x249);
				header.getCell(2).setCellStyle(csData);
			}

			else {
				Object[] eventsEtudiant = evenementService
						.getEventByStudentBetweenTwoDates(idSession,
								abs.getNomEtudiant(), joursSemaine[1]);
				if (eventsEtudiant != null) {
					if(eventsEtudiant[3].equals("RETARD")){
						header.createCell(cellIndex++).setCellValue("RETARD"+calculateDuree(eventsEtudiant));
						header.setHeight(( short ) 0x249);
						header.getCell(3).setCellStyle(csDataWarning);
					}else if(eventsEtudiant[3].equals("ABSENCE")){
						header.createCell(cellIndex++).setCellValue("ABSENCE");
						header.setHeight(( short ) 0x249);
						header.getCell(3).setCellStyle(csDataWarning);
					}
					else{
						header.createCell(cellIndex++).setCellValue("ENTRETIEN");
						header.setHeight(( short ) 0x249);
						header.getCell(3).setCellStyle(csDataWarning);
					}
				} else {
					header.createCell(cellIndex++).setCellValue("OK");
					header.setHeight(( short ) 0x249);
					header.getCell(3).setCellStyle(csData);
				}

			}
			if (joursSemaine[2].isAfter(session.getDateFin().getTime())) {
				header.createCell(cellIndex++).setCellValue("TERMINE");
				header.setHeight(( short ) 0x249);
				header.getCell(4).setCellStyle(csData);
			}
			else if(verificationJoursFerie(joursSemaine[2])){
				header.createCell(cellIndex++).setCellValue("FÉRIE");
				header.setHeight(( short ) 0x249);
				header.getCell(2).setCellStyle(csData);
			}

			else {
				Object[] eventsEtudiant = evenementService
						.getEventByStudentBetweenTwoDates(idSession,
								abs.getNomEtudiant(), joursSemaine[2]);
				if (eventsEtudiant != null) {
					if(eventsEtudiant[3].equals("RETARD")){
						header.createCell(cellIndex++).setCellValue("RETARD"+calculateDuree(eventsEtudiant));
						header.setHeight(( short ) 0x249);
						header.getCell(4).setCellStyle(csDataWarning);
					}else if(eventsEtudiant[3].equals("ABSENCE")){
						header.createCell(cellIndex++).setCellValue("ABSENCE");
						header.setHeight(( short ) 0x249);
						header.getCell(4).setCellStyle(csDataWarning);
					}
					else{
						header.createCell(cellIndex++).setCellValue("ENTRETIEN");
						header.setHeight(( short ) 0x249);
						header.getCell(4).setCellStyle(csDataWarning);
					}
				} else {
					header.createCell(cellIndex++).setCellValue("OK");
					header.setHeight(( short ) 0x249);
					header.getCell(4).setCellStyle(csData);
				}

			}
			if (joursSemaine[3].isAfter(session.getDateFin().getTime())) {
				header.createCell(cellIndex++).setCellValue("TERMINE");
				header.setHeight(( short ) 0x249);
				header.getCell(5).setCellStyle(csData);
			}
			else if(verificationJoursFerie(joursSemaine[3])){
				header.createCell(cellIndex++).setCellValue("FÉRIE");
				header.setHeight(( short ) 0x249);
				header.getCell(2).setCellStyle(csData);
			}

			else {
				Object[] eventsEtudiant = evenementService
						.getEventByStudentBetweenTwoDates(idSession,
								abs.getNomEtudiant(), joursSemaine[3]);
				if (eventsEtudiant != null) {
					if(eventsEtudiant[3].equals("RETARD")){
						header.createCell(cellIndex++).setCellValue("RETARD"+calculateDuree(eventsEtudiant));
						header.setHeight(( short ) 0x249);	
						header.getCell(5).setCellStyle(csDataWarning);
					}else if(eventsEtudiant[3].equals("ABSENCE")){
						header.createCell(cellIndex++).setCellValue("ABSENCE");
						header.setHeight(( short ) 0x249);
						header.getCell(5).setCellStyle(csDataWarning);
					}
					else{
						header.createCell(cellIndex++).setCellValue("ENTRETIEN");
						header.setHeight(( short ) 0x249);
						header.getCell(5).setCellStyle(csDataWarning);
					}
				} else {
					header.createCell(cellIndex++).setCellValue("OK");
					header.setHeight(( short ) 0x249);
					header.getCell(5).setCellStyle(csData);
				}

			}
			if (joursSemaine[4].isAfter(session.getDateFin().getTime())) {
				header.createCell(cellIndex++).setCellValue("TERMINE");
				header.setHeight(( short ) 0x249);
				header.getCell(6).setCellStyle(csData);
			}
			else if(verificationJoursFerie(joursSemaine[4])){
				header.createCell(cellIndex++).setCellValue("FÉRIE");
				header.setHeight(( short ) 0x249);
				header.getCell(2).setCellStyle(csData);
			}

			else {
				Object[] eventsEtudiant = evenementService
						.getEventByStudentBetweenTwoDates(idSession,
								abs.getNomEtudiant(), joursSemaine[4]);
				if (eventsEtudiant != null) {
					if(eventsEtudiant[3].equals("RETARD")){
						header.createCell(cellIndex++).setCellValue("RETARD"+calculateDuree(eventsEtudiant));
						header.setHeight(( short ) 0x249);	
						header.getCell(6).setCellStyle(csDataWarning);
					}else if(eventsEtudiant[3].equals("ABSENCE")){
						header.createCell(cellIndex++).setCellValue("ABSENCE");
						header.setHeight(( short ) 0x249);
						header.getCell(6).setCellStyle(csDataWarning);
					}
					else{
						header.createCell(cellIndex++).setCellValue("ENTRETIEN");
						header.setHeight(( short ) 0x249);
						header.getCell(6).setCellStyle(csDataWarning);
					}
				} else {
					header.createCell(cellIndex++).setCellValue("OK");
					header.setHeight(( short ) 0x249);
					header.getCell(6).setCellStyle(csData);
				}

			}

		}
		
		String fileName = createFileNameExcel("Absences");
		
		exportExcel(workbook, fileName);

	}

	public void generateExcelProspection() throws IOException{
		int rowIndex = 1;

		try {
			List<Object[]> prospection = new ArrayList<Object[]>();
			prospection = prospectionService.getAllProspectionBySession(idSession);
			Workbook workbook = new XSSFWorkbook();
			// create sheet etudiant (nom File)
			Sheet etudiantSheet = createSheet(workbook,"Prosepection");

			etudiantSheet.setColumnWidth((short) 0, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 1, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 2, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 3, (short) (50 * 700));

			//color custom
			CellStyle csHeader = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font f = workbook.createFont();
			f.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			f.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
			csHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
			// custom Border Header
			customBorderHeader(csHeader);
			
			//appliquer la font sur le style
			csHeader.setFont(f);
			//creattion Header
			//creation de cellules avec le style
			Row header = createRowHeader(etudiantSheet,csHeader,new String []{"Nom","Prénom","Risque","Commentaire"});

			// css pour les données(resultats)
			// des bordures sur le (body)
			
			CellStyle csData = customBorderBody(workbook);
			
			CellStyle csDataWarning = customBorderAlert(workbook, f);

			for (Object [] prospec : prospection) {
				int cellIndex = 0;
				header = etudiantSheet.createRow(rowIndex++);
				if (prospec[0] != null) {
					header.setHeight(( short ) 0x249);
					String nomEtudiant = (String) prospec[0];
					header.createCell(cellIndex++).setCellValue(
							nomEtudiant);
					header.getCell(0).setCellStyle(csData);
					
				}
				if (prospec[1] != null) {
					header.setHeight(( short ) 0x249);
					String prenomEtudiant = (String) prospec[1];
					header.createCell(cellIndex++).setCellValue(
							prenomEtudiant);
					header.getCell(1).setCellStyle(csData);
					
				}
				if (prospec[2] != null) {
					header.setHeight(( short ) 0x249);
					String risque = (String) prospec[2];
					header.createCell(cellIndex++).setCellValue(
							risque);
					if(risque.equals("Élevé")){
                        header.getCell(2).setCellStyle(csDataWarning);
					}else{
						header.getCell(2).setCellStyle(csData);
					}

				}
				
				if (prospec[3] != null) {
					header.setHeight(( short ) 0x249);
					String comment = (String) prospec[3];
					header.createCell(cellIndex++).setCellValue(
							comment);
					header.getCell(3).setCellStyle(csData);
				}

			}
			
			String fileName = createFileNameExcel("Prospection");
			exportExcel(workbook, fileName);
			
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}

	}

	
	public void generateExcelEvaluation() throws IOException{
		int rowIndex = 1;

		try {
			List<Object[]> evaluations = new ArrayList<Object[]>();
			evaluations = evaluationService.getAllEvaluationsBySession(idSession);
			Workbook workbook = new XSSFWorkbook();
			Sheet etudiantSheet = createSheet(workbook, "Evaluation");

			etudiantSheet.setColumnWidth((short) 0, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 1, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 2, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 3, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 4, (short) (50 * 120));
			etudiantSheet.setColumnWidth((short) 5, (short) (50 * 120));
			Row header = etudiantSheet.createRow(0);
			header.createCell(0).setCellValue("Nom");
			header.createCell(1).setCellValue("Prénom");
			header.createCell(2).setCellValue("Application Tp");
			header.createCell(3).setCellValue("Compréhension");
			header.createCell(4).setCellValue("Participation");
			header.createCell(5).setCellValue("Module");
			header.setHeight(( short ) 0x249);
			//color custom
			
			CellStyle csHeader = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font f = workbook.createFont();
			f.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			f.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
			csHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
			customBorderHeader(csHeader);
			//appliquer la font sur le style
			csHeader.setFont(f);
			//creation de cellules avec le style
			header.getCell(0).setCellStyle(csHeader);
			header.getCell(1).setCellStyle(csHeader);
			header.getCell(2).setCellStyle(csHeader);
			header.getCell(3).setCellStyle(csHeader);
			header.getCell(4).setCellStyle(csHeader);
			header.getCell(5).setCellStyle(csHeader);
			CellStyle csData = customBorderBody(workbook);
			
			CellStyle csDataWarning = customBorderAlert(workbook, f);

			//csDataWarning.setVerticalAlignment(VerticalAlign.);
			
			Set<String> tab = new HashSet<String>();
			for (Object [] eval : evaluations) {
				
				int cellIndex = 0;
				String moduleExsit = null;
				moduleExsit =(String) eval [5];//java
				tab.add(moduleExsit);//1
				if(tab.size() > 1){
					
					etudiantSheet.createRow(rowIndex++);
					etudiantSheet.createRow(rowIndex++);
					etudiantSheet.createRow(rowIndex++);
					etudiantSheet.createRow(rowIndex++);
					
					Row headerSpaceTwo = etudiantSheet.createRow(rowIndex++);
					headerSpaceTwo.setHeight(( short ) 0x249);
					csHeader.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

					
					Row headerSpaceThree = etudiantSheet.createRow(rowIndex++);
					headerSpaceThree.setHeight(( short ) 0x249);
					headerSpaceThree.createCell(0).setCellValue("Nom");
					headerSpaceThree.createCell(1).setCellValue("Prénom");
					headerSpaceThree.createCell(2).setCellValue("Application Tp");
					headerSpaceThree.createCell(3).setCellValue("Compréhension");
					headerSpaceThree.createCell(4).setCellValue("Participation");
					headerSpaceThree.createCell(5).setCellValue("Module");
					headerSpaceThree.getCell(0).setCellStyle(csHeader);
					headerSpaceThree.getCell(1).setCellStyle(csHeader);
					headerSpaceThree.getCell(2).setCellStyle(csHeader);
					headerSpaceThree.getCell(3).setCellStyle(csHeader);
					headerSpaceThree.getCell(4).setCellStyle(csHeader);
					headerSpaceThree.getCell(5).setCellStyle(csHeader);
					
					tab.clear();
					tab = null;
					tab = new HashSet<String>();
				}
				header = etudiantSheet.createRow(rowIndex++);
				if (eval[0] != null) {
					header.setHeight(( short ) 0x249);
					String nomEtudiant = (String) eval[0];
					header.createCell(cellIndex++).setCellValue(
							nomEtudiant);
					header.getCell(0).setCellStyle(csData);
					
				}
				if (eval[1] != null) {
					header.setHeight(( short ) 0x249);
					String prenomEtudiant = (String) eval[1];
					header.createCell(cellIndex++).setCellValue(
							prenomEtudiant);
					header.getCell(1).setCellStyle(csData);
					
				}
				if (eval[2] != null) {
					header.setHeight(( short ) 0x249);
					String application = (String) eval[2];
					header.createCell(cellIndex++).setCellValue(
							application);
					if(application.equals("Trop Juste")){
						header.getCell(2).setCellStyle(csDataWarning);
					}else{
                        header.getCell(2).setCellStyle(csData);
					}
		
				}
				if (eval[3] != null) {
					header.setHeight(( short ) 0x249);
					String comprehension = (String) eval[3];
					header.createCell(cellIndex++).setCellValue(
							comprehension);
					if(comprehension.equals("Trop Juste")){
						header.getCell(3).setCellStyle(csDataWarning);
					}else{
                        header.getCell(3).setCellStyle(csData);
					}
		
				}
				if (eval[4] != null) {
					header.setHeight(( short ) 0x249);
					String participation = (String) eval[4];
					header.createCell(cellIndex++).setCellValue(
							participation);
					if(participation.equals("Trop Juste")){
						header.getCell(4).setCellStyle(csDataWarning);
					}else{
                        header.getCell(4).setCellStyle(csData);
					}
		
				}
				if (eval[5] != null) {
					header.setHeight(( short ) 0x249);
					String module = (String) eval[5];
					header.createCell(cellIndex++).setCellValue(
							module);
					if(module.equals("Trop Juste")){
						header.getCell(5).setCellStyle(csDataWarning);
					}else{
                        header.getCell(5).setCellStyle(csData);
					}
		
				}
				
				


			}
			String fileName = createFileNameExcel("Evaluation");
			
			exportExcel(workbook, fileName);
			
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
	}
	
	/**
	 * @param workbook
	 * @return file name de la page excel
	 */
	private Sheet createSheet(Workbook workbook,String typeReporting) {
		Sheet etudiantSheet = workbook.createSheet(""
				.concat(typeReporting)
				.concat("_Etudiants")
				.concat("_Session_N°")
				.concat("_" + idSession)
				.concat("_Semaine_")
				.concat("N°_" + numeroDeSemaine)
				.concat("Lieu :" + session.getSite().getAdresse().getVille())
				.concat("Spécialité "
						+ session.getSpecialite().getDesignation()));
		return etudiantSheet;
	}
	
	/**
	 * @param choix
	 * @return nom du fichier excel avec l'extention .xls
	 */
	private String createFileNameExcel(String choix) {
		String fileName = ""
				.concat(choix)
				.concat("_Etudiants")
				.concat("_Session_N°")
				.concat("_" + idSession)
				.concat("_Semaine_")
				.concat("N°_" + numeroDeSemaine)
				.concat("_Lieu_" + session.getSite().getAdresse().getVille())
				.concat("_Spécialité_"
						+ session.getSpecialite().getDesignation())
				.concat(".xls");
		return fileName;
	}
    
	/**
	 * @param jours de semaine en format dateTime 
	 * @return true c'est un joursdeSemaine est un jours ferié
	 */
	private boolean verificationJoursFerie(DateTime joursSemaine){
		boolean confirm = false;
		if(
				joursSemaine.withTimeAtStartOfDay().isEqual(holydays[0].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[1].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[2].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[3].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[4].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[5].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[6].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[7].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[8].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[9].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[10].withTimeAtStartOfDay())
			||  joursSemaine.withTimeAtStartOfDay().isEqual(holydays[11].withTimeAtStartOfDay())
		  )
		{
			confirm = true;
		}
		return confirm;
	}
	/**
	 * @param tableau d'object de type date
	 * @return la durée de retard sous format de string
	 */
	private String calculateDuree(Object[] e){
	    Date de =(Date) e[1];
	    Date a = (Date) e[2];
	    DateTime dateTimeStart = new DateTime(de);
  	    DateTime dateTimeEnd   = new DateTime(a);
  	   
  	    int hoursBetweenDates  = Hours.hoursBetween(dateTimeStart, dateTimeEnd).getHours() % 24;
  	    int minuteBetweenDates = Minutes.minutesBetween(dateTimeStart, dateTimeEnd).getMinutes() % 60;
        return " - "+hoursBetweenDates+" H "+minuteBetweenDates+" M";
     }
	
	/** @method generate calendrier de jours entre le debut et la fin */
	private void genererDates() {
		DateTime date = new DateTime(dateIn);
		final int dayOfWeek = date.getDayOfWeek();
		annee = date.getYear();
		semaine = date.getWeekOfWeekyear();

		// si jour select n'est pas un lundi
		if (dayOfWeek != 1) {
			date = date.withDayOfWeek(1);
		}

		joursSemaine = new DateTime[5];
		dateString = new String[5];

		// on boucle jusqu'a vendredi
		for (int i = 0; i < 5; i++) {
			joursSemaine[i] = date.plusDays(i);
			dateString[i] = joursSemaine[i].getDayOfMonth() + "/"
					+ joursSemaine[i].getMonthOfYear()+"/"+joursSemaine[i].getYear();
		}
	}
	
	
	/**
	 * @param etudiantSheet
	 * @return header , cette methode permet de créer le hader de la page excell
	 */
	private Row createRowHeader(Sheet etudiantSheet,CellStyle csHeader,String [] tabCell) {
		Row header = etudiantSheet.createRow(0);
		for(int cell =0, length = tabCell.length;cell<length;cell ++){
			header.createCell(cell).setCellValue(tabCell[cell]);
			header.getCell(cell).setCellStyle(csHeader);
			header.setHeight(( short ) 0x249);
		}
		return header;
	}
	
	/**
	 * @param workbook
	 * @param fileName
	 * @throws IOException
	 */
	private void exportExcel(Workbook workbook, String fileName)
			throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		response.setContentType("application/vnd.ms-excel"); // Set up
																// mime
																// type
		response.addHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		OutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
		context.responseComplete();
	}
	
	/**
	 * @param workbook
	 * @param f
	 * @return cette methode permet de customiser les bordures de la page excel en cas d'alert
	 */
	private CellStyle customBorderAlert(Workbook workbook,
			org.apache.poi.ss.usermodel.Font f) {
		CellStyle csDataWarning = workbook.createCellStyle();
		customBorderHeader(csDataWarning);
		csDataWarning.setTopBorderColor(IndexedColors.RED.getIndex());
		csDataWarning.setBottomBorderColor(IndexedColors.RED.getIndex());
		csDataWarning.setLeftBorderColor(IndexedColors.RED.getIndex());
		csDataWarning.setRightBorderColor(IndexedColors.RED.getIndex());
		csDataWarning.setFont(f);
		return csDataWarning;
	}
	
	/**
	 * @param workbook
	 * @return cette methode permet de customiser les bordures de la page excel (body)
	 */
	private CellStyle customBorderBody(Workbook workbook) {
		CellStyle csData = workbook.createCellStyle();
		csData.setBorderBottom(XSSFCellStyle.BORDER_HAIR);
		csData.setBorderTop(XSSFCellStyle.BORDER_HAIR);
		csData.setBorderLeft(XSSFCellStyle.BORDER_HAIR);
		csData.setBorderRight(XSSFCellStyle.BORDER_HAIR);
		return csData;
	}
	
	/**
	 * @param csHeader cette permet de customiser les bordures du header
	 */
	private void customBorderHeader(CellStyle csHeader) {
		csHeader.setBorderBottom(XSSFCellStyle.FINE_DOTS);
		csHeader.setBorderTop(XSSFCellStyle.FINE_DOTS);
		csHeader.setBorderLeft(XSSFCellStyle.FINE_DOTS);
		csHeader.setBorderRight(XSSFCellStyle.FINE_DOTS);
	}
	
	
	
	//generatePDF
	/*public void generatePdf(String choix) throws JRException, IOException {
	Map<String, Object> parameters = null;
	events = null;
	File jasper = null;
	JasperPrint jasperPrint = null;
	try {
		events = new ArrayList<Evenement>();
		events = sessionService.getMoreInformationBySession(idSession,
				choix);

		parameters = new HashMap<String, Object>();
		parameters.put("NumeroSemaine", numeroDeSemaine);

		jasper = reprotingInputPath(choix);
		jasperPrint = JasperFillManager.fillReport(jasper.getPath(),
				parameters, new JRBeanCollectionDataSource(events));

		HttpServletResponse reponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		reponse.addHeader("Content-disposition",
				"attachment;filename=".concat(choix).concat("_Etudiants")
						.concat("_Session_N°").concat("_" + idSession)
						.concat("_Semaine_")
						.concat("N°_" + numeroDeSemaine).concat(".PDF"));
		reponse.setContentType("application/pdf");

		ServletOutputStream stream = reponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();

	} catch (VerificationInDataBaseException e) {
		Utilitaire.displayMessageWarning(e.getMessage());
	}

}
/*private File reprotingInputPath(String choix) {
	File file = null;
	if (choix.equals("TOP")) {
		final String REPPORT_PATH = "/reports/rpts_team_leader.jasper";
		file = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(REPPORT_PATH));

	} else if (choix.equals("WARNING")) {
		final String REPPORT_PATH = "/reports/rpts_black_liste.jasper";
		file = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(REPPORT_PATH));
	}

	return file;
}
*/

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut
	 *            the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin
	 *            the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the idSession
	 */
	public Long getIdSession() {
		return idSession;
	}

	/**
	 * @param idSession
	 *            the idSession to set
	 */
	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	/**
	 * @return the sessions
	 */
	public List<Object[]> getSessions() {
		return sessions;
	}

	/**
	 * @param sessions
	 *            the sessions to set
	 */
	public void setSessions(List<Object[]> sessions) {
		this.sessions = sessions;
	}

	/**
	 * @return the events
	 */
	public List<Evenement> getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(List<Evenement> events) {
		this.events = events;
	}

	/**
	 * @return the tOP
	 */
	public String getTOP() {
		return TOP;
	}

	/**
	 * @return the wARNING
	 */
	public String getWARNING() {
		return WARNING;
	}

	/**
	 * @return the aBSENCE
	 */
	public String getABSENCE() {
		return ABSENCE;
	}

	/**
	 * @return the rETARD
	 */
	public String getRETARD() {
		return RETARD;
	}

	/**
	 * @return the choix
	 */
	public String getChoix() {
		return choix;
	}

	/**
	 * @param choix
	 *            the choix to set
	 */
	public void setChoix(String choix) {
		this.choix = choix;
	}

	/**
	 * @return the numeroDeSemaine
	 */
	public int getNumeroDeSemaine() {
		return numeroDeSemaine;
	}

	/**
	 * @param numeroDeSemaine
	 *            the numeroDeSemaine to set
	 */
	public void setNumeroDeSemaine(int numeroDeSemaine) {
		this.numeroDeSemaine = numeroDeSemaine;
	}

	/**
	 * @return the dateIn
	 */
	public Date getDateIn() {
		return dateIn;
	}

	/**
	 * @param dateIn
	 *            the dateIn to set
	 */
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	/**
	 * @return the dateString
	 */
	public String[] getDateString() {
		return dateString;
	}

	/**
	 * @param dateString
	 *            the dateString to set
	 */
	public void setDateString(String[] dateString) {
		this.dateString = dateString;
	}

	/**
	 * @return the joursSemaine
	 */
	public DateTime[] getJoursSemaine() {
		return joursSemaine;
	}

	/**
	 * @param joursSemaine
	 *            the joursSemaine to set
	 */
	public void setJoursSemaine(DateTime[] joursSemaine) {
		this.joursSemaine = joursSemaine;
	}

	/**
	 * @return the annee
	 */
	public int getAnnee() {
		return annee;
	}

	/**
	 * @param annee
	 *            the annee to set
	 */
	public void setAnnee(int annee) {
		this.annee = annee;
	}

	/**
	 * @return the semaine
	 */
	public int getSemaine() {
		return semaine;
	}

	/**
	 * @param semaine
	 *            the semaine to set
	 */
	public void setSemaine(int semaine) {
		this.semaine = semaine;
	}

}
