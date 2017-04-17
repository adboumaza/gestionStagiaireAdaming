package com.adaming.myapp.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public class EtudiantDto {

	
	private Long idEtudiant;
	private String nomEtudiant;
	private String prenomEtudiant;
	private Date dateDeNaissance;
	private String formationInitial;
	private String ecole;
	private Date dateObtention;
	private String numTel;
	private String mail;
	private AdresseDTO adresseDto = new AdresseDTO();
    private SessionDTO sessionDto;
	private List<NoteDTO> notesDto;
	private List<EvenementDTO> evenementsDto;
	private List<ProspectionDTO> prospectionsDto;
	private Set<EvaluationDTO> evaluationsDto;
	/**
	 * @return the idEtudiant
	 */
	public Long getIdEtudiant() {
		return idEtudiant;
	}
	/**
	 * @param idEtudiant the idEtudiant to set
	 */
	public void setIdEtudiant(Long idEtudiant) {
		this.idEtudiant = idEtudiant;
	}
	/**
	 * @return the nomEtudiant
	 */
	public String getNomEtudiant() {
		return nomEtudiant;
	}
	/**
	 * @param nomEtudiant the nomEtudiant to set
	 */
	public void setNomEtudiant(String nomEtudiant) {
		this.nomEtudiant = nomEtudiant;
	}
	/**
	 * @return the prenomEtudiant
	 */
	public String getPrenomEtudiant() {
		return prenomEtudiant;
	}
	/**
	 * @param prenomEtudiant the prenomEtudiant to set
	 */
	public void setPrenomEtudiant(String prenomEtudiant) {
		this.prenomEtudiant = prenomEtudiant;
	}
	/**
	 * @return the dateDeNaissance
	 */
	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}
	/**
	 * @param dateDeNaissance the dateDeNaissance to set
	 */
	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}
	/**
	 * @return the formationInitial
	 */
	public String getFormationInitial() {
		return formationInitial;
	}
	/**
	 * @param formationInitial the formationInitial to set
	 */
	public void setFormationInitial(String formationInitial) {
		this.formationInitial = formationInitial;
	}
	/**
	 * @return the ecole
	 */
	public String getEcole() {
		return ecole;
	}
	/**
	 * @param ecole the ecole to set
	 */
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	/**
	 * @return the dateObtention
	 */
	public Date getDateObtention() {
		return dateObtention;
	}
	/**
	 * @param dateObtention the dateObtention to set
	 */
	public void setDateObtention(Date dateObtention) {
		this.dateObtention = dateObtention;
	}
	/**
	 * @return the numTel
	 */
	public String getNumTel() {
		return numTel;
	}
	/**
	 * @param numTel the numTel to set
	 */
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the adresseDto
	 */
	public AdresseDTO getAdresseDto() {
		return adresseDto;
	}
	/**
	 * @param adresseDto the adresseDto to set
	 */
	public void setAdresseDto(AdresseDTO adresseDto) {
		this.adresseDto = adresseDto;
	}
	/**
	 * @return the sessionDto
	 */
	public SessionDTO getSessionDto() {
		return sessionDto;
	}
	/**
	 * @param sessionDto the sessionDto to set
	 */
	public void setSessionDto(SessionDTO sessionDto) {
		this.sessionDto = sessionDto;
	}
	/**
	 * @return the notesDto
	 */
	public List<NoteDTO> getNotesDto() {
		return notesDto;
	}
	/**
	 * @param notesDto the notesDto to set
	 */
	public void setNotesDto(List<NoteDTO> notesDto) {
		this.notesDto = notesDto;
	}
	/**
	 * @return the evenementsDto
	 */
	public List<EvenementDTO> getEvenementsDto() {
		return evenementsDto;
	}
	/**
	 * @param evenementsDto the evenementsDto to set
	 */
	public void setEvenementsDto(List<EvenementDTO> evenementsDto) {
		this.evenementsDto = evenementsDto;
	}
	/**
	 * @return the prospectionsDto
	 */
	public List<ProspectionDTO> getProspectionsDto() {
		return prospectionsDto;
	}
	/**
	 * @param prospectionsDto the prospectionsDto to set
	 */
	public void setProspectionsDto(List<ProspectionDTO> prospectionsDto) {
		this.prospectionsDto = prospectionsDto;
	}
	/**
	 * @return the evaluationsDto
	 */
	public Set<EvaluationDTO> getEvaluationsDto() {
		return evaluationsDto;
	}
	/**
	 * @param evaluationsDto the evaluationsDto to set
	 */
	public void setEvaluationsDto(Set<EvaluationDTO> evaluationsDto) {
		this.evaluationsDto = evaluationsDto;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EtudiantDto [idEtudiant=" + idEtudiant + ", nomEtudiant="
				+ nomEtudiant + ", prenomEtudiant=" + prenomEtudiant
				+ ", dateDeNaissance=" + dateDeNaissance
				+ ", formationInitial=" + formationInitial + ", ecole=" + ecole
				+ ", dateObtention=" + dateObtention + ", numTel=" + numTel
				+ ", mail=" + mail + ", adresseDto=" + adresseDto
				+ ", sessionDto=" + sessionDto + ", notesDto=" + notesDto
				+ ", evenementsDto=" + evenementsDto + ", prospectionsDto="
				+ prospectionsDto + ", evaluationsDto=" + evaluationsDto + "]";
	}
	
	
	
	
	
	
	
}
