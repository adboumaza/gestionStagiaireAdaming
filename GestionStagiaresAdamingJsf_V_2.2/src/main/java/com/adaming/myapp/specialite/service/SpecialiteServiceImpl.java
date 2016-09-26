package com.adaming.myapp.specialite.service;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Specialite;
import com.adaming.myapp.exception.AddSpecialiteException;
import com.adaming.myapp.specialite.dao.ISpecialiteDao;

@Transactional
public class SpecialiteServiceImpl implements ISpecialiteService{
    
	Logger log = Logger.getLogger("SpecialiteServiceImpl");
	
	private ISpecialiteDao dao;
	
	public void setDao(ISpecialiteDao dao) {
		this.dao = dao;
		log.info("<----------dao specialite injected-------->");
	}

	@Override
	public Specialite addSpecialite(Specialite sp) throws AddSpecialiteException {
		// TODO Auto-generated method stub
		return dao.addSpecialite(sp);
	}

	@Override
	public Specialite updateSpecialite(Specialite sp) {
		// TODO Auto-generated method stub
		return dao.updateSpecialite(sp);
	}

	@Override
	public Specialite getSpecialiteById(Long idSpecialite) {
		// TODO Auto-generated method stub
		return dao.getSpecialiteById(idSpecialite);
	}

	@Override
	public List<Specialite> getAllSpec() {
		// TODO Auto-generated method stub
		return dao.getAllSpec();
	}
	
	

}
