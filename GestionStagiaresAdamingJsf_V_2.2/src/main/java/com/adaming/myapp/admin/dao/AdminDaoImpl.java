package com.adaming.myapp.admin.dao;

import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public class AdminDaoImpl extends AdminAbstractJpa implements IAdminDao{

	@Override
	public Admin createAdmin(Admin admin){
		
		return createAdminAbstractJpa(admin);
	}

	@Override
	public Admin deleteAdmin(Long idAdmin) {
		// TODO Auto-generated method stub
		return deleteAdminAbstractJpa(idAdmin);
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return updateAdminAbstractJpa(admin);
	}

	@Override
	public Admin getOneAdmin(Long idAdmin) {
		// TODO Auto-generated method stub
		return getOneAdminAbstractJpa(idAdmin);
	}

}
