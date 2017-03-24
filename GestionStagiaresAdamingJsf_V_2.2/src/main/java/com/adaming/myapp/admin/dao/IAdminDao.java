package com.adaming.myapp.admin.dao;

import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IAdminDao {

	Admin createAdmin(final Admin admin);
	
	Admin deleteAdmin(final Long idAdmin);
	
	Admin updateAdmin(final Admin admin);
	
	Admin getOneAdmin(final Long idAdmin);
	
	
}
