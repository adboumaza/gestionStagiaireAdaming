package com.adaming.myapp.formateur.service;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.SessionEtudiant;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.formateur.dao.IFormateurDao;
import com.adaming.myapp.role.dao.IRoleDao;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.user.dao.IUserDao;

@Transactional(readOnly=true)
public class FormateurServiceImpl implements IFormateurService {
    
	private IFormateurDao dao;
	
	@Inject
	private IUserDao daoUser;
	@Inject
	private IRoleDao daoRole;


	public void setDao(IFormateurDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<----------Dao Formateur Injected-------->");
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = VerificationInDataBaseException.class)
	public Formateur addFormateur(final Formateur f,final User user,final Role role)
			throws VerificationInDataBaseException {
		
		if(!daoUser.getUsersByMail(f.getMail()).isEmpty())
		{
			throw new VerificationInDataBaseException("l'adresse mail "+f.getMail()+" existe déjà dans notre base de donnée Veuillez renseigner une autre adresse mail");
		}
		else
		{
			dao.addFormateur(f);
			daoUser.saveUser(user);
			daoRole.saveRole(role,user.getIdUser());
		}
		return null;
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
