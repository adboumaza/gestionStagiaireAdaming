package com.adaming.myapp.admin.dao;


import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.persistence.EntityManagerAbstract;



public abstract class AdminAbstractJpa  extends EntityManagerAbstract {

	
	public Admin createAdminAbstractJpa(Admin admin){
		entityManager.persist(admin);
		
		return admin;
	}

	
	public Admin deleteAdminAbstractJpa(final Long idAdmin) {
		Admin admin = getOneAdminAbstractJpa(idAdmin);
		entityManager.remove(admin);
		return admin;
	}

	
	public Admin updateAdminAbstractJpa(final Admin admin) {
		entityManager.merge(admin);
		return admin;
	}


	public Admin getOneAdminAbstractJpa(Long idAdmin) {
		Admin admin = entityManager.find(Admin.class, idAdmin);
		return admin;
	}
	
}
