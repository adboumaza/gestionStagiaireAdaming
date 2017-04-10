package com.adaming.myapp.user.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.GetUserException;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
public abstract class UserAbstractJpa {

	@PersistenceContext
	private EntityManager em;

	public User saveUserAbstractJpa(final User u) {
		em.persist(u);
		LoggerConfig.logInfo("l'identifiant et le mot de passe ont bien Générées"+"USER N° : "+"psseudo Name : "+u.getName()+" Password: "+u.getPassword());
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<User> getUsersByMailAbstractJpa(final String mail){
		final String SQL = "From User u where u.name=:x";
		List<User> u = null;
		Query query = em.createQuery(SQL).setParameter("x", mail);
		u = query.getResultList();
		return u;
	}
	
	
	public User getUserAbstractJpa(final String mail) throws GetUserException {
		
		List<User> u = getUsersByMailAbstractJpa(mail);

		if(u.size()==0)
			throw new GetUserException("Ce mail ne correspond a aucun utilisateur !");
		
		if(u.get(0).isActived()== false)
			throw new GetUserException("Cet utilisateur n'est pas activé !");

		return u.get(0);
	}
	
	
	public User getUserByMailAbstractJpa(final String mail) {
		
		final String SQL = "From User u where u.name =:x";
		Query query = em.createQuery(SQL).setParameter("x",mail);
		User u = null;
		if(!query.getResultList().isEmpty() && query.getResultList() != null){
			u = (User) query.getResultList().get(0);
		}
		return u;
	}
	
	public User customPasswordAbstractJpa(final User u){
		  em.merge(u);
		  return u;
	}

}



