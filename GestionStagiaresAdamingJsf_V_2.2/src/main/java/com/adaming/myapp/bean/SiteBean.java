package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Embedded;
import javax.print.attribute.standard.Severity;

import org.geonames.Toponym;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.entities.Adresse;
import com.adaming.myapp.entities.Salle;
import com.adaming.myapp.entities.Site;
import com.adaming.myapp.exception.VerificationInDataBaseException;
import com.adaming.myapp.site.service.ISiteService;
import com.adaming.myapp.tools.Utilitaire;

@SuppressWarnings("serial")
@Component("siteBean")
@Scope("session")
public class SiteBean implements Serializable {

	@Inject
	private ISiteService serviceSite;
	@Inject
	private FormateurBean formateurBean;

	private Adresse adresseObject;
	private Site site;
	private List<Site> sites;
	private List<Object[]> salles;
	private MapModel geoModel;
	private String centerGeoMap = "41.850033, -87.6500523";
	private List<Toponym> villes;

	@NotEmpty
	@NotBlank
	private String nomSite;
	@NotEmpty
	@NotBlank
	private String adresse;
	@NotEmpty
	private String ville;
	@NotEmpty
	private String codePostal;
	@NotEmpty
	@NotBlank
	private String pays;
	
	

	public void addSite() {
		adresseObject = new Adresse(adresse, ville, codePostal, pays);
		site = FactoryBean.getSiteFactory().create("Site");
		site.setAdresse(adresseObject);
		site.setNomSite(nomSite);
		try {
			serviceSite.add(site);
			Utilitaire.displayMessageInfo(
							"le Site " + nomSite
									+ " � bien �t� ajouter avec succ�s");
			reset();
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
		}
	}

	public void reset() {
		nomSite = "";
		adresse = "";
		codePostal = "";
		ville = "";
		pays = "";
		villes = null;
	}

	public String resetAndRedirect() {
		reset();
		return "site?faces-redirect=true";
	}

	public String getAll() {
		sites = serviceSite.getAll();
		return "liste_sites?faces-redirect=true";
	}
	
	public String getAllSallesBySite(Long idSite){
		try {
			salles = serviceSite.getSallesBySite(idSite);
		} catch (VerificationInDataBaseException e) {
			Utilitaire.displayMessageWarning(e.getMessage());
			return null;
		}
		return "salles-by-sites?faces-redirect=true";
	}
	

	public String getSiteById(Long idSite) {
		site = serviceSite.getOne(idSite);
		return "informationSite?faces-redirect=true";
	}

	@PostConstruct
	public void init() {
		geoModel = new DefaultMapModel();
	}
    /*@method pour recup�rer la localisation de l'adresse*/
	public void onGeocode(GeocodeEvent event) {
		List<GeocodeResult> results = event.getResults();

		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			centerGeoMap = center.getLat() + "," + center.getLng();

			for (int i = 0; i < results.size(); i++) {
				GeocodeResult result = results.get(i);
				geoModel.addOverlay(new Marker(result.getLatLng(), result
						.getAddress()));
			}
		}
	}
	
	public List<Toponym> getVillesByCp(String codePostal){
		villes = new ArrayList<Toponym>();
		villes = formateurBean.getVillesByCp(codePostal);
		return villes;
	}

	/**
	 * @return the nomSite
	 */
	public String getNomSite() {
		return nomSite;
	}

	/**
	 * @param nomSite
	 *            the nomSite to set
	 */
	public void setNomSite(String nomSite) {
		this.nomSite = nomSite;
	}

	/**
	 * @return the adresseObject
	 */
	public Adresse getAdresseObject() {
		return adresseObject;
	}

	/**
	 * @param adresseObject
	 *            the adresseObject to set
	 */
	public void setAdresseObject(Adresse adresseObject) {
		this.adresseObject = adresseObject;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse
	 *            the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville
	 *            the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the codePostale
	 */
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostale
	 *            the codePostale to set
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the pays
	 */
	public String getPays() {
		return pays;
	}

	/**
	 * @param pays
	 *            the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site
	 *            the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the sites
	 */
	public List<Site> getSites() {
		return sites;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	
	

	

	

	/**
	 * @return the salles
	 */
	public List<Object[]> getSalles() {
		return salles;
	}

	/**
	 * @param salles the salles to set
	 */
	public void setSalles(List<Object[]> salles) {
		this.salles = salles;
	}

	/**
	 * @return the geoModel
	 */
	public MapModel getGeoModel() {
		return geoModel;
	}

	/**
	 * @param geoModel
	 *            the geoModel to set
	 */
	public void setGeoModel(MapModel geoModel) {
		this.geoModel = geoModel;
	}

	/**
	 * @return the centerGeoMap
	 */
	public String getCenterGeoMap() {
		return centerGeoMap;
	}

	/**
	 * @param centerGeoMap
	 *            the centerGeoMap to set
	 */
	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}

	public List<Toponym> getVilles() {
		return villes;
	}

	public void setVilles(List<Toponym> villes) {
		this.villes = villes;
	}

	public FormateurBean getFormateurBean() {
		return formateurBean;
	}

	public void setFormateurBean(FormateurBean formateurBean) {
		this.formateurBean = formateurBean;
	}

	
	

}
