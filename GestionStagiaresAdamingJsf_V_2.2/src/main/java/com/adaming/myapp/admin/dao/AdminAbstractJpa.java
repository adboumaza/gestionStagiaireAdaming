package com.adaming.myapp.admin.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.adaming.myapp.entities.Admin;


public abstract class AdminAbstractJpa  {

	
	@PersistenceContext
	private EntityManager entityManger;
 

	public Admin createAdminAbstractJpa(Admin admin){
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
