package com.adaming.myapp.admin.service;

import com.adaming.myapp.admin.dao.IAdminDao;
import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.tools.LoggerConfig;

public class AdminServiceImpl implements IAdminService{
    
	private IAdminDao dao;

	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IAdminDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<------dao Admin injected------>");
	}

	@Override
	public Admin createAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return dao.createAdmin(admin);
	}

	@Override
	public Admin deleteAdmin(Long idAdmin) {
		// TODO Auto-generated method stub
		return dao.deleteAdmin(idAdmin);
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return dao.updateAdmin(admin);
	}

	@Override
	public Admin getOneAdmin(Long idAdmin) {
		// TODO Auto-generated method stub
		return dao.getOneAdmin(idAdmin);
	}

}
