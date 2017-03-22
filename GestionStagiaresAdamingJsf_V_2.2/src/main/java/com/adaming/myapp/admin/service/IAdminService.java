package com.adaming.myapp.admin.service;

import com.adaming.myapp.entities.Admin;

public interface IAdminService {

	Admin createAdmin(final Admin admin);

	Admin deleteAdmin(final Long idAdmin);

	Admin updateAdmin(final Admin admin);
	
	Admin getOneAdmin(final Long idAdmin);
}
