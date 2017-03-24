package com.adaming.myapp.admin.service;

import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.exception.VerificationInDataBaseException;

public interface IAdminService {

	Admin createAdmin(final Admin admin) throws VerificationInDataBaseException;

	Admin deleteAdmin(final Long idAdmin);

	Admin updateAdmin(final Admin admin);
	
	Admin getOneAdmin(final Long idAdmin);
}
