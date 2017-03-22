package com.adaming.myapp.admin.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.entities.Role;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.tools.Utilitaire;
import com.adaming.myapp.user.dao.IUserDao;
import com.adaming.myapp.user.service.IUserService;

public abstract class AdminAbstractJpa  {
    
	
	
	@PersistenceContext
	private EntityManager entityManger;


	public Admin createAdminAbstractJpa(Admin admin) {
		entityManger.persist(admin);
		
		return admin;
	}

	
	public Admin deleteAdminAbstractJpa(final Long idAdmin) {
		Admin admin = getOneAdminAbstractJpa(idAdmin);
		entityManger.remove(admin);
		return admin;
	}

	
	public Admin updateAdminAbstractJpa(final Admin admin) {
		entityManger.merge(admin);
		return admin;
	}


	public Admin getOneAdminAbstractJpa(Long idAdmin) {
		Admin admin = entityManger.find(Admin.class, idAdmin);
		return admin;
	}
	
}
