package com.adaming.myapp.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.admin.dao.IAdminDao;
import com.adaming.myapp.entities.Admin;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.user.dao.IUserDao;
import com.adaming.myapp.user.service.IUserService;
@Transactional(readOnly = true)
public class AdminServiceImpl implements IAdminService{
    
	private IAdminDao dao;
   
	@Inject
	private IUserDao daoUser;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IAdminDao dao) {
		this.dao = dao;
		LoggerConfig.logInfo("<------dao Admin injected------>");
	}

	@Override
	@Transactional(readOnly = false)
	public Admin createAdmin(Admin admin) throws VerificationInDataBaseException {
		List<User> u = daoUser.getUsersByMail(admin.getMail());
		if(u.size() == 0){
			return dao.createAdmin(admin);
		}else{
			 throw new VerificationInDataBaseException("cette adresse mail existe déja dans notre base de donnée");
		}
		
	}

	@Override
	@Transactional(readOnly = false)
	public Admin deleteAdmin(Long idAdmin) {
		// TODO Auto-generated method stub
		return dao.deleteAdmin(idAdmin);
	}

	@Override
	@Transactional(readOnly = false)
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
