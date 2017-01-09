package com.adaming.myapp.site.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.adaming.myapp.entities.Salle;
import com.adaming.myapp.entities.Site;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.site.dao.ISiteDao;

@Transactional(readOnly=true)
public class SiteServiceImpl implements ISiteService{
    
	private final Logger LOGGER = Logger.getLogger(SiteServiceImpl.class);
	
	private ISiteDao dao;

	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(ISiteDao dao) {
		this.dao = dao;
		LOGGER.info("<------Dao Site Injected----->");
	}

	@Override
	@Transactional(readOnly=false)
	public Site add(Site site) throws VerificationInDataBaseException {
		List<Site> sites = getSiteByName(site.getNomSite(),site.getAdresse().getAdresse());
		if(sites.size()>0){
			throw new VerificationInDataBaseException("le site" +site.getNomSite()+" existe d�ja ..!");
		}
		return dao.add(site);
	}

	@Override
	@Transactional(readOnly=false)
	public Site update(Site site) {
		// TODO Auto-generated method stub
		return dao.update(site);
	}

	@Override
	@Transactional(readOnly=false)
	public Site remove(Long idSite) {
		// TODO Auto-generated method stub
		return dao.remove(idSite);
	}

	@Override
	public Site getOne(Long idSite) {
		// TODO Auto-generated method stub
		return dao.getOne(idSite);
	}

	@Override
	public List<Site> getAll() {
		// TODO Auto-generated method stub
		return dao.getAll();
	}

	@Override
	public List<Site> getSiteByName(String nom, String adresse) {
		// TODO Auto-generated method stub
		return dao.getSiteByName(nom, adresse);
	}

	@Override
	public List<Object[]> getSallesBySite(Long idSite) {
		// TODO Auto-generated method stub
		return dao.getSallesBySite(idSite);
	}

}
