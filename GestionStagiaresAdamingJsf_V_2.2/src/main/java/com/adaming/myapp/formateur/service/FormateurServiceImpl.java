package com.adaming.myapp.formateur.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.dao.IFormateurDao;
import com.adaming.myapp.tools.LoggerConfig;

@Transactional(readOnly=true)
public class FormateurServiceImpl implements IFormateurService {

	private IFormateurDao dao;


	public void setDao(IFormateurDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<----------Dao Formateur Injected-------->");
	}

	@Override
	@Transactional(readOnly=false)
	public Formateur addFormateur(final Formateur f)
			throws VerificationInDataBaseException {
		List<Object[]> formateurs = getFormateuByName(f.getNom(), f.getDateDeNaissance(),f.getMail());
		
		/*verification*/
		if(!formateurs.isEmpty()){
			for(Object[] formateur:formateurs){
				String nom = (String) formateur[0];
				Date date = (Date) formateur[1];
				String mail = (String) formateur[2];
				if(date.compareTo(f.getDateDeNaissance()) == 0
						&& nom.equals(f.getNom()))
				{
					throw new VerificationInDataBaseException("Le Formateur "
							+ f.getNom()
							+ " Existe déja dans la base de données");
				}
				else if(mail.equals(f.getMail()))
				{
					throw new VerificationInDataBaseException("l'adresse mail "+mail+" existe déjà dans la base de donnée, Veuillez renseigner une autre adresse mail");
				}
			}
		}
		return dao.addFormateur(f);
	}

	@Override
	@Transactional(readOnly=false)
	public void addFormateurToSession(final Long idSession,final Long idFormateur) throws VerificationInDataBaseException {
		verifyExistingAffectation(idFormateur, idSession);
		dao.addFormateurToSession(idSession, idFormateur);
	}

	@Override
	public List<Formateur> getAllFormateurs() {
		// TODO Auto-generated method stub
		return dao.getAll();
	}

	@Override
	public Formateur getFormateur(final String mail) {
		// TODO Auto-generated method stub
		return dao.getFormateur(mail);
	}

	@Override
	public Formateur getFormateurById(final Long idFormateur) throws VerificationInDataBaseException {
		Formateur formateur = dao.getOne(idFormateur);
		if(formateur == null){
			throw new VerificationInDataBaseException("le Formateur n'est pas encore affécté, on peut pas accéder à ces informations");
		}
		return dao.getOne(idFormateur);
	}

	@Override
	public List<Object[]> getFormateuByName(final String nom, final Date dateDeNaissance,
			String mail) {
		// TODO Auto-generated method stub
		return dao.getFormateuByName(nom, dateDeNaissance, mail);
	}

	@Override
	public  SessionEtudiant verifyExistingAffectation(final Long idFormateur,final Long idSession) throws VerificationInDataBaseException {
		SessionEtudiant sessionEtudiant = dao.verifyExistingAffectation(idFormateur, idSession);
		if(sessionEtudiant != null){
			throw new VerificationInDataBaseException("le Formateur est déja affecté à la session N°"+sessionEtudiant.getIdSession()+" , on peut pas l'affecter une 2éme fois ");
		}
		return sessionEtudiant;
	}

	@Override
	public List<Formateur> getFormateursInSessionEncours() {
		// TODO Auto-generated method stub
		return dao.getFormateursInSessionEncours();
	}

}
